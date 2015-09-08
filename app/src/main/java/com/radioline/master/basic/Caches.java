package com.radioline.master.basic;

import android.util.Log;

import com.parse.DeleteCallback;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;
import com.radioline.master.tools.DateTimeLibHelper;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import hugo.weaving.DebugLog;

/**
 * Created by master on 05.09.2015.
 */
public class Caches {

    public static ParseObject getCaches(String className){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("CacheInfo");
        query.fromLocalDatastore();
        query.whereEqualTo("classCache", className);

        try {
            return query.getFirst();
        }
        catch(Exception e) {
            return null;
        }
    }
    public static void setCaches(String className){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("CacheInfo");
        query.fromLocalDatastore();
        query.whereEqualTo("classCache", className);
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject parseObject, ParseException e) {

                parseObject.put("dateCache", Calendar.getInstance().getTime());
                parseObject.pinInBackground();
            }
        });

        ParseObject cacheInfo = new ParseObject("CacheInfo");
        cacheInfo.put("classCache", className);//"ParseGroups");
        cacheInfo.put("dateCache", Calendar.getInstance().getTime());
        cacheInfo.pinInBackground();
    }

    public static void clearCaches(){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("CacheInfo");
        query.fromLocalDatastore();
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                //ParseObject.deleteAllInBackground(list);
                ParseObject.unpinAllInBackground(list);
            }
        });
    }

    @DebugLog
    public static void cacheGroups(){
       ParseObject.unpinAllInBackground("ParseGroups", new DeleteCallback() {
           @Override
           @DebugLog
           public void done(ParseException e) {
               ParseQuery<Group> query = Group.getQuery();
               query.whereEqualTo("Enable", true);
               query.findInBackground(new FindCallback<Group>() {
                   @Override
                   @DebugLog
                   public void done(List<Group> list, ParseException e) {
                       ParseObject.pinAllInBackground("ParseGroups", list, new SaveCallback() {
                           @Override
                           public void done(ParseException e) {
                               setCaches("ParseGroups");
                           }
                       });
                   }
               });
           }
       });
//        try {
//        ParseObject.unpinAllInBackground("ParseGroups", new DeleteCallback() {
//            @Override
//            public void done(ParseException e) {
//                ParseQuery<Group> query = Group.getQuery();
//                query.whereEqualTo("Enable", true);
//                query.findInBackground(new FindCallback<Group>() {
//                        @Override
//                        public void done(List<Group> list, ParseException e) {
//                            ParseObject.pinAllInBackground("ParseGroups",list, new SaveCallback() {
//                                @Override
//                                public void done(ParseException e) {
//
//                                };
//                            };
//
//                        }
//                };
//            }
//        });

    }

    public static void setCacheGroups(){
        ParseObject infoCache = getCaches("ParseGroups");
        Date lastSave = (Date)infoCache.get("dateCache");

       // long diffmin = DateTimeLibHelper.minDiff(Calendar.getInstance().getTime(),lastSave);
        ParseQuery<ParseObject> query = ParseQuery.getQuery("ParseGroups");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(final List<ParseObject> groupList, ParseException e) {
                if (e != null) {
                    // There was an error or the network wasn't available.
                    return;
                }

                // Release any objects previously pinned for this query.
                ParseObject.unpinAllInBackground("ParseGroups", groupList, new DeleteCallback() {
                    public void done(ParseException e) {
                        if (e != null) {
                            // There was some error.
                            return;
                        }

                        // Add the latest results for this query to the cache.
                        ParseObject.pinAllInBackground("ParseGroups", groupList);
                    }
                });
            }
        });



//        ParseQuery<Group> query = Group.getQuery();
//        // Query for new results from the network.
//        query.findInBackground(new FindCallback<Group>() {
//            public void done(final List<Group> groups, ParseException e) {
//                // Remove the previously cached results.
//                ParseObject.unpinAllInBackground(new DeleteCallback() {
//                    public void done(ParseException e) {
//                        // Cache the new results.
//                        ParseObject.pinAllInBackground(groups);
//                        ParseObject cacheInfo = getCaches("ParseGroups");
//                        cacheInfo.put("classCache", "ParseGroups");
//                        cacheInfo.put("dateCache", Calendar.getInstance().getTime());
//                        cacheInfo.pinInBackground();
//                    }
//                });
//            }
//        });
    }
}
