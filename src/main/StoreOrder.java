package main;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.gson.JsonObject;

public class StoreOrder {
    
     private long id;
     private long numberOfUnits;
     private String storeName;
     private double amount;
     private Date orderDate;
     private String country;
     
     public StoreOrder () {
     }
     
     public StoreOrder (String country, Date orderDate, String storeName, String productName, long numberOfUnits, double amount) {
         this.orderDate = orderDate;
         this.numberOfUnits = numberOfUnits;
         this.amount = amount;
         this.storeName = storeName;
         this.productName = productName;
         this.country = country;
     }
     
     public long getNumberOfUnits() {
        return numberOfUnits;
    }

    public void setNumberOfUnits(long numberOfUnits) {
        this.numberOfUnits = numberOfUnits;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }
    
    public JsonObject toJson () {
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-YYYY");
        JsonObject order = new JsonObject ();
        order.addProperty("storeName", this.storeName);
        order.addProperty("amount", this.amount);
        order.addProperty("id", this.id);
        order.addProperty("numberOfUnits", this.numberOfUnits);
        order.addProperty("date", sdf.format(this.orderDate));
        return order;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

     public long getId() {
         return this.id;
     }
     
     public void setId(long id) {
         this.id = id;
     }
     
     public String getCountry() {
         return country;
     }

     public void setCountry(String country) {
         this.country = country;
     }

     public String getProductName() {
         return productName;
     }

     public void setProductName(String productName) {
         this.productName = productName;
     }

     private String productName;
}
