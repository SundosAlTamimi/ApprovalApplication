package com.example.approvalapp;

import static androidx.core.content.PackageManagerCompat.LOG_TAG;

import static com.example.approvalapp.view.Login.userName;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.approvalapp.Adapter.ListAdapterOrder;
import com.example.approvalapp.Json.ImportJson;
import com.example.approvalapp.Model.ListOfOrderData;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ListView list;
    ListAdapterOrder listAdapterOrder;
    List<ListOfOrderData>listData;
    EditText simpleSearchView;

    List<ListOfOrderData> filterList=new ArrayList<>();
    ImportJson importJson;
    SwipeRefreshLayout mySwipeRefreshLayout;
    public static Context globalContext;
    TextView user_text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
initialization();


    }

    private void initialization() {
        globalContext=MainActivity.this;
        list=findViewById(R.id.list);
         simpleSearchView =  findViewById(R.id.search); // inititate a search view
        listData=new ArrayList<>();
        user_text=findViewById(R.id.user_text);
        user_text.setText(userName+"");
        mySwipeRefreshLayout=findViewById(R.id.swiperefresh);
        importJson=new ImportJson(MainActivity.this);
        importJson.getOrder();
        mySwipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        Log.e("refresh", "onRefresh called from SwipeRefreshLayout");

                        // This method performs the actual data-refresh operation.
                        // The method calls setRefreshing(false) when it's finished.
                        importJson.getOrder();
                    }
                }
        );





//        listData.add(new ListOfOrderData("gr"));
//        listData.add(new ListOfOrderData("wes"));
//        listData.add(new ListOfOrderData("s"));
//        listData.add(new ListOfOrderData("v"));
//        listData.add(new ListOfOrderData("w"));
//        listData.add(new ListOfOrderData("w"));
//        listData.add(new ListOfOrderData("gr"));
//        listData.add(new ListOfOrderData("o"));
//        listData.add(new ListOfOrderData("l"));
//        listData.add(new ListOfOrderData("p"));
//        listData.add(new ListOfOrderData("ki"));
//        listData.add(new ListOfOrderData("hg"));
//        listData.add(new ListOfOrderData("fr"));
//        listData.add(new ListOfOrderData("iu"));
//        listData.add(new ListOfOrderData("or"));
//        listAdapterOrder=new ListAdapterOrder(MainActivity.this,listData);
//        list.setAdapter(listAdapterOrder);

        simpleSearchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String text = simpleSearchView.getText().toString();
                Log.e("gggggg",""+listData.size());
                if(!text.equals("")) {


                    listContains(listData, text);
                }
                else{
                    listOfOrder_2(listData);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public void listOfOrder(List<ListOfOrderData>allList) {
        Log.e("gggggg2",""+listData.size());
        listData=allList;
        listAdapterOrder=new ListAdapterOrder(MainActivity.this,allList);
        list.setAdapter(listAdapterOrder);
        try {
            mySwipeRefreshLayout.setRefreshing(false);
        }catch (Exception e){

        }
    }
    public void listOfOrder_2(List<ListOfOrderData>allList) {
        Log.e("gggggg2",""+listData.size());
        listAdapterOrder=new ListAdapterOrder(MainActivity.this,allList);
        list.setAdapter(listAdapterOrder);
    }

    public void listOfOrderClear() {
        listData.clear();
        listAdapterOrder=new ListAdapterOrder(MainActivity.this,listData);
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
        listOfOrder_2(filterList);
        Log.e("data2=",""+filterList.size());

    }
}