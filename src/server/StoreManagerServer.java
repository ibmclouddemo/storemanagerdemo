package server;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import main.StoreExpense;
import main.StoreOrder;
import main.StoreUtil;



public class StoreManagerServer {
    
    static private StoreManagerServer instance;

     
    //----------------------------------------------------------------------------------------------
    /**
     * Start the server instance.
     */
    static public void initialize(ServletContext context) {
        System.out.println("Initialized");
        instance = new StoreManagerServer();
    }
    
    public static SessionFactory getHibernateFactory() {
        Configuration conf = new Configuration();
        conf.configure("hibernate.cfg.xml");
        
        String url = System.getenv("DATABASE_CONNECTION_URL");
        String password = System.getenv("DATABASE_CONNECTION_PASSWORD");
        String user = System.getenv("DATABASE_CONNECTION_USERNAME");
        
        if (url != null && password != null && user != null) {
            System.out.println("Database environment variables are detected:"+url+" with user "+user+" and password "+password);
            // <!-- Database connection settings -->
            conf.setProperty("hibernate.connection.url", url);
            conf.setProperty("hibernate.connection.username", user);
            conf.setProperty("hibernate.connection.password", password);
            }
        
        return conf.buildSessionFactory();
    }
    
    public static void createOrder() {

        SessionFactory factory = getHibernateFactory();
        Session session = factory.openSession();
        StoreOrder order = new StoreOrder(StoreUtil.Countries.FRANCE.toString(),new Date(System.currentTimeMillis()), "New York", StoreUtil.ProductNames.CESSNA152.toString(), 10, 200);
 
        Transaction tx = session.beginTransaction();
        session.save(order);
        System.out.println("Order saved successfully.....!!");
        tx.commit();
        session.close();
        factory.close();
    }
    
    public static void createExpense() {
        
        SessionFactory factory = getHibernateFactory();
        Session session = factory.openSession();
        StoreExpense expense = new StoreExpense(new Date(System.currentTimeMillis()), "New York", 10);
 
        Transaction tx = session.beginTransaction();
        session.save(expense);
        System.out.println("Expense saved successfully.....!!");
        tx.commit();
        session.close();
        factory.close();
    }
    
    public List<StoreOrder> getAllOrders() {
        List<StoreOrder> list = new ArrayList();

        SessionFactory factory = getHibernateFactory();
        Session session = factory.openSession();
 
        Transaction tx = session.beginTransaction();
        list = session.createCriteria(StoreOrder.class).list();
        System.out.println("Objects retrieved successfully.....!!");
        tx.commit();
        session.close();
        factory.close();
        
        return list;
    }
    
    public List<StoreExpense> getAllExpenses() {
        List<StoreExpense> list = new ArrayList();

        SessionFactory factory = getHibernateFactory();
        Session session = factory.openSession();
 
        Transaction tx = session.beginTransaction();
        list = session.createCriteria(StoreExpense.class).list();
        System.out.println("Objects retrieved successfully.....!!");
        tx.commit();
        session.close();
        factory.close();
        
        return list;
    }
    
    //----------------------------------------------------------------------------------------------
    /**
     * @return the instance of this singleton
     */
    static public StoreManagerServer getInstance() {
        if (instance == null) {
            throw new RuntimeException("Server not yet initialized");
        }

        return instance;
    }
    
    //----------------------------------------------------------------------------------------------
    /**
     * Stop the server. Responsible for stopping/cleaning up any other services.
     */
    public void stop() {
        
    }
}
