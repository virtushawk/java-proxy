package org.virtushawk.service;

import org.virtushawk.core.annotation.Component;
import org.virtushawk.core.annotation.LogMethodName;

/**
 * Simple service implementation used to demonstrate how proxy works. One method is annotated with the logMethodName
 * and should be logged using proxy and other should not be logged
 */
@Component
public class SimpleServiceImpl implements SimpleService {

    @Override
    @LogMethodName
    public String getLoggedMethod() {
        return "LoggedMethod";
    }

    @Override
    public String getNotLoggedMethod() {
        return "NotLoggedMethod";
    }
}
