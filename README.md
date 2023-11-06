# java-proxy
All documentation was taken from [Oracle](https://docs.oracle.com/javase/8/docs/technotes/guides/reflection/proxy.html)
===============

<p>A dynamic proxy class is a class that implements a list of interfaces specified at runtime such that a method invocation through one of the interfaces on an instance of the class will be encoded and dispatched to another object through a uniform interface. Method invocations on an instance of a dynamic proxy class are dispatched to a single method in the instance's invocation handler, and they are encoded with a java.lang.reflect.Method object identifying the method that was invoked and an array of type Object containing the arguments.<p>

A **dynamic proxy class** (simply referred to as a proxy class below) is a class that implements a list of interfaces specified at runtime when the class is created.

A **proxy interface** is such an interface that is implemented by a proxy class.

A **proxy instance** is an instance of a proxy class.

## Proxy

Proxy classes, as well as instances of them, are created using the static methods of the class java.lang.reflect.Proxy.

The Proxy.newProxyInstance method returns the java.lang.Class object for a proxy class given a class loader and an array of interfaces and InvocationHandler. The proxy class will be defined in the specified class loader and will implement all of the supplied interfaces. 
