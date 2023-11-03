package org.virtushawk.service;

import org.virtushawk.core.annotation.Component;
import org.virtushawk.core.annotation.LogMethodName;

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
