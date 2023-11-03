package org.virtushawk;

import org.virtushawk.core.context.ApplicationContext;
import org.virtushawk.service.SimpleService;

public class Main {

    public static void main(String[] args) {
        final ApplicationContext applicationContext = new ApplicationContext(Main.class);
        final SimpleService simpleService = applicationContext.getBean(SimpleService.class);

        simpleService.getLoggedMethod();
    }
}