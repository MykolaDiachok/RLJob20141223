package com.radioline.master.basic;

import org.ksoap2.serialization.SoapObject;

/**
 * Created by master on 06.11.2014.
 */
public class Item {

    private String Id;
    private String GroupId;
    private String BarCode;
    private String Code;
    private String Article;
    private String PartNumber;
    private String Name;
    private String Description;
    private String Type;
    private String Brand;
    private String Model;
    private String ModelCharacteristic;
    private String Coloration;
    private String Site;
    private String OurWebSite;
    private String Warranty;
    private String Group;
    private String FullNameGroup;
    private float Price;
    private float PriceUAH;
    private float PriceRRP;
    private float PriceRRPUAH;
    private int Availability;
    private String Store;
    private Boolean full;


    public Item(String id, String groupId, String barCode, String code, String article, String partNumber, String name, String description, String type, String brand, String model, String modelCharacteristic, String coloration, String site, String ourWebSite, String warranty, String group, String fullNameGroup, float price, float priceUAH, float priceRRP, float priceRRPUAH, int availability, String store, Boolean full) {
        Id = id;
        GroupId = groupId;
        BarCode = barCode;
        Code = code;
        Article = article;
        PartNumber = partNumber;
        Name = name;
        Description = description;
        Type = type;
        Brand = brand;
        Model = model;
        ModelCharacteristic = modelCharacteristic;
        Coloration = coloration;
        Site = site;
        OurWebSite = ourWebSite;
        Warranty = warranty;
        Group = group;
        FullNameGroup = fullNameGroup;
        Price = price;
        PriceUAH = priceUAH;
        PriceRRP = priceRRP;
        PriceRRPUAH = priceRRPUAH;
        Availability = availability;
        Store = store;
        this.full = full;
    }

    public Item(SoapObject item) {
        this.Id = item.getProperty("Id").toString();
        this.GroupId = item.getProperty("GroupId").toString();
        if (item.hasProperty("BarCode")) {
            this.BarCode = item.getProperty("BarCode").toString();
        }
        if (item.hasProperty("Code")) {
            this.Code = item.getProperty("Code").toString();
        }
        if (item.hasProperty("Article")) {
            this.Article = item.getProperty("Article").toString();
        }
        if (item.hasProperty("PartNumber")) {
            if (item.getProperty("PartNumber").toString()!="anyType{}"){
                this.PartNumber = item.getProperty("PartNumber").toString();
            }
            else {
                this.PartNumber = "";
            };
        }
        this.Name = item.getProperty("Name").toString();
        if (item.hasProperty("Description")) {
            this.Description = item.getProperty("Description").toString();
        }
        if (item.hasProperty("Type")) {
            this.Type = item.getProperty("Type").toString();
        }
        if (item.hasProperty("Brand")) {
            this.Brand = item.getProperty("Brand").toString();
        }
        if (item.hasProperty("Model")) {
            this.Model = item.getProperty("Model").toString();
        }
        if (item.hasProperty("ModelCharacteristic")) {
            this.ModelCharacteristic = item.getProperty("ModelCharacteristic").toString();
        }
        if (item.hasProperty("Coloration")) {
            this.Coloration = item.getProperty("Coloration").toString();
        }
        if (item.hasProperty("Site")) {
            this.Site = item.getProperty("Site").toString();
        }
        if (item.hasProperty("OurWebSite")) {
            this.OurWebSite = item.getProperty("OurWebSite").toString();
        }
        if (item.hasProperty("Warranty")) {
            this.Warranty = item.getProperty("Warranty").toString();
        }
        if (item.hasProperty("Group")) {
            this.Group = item.getProperty("Group").toString();
        }
        if (item.hasProperty("FullNameGroup")) {
            this.FullNameGroup = item.getProperty("FullNameGroup").toString();
        }
        if (item.hasProperty("Price")) {
            this.Price = Float.valueOf(item.getProperty("Price").toString());
        }
        if (item.hasProperty("PriceUAH")) {
            this.PriceUAH = Float.valueOf(item.getProperty("PriceUAH").toString());
        }
        if (item.hasProperty("PriceRRP")) {
            this.PriceRRP = Float.valueOf(item.getProperty("PriceRRP").toString());
        }
        if (item.hasProperty("PriceRRPUAH")) {
            this.PriceRRPUAH = Float.valueOf(item.getProperty("PriceRRPUAH").toString());
        }
        if (item.hasProperty("Availability")) {
            this.Availability = Integer.valueOf(item.getProperty("Availability").toString());
        }
        if (item.hasProperty("Store")) {
            this.Store = item.getProperty("Store").toString();
        }
        this.full = false;
    }


    public Item(SoapObject item,Boolean full) {
        this.Id = item.getProperty("Id").toString();
        this.GroupId = item.getProperty("GroupId").toString();
        if (item.hasProperty("BarCode")) {
            this.BarCode = item.getProperty("BarCode").toString();
        }
        if (item.hasProperty("Code")) {
            this.Code = item.getProperty("Code").toString();
        }
        if (item.hasProperty("Article")) {
            this.Article = item.getProperty("Article").toString();
        }
        if (item.hasProperty("PartNumber")) {
            if (item.getProperty("PartNumber").toString()!="anyType{}"){
                this.PartNumber = item.getProperty("PartNumber").toString();
            }
            else {
                this.PartNumber = "";
            };
        }
        this.Name = item.getProperty("Name").toString();
        if (item.hasProperty("Description")) {
            this.Description = item.getProperty("Description").toString();
        }
        if (item.hasProperty("Type")) {
            this.Type = item.getProperty("Type").toString();
        }
        if (item.hasProperty("Brand")) {
            this.Brand = item.getProperty("Brand").toString();
        }
        if (item.hasProperty("Model")) {
            this.Model = item.getProperty("Model").toString();
        }
        if (item.hasProperty("ModelCharacteristic")) {
            this.ModelCharacteristic = item.getProperty("ModelCharacteristic").toString();
        }
        if (item.hasProperty("Coloration")) {
            this.Coloration = item.getProperty("Coloration").toString();
        }
        if (item.hasProperty("Site")) {
            this.Site = item.getProperty("Site").toString();
        }
        if (item.hasProperty("OurWebSite")) {
            this.OurWebSite = item.getProperty("OurWebSite").toString();
        }
        if (item.hasProperty("Warranty")) {
            this.Warranty = item.getProperty("Warranty").toString();
        }
        if (item.hasProperty("Group")) {
            this.Group = item.getProperty("Group").toString();
        }
        if (item.hasProperty("FullNameGroup")) {
            this.FullNameGroup = item.getProperty("FullNameGroup").toString();
        }
        if (item.hasProperty("Price")) {
            this.Price = Float.valueOf(item.getProperty("Price").toString());
        }
        if (item.hasProperty("PriceUAH")) {
            this.PriceUAH = Float.valueOf(item.getProperty("PriceUAH").toString());
        }
        if (item.hasProperty("PriceRRP")) {
            this.PriceRRP = Float.valueOf(item.getProperty("PriceRRP").toString());
        }
        if (item.hasProperty("PriceRRPUAH")) {
            this.PriceRRPUAH = Float.valueOf(item.getProperty("PriceRRPUAH").toString());
        }
        if (item.hasProperty("Availability")) {
            this.Availability = Integer.valueOf(item.getProperty("Availability").toString());
        }
        if (item.hasProperty("Store")) {
            this.Store = item.getProperty("Store").toString();
        }
        this.full = full;
    }



    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Item{");
        sb.append("Id='").append(Id).append('\'');
        sb.append(", GroupId='").append(GroupId).append('\'');
        sb.append(", Name='").append(Name).append('\'');
        sb.append(", Model='").append(Model).append('\'');
        sb.append('}');
        return sb.toString();
    }


    public Boolean getFull() {
        return full;
    }

    public void setFull(Boolean full) {
        this.full = full;
    }

    public float getPrice() {
        return Price;
    }

    public void setPrice(float price) {
        Price = price;
    }

    public float getPriceUAH() {
        return PriceUAH;
    }

    public void setPriceUAH(float priceUAH) {
        PriceUAH = priceUAH;
    }

    public float getPriceRRP() {
        return PriceRRP;
    }

    public void setPriceRRP(float priceRRP) {
        PriceRRP = priceRRP;
    }

    public float getPriceRRPUAH() {
        return PriceRRPUAH;
    }

    public void setPriceRRPUAH(float priceRRPUAH) {
        PriceRRPUAH = priceRRPUAH;
    }

    public int getAvailability() {
        return Availability;
    }

    public void setAvailability(int availability) {
        Availability = availability;
    }

    public String getStore() {
        return Store;
    }

    public void setStore(String store) {
        Store = store;
    }


    public String getBarCode() {
        return BarCode;
    }

    public void setBarCode(String barCode) {
        BarCode = barCode;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getGroupId() {
        return GroupId;
    }

    public void setGroupId(String groupId) {
        GroupId = groupId;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getArticle() {
        return Article;
    }

    public void setArticle(String article) {
        Article = article;
    }

    public String getPartNumber() {
        if (PartNumber=="anyType{}"){
            PartNumber="";
        }
        return PartNumber;
    }

    public void setPartNumber(String partNumber) {
        PartNumber = partNumber;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getBrand() {
        return Brand;
    }

    public void setBrand(String brand) {
        Brand = brand;
    }

    public String getModel() {
        return Model;
    }

    public void setModel(String model) {
        Model = model;
    }

    public String getModelCharacteristic() {
        return ModelCharacteristic;
    }

    public void setModelCharacteristic(String modelCharacteristic) {
        ModelCharacteristic = modelCharacteristic;
    }

    public String getColoration() {
        return Coloration;
    }

    public void setColoration(String coloration) {
        Coloration = coloration;
    }

    public String getSite() {
        return Site;
    }

    public void setSite(String site) {
        Site = site;
    }

    public String getOurWebSite() {
        return OurWebSite;
    }

    public void setOurWebSite(String ourWebSite) {
        OurWebSite = ourWebSite;
    }

    public String getWarranty() {
        return Warranty;
    }

    public void setWarranty(String warranty) {
        Warranty = warranty;
    }

    public String getGroup() {
        return Group;
    }

    public void setGroup(String group) {
        Group = group;
    }

    public String getFullNameGroup() {
        return FullNameGroup;
    }

    public void setFullNameGroup(String fullNameGroup) {
        FullNameGroup = fullNameGroup;
    }


}
