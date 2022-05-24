package com.example.approvalapp.Services;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class ApprovalDatabase extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION =1;//version Db
    private static final String DATABASE_Name = "ApprovalDBase";//name Db

    static SQLiteDatabase Idb;

    //___________________________________________________________________________________
    private static final String REQ_NO_TABLE = "REQ_NO_TABLE";

    private static final String REQ_NO = "REQ_NO";

    //_________________________________________________________________________________

    public ApprovalDatabase(Context context) {
        super(context, DATABASE_Name, null, DATABASE_VERSION);
    }
    //__________________________________________________________________________________


    @Override
    public void onCreate(SQLiteDatabase Idb) {

        String CREATE_TABLE_ITEM_CARD = "CREATE TABLE " + REQ_NO_TABLE + "("
                + REQ_NO + " NVARCHAR" + ")";
        Idb.execSQL(CREATE_TABLE_ITEM_CARD);
    }

    @Override
    public void onUpgrade(SQLiteDatabase Idb, int oldVersion, int newVersion) {

        try {
            String CREATE_TABLE_ITEM_CARD = "CREATE TABLE " + REQ_NO_TABLE + "("
                    + REQ_NO + " NVARCHAR" + ")";
            Idb.execSQL(CREATE_TABLE_ITEM_CARD);
        }catch (Exception e){
            Log.e("upgrade", "ITEMS_INFO TRN_DATE4");
        }
    }
}
