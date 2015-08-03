package com.radioline.master.basic;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.Date;


/**
 * Created by master on 17.11.2014.
 */
@ParseClassName("ParseGroups")
public class ParseGroups extends ParseObject {
    public ParseGroups(Date createat, String groupid, String name, String parentid, String fullnamegroup, String sortcode) {
        put("createat", createat);
        put("groupid", groupid);
        put("name", name);
        put("parentid", parentid);
        put("fullnamegroup", fullnamegroup);
        put("sortcode", sortcode);

    }

    public ParseGroups(String groupid, String name, String parentid, String fullnamegroup, String sortcode) {
        put("createat", new Date());
        put("groupid", groupid);
        put("name", name);
        put("parentid", parentid);
        put("fullnamegroup", fullnamegroup);
        put("sortcode", sortcode);

    }


    public static ParseQuery<ParseGroups> getQuery() {
        return ParseQuery.getQuery(ParseGroups.class);
    }

    public String getCreateAt() {
        return getString("createat");
    }

    public void setCreateAt(Date createat) {
        put("createat", createat);
    }

    public String getGroupid() {
        return getString("groupid");
    }

    public void setGroupid(String groupid) {
        put("groupid", groupid);
    }

    public String getName() {
        return getString("name");
    }

    public void setName(String name) {
        put("name", name);
    }

    public String getParentid() {
        return getString("parentid");
    }

    public void setParentid(String parentid) {
        put("parentid", parentid);
    }

    public String getFullnamegroup() {
        return getString("fullnamegroup");
    }

    public void setFullnamegroup(String fullnamegroup) {
        put("fullnamegroup", fullnamegroup);
    }

    public String getCode() {
        return getString("code");
    }

    public void setCode(String code) {
        put("code", code);
    }

    public String getSortcode() {
        return getString("sortcode");
    }

    public void setSortcode(String sortcode) {
        put("sortcode", sortcode);
    }

}
