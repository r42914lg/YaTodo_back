package com.r42914lg.arkados.yatodo.jpa;

import java.io.Serializable;

public class CompositeKey implements Serializable {
    private String userid;
    private int localid;
    private String deviceid;

    public CompositeKey() {}

    public CompositeKey(String userid, int localid, String deviceid) {
        this.userid = userid;
        this.localid = localid;
        this.deviceid = deviceid;
    }

    public String getUserid() { return userid; }
    public String getDeviceid() { return deviceid; }
    public int getLocalid() { return localid; }

    public void setUserid(String userid) { this.userid = userid; }
    public void setDeviceid(String deviceid) { this.deviceid = deviceid; }
    public void setLocalid(int localid) { this.localid = localid; }
}
