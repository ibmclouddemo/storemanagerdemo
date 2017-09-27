package server;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;


public class ServerListener implements ServletContextListener {

    //**********************************************************************************************
    // CLASS
    //**********************************************************************************************

    //**********************************************************************************************
    // INSTANCE
    //**********************************************************************************************

    //----------------------------------------------------------------------------------------------
    /**
     * {@inheritDoc}
     */
    @Override
    public void contextInitialized(ServletContextEvent event) {
        System.out.println("Initialized1");
        ServletContext context = event.getServletContext();
        StoreManagerServer.initialize(context);
    }

    //----------------------------------------------------------------------------------------------
    /**
     * {@inheritDoc}
     */
    @Override
    public void contextDestroyed(ServletContextEvent event) {
        StoreManagerServer.getInstance().stop();
    }
    

}

