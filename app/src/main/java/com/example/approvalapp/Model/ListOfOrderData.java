package com.example.approvalapp.Model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

@Entity(tableName = "REQ_NO_TABLE")

public class ListOfOrderData implements Serializable {
//[{"APPKIND":"0","POSNO":"2","USERNO":"1000","USERNM":"المستخدم الرئيسي","REQNO":"D_2_1000_20211230184756","WDATE":"14\/07\/2020","TOTAL":"9.000",
// "DISCOUNT":"0.900","REQINDATE":"30\/12\/2021 18:47:58","APPUSERNO":"1000","APPUSERNM":"المستخدم الرئيسي"
// ,"APPROVNO":"","APPROVREM":"","APPRVINDATE":"30\/12\/2021 19:03:39","ISDONE":"0","KINDNM":"Discount","POSNM":"POS2","APPSTATUS":"Waiting"},
@Ignore
    @SerializedName("APPKIND")
    public String APPKIND;
    @Ignore
    @SerializedName("POSNO")
    public String POSNO;
    @Ignore
    @SerializedName("USERNO")
    public String USERNO;
    @Ignore
    @SerializedName("USERNM")
    public String USERNM;
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "REQ_NO")
    @SerializedName("REQNO")
    public String REQNO;
    @Ignore
    @SerializedName("WDATE")
    public String WDATE;
    @Ignore
    @SerializedName("TOTAL")
    public String TOTAL;
    @Ignore
    @SerializedName("DISCOUNT")
    public String DISCOUNT;
    @Ignore
    @SerializedName("REQINDATE")
    public String REQINDATE;
    @Ignore
    @SerializedName("APPUSERNO")
    public String APPUSERNO;
    @Ignore
    @SerializedName("APPUSERNM")
    public String APPUSERNM;
    @Ignore
    @SerializedName("APPROVNO")
    public String APPROVNO;
    @Ignore
    @SerializedName("APPROVREM")
    public String APPROVREM;
    @Ignore
    @SerializedName("APPRVINDATE")
    public String APPRVINDATE;
    @Ignore
    @SerializedName("ISDONE")
    public String ISDONE;
    @Ignore
    @SerializedName("KINDNM")
    public String KINDNM;
    @Ignore
    @SerializedName("POSNM")
    public String POSNM;
    @Ignore
    @SerializedName("APPSTATUS")
    public String APPSTATUS;
   // @SerializedName("INFO")
   @Ignore
    public List<ListOfOrderData> INFO;

    public ListOfOrderData() {
    }

    public ListOfOrderData(String name) {
        this.USERNM = name;
    }


    public String getAPPKIND() {
        return APPKIND;
    }

    public void setAPPKIND(String APPKIND) {
        this.APPKIND = APPKIND;
    }

    public String getPOSNO() {
        return POSNO;
    }

    public void setPOSNO(String POSNO) {
        this.POSNO = POSNO;
    }

    public String getUSERNO() {
        return USERNO;
    }

    public void setUSERNO(String USERNO) {
        this.USERNO = USERNO;
    }

    public String getUSERNM() {
        return USERNM;
    }

    public void setUSERNM(String USERNM) {
        this.USERNM = USERNM;
    }

    public String getREQNO() {
        return REQNO;
    }

    public void setREQNO(String REQNO) {
        this.REQNO = REQNO;
    }

    public String getWDATE() {
        return WDATE;
    }

    public void setWDATE(String WDATE) {
        this.WDATE = WDATE;
    }

    public String getTOTAL() {
        return TOTAL;
    }

    public void setTOTAL(String TOTAL) {
        this.TOTAL = TOTAL;
    }

    public String getDISCOUNT() {
        return DISCOUNT;
    }

    public void setDISCOUNT(String DISCOUNT) {
        this.DISCOUNT = DISCOUNT;
    }

    public String getREQINDATE() {
        return REQINDATE;
    }

    public void setREQINDATE(String REQINDATE) {
        this.REQINDATE = REQINDATE;
    }

    public String getAPPUSERNO() {
        return APPUSERNO;
    }

    public void setAPPUSERNO(String APPUSERNO) {
        this.APPUSERNO = APPUSERNO;
    }

    public String getAPPUSERNM() {
        return APPUSERNM;
    }

    public void setAPPUSERNM(String APPUSERNM) {
        this.APPUSERNM = APPUSERNM;
    }

    public String getAPPROVNO() {
        return APPROVNO;
    }

    public void setAPPROVNO(String APPROVNO) {
        this.APPROVNO = APPROVNO;
    }

    public String getAPPROVREM() {
        return APPROVREM;
    }

    public void setAPPROVREM(String APPROVREM) {
        this.APPROVREM = APPROVREM;
    }

    public String getAPPRVINDATE() {
        return APPRVINDATE;
    }

    public void setAPPRVINDATE(String APPRVINDATE) {
        this.APPRVINDATE = APPRVINDATE;
    }

    public String getISDONE() {
        return ISDONE;
    }

    public void setISDONE(String ISDONE) {
        this.ISDONE = ISDONE;
    }

    public String getKINDNM() {
        return KINDNM;
    }

    public void setKINDNM(String KINDNM) {
        this.KINDNM = KINDNM;
    }

    public String getPOSNM() {
        return POSNM;
    }

    public void setPOSNM(String POSNM) {
        this.POSNM = POSNM;
    }

    public String getAPPSTATUS() {
        return APPSTATUS;
    }

    public void setAPPSTATUS(String APPSTATUS) {
        this.APPSTATUS = APPSTATUS;
    }

    public List<ListOfOrderData> getINFO() {
        return INFO;
    }

    public void setINFO(List<ListOfOrderData> INFO) {
        this.INFO = INFO;
    }
}
