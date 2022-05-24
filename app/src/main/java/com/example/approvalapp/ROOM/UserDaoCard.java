package com.example.approvalapp.ROOM;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.approvalapp.Model.ListOfOrderData;

import java.util.List;

@Dao
public interface UserDaoCard {
    @Query("SELECT * FROM REQ_NO_TABLE")
    List<String> getAll();

    @Query("SELECT * FROM REQ_NO_TABLE ")
    List<ListOfOrderData> loadAllByIds();

//    @Query("SELECT * FROM UserModel WHERE first_name LIKE :first AND " +
//            "last_name LIKE :last LIMIT 1")
//    UserModel findByName(String first, String last);

   // @Insert
   @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<ListOfOrderData> users);

    @Delete
    void delete(ListOfOrderData user);

    @Query("DELETE  FROM REQ_NO_TABLE")
    void deleteAll();

}
