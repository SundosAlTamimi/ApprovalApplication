package com.example.approvalapp.Json;

import static androidx.core.content.ContextCompat.getSystemService;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.example.approvalapp.MainActivity;
import com.example.approvalapp.Model.ListOfOrderData;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.HttpResponse;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.HttpClient;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.methods.HttpGet;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.conn.HttpHostConnectException;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.impl.client.DefaultHttpClient;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ImportJson {
    SweetAlertDialog swALogIn,swAStusus,swAStususRej;
Context context;
    String URL_TO_HIT;
    List<ListOfOrderData> clientOrders;
    public ImportJson(Context context) {
        this.context=context;
        this.clientOrders=new ArrayList<>();
    }


    public void getOrder(){
        new GetOrder().execute();
    }


    private class GetOrder extends AsyncTask<String, String, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //isOk=false;

        }

        @Override
        protected String doInBackground(String... params) {

            try {

//                if (!ipAddress.equals("")) {


//                String id=valetDatabase.getAllSetting();
//                String ip =valetDatabase.getAllIPSetting();
                URL_TO_HIT = "http://10.0.0.16:8080/BCIAPP/main.dll/GetAllApprovals";
//                }
            } catch (Exception e) {
                Log.e("URL_TO_HIT111", "JsonResponse\t" + URL_TO_HIT);
            }


            Log.e("URL_TO_HIT", "JsonResponse\t" + URL_TO_HIT);
            try {

                String JsonResponse = null;
                HttpClient client = new DefaultHttpClient();
                HttpGet request = new HttpGet();
                request.setURI(new URI(URL_TO_HIT));

//

                HttpResponse response = client.execute(request);


                BufferedReader in = new BufferedReader(new
                        InputStreamReader(response.getEntity().getContent()));

                StringBuffer sb = new StringBuffer("");
                String line = "";

                while ((line = in.readLine()) != null) {
                    sb.append(line);
                }

                in.close();


                JsonResponse = sb.toString();
                Log.e("tag_allcheques", "JsonResponse\t" + JsonResponse);

                return JsonResponse;


            }//org.apache.http.conn.HttpHostConnectException: Connection to http://10.0.0.115 refused
            catch (HttpHostConnectException ex) {
                ex.printStackTrace();
//                progressDialog.dismiss();

                Handler h = new Handler(Looper.getMainLooper());
                h.post(new Runnable() {
                    public void run() {

                        Toast.makeText(context, "Ip Connection Failed ", Toast.LENGTH_LONG).show();
                    }
                });


                return null;
            } catch (Exception e) {
                e.printStackTrace();
//                progressDialog.dismiss();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            JSONObject result = null;
            String impo = "";
            if (s != null) {

                if (s.contains("ClientName")) {


                    Gson gson = new Gson();
                    try {
                        JSONArray jsonArray=new JSONArray(s);

                        Type collectionType = new TypeToken<Collection<ListOfOrderData>>(){}.getType();
                        Collection<ListOfOrderData> enums = gson.fromJson(s, collectionType);

//                    CaptainClientTransfer gsonObj = gson.fromJson(jsonArray.getJSONObject().toString(), CaptainClientTransfer.class);
                        clientOrders.clear();
                        // captainClientTransfers.addAll(enums.getOrderList());
                        clientOrders= (List<ListOfOrderData>) enums;
                        MainActivity mainValetActivity = (MainActivity) context;
                        mainValetActivity.listOfOrder(clientOrders);
                        //isOk=true;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {
                    clientOrders.clear();
                    MainActivity mainValetActivity = (MainActivity) context;
                    mainValetActivity.listOfOrderClear();
                    Log.e("onPostExecute", "" + s.toString());
                //isOk=true;

                }
            }else {
            //    isOk=true;
            }
        }

    }


}
