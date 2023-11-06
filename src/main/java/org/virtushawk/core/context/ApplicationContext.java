package org.virtushawk.core.context;

import org.reflections.Reflections;
import org.virtushawk.core.annotation.Component;
import org.virtushawk.core.annotation.LogMethodName;
import org.virtushawk.core.proxy.handler.DynamicProxyHandler;

import java.lang.reflect.Constructor;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Simple application context to achieve dynamic proxy injection
 */
public class ApplicationContext {

    private final Set<Class<?>> componentBeans;

    /**
     * Instantiates a new Application context and finds classes that should be considered as beans using provided class
     * package
     *
     * @param applicationClass the application class
     */
    public ApplicationContext(Class<?> applicationClass) {
        Reflections reflections = new Reflections(applicationClass.getPackage().getName());
        this.componentBeans = reflections.getTypesAnnotatedWith(Component.class).stream()
                .filter(clazz -> !clazz.isInterface())
                .collect(Collectors.toSet());
    }

    /**
     * Gets bean using provided Interface. Argument must be an interface or {@link IllegalArgumentException} will be thrown
     *
     * @param clazz the interface
     * @return the bean
     */
    public <T> T getBean(Class<T> clazz) {
        if (!clazz.isInterface()) {
            throw new IllegalArgumentException("Class " + clazz.getName() + " should be an interface");
        }

        final Class<T> implementation = findImplementationByInterface(clazz);
        return createBean(clazz, implementation);
    }

    @SuppressWarnings("unchecked")
    private <T> Class<T> findImplementationByInterface(Class<T> interfaceItem) {
        final Set<Class<?>> classesWithInterfaces = componentBeans.stream()
                .filter(componentBean -> List.of(componentBean.getInterfaces()).contains(interfaceItem))
                .collect(Collectors.toSet());

        if (classesWithInterfaces.size() > 1) {
            throw new IllegalArgumentException("There are more than 1 implementation: " + interfaceItem.getName());
        }

        return (Class<T>) classesWithInterfaces.stream()
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("The is no class with interface: " + interfaceItem));
    }

    private <T> T createBean(Class<T> clazz, Class<T> implementation) {
        try {
            final Constructor<T> constructor = findConstructor(implementation);
            final Object[] parameters = findConstructorParameters(constructor);
            final T bean = constructor.newInstance(parameters);

            if (Arrays.stream(implementation.getMethods()).noneMatch(method -> method.isAnnotationPresent(LogMethodName.class))) {
                return bean;
            }

            final Object proxy = Proxy.newProxyInstance(
                    ApplicationContext.class.getClassLoader(),
                    new Class[]{clazz},
                    new DynamicProxyHandler(bean));

            return clazz.cast(proxy);
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    @SuppressWarnings("unchecked")
    private <T> Constructor<T> findConstructor(Class<T> clazz) {
        final Constructor<T>[] constructors = (Constructor<T>[]) clazz.getConstructors();
        return constructors[0];
    }

    private <T> Object[] findConstructorParameters(Constructor<T> constructor) {
        final Class<?>[] parameterTypes = constructor.getParameterTypes();
        return Arrays.stream(parameterTypes)
                .map(this::getBean)
                .toArray(Object[]::new);
    }
}
