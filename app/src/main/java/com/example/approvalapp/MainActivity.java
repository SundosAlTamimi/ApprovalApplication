package com.example.approvalapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.approvalapp.Adapter.ListAdapterOrder;
import com.example.approvalapp.Json.ImportJson;
import com.example.approvalapp.Model.ListOfOrderData;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ListView list;
    ListAdapterOrder listAdapterOrder;
    List<ListOfOrderData>listData;
    SearchView simpleSearchView;
    List<ListOfOrderData> filterList=new ArrayList<>();
    ImportJson importJson;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
initialization();


    }

    private void initialization() {
        list=findViewById(R.id.list);
         simpleSearchView = (SearchView) findViewById(R.id.search); // inititate a search view
        listData=new ArrayList<>();
        simpleSearchView.setQueryHint("Search View");

        importJson=new ImportJson(MainActivity.this);
        importJson.getOrder();

//        listData.add(new ListOfOrderData("ee"));
//
//        listData.add(new ListOfOrderData("ee1"));
//        listData.add(new ListOfOrderData("ee2"));
//        listData.add(new ListOfOrderData("ee3"));
//        listData.add(new ListOfOrderData("ee4"));
//        listData.add(new ListOfOrderData("ee5"));
//        listData.add(new ListOfOrderData("ee6"));
//        listData.add(new ListOfOrderData("ee7"));
//        listData.add(new ListOfOrderData("ee8"));
//        listData.add(new ListOfOrderData("ee9"));
//        listData.add(new ListOfOrderData("ee74"));
//        listData.add(new ListOfOrderData("ee5"));
//        listData.add(new ListOfOrderData("ee2"));
//        listData.add(new ListOfOrderData("ee2"));
//        listData.add(new ListOfOrderData("ee5"));
//        listAdapterOrder=new ListAdapterOrder(MainActivity.this,listData);
//        list.setAdapter(listAdapterOrder);

        simpleSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {


                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                String text = s;
                if(!text.equals("")) {

                    listContains(listData, text);
                }
//                else{
//                    listOfOrder(listData);
//                }
                return true;
            }
        });

    }

    public void listOfOrder(List<ListOfOrderData>allList) {
        listData=allList;
        listAdapterOrder=new ListAdapterOrder(MainActivity.this,allList);
        list.setAdapter(listAdapterOrder);
    }

    public void listOfOrderClear() {
        listAdapterOrder=new ListAdapterOrder(MainActivity.this,null);
        list.setAdapter(listAdapterOrder);
    }




    public void listContains(List<ListOfOrderData> customTypeList, String searchedString) {
        try {
        filterList.clear();
            for (ListOfOrderData val : customTypeList) {
                Log.e("data=",""+val.getUSERNM());
                if(val.getUSERNM().toString().contains(searchedString)){
                    filterList.add(val);
                    Log.e("data=",""+val.getUSERNM());
                }
            }
        }catch (Exception e)
        {
//
        }
        listOfOrder(filterList);
        Log.e("data2=",""+filterList.size());

    }
}