package org.virtushawk.core.proxy.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.virtushawk.core.annotation.LogMethodName;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Simple dynamic proxy handler that shows the example of the proxy handling.
 * Will react only to methods annotated with the {@link LogMethodName} and log
 */
public class DynamicProxyHandler implements InvocationHandler {

    private static final Logger logger = LoggerFactory.getLogger(DynamicProxyHandler.class);

    private final Object target;

    public DynamicProxyHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (isLogMethodName(method)) {
            logger.info("Invoked method {}", method.getName());
        }

        return method.invoke(target, args);
    }

    private boolean isLogMethodName(Method method) {
        try {
            return target.getClass().getMethod(method.getName(), method.getParameterTypes()).isAnnotationPresent(LogMethodName.class);
        } catch (NoSuchMethodException e) {
            return false;
        }
    }
}
