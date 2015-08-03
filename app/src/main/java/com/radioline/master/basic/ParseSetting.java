package com.radioline.master.basic;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;

/**
 * Created by master on 24.11.2014.
 */
@ParseClassName("Parse")
public class ParseSetting extends ParseObject {

    public static ParseQuery<ParseSetting> getQuery() {
        return ParseQuery.getQuery(ParseSetting.class);
    }


    public String getKey() {
        return getString("key");
    }

    public void setKey(String key) {
        put("key", key);
    }

    public String getValue() {
        return getString("value");
    }

    public void setValue(String value) {
        put("value", value);
    }


}
