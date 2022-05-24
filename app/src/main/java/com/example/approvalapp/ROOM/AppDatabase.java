package com.example.approvalapp.ROOM;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.approvalapp.Model.ListOfOrderData;


@Database(entities = {ListOfOrderData.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract UserDaoCard itemCard();

}

