package com.radioline.master.soapconnector;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.radioline.master.basic.BaseValues;
import com.radioline.master.basic.Group;
import com.radioline.master.basic.Item;

import org.kobjects.base64.Base64;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.ExecutionException;

import hugo.weaving.DebugLog;

import static java.util.Collections.sort;

/**
 * Created by master on 01.11.2014.
 */
public class Converts {
    final String NULL1C = "00000000-0000-0000-0000-000000000000";

    LinkAsyncTaskGetSoapObject linkAsync;
    LinkAsyncTaskGetSoapPrimitive linkAsyncTaskGetSoapPrimitive;

    @DebugLog
    public Group[] getGroupsFromServer() throws ExecutionException, InterruptedException {
        final String method_name = "GetAllGroups";


        // SoapObject tSoap = getFromServerSoapObject(method_name,soap_action);
        linkAsync = new LinkAsyncTaskGetSoapObject(method_name);
        //linkAsync.execute();

        SoapObject tSoap = linkAsync.execute().get();
        SoapObject itemsGroups = (SoapObject) tSoap.getProperty("ItemsGroups");

        int groupsCount = itemsGroups.getPropertyCount();
        Group[] Groups = new Group[groupsCount];
        for (int curCount = 0; curCount < groupsCount; curCount++) {
            SoapObject item = (SoapObject) itemsGroups.getProperty(curCount);
            Groups[curCount] = new Group(item);
        }
        return Groups;
    }

    @DebugLog
    public ArrayList<Group> getGroupsArrayListFromServer() throws ExecutionException, InterruptedException {
        final String method_name = "GetGroups";


        // SoapObject tSoap = getFromServerSoapObject(method_name,soap_action);
        linkAsync = new LinkAsyncTaskGetSoapObject(method_name);
        //linkAsync.execute();
        PropertyInfo pi = new PropertyInfo();
        pi.setName("GroupId");
        pi.setValue(NULL1C);
        //pi.setValue("5374706f-daf0-11e1-937d-00155d040a09");
        //pi.setType("5374706f-daf0-11e1-937d-00155d040a09".getClass());
        pi.setType(String.class);
        SoapObject tSoap = linkAsync.execute(pi).get();
        if (tSoap == null) {
            return null;
        }
        SoapObject itemsGroups = (SoapObject) tSoap.getProperty("ItemsGroups");

        int groupsCount = itemsGroups.getPropertyCount();
        ArrayList<Group> Groups = new ArrayList<Group>();
        for (int curCount = 0; curCount < groupsCount; curCount++) {
            SoapObject item = (SoapObject) itemsGroups.getProperty(curCount);
            Groups.add(new Group(item));
        }

        sort(Groups, new Comparator<Group>() {
            public int compare(Group p1, Group p2) {
                return p1.getSortcode().compareToIgnoreCase(
                        p2.getSortcode());
            }
        });
        return Groups;
    }

    @DebugLog
    public ArrayList<Group> getGroupsArrayListFromServerWithoutAsync() throws ExecutionException, InterruptedException {
        final String method_name = "GetGroups";


        // SoapObject tSoap = getFromServerSoapObject(method_name,soap_action);
        //linkAsync = new LinkAsyncTaskGetSoapObject(method_name);
        Link link = new Link();
        //linkAsync.execute();
        PropertyInfo pi = new PropertyInfo();
        pi.setName("GroupId");
        pi.setValue(NULL1C);
        //pi.setValue("5374706f-daf0-11e1-937d-00155d040a09");
        //pi.setType("5374706f-daf0-11e1-937d-00155d040a09".getClass());
        pi.setType(String.class);
        //SoapObject tSoap = linkAsync.execute(pi).get();
        SoapObject tSoap = link.getFromServerSoapObject(method_name, new PropertyInfo[]{pi});
        if (tSoap == null) {
            return null;
        }
        SoapObject itemsGroups = (SoapObject) tSoap.getProperty("ItemsGroups");

        int groupsCount = itemsGroups.getPropertyCount();
        ArrayList<Group> Groups = new ArrayList<Group>();
        for (int curCount = 0; curCount < groupsCount; curCount++) {
            SoapObject item = (SoapObject) itemsGroups.getProperty(curCount);
            Groups.add(new Group(item));
        }

        sort(Groups, new Comparator<Group>() {
            public int compare(Group p1, Group p2) {
                return p1.getSortcode().compareToIgnoreCase(
                        p2.getSortcode());
            }
        });
        return Groups;
    }

    @DebugLog
    public ArrayList<Group> getGroupsArrayListFromServer(String IdGroup) throws ExecutionException, InterruptedException {
        final String method_name = "GetGroups";


        // SoapObject tSoap = getFromServerSoapObject(method_name,soap_action);
        linkAsync = new LinkAsyncTaskGetSoapObject(method_name);
        //linkAsync.execute();
        PropertyInfo pi = new PropertyInfo();
        pi.setName("GroupId");
        pi.setValue(IdGroup);
        //pi.setValue("5374706f-daf0-11e1-937d-00155d040a09");
        //pi.setType("5374706f-daf0-11e1-937d-00155d040a09".getClass());
        pi.setType(String.class);
        SoapObject tSoap = linkAsync.execute(pi).get();
        try {
            SoapObject itemsGroups = (SoapObject) tSoap.getProperty("ItemsGroups");

            int groupsCount = itemsGroups.getPropertyCount();
            ArrayList<Group> Groups = new ArrayList<Group>();
            for (int curCount = 0; curCount < groupsCount; curCount++) {
                SoapObject item = (SoapObject) itemsGroups.getProperty(curCount);
                Groups.add(new Group(item));
            }

            sort(Groups, new Comparator<Group>() {
                public int compare(Group p1, Group p2) {
                    return p1.getSortcode().compareToIgnoreCase(
                            p2.getSortcode());
                }
            });
            return Groups;
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return null;
    }

    @DebugLog
    public ArrayList<Group> getGroupsArrayListFromServerWithoutAsync(String IdGroup) throws ExecutionException, InterruptedException {
        final String method_name = "GetGroups";


        // SoapObject tSoap = getFromServerSoapObject(method_name,soap_action);
//        linkAsync = new LinkAsyncTaskGetSoapObject(method_name);
        Link link = new Link();
        //linkAsync.execute();
        PropertyInfo pi = new PropertyInfo();
        pi.setName("GroupId");
        pi.setValue(IdGroup);
        //pi.setValue("5374706f-daf0-11e1-937d-00155d040a09");
        //pi.setType("5374706f-daf0-11e1-937d-00155d040a09".getClass());
        pi.setType(String.class);
        ///SoapObject tSoap = linkAsync.execute(pi).get();
        SoapObject tSoap = link.getFromServerSoapObject(method_name, new PropertyInfo[]{pi});
        SoapObject itemsGroups = (SoapObject) tSoap.getProperty("ItemsGroups");

        int groupsCount = itemsGroups.getPropertyCount();
        ArrayList<Group> Groups = new ArrayList<Group>();
        for (int curCount = 0; curCount < groupsCount; curCount++) {
            SoapObject item = (SoapObject) itemsGroups.getProperty(curCount);
            Groups.add(new Group(item));
        }

        sort(Groups, new Comparator<Group>() {
            public int compare(Group p1, Group p2) {
                return p1.getSortcode().compareToIgnoreCase(
                        p2.getSortcode());
            }
        });
        return Groups;
    }

    @DebugLog
    public ArrayList<Item> getItemsArrayListFromServer(String IdGroup) throws ExecutionException, InterruptedException {
        return getItemsArrayListFromServer(IdGroup, false);
    }

    @DebugLog
    public ArrayList<Item> getItemsArrayListFromServer(String IdGroup, Boolean full) throws ExecutionException, InterruptedException {
        final String method_name = "GetPriceUseParentGroup";


        // SoapObject tSoap = getFromServerSoapObject(method_name,soap_action);
        linkAsync = new LinkAsyncTaskGetSoapObject(method_name);
        //linkAsync.execute();
        PropertyInfo pi0 = new PropertyInfo();
        pi0.setName("PartnerId");
        String partnerId = BaseValues.GetValue("PartnerId");
        if (partnerId == null) {
            return null;
        }
        pi0.setValue(partnerId);
        pi0.setType(String.class);

        PropertyInfo pi1 = new PropertyInfo();
        pi1.setName("GroupId");
        pi1.setValue(IdGroup);
        pi1.setType(String.class);


        PropertyInfo pi2 = new PropertyInfo();
        pi2.setName("Simple");
        pi2.setValue(!full);
        pi2.setType(Boolean.class);

        SoapObject tSoap = linkAsync.execute(pi0, pi1, pi2).get();
        if (tSoap == null)
            return null;
        SoapObject items = (SoapObject) tSoap.getProperty("Prices");
        if (items == null)
            return null;
        //SoapObject items = (SoapObject)prices.getProperty("Item");

        int itemsCount = items.getPropertyCount();
        ArrayList<Item> Items = new ArrayList<Item>();
        for (int curCount = 0; curCount < itemsCount; curCount++) {
            SoapObject item = (SoapObject) items.getProperty(curCount);
            Items.add(new Item(item));
        }

        Collections.sort(Items, new Comparator<Item>() {
            public int compare(Item p1, Item p2) {
                return p1.getName().compareToIgnoreCase(
                        p2.getName());
            }
        });
        return Items;
    }

    @DebugLog
    public ArrayList<Item> getItemsArrayListFromServerWithoutAsync(String IdGroup, Boolean full) throws ExecutionException, InterruptedException {
        final String method_name = "GetPriceUseParentGroup";


        // SoapObject tSoap = getFromServerSoapObject(method_name,soap_action);
        //linkAsync = new LinkAsyncTaskGetSoapObject(method_name);
        Link link = new Link();
        //linkAsync.execute();
        PropertyInfo pi0 = new PropertyInfo();
        pi0.setName("PartnerId");
        pi0.setValue(BaseValues.GetValue("PartnerId"));
        pi0.setType(String.class);

        PropertyInfo pi1 = new PropertyInfo();
        pi1.setName("GroupId");
        pi1.setValue(IdGroup);
        pi1.setType(String.class);


        PropertyInfo pi2 = new PropertyInfo();
        pi2.setName("Simple");
        pi2.setValue(!full);
        pi2.setType(Boolean.class);

        //SoapObject tSoap = linkAsync.execute(pi0,pi1,pi2).get();
        try {
            SoapObject tSoap = link.getFromServerSoapObject(method_name, new PropertyInfo[]{pi0, pi1, pi2});
            if (tSoap == null)
                return null;
            SoapObject items = (SoapObject) tSoap.getProperty("Prices");
            if (items == null)
                return null;
            //SoapObject items = (SoapObject)prices.getProperty("Item");

            int itemsCount = items.getPropertyCount();
            ArrayList<Item> Items = new ArrayList<Item>();

            for (int curCount = 0; curCount < itemsCount; curCount++) {
                SoapObject item = (SoapObject) items.getProperty(curCount);
                Items.add(new Item(item));
            }

            Collections.sort(Items, new Comparator<Item>() {
                public int compare(Item p1, Item p2) {
                    return p1.getName().compareToIgnoreCase(
                            p2.getName());
                }
            });
            return Items;
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return null;
    }


    /*
    Don't use this method, not working SOAP call
    */
    @DebugLog
    public ArrayList<Item> getAllItemsArrayListFromServer(Boolean full) throws ExecutionException, InterruptedException {
        final String method_name = "GetAllItemPrice";


        linkAsync = new LinkAsyncTaskGetSoapObject(method_name);

        PropertyInfo pi0 = new PropertyInfo();
        pi0.setName("PartnerId");
        pi0.setValue(BaseValues.GetValue("PartnerId"));
        pi0.setType(String.class);


        PropertyInfo pi1 = new PropertyInfo();
        pi1.setName("Simple");
        pi1.setValue(!full);
        pi1.setType(Boolean.class);

        SoapObject tSoap = linkAsync.execute(pi0, pi1).get();
        SoapObject items = (SoapObject) tSoap.getProperty("Prices");
        //SoapObject items = (SoapObject)prices.getProperty("Item");

        int itemsCount = items.getPropertyCount();
        ArrayList<Item> Items = new ArrayList<Item>();
        for (int curCount = 0; curCount < itemsCount; curCount++) {
            SoapObject item = (SoapObject) items.getProperty(curCount);
            Items.add(new Item(item));
        }

//        Collections.sort(Items, new Comparator<Item>() {
//            public int compare(Item p1, Item p2) {
//                return p1.getName().compareToIgnoreCase(
//                        p2.getName());
//            }
//        });
        return Items;
    }

    /*
        Don't use this method, not working SOAP call
        */
    @DebugLog
    public ArrayList<Item> getAllItemsArrayListFromServerWithOutAsync(Boolean full) throws ExecutionException, InterruptedException {
        final String method_name = "GetAllItemPrice";


        Link link = new Link();

        PropertyInfo pi0 = new PropertyInfo();
        pi0.setName("PartnerId");
        pi0.setValue(BaseValues.GetValue("PartnerId"));
        pi0.setType(String.class);


        PropertyInfo pi1 = new PropertyInfo();
        pi1.setName("Simple");
        pi1.setValue(!full);
        pi1.setType(Boolean.class);
        SoapObject tSoap;

        tSoap = link.getFromServerSoapObject(method_name, new PropertyInfo[]{pi0, pi1});

        SoapObject items = (SoapObject) tSoap.getProperty("Prices");
        //SoapObject items = (SoapObject)prices.getProperty("Item");

        int itemsCount = items.getPropertyCount();
        ArrayList<Item> Items = new ArrayList<Item>();
        for (int curCount = 0; curCount < itemsCount; curCount++) {
            SoapObject item = (SoapObject) items.getProperty(curCount);
            Items.add(new Item(item));
        }

//        Collections.sort(Items, new Comparator<Item>() {
//            public int compare(Item p1, Item p2) {
//                return p1.getName().compareToIgnoreCase(
//                        p2.getName());
//            }
//        });
        return Items;
    }

    @DebugLog
    public ArrayList<Item> getItemsArrayListFromServerWithBarcode(String barcode, Boolean full) throws ExecutionException, InterruptedException {
        final String method_name = "GetPriceOnBarCode";


        // SoapObject tSoap = getFromServerSoapObject(method_name,soap_action);
        linkAsync = new LinkAsyncTaskGetSoapObject(method_name);
        //linkAsync.execute();
        PropertyInfo pi0 = new PropertyInfo();
        pi0.setName("PartnerId");
        pi0.setValue(BaseValues.GetValue("PartnerId"));
        pi0.setType(String.class);

        PropertyInfo pi1 = new PropertyInfo();
        pi1.setName("barcode");
        pi1.setValue(barcode);
        pi1.setType(String.class);


        PropertyInfo pi2 = new PropertyInfo();
        pi2.setName("SimpleFields");
        pi2.setValue(!full);
        pi2.setType(Boolean.class);

        SoapObject tSoap = linkAsync.execute(pi0, pi1, pi2).get();
        if (tSoap == null) {
            return null;
        }
        SoapObject items = (SoapObject) tSoap.getProperty("Prices");
        //SoapObject items = (SoapObject)prices.getProperty("Item");

        int itemsCount = items.getPropertyCount();
        ArrayList<Item> Items = new ArrayList<Item>();
        for (int curCount = 0; curCount < itemsCount; curCount++) {
            SoapObject item = (SoapObject) items.getProperty(curCount);
            Items.add(new Item(item));
        }

        Collections.sort(Items, new Comparator<Item>() {
            public int compare(Item p1, Item p2) {
                return p1.getName().compareToIgnoreCase(
                        p2.getName());
            }
        });
        return Items;
    }

    @DebugLog
    public ArrayList<Item> getItemsArrayListFromServerSearchByName(String SubString, Boolean full) throws ExecutionException, InterruptedException {
        final String method_name = "GetPriceOnSearchName";


        // SoapObject tSoap = getFromServerSoapObject(method_name,soap_action);
        linkAsync = new LinkAsyncTaskGetSoapObject(method_name);
        //linkAsync.execute();
        PropertyInfo pi0 = new PropertyInfo();
        pi0.setName("PartnerId");
        pi0.setValue(BaseValues.GetValue("PartnerId"));
        pi0.setType(String.class);

        PropertyInfo pi1 = new PropertyInfo();
        pi1.setName("SubString");
        pi1.setValue(SubString);
        pi1.setType(String.class);


        PropertyInfo pi2 = new PropertyInfo();
        pi2.setName("SimpleFields");
        pi2.setValue(!full);
        pi2.setType(Boolean.class);

        SoapObject tSoap = linkAsync.execute(pi0, pi1, pi2).get();
        pi0 = null;
        pi1 = null;
        pi2 = null;

        if (tSoap == null) {
            return null;
        }
        SoapObject items = (SoapObject) tSoap.getProperty("Prices");
        tSoap = null;
        //SoapObject items = (SoapObject)prices.getProperty("Item");

        int itemsCount = items.getPropertyCount();
        ArrayList<Item> arrayItems = new ArrayList<Item>();
        if (arrayItems == null) return null;
        for (int curCount = 0; curCount < itemsCount; curCount++) {
            SoapObject item = (SoapObject) items.getProperty(curCount);
            if (item != null)
                arrayItems.add(new Item(item));
        }
        items = null;
        Collections.sort(arrayItems, new Comparator<Item>() {
            public int compare(Item p1, Item p2) {
                return p1.getName().compareToIgnoreCase(
                        p2.getName());
            }
        });
        return arrayItems;
    }

    @DebugLog
    public Bitmap getBitMapFromServer(String idItem) throws ExecutionException, InterruptedException {
        final String method_name = "GetPNG";


        // SoapObject tSoap = getFromServerSoapObject(method_name,soap_action);
        linkAsyncTaskGetSoapPrimitive = new LinkAsyncTaskGetSoapPrimitive(method_name);
        //linkAsync.execute();
        PropertyInfo pi = new PropertyInfo();
        pi.setName("ItemId");
        pi.setValue(idItem);
        pi.setValue("e3dd8ed4-8fa6-11e2-b51b-00155d060502");
        //pi.setType("5374706f-daf0-11e1-937d-00155d040a09".getClass());
        pi.setType(String.class);
        String base64String = linkAsyncTaskGetSoapPrimitive.execute(pi).get().toString();
        byte[] bytearray = Base64.decode(base64String);

        return BitmapFactory.decodeByteArray(bytearray, 0, bytearray.length);

    }

    @DebugLog
    public Bitmap getBitMapFromServer(String idItem, int Height, int Width, int Quality, Boolean HardCompression) throws ExecutionException, InterruptedException {
        final String method_name = "GetPNGWithSize";


        // SoapObject tSoap = getFromServerSoapObject(method_name,soap_action);
        linkAsyncTaskGetSoapPrimitive = new LinkAsyncTaskGetSoapPrimitive(method_name);
        //linkAsync.execute();
        PropertyInfo piidItem = new PropertyInfo();
        piidItem.setName("ItemId");
        piidItem.setValue(idItem);
        //piidItem.setValue("e3dd8ed4-8fa6-11e2-b51b-00155d060502");
        piidItem.setType(String.class);

        PropertyInfo piHeight = new PropertyInfo();
        piHeight.setName("Height");
        piHeight.setValue(Height);
        piHeight.setType(Integer.class);

        PropertyInfo piWidth = new PropertyInfo();
        piWidth.setName("Width");
        piWidth.setValue(Width);
        piWidth.setType(Integer.class);

        PropertyInfo piQuality = new PropertyInfo();
        piQuality.setName("Quality");
        piQuality.setValue(Quality);
        piQuality.setType(Integer.class);

        PropertyInfo piHardCompression = new PropertyInfo();
        piHardCompression.setName("HardCompression");
        piHardCompression.setValue(HardCompression);
        piHardCompression.setType(Boolean.class);

        SoapPrimitive runSoap = linkAsyncTaskGetSoapPrimitive.execute(piidItem, piHeight, piWidth, piQuality, piHardCompression).get();
        if (runSoap == null) {
            return null;
        }
        String base64String = runSoap.toString();
        byte[] bytearray = Base64.decode(base64String);

        return BitmapFactory.decodeByteArray(bytearray, 0, bytearray.length);

    }


}
