package main;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.gson.JsonObject;

public class StoreExpense {
    
     private long id;
     private String storeName;
     private double amount;
     private Date expenseDate;
     
     public StoreExpense () {
     }
     
     public StoreExpense (Date expenseDate, String storeName, double amount) {
         this.expenseDate = expenseDate;
         this.amount = amount;
         this.storeName = storeName;
     }
     
    public Date getExpenseDate() {
        return expenseDate;
    }

    public void setExpenseDate(Date expenseDate) {
        this.expenseDate = expenseDate;
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
        order.addProperty("date", sdf.format(this.expenseDate));
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
}
