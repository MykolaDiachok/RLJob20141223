package com.radioline.master.basic;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import org.ksoap2.serialization.SoapObject;

/**
 * Created by master on 28.10.2014.
 */
@ParseClassName("ParseGroups")
public class Group extends ParseObject {


    public Group() {
    }

    public static ParseQuery<Group> getQuery() {
        return ParseQuery.getQuery(Group.class);
    }

    public Group(SoapObject inputGroup){

    }



    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Groups{");
        sb.append("groupid='").append(getString("groupid")).append('\'');
        sb.append(", name='").append(getString("name")).append('\'');
        sb.append(", parentid='").append(getString("parentid")).append('\'');
        sb.append(", fullnamegroup='").append(getString("fullnamegroup")).append('\'');
        sb.append(", code='").append(getString("code")).append('\'');
        sb.append(", sortcode='").append(getString("sortcode")).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public String getGroupid() {
        return getString("groupid");
    }

    public String getName() {
        return getString("name");
    }

   public String getParentid() {
        return getString("parentid");
    }

   public String getCode() {
        return getString("code");
    }

   public String getFullnamegroup() {
        return getString("fullnamegroup");
    }

     public String getSortcode() {
        return  getString("sortcode");
    }

}

