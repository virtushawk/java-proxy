package org.virtushawk;

import org.virtushawk.core.context.ApplicationContext;
import org.virtushawk.service.SimpleService;

public class Main {

    public static void main(String[] args) {
        //Initialize application context
        final ApplicationContext applicationContext = new ApplicationContext(Main.class);

        //Get proxy bean
        final SimpleService simpleService = applicationContext.getBean(SimpleService.class);

        //should log message
        simpleService.getLoggedMethod();
    }
}