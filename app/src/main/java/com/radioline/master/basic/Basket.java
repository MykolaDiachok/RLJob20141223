package com.radioline.master.basic;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.UUID;

/**
 * Created by master on 13.11.2014.
 */
@ParseClassName("Basket")
public class Basket extends ParseObject {

//    public Basket(){
//        put("objectId", UUID.randomUUID().toString());
//    }
//
//    public String getObjectId() {
//        return getString("objectId");
//
//    }


    public String getProductId() {
        return getString("productId");
    }

    public void setProductId(String productId) {
        put("productId", productId);
    }

    public String getName() {
        return getString("name");
    }

    public void setName(String name) {
        put("name", name);
    }

    public int getQuantity() {
        return getInt("quantity");
    }

    public void setQuantity(int quantity) {
        put("quantity", quantity);
    }

    public double getRequiredpriceUSD() {
        return getDouble("requiredpriceUSD");
    }

    public void setRequiredpriceUSD(double requiredpriceUSD) {
        put("requiredpriceUSD", requiredpriceUSD);
    }

    public double getRequiredpriceUAH() {
        return getDouble("requiredpriceUAH");
    }

    public void setRequiredpriceUAH(float requiredpriceUAH) {
        put("requiredpriceUAH", requiredpriceUAH);
    }

    public static ParseQuery<Basket> getQuery() {
        return ParseQuery.getQuery(Basket.class);
    }

}
