package rest;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import main.StoreExpense;
import main.StoreOrder;
import main.StoreUtil;
import server.StoreManagerServer;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;

@javax.ws.rs.ApplicationPath("resources")
@Path("/api")
public class ApiRest extends javax.ws.rs.core.Application {

    @Context
    private UriInfo context;

    @GET
    @Produces("application/json")
    @Path ("data/sales_expenses")
    public Response getJson() {
        String connectionURL = System.getenv("DATABASE_CONNECTION_URL");
        if (connectionURL == null || connectionURL.isEmpty()) {
            
            String sample = "{\"data\":\"[['Month', 'Sales', 'Expenses'],['01',0.0,0.0],['02',0.0,940.0],['03',200.0,0.0],['04',0.0,30.0],['05',0.0,0.0],['06',1000.0,70.0],['07',0.0,0.0],['08',200.0,10.0],['09',0.0,250.0],['10',600.0,0.0],['11',0.0,0.0],['12',400.0,0.0]]\"}";
            return Response.ok(sample, MediaType.APPLICATION_JSON).build();
        }
        else {
            JsonObject data = new JsonObject();
            
            List<StoreOrder> orders = StoreManagerServer.getInstance().getAllOrders();
            List<StoreExpense> expenses = StoreManagerServer.getInstance().getAllExpenses();
            data.addProperty("data", getDataForSalesAndExpenses (orders, expenses));        

            return Response.ok(data.toString(), MediaType.APPLICATION_JSON).build();
        }
    }
    
    @GET
    @Produces("application/json")
    @Path ("data/sales_by_country")
    public Response getSalesByCountry() {
        
        String connectionURL = System.getenv("DATABASE_CONNECTION_URL");
        if (connectionURL == null || connectionURL.isEmpty()) {
            String sample = "{\"data\":\"[['Country', 'Total Sale'],['France',1600.0],['USA',400.0],['UK',400.0]]\"}";
            return Response.ok(sample, MediaType.APPLICATION_JSON).build();
        }
        else {
            JsonObject data = new JsonObject();

            List<StoreOrder> orders = StoreManagerServer.getInstance().getAllOrders();
            data.addProperty("data", getDataForSalesByCountry(orders));        

            return Response.ok(data.toString(), MediaType.APPLICATION_JSON).build();
        }
    }
    
    @GET
    @Produces("application/json")
    @Path ("data/sales_by_product")
    public Response getSalesByProduct() {
        
        String connectionURL = System.getenv("DATABASE_CONNECTION_URL");
        if (connectionURL == null || connectionURL.isEmpty()) {
            String sample = "{\"data\":\"[['Product', 'Total Sale'],['Cessna 152',400.0],['Cessna 172',1000.0],['Cessna 182',1000.0],]\"}";
            return Response.ok(sample, MediaType.APPLICATION_JSON).build();
        }
        else {
            JsonObject data = new JsonObject();
    
            List<StoreOrder> orders = StoreManagerServer.getInstance().getAllOrders();
            data.addProperty("data", getDataForSalesByProduct(orders));        
    
            return Response.ok(data.toString(), MediaType.APPLICATION_JSON).build();
        }
    }
    
    //Build data for google chart
    private String getDataForSalesAndExpenses (List<StoreOrder> orders, List<StoreExpense> expenses) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM");
        String data = "[['Month', 'Sales', 'Expenses'],";
        
        for (StoreUtil.Months m: StoreUtil.Months.values()) {
            double totalSale = 0.0;
            double totalExpense = 0.0;
            for (StoreOrder o: orders) {
                if (sdf.format(o.getOrderDate()).equals(m.toString())) {
                    totalSale+=o.getAmount();
                }
            }
            
            for (StoreExpense ex: expenses) {
                if (sdf.format(ex.getExpenseDate()).equals(m.toString())) {
                    totalExpense+=ex.getAmount();
                }
            }
            
            data+="['"+m.toString()+"',"+totalSale+","+totalExpense+"]";
            
            if (!m.equals(StoreUtil.Months.DECEMBER)) {
                data+=",";
            }
        }
        
        data += "]";
        
        return data;
    }
    
    //Build data for google chart
    private String getDataForSalesByCountry (List<StoreOrder> orders) {

        String data = "[['Country', 'Total Sale'],";
        
        for (StoreUtil.Countries m: StoreUtil.Countries.values()) {
            double totalSale = 0.0;

            for (StoreOrder o: orders) {
                if (o.getCountry().equals(m.toString())) {
                    totalSale+=o.getAmount();
                }
            }
            
            data+="['"+m.toString()+"',"+totalSale+"]";
            
            if (!m.equals(StoreUtil.Countries.UK)) {
                data+=",";
            }
        }
        
        data += "]";
        
        return data;
    }
    
    //Build data for google chart
    private String getDataForSalesByProduct (List<StoreOrder> orders) {

        String data = "[['Product', 'Total Sale'],";
        
        for (StoreUtil.ProductNames m: StoreUtil.ProductNames.values()) {
            double totalSale = 0.0;

            for (StoreOrder o: orders) {
                if (o.getProductName().equals(m.toString())) {
                    totalSale+=o.getAmount();
                }
            }
            
            data+="['"+m.toString()+"',"+totalSale+"]";
            
            if (!m.equals(StoreUtil.Countries.UK)) {
                data+=",";
            }
        }
        
        data += "]";
        
        return data;
    }
    
    @GET
    @Produces("application/json")
    @Path ("data/all_orders")
    public Response getOrders() {
        
        List<StoreOrder> orders = StoreManagerServer.getInstance().getAllOrders();
        JsonArray data = new JsonArray();
        
        for (StoreOrder o: orders) {
            data.add(o.toJson());
        }
        
        return Response.ok(data.toString(), MediaType.APPLICATION_JSON).build();
    }
    
    @GET
    @Produces("application/json")
    @Path ("data/all_expenses")
    public Response getExpenses() {
        
        List<StoreExpense> expenses = StoreManagerServer.getInstance().getAllExpenses();
        
        JsonArray data = new JsonArray();
        
        for (StoreExpense ex: expenses) {
            data.add(ex.toJson());
        }
        
        return Response.ok(data.toString(), MediaType.APPLICATION_JSON).build();
    }
    
/*    @GET
    @Produces("application/json")
    @Path ("data/create")
    public Response create() {
        
        StoreManagerServer.getInstance().createOrder();
        StoreManagerServer.getInstance().createExpense();
        return Response.ok().build();
    }*/
    
    @GET
    @Produces("application/json")
    @Path ("version")
    public Response getVersion() {
        
        String versionNumber = System.getenv("VersionNumber");
        
        JsonObject version = new JsonObject();
        if (versionNumber == null) {
            version.addProperty("version", "1.0");
        }
        else {
            version.addProperty("version", versionNumber);
        }

        return Response.ok(version.toString(), MediaType.APPLICATION_JSON).build();
    }
    
    @GET
    @Produces("text/plain")
    @Path ("dbtest")
    public Response getDBTest() {
        
        String connectionURL = System.getenv("DATABASE_CONNECTION_URL");
        
        if (connectionURL == null) {
            connectionURL = "NOT SET";
        }
        else if (connectionURL.isEmpty()) {
            connectionURL = "NOT SET - EMPTY";
        }
        else {
            connectionURL = connectionURL.length()+"";
        }
        return Response.ok(connectionURL, MediaType.TEXT_PLAIN).build();
    }

}
