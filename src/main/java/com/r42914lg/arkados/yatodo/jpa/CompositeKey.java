package com.r42914lg.arkados.yatodo.jpa;

import java.io.Serializable;

public class CompositeKey implements Serializable {
    private String userid;
    private int localid;

    public CompositeKey() {}

    public CompositeKey(String userid, int localid) {
        this.userid = userid;
        this.localid = localid;
    }

    public String getUserid() { return userid; }
    public int getLocalid() { return localid; }

    public void setUserid(String userid) { this.userid = userid; }
    public void setLocalid(int localid) { this.localid = localid; }
}
