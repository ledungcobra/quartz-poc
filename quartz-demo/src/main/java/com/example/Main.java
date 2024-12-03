package com.example;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

public class Main {
    public static void main(String[] args) throws Exception {
        final int port = 8084;
        Properties properties = loadConfig();

        SchedulerFactory schedulerFactory = new StdSchedulerFactory(properties);
        Scheduler scheduler = schedulerFactory.getScheduler();
        scheduler.start();

        Server server = new Server(port);
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);

        // Configure Jersey
        ResourceConfig config = new ResourceConfig();
        config.register(new JobResource(scheduler));
        ServletContainer servletContainer = new ServletContainer(config);
        context.addServlet(new ServletHolder(servletContainer), "/*");

        // Start Jetty server
        server.start();
        System.out.println("Server started on port " + port);
        server.join();
    }

    private static Properties loadConfig() throws IOException {
        Properties properties = new Properties();
        try (InputStream input = Main.class.getClassLoader().getResourceAsStream("quartz.properties")) {
            if (input == null) {
                System.out.println("Sorry, unable to find quartz.properties");
                System.exit(1);
            }
            properties.load(input);
        }
        return properties;
    }
}