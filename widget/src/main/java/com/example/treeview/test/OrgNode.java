package com.example.treeview.test;

import com.example.treeview.annotation.TreeNodeId;
import com.example.treeview.annotation.TreeNodeLabel;
import com.example.treeview.annotation.TreeNodePid;

/**
 * Created by dingshuhong on 2018/9/10.
 */

public class OrgNode {


    /**
     * id : 4
     * parentId : 1
     * name : 合浦项目
     * type : 2
     * tenantId : 1
     * fullName : 合浦项目
     * address : null
     * tel : null
     * leader : null
     * state : 1
     * createUserId : null
     * createDate : null
     * updateUserId : null
     * updateDate : null
     * version : 5
     * no : bb8ad8cba69c11e8ab9700155d1ef40a
     */

    @TreeNodeId
    private int id;
    @TreeNodePid
    private int parentId;
    @TreeNodeLabel
    private String name;
    private int type;
    private String rtype;
    private int tenantId;
    private String fullName;
    private String address;
    private String tel;
    private String leader;
    private int state;
    private String createUserId;
    private String createDate;
    private String updateUserId;
    private String updateDate;
    private int version;
    private String no;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getRtype() {
        return rtype;
    }

    public void setRtype(String rtype) {
        this.rtype = rtype;
    }

    public int getTenantId() {
        return tenantId;
    }

    public void setTenantId(int tenantId) {
        this.tenantId = tenantId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getLeader() {
        return leader;
    }

    public void setLeader(String leader) {
        this.leader = leader;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(String updateUserId) {
        this.updateUserId = updateUserId;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

}
