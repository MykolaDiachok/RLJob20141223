package com.radioline.master.basic;

import com.parse.ParseObject;

import org.ksoap2.serialization.SoapObject;

/**
 * Created by master on 28.10.2014.
 */

public class Group {
    private String groupid;
    private String name;
    private String parentid;
    private String fullnamegroup;
    private String code;
    private String sortcode;


    public Group(String groupid, String name, String fullnamegroup, String parentid, String code, String sortcode) {
        super();
        this.groupid = groupid;
        this.name = name;
        this.fullnamegroup = fullnamegroup;
        this.parentid = parentid;
        this.code = code;
        this.sortcode = sortcode + this.name.substring(0, Math.min(this.name.length(), 10));
    }

    public Group(SoapObject itemGroup) {
        super();
        this.groupid = itemGroup.getProperty("Id").toString();
        this.name = itemGroup.getProperty("Name").toString();
        this.fullnamegroup = itemGroup.getProperty("FullNameGroup").toString();
        this.parentid = itemGroup.getProperty("ParentId").toString();
        this.code = itemGroup.getProperty("Code").toString();
        this.sortcode = itemGroup.getProperty("SortCode").toString() + this.name.substring(0, Math.min(this.name.length(), 10));
    }

    public Group(ParseObject pgroup) {
        super();
        this.groupid = pgroup.getString("groupid");
        this.name = pgroup.getString("name");
        this.fullnamegroup = pgroup.getString("fullnamegroup");
        this.parentid = pgroup.getString("parentid");
        this.code = pgroup.getString("code");
        this.sortcode = pgroup.getString("sortcode") + this.name.substring(0, Math.min(this.name.length(), 10));
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Groups{");
        sb.append("groupid='").append(groupid).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", parentid='").append(parentid).append('\'');
        sb.append(", fullnamegroup='").append(fullnamegroup).append('\'');
        sb.append(", code='").append(code).append('\'');
        sb.append(", sortcode='").append(sortcode).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public String getGroupid() {
        return groupid;
    }

    public void setGroupid(String groupid) {
        this.groupid = groupid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentid() {
        return parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getFullnamegroup() {
        return fullnamegroup;
    }

    public void setFullnamegroup(String fullnamegroup) {
        this.fullnamegroup = fullnamegroup;
    }

    public String getSortcode() {
        return sortcode;
    }

    public void setSortcode(String sortcode) {
        this.sortcode = sortcode;
    }
}

