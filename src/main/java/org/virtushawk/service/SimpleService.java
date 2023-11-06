package org.virtushawk.service;

/**
 * Simple service used for proxy demonstration
 */
public interface SimpleService {

    /**
     * Return name of the method
     *
     * @return the logged method
     */
    String getLoggedMethod();

    /**
     * Return name of the method
     *
     * @return the logged method
     */
    String getNotLoggedMethod();
}
