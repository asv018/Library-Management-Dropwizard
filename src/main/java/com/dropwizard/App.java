package com.dropwizard;

import org.eclipse.jetty.servlets.CrossOriginFilter;
import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import java.util.EnumSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.dropwizard.Application;
import io.dropwizard.Configuration;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

// Import the resource classes
import com.librarymanagement.resources.AuthorResource;
import com.librarymanagement.resources.BookResource;

// Import the business classes
import com.librarymanagement.business.AuthorBusiness;
import com.librarymanagement.business.BookBusiness;

// Import the DAO classes
import com.librarymanagement.dao.AuthorDAO;
import com.librarymanagement.dao.BookDAO;

public class App extends Application<Configuration> {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);
    
    @Override
    public void initialize(Bootstrap<Configuration> bootstrap) {
        // Any initialization logic can go here
        LOGGER.info("Initializing Library Management Application");
    }
    
    @Override
    public void run(Configuration configuration, Environment environment) throws Exception {
        LOGGER.info("Starting Library Management Application");
        
        // Configure CORS filter to allow requests from UI
        configureCors(environment);
        
        // Initialize DAOs
        AuthorDAO authorDAO = new AuthorDAO();
        BookDAO bookDAO = new BookDAO();
        
        // Initialize Business layer with DAOs
        AuthorBusiness authorBusiness = new AuthorBusiness(authorDAO);
        BookBusiness bookBusiness = new BookBusiness(bookDAO);
        
        // Register REST Resources
        registerResources(environment, authorBusiness, bookBusiness);
        
        LOGGER.info("Library Management Application started successfully!");
    }
    
    private void configureCors(Environment environment) {
        LOGGER.info("Configuring CORS filter");
        
        final FilterRegistration.Dynamic cors = 
            environment.servlets().addFilter("CORS", CrossOriginFilter.class);
        
        // Configure CORS parameters
        cors.setInitParameter(CrossOriginFilter.ALLOWED_ORIGINS_PARAM, "*");
        cors.setInitParameter(CrossOriginFilter.ALLOWED_HEADERS_PARAM, 
            "X-Requested-With,Content-Type,Accept,Origin,Authorization");
        cors.setInitParameter(CrossOriginFilter.ALLOWED_METHODS_PARAM, 
            "OPTIONS,GET,PUT,POST,DELETE,HEAD");
        cors.setInitParameter(CrossOriginFilter.ALLOW_CREDENTIALS_PARAM, "true");
        
        // Add URL mapping
        cors.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
    }
    
    private void registerResources(Environment environment, 
                                  AuthorBusiness authorBusiness, 
                                  BookBusiness bookBusiness) {
        LOGGER.info("Registering REST resources");
        
        // Register Author Resource
        environment.jersey().register(new AuthorResource(authorBusiness));
        LOGGER.info("Registered AuthorResource at /authors");
        
        // Register Book Resource
        environment.jersey().register(new BookResource(bookBusiness));
        LOGGER.info("Registered BookResource at /books");
        
        // You can add more resources here as your application grows
        // For example: GenreResource, PublisherResource, etc.
    }
    
    public static void main(String[] args) throws Exception {
        new App().run(args);
    }
}

