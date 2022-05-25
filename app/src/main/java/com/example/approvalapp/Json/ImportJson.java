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
           // s="{\"StatusCode\":0,\"Descreption\":\"OK\",\"INFO\":[{\"APPKIND\":\"0\",\"POSNO\":\"2\",\"USERNO\":\"1000\",\"USERNM\":\"المستخدم الرئيسي\",\"REQNO\":\"D_2_1000_20211230184756\",\"WDATE\":\"14\\/07\\/2020\",\"TOTAL\":\"9.000\",\"DISCOUNT\":\"0.900\",\"REQINDATE\":\"30\\/12\\/2021 18:47:58\",\"APPUSERNO\":\"1000\",\"APPUSERNM\":\"المستخدم الرئيسي\",\"APPROVNO\":\"\",\"APPROVREM\":\"\",\"APPRVINDATE\":\"30\\/12\\/2021 19:03:39\",\"ISDONE\":\"0\",\"KINDNM\":\"Discount\",\"POSNM\":\"POS2\",\"APPSTATUS\":\"Waiting\"},{\"APPKIND\":\"0\",\"POSNO\":\"2\",\"USERNO\":\"1000\",\"USERNM\":\"المستخدم الرئيسي\",\"REQNO\":\"D_2_1000_20220108145342\",\"WDATE\":\"14\\/07\\/2020\",\"TOTAL\":\"200.000\",\"DISCOUNT\":\"20.000\",\"REQINDATE\":\"08\\/01\\/2022 14:53:44\",\"APPUSERNO\":\"1000\",\"APPUSERNM\":\"المستخدم الرئيسي\",\"APPROVNO\":\"\",\"APPROVREM\":\"\",\"APPRVINDATE\":\"08\\/01\\/2022 14:57:52\",\"ISDONE\":\"0\",\"KINDNM\":\"Discount\",\"POSNM\":\"POS2\",\"APPSTATUS\":\"Waiting\"},{\"APPKIND\":\"0\",\"POSNO\":\"2\",\"USERNO\":\"1000\",\"USERNM\":\"المستخدم الرئيسي\",\"REQNO\":\"D_2_1000_20220108144543\",\"WDATE\":\"14\\/07\\/2020\",\"TOTAL\":\"200.00\",\"DISCOUNT\":\"20.00\",\"REQINDATE\":\"08\\/01\\/2022 14:46:10\",\"APPUSERNO\":\"1000\",\"APPUSERNM\":\"المستخدم الرئيسي\",\"APPROVNO\":\"\",\"APPROVREM\":\"\",\"APPRVINDATE\":\"08\\/01\\/2022 14:47:17\",\"ISDONE\":\"0\",\"KINDNM\":\"Discount\",\"POSNM\":\"POS2\",\"APPSTATUS\":\"Waiting\"},{\"APPKIND\":\"0\",\"POSNO\":\"2\",\"USERNO\":\"1000\",\"USERNM\":\"المستخدم الرئيسي\",\"REQNO\":\"D_2_1000_20220108144543\",\"WDATE\":\"14\\/07\\/2020\",\"TOTAL\":\"200.00\",\"DISCOUNT\":\"20.00\",\"REQINDATE\":\"08\\/01\\/2022 14:47:36\",\"APPUSERNO\":\"1000\",\"APPUSERNM\":\"المستخدم الرئيسي\",\"APPROVNO\":\"\",\"APPROVREM\":\"\",\"APPRVINDATE\":\"08\\/01\\/2022 14:48:00\",\"ISDONE\":\"0\",\"KINDNM\":\"Discount\",\"POSNM\":\"POS2\",\"APPSTATUS\":\"Waiting\"},{\"APPKIND\":\"0\",\"POSNO\":\"2\",\"USERNO\":\"1000\",\"USERNM\":\"المستخدم الرئيسي\",\"REQNO\":\"D_2_1000_20220108144809\",\"WDATE\":\"14\\/07\\/2020\",\"TOTAL\":\"200.00\",\"DISCOUNT\":\"20.00\",\"REQINDATE\":\"08\\/01\\/2022 14:48:13\",\"APPUSERNO\":\"1000\",\"APPUSERNM\":\"المستخدم الرئيسي\",\"APPROVNO\":\"\",\"APPROVREM\":\"\",\"APPRVINDATE\":\"08\\/01\\/2022 14:48:23\",\"ISDONE\":\"0\",\"KINDNM\":\"Discount\",\"POSNM\":\"POS2\",\"APPSTATUS\":\"Waiting\"},{\"APPKIND\":\"0\",\"POSNO\":\"2\",\"USERNO\":\"1000\",\"USERNM\":\"المستخدم الرئيسي\",\"REQNO\":\"D_2_1000_20220108145758\",\"WDATE\":\"14\\/07\\/2020\",\"TOTAL\":\"200.00\",\"DISCOUNT\":\"20.00\",\"REQINDATE\":\"08\\/01\\/2022 14:58:00\",\"APPUSERNO\":\"1000\",\"APPUSERNM\":\"المستخدم الرئيسي\",\"APPROVNO\":\"\",\"APPROVREM\":\"\",\"APPRVINDATE\":\"08\\/01\\/2022 14:58:07\",\"ISDONE\":\"0\",\"KINDNM\":\"Discount\",\"POSNM\":\"POS2\",\"APPSTATUS\":\"Waiting\"},{\"APPKIND\":\"0\",\"POSNO\":\"2\",\"USERNO\":\"1000\",\"USERNM\":\"المستخدم الرئيسي\",\"REQNO\":\"D_2_1000_20220108145905\",\"WDATE\":\"14\\/07\\/2020\",\"TOTAL\":\"200.00\",\"DISCOUNT\":\"20.00\",\"REQINDATE\":\"08\\/01\\/2022 14:59:10\",\"APPUSERNO\":\"1000\",\"APPUSERNM\":\"المستخدم الرئيسي\",\"APPROVNO\":\"\",\"APPROVREM\":\"\",\"APPRVINDATE\":\"08\\/01\\/2022 14:59:31\",\"ISDONE\":\"0\",\"KINDNM\":\"Discount\",\"POSNM\":\"POS2\",\"APPSTATUS\":\"Waiting\"},{\"APPKIND\":\"0\",\"POSNO\":\"2\",\"USERNO\":\"1000\",\"USERNM\":\"المستخدم الرئيسي\",\"REQNO\":\"D_2_1000_20220109152811\",\"WDATE\":\"14\\/07\\/2020\",\"TOTAL\":\"200.00\",\"DISCOUNT\":\"20.00\",\"REQINDATE\":\"09\\/01\\/2022 15:28:59\",\"APPUSERNO\":\"1000\",\"APPUSERNM\":\"المستخدم الرئيسي\",\"APPROVNO\":\"\",\"APPROVREM\":\"\",\"APPRVINDATE\":\"09\\/01\\/2022 15:29:18\",\"ISDONE\":\"0\",\"KINDNM\":\"Discount\",\"POSNM\":\"POS2\",\"APPSTATUS\":\"Waiting\"},{\"APPKIND\":\"1\",\"POSNO\":\"2\",\"USERNO\":\"1000\",\"USERNM\":\"المستخدم الرئيسي\",\"REQNO\":\"U_2_1000_20220109152944\",\"WDATE\":\"14\\/07\\/2020\",\"TOTAL\":\"\",\"DISCOUNT\":\"\",\"REQINDATE\":\"09\\/01\\/2022 15:29:47\",\"APPUSERNO\":\"1000\",\"APPUSERNM\":\"المستخدم الرئيسي\",\"APPROVNO\":\"\",\"APPROVREM\":\"\",\"APPRVINDATE\":\"09\\/01\\/2022 15:29:59\",\"ISDONE\":\"0\",\"KINDNM\":\"Update Price\",\"POSNM\":\"POS2\",\"APPSTATUS\":\"Waiting\"},{\"APPKIND\":\"1\",\"POSNO\":\"2\",\"USERNO\":\"1000\",\"USERNM\":\"المستخدم الرئيسي\",\"REQNO\":\"U_2_1000_20220109153012\",\"WDATE\":\"14\\/07\\/2020\",\"TOTAL\":\"\",\"DISCOUNT\":\"\",\"REQINDATE\":\"09\\/01\\/2022 15:30:16\",\"APPUSERNO\":\"1000\",\"APPUSERNM\":\"المستخدم الرئيسي\",\"APPROVNO\":\"\",\"APPROVREM\":\"\",\"APPRVINDATE\":\"09\\/01\\/2022 15:30:21\",\"ISDONE\":\"0\",\"KINDNM\":\"Update Price\",\"POSNM\":\"POS2\",\"APPSTATUS\":\"Waiting\"},{\"APPKIND\":\"2\",\"POSNO\":\"2\",\"USERNO\":\"1000\",\"USERNM\":\"المستخدم الرئيسي\",\"REQNO\":\"R_2_1000_20220109153051\",\"WDATE\":\"14\\/07\\/2020\",\"TOTAL\":\"\",\"DISCOUNT\":\"\",\"REQINDATE\":\"09\\/01\\/2022 15:30:54\",\"APPUSERNO\":\"1000\",\"APPUSERNM\":\"المستخدم الرئيسي\",\"APPROVNO\":\"\",\"APPROVREM\":\"\",\"APPRVINDATE\":\"09\\/01\\/2022 15:31:32\",\"ISDONE\":\"0\",\"KINDNM\":\"Return\",\"POSNM\":\"POS2\",\"APPSTATUS\":\"Waiting\"},{\"APPKIND\":\"2\",\"POSNO\":\"2\",\"USERNO\":\"1000\",\"USERNM\":\"المستخدم الرئيسي\",\"REQNO\":\"R_2_1000_20220109153159\",\"WDATE\":\"14\\/07\\/2020\",\"TOTAL\":\"\",\"DISCOUNT\":\"\",\"REQINDATE\":\"09\\/01\\/2022 15:32:01\",\"APPUSERNO\":\"1000\",\"APPUSERNM\":\"المستخدم الرئيسي\",\"APPROVNO\":\"\",\"APPROVREM\":\"\",\"APPRVINDATE\":\"09\\/01\\/2022 15:32:07\",\"ISDONE\":\"0\",\"KINDNM\":\"Return\",\"POSNM\":\"POS2\",\"APPSTATUS\":\"Waiting\"},{\"APPKIND\":\"0\",\"POSNO\":\"2\",\"USERNO\":\"1000\",\"USERNM\":\"المستخدم الرئيسي\",\"REQNO\":\"D_2_1000_20220109171411\",\"WDATE\":\"14\\/07\\/2020\",\"TOTAL\":\"200.00\",\"DISCOUNT\":\"20.00\",\"REQINDATE\":\"09\\/01\\/2022 17:15:28\",\"APPUSERNO\":\"1000\",\"APPUSERNM\":\"المستخدم الرئيسي\",\"APPROVNO\":\"\",\"APPROVREM\":\"\",\"APPRVINDATE\":\"09\\/01\\/2022 17:16:53\",\"ISDONE\":\"0\",\"KINDNM\":\"Discount\",\"POSNM\":\"POS2\",\"APPSTATUS\":\"Waiting\"},{\"APPKIND\":\"0\",\"POSNO\":\"2\",\"USERNO\":\"1000\",\"USERNM\":\"المستخدم الرئيسي\",\"REQNO\":\"D_2_1000_20220109171741\",\"WDATE\":\"14\\/07\\/2020\",\"TOTAL\":\"200.00\",\"DISCOUNT\":\"20.00\",\"REQINDATE\":\"09\\/01\\/2022 17:17:48\",\"APPUSERNO\":\"1000\",\"APPUSERNM\":\"المستخدم الرئيسي\",\"APPROVNO\":\"\",\"APPROVREM\":\"\",\"APPRVINDATE\":\"09\\/01\\/2022 17:18:27\",\"ISDONE\":\"0\",\"KINDNM\":\"Discount\",\"POSNM\":\"POS2\",\"APPSTATUS\":\"Waiting\"},{\"APPKIND\":\"1\",\"POSNO\":\"2\",\"USERNO\":\"1000\",\"USERNM\":\"المستخدم الرئيسي\",\"REQNO\":\"U_2_1000_20220109172036\",\"WDATE\":\"14\\/07\\/2020\",\"TOTAL\":\"\",\"DISCOUNT\":\"\",\"REQINDATE\":\"09\\/01\\/2022 17:20:50\",\"APPUSERNO\":\"1000\",\"APPUSERNM\":\"المستخدم الرئيسي\",\"APPROVNO\":\"\",\"APPROVREM\":\"\",\"APPRVINDATE\":\"09\\/01\\/2022 17:21:50\",\"ISDONE\":\"0\",\"KINDNM\":\"Update Price\",\"POSNM\":\"POS2\",\"APPSTATUS\":\"Waiting\"},{\"APPKIND\":\"2\",\"POSNO\":\"2\",\"USERNO\":\"1000\",\"USERNM\":\"المستخدم الرئيسي\",\"REQNO\":\"R_2_1000_20220109172324\",\"WDATE\":\"14\\/07\\/2020\",\"TOTAL\":\"\",\"DISCOUNT\":\"\",\"REQINDATE\":\"09\\/01\\/2022 17:23:31\",\"APPUSERNO\":\"1000\",\"APPUSERNM\":\"المستخدم الرئيسي\",\"APPROVNO\":\"\",\"APPROVREM\":\"\",\"APPRVINDATE\":\"09\\/01\\/2022 17:23:55\",\"ISDONE\":\"0\",\"KINDNM\":\"Return\",\"POSNM\":\"POS2\",\"APPSTATUS\":\"Waiting\"},{\"APPKIND\":\"2\",\"POSNO\":\"119\",\"USERNO\":\"1000\",\"USERNM\":\"المستخدم الرئيسي\",\"REQNO\":\"R_119_1000_20220310170412\",\"WDATE\":\"02\\/03\\/2022\",\"TOTAL\":\"\",\"DISCOUNT\":\"\",\"REQINDATE\":\"10\\/03\\/2022 17:04:29\",\"APPUSERNO\":\"\",\"APPUSERNM\":\"\",\"APPROVNO\":\"\",\"APPROVREM\":\"\",\"APPRVINDATE\":\"\",\"ISDONE\":\"0\",\"KINDNM\":\"Return\",\"POSNM\":\"\",\"APPSTATUS\":\"Waiting\"},{\"APPKIND\":\"1\",\"POSNO\":\"119\",\"USERNO\":\"1000\",\"USERNM\":\"المستخدم الرئيسي\",\"REQNO\":\"U_119_1000_20220305173254\",\"WDATE\":\"02\\/03\\/2022\",\"TOTAL\":\"\",\"DISCOUNT\":\"\",\"REQINDATE\":\"05\\/03\\/2022 17:32:56\",\"APPUSERNO\":\"200\",\"APPUSERNM\":\"ALAA\",\"APPROVNO\":\"\",\"APPROVREM\":\"\",\"APPRVINDATE\":\"09\\/01\\/2022\",\"ISDONE\":\"0\",\"KINDNM\":\"Update Price\",\"POSNM\":\"\",\"APPSTATUS\":\"Waiting\"}]}";

            if (s != null) {

                if (s.contains("APPKIND")) {


                    Gson gson = new Gson();
                    try {
                        //JSONArray jsonArray=new JSONArray(s);
                        s=s.substring(s.indexOf("["),s.lastIndexOf("]")+1);
                        Log.e("fffff",""+s+"");
                        Type collectionType = new TypeToken<Collection<ListOfOrderData>>(){}.getType();
                        Collection<ListOfOrderData> enums = gson.fromJson(s, collectionType);

//                    CaptainClientTransfer gsonObj = gson.fromJson(jsonArray.getJSONObject().toString(), CaptainClientTransfer.class);
                        clientOrders.clear();
                        // captainClientTransfers.addAll(enums.getOrderList());
                        clientOrders= (List<ListOfOrderData>) enums;
                        MainActivity mainValetActivity = (MainActivity) context;
                        mainValetActivity.listOfOrder(clientOrders);
                        //isOk=true;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {
                    try {
                        clientOrders.clear();
                        MainActivity mainValetActivity = (MainActivity) context;
                        mainValetActivity.listOfOrderClear();
                        Log.e("onPostExecute", "" + s.toString());
                    }catch (Exception e){

                    }
                //isOk=true;

                }
            }else {
            //    isOk=true;
//
//                s="{\"StatusCode\":0,\"Descreption\":\"OK\",\"INFO\":[{\"APPKIND\":\"0\",\"POSNO\":\"2\",\"USERNO\":\"1000\",\"USERNM\":\"المستخدم الرئيسي\",\"REQNO\":\"D_2_1000_20211230184756\",\"WDATE\":\"14\\/07\\/2020\",\"TOTAL\":\"9.000\",\"DISCOUNT\":\"0.900\",\"REQINDATE\":\"30\\/12\\/2021 18:47:58\",\"APPUSERNO\":\"1000\",\"APPUSERNM\":\"المستخدم الرئيسي\",\"APPROVNO\":\"\",\"APPROVREM\":\"\",\"APPRVINDATE\":\"30\\/12\\/2021 19:03:39\",\"ISDONE\":\"0\",\"KINDNM\":\"Discount\",\"POSNM\":\"POS2\",\"APPSTATUS\":\"Waiting\"},{\"APPKIND\":\"0\",\"POSNO\":\"2\",\"USERNO\":\"1000\",\"USERNM\":\"المستخدم الرئيسي\",\"REQNO\":\"D_2_1000_20220108145342\",\"WDATE\":\"14\\/07\\/2020\",\"TOTAL\":\"200.000\",\"DISCOUNT\":\"20.000\",\"REQINDATE\":\"08\\/01\\/2022 14:53:44\",\"APPUSERNO\":\"1000\",\"APPUSERNM\":\"المستخدم الرئيسي\",\"APPROVNO\":\"\",\"APPROVREM\":\"\",\"APPRVINDATE\":\"08\\/01\\/2022 14:57:52\",\"ISDONE\":\"0\",\"KINDNM\":\"Discount\",\"POSNM\":\"POS2\",\"APPSTATUS\":\"Waiting\"},{\"APPKIND\":\"0\",\"POSNO\":\"2\",\"USERNO\":\"1000\",\"USERNM\":\"المستخدم الرئيسي\",\"REQNO\":\"D_2_1000_20220108144543\",\"WDATE\":\"14\\/07\\/2020\",\"TOTAL\":\"200.00\",\"DISCOUNT\":\"20.00\",\"REQINDATE\":\"08\\/01\\/2022 14:46:10\",\"APPUSERNO\":\"1000\",\"APPUSERNM\":\"المستخدم الرئيسي\",\"APPROVNO\":\"\",\"APPROVREM\":\"\",\"APPRVINDATE\":\"08\\/01\\/2022 14:47:17\",\"ISDONE\":\"0\",\"KINDNM\":\"Discount\",\"POSNM\":\"POS2\",\"APPSTATUS\":\"Waiting\"},{\"APPKIND\":\"0\",\"POSNO\":\"2\",\"USERNO\":\"1000\",\"USERNM\":\"المستخدم الرئيسي\",\"REQNO\":\"D_2_1000_20220108144543\",\"WDATE\":\"14\\/07\\/2020\",\"TOTAL\":\"200.00\",\"DISCOUNT\":\"20.00\",\"REQINDATE\":\"08\\/01\\/2022 14:47:36\",\"APPUSERNO\":\"1000\",\"APPUSERNM\":\"المستخدم الرئيسي\",\"APPROVNO\":\"\",\"APPROVREM\":\"\",\"APPRVINDATE\":\"08\\/01\\/2022 14:48:00\",\"ISDONE\":\"0\",\"KINDNM\":\"Discount\",\"POSNM\":\"POS2\",\"APPSTATUS\":\"Waiting\"},{\"APPKIND\":\"0\",\"POSNO\":\"2\",\"USERNO\":\"1000\",\"USERNM\":\"المستخدم الرئيسي\",\"REQNO\":\"D_2_1000_20220108144809\",\"WDATE\":\"14\\/07\\/2020\",\"TOTAL\":\"200.00\",\"DISCOUNT\":\"20.00\",\"REQINDATE\":\"08\\/01\\/2022 14:48:13\",\"APPUSERNO\":\"1000\",\"APPUSERNM\":\"المستخدم الرئيسي\",\"APPROVNO\":\"\",\"APPROVREM\":\"\",\"APPRVINDATE\":\"08\\/01\\/2022 14:48:23\",\"ISDONE\":\"0\",\"KINDNM\":\"Discount\",\"POSNM\":\"POS2\",\"APPSTATUS\":\"Waiting\"},{\"APPKIND\":\"0\",\"POSNO\":\"2\",\"USERNO\":\"1000\",\"USERNM\":\"المستخدم الرئيسي\",\"REQNO\":\"D_2_1000_20220108145758\",\"WDATE\":\"14\\/07\\/2020\",\"TOTAL\":\"200.00\",\"DISCOUNT\":\"20.00\",\"REQINDATE\":\"08\\/01\\/2022 14:58:00\",\"APPUSERNO\":\"1000\",\"APPUSERNM\":\"المستخدم الرئيسي\",\"APPROVNO\":\"\",\"APPROVREM\":\"\",\"APPRVINDATE\":\"08\\/01\\/2022 14:58:07\",\"ISDONE\":\"0\",\"KINDNM\":\"Discount\",\"POSNM\":\"POS2\",\"APPSTATUS\":\"Waiting\"},{\"APPKIND\":\"0\",\"POSNO\":\"2\",\"USERNO\":\"1000\",\"USERNM\":\"المستخدم الرئيسي\",\"REQNO\":\"D_2_1000_20220108145905\",\"WDATE\":\"14\\/07\\/2020\",\"TOTAL\":\"200.00\",\"DISCOUNT\":\"20.00\",\"REQINDATE\":\"08\\/01\\/2022 14:59:10\",\"APPUSERNO\":\"1000\",\"APPUSERNM\":\"المستخدم الرئيسي\",\"APPROVNO\":\"\",\"APPROVREM\":\"\",\"APPRVINDATE\":\"08\\/01\\/2022 14:59:31\",\"ISDONE\":\"0\",\"KINDNM\":\"Discount\",\"POSNM\":\"POS2\",\"APPSTATUS\":\"Waiting\"},{\"APPKIND\":\"0\",\"POSNO\":\"2\",\"USERNO\":\"1000\",\"USERNM\":\"المستخدم الرئيسي\",\"REQNO\":\"D_2_1000_20220109152811\",\"WDATE\":\"14\\/07\\/2020\",\"TOTAL\":\"200.00\",\"DISCOUNT\":\"20.00\",\"REQINDATE\":\"09\\/01\\/2022 15:28:59\",\"APPUSERNO\":\"1000\",\"APPUSERNM\":\"المستخدم الرئيسي\",\"APPROVNO\":\"\",\"APPROVREM\":\"\",\"APPRVINDATE\":\"09\\/01\\/2022 15:29:18\",\"ISDONE\":\"0\",\"KINDNM\":\"Discount\",\"POSNM\":\"POS2\",\"APPSTATUS\":\"Waiting\"},{\"APPKIND\":\"1\",\"POSNO\":\"2\",\"USERNO\":\"1000\",\"USERNM\":\"المستخدم الرئيسي\",\"REQNO\":\"U_2_1000_20220109152944\",\"WDATE\":\"14\\/07\\/2020\",\"TOTAL\":\"\",\"DISCOUNT\":\"\",\"REQINDATE\":\"09\\/01\\/2022 15:29:47\",\"APPUSERNO\":\"1000\",\"APPUSERNM\":\"المستخدم الرئيسي\",\"APPROVNO\":\"\",\"APPROVREM\":\"\",\"APPRVINDATE\":\"09\\/01\\/2022 15:29:59\",\"ISDONE\":\"0\",\"KINDNM\":\"Update Price\",\"POSNM\":\"POS2\",\"APPSTATUS\":\"Waiting\"},{\"APPKIND\":\"1\",\"POSNO\":\"2\",\"USERNO\":\"1000\",\"USERNM\":\"المستخدم الرئيسي\",\"REQNO\":\"U_2_1000_20220109153012\",\"WDATE\":\"14\\/07\\/2020\",\"TOTAL\":\"\",\"DISCOUNT\":\"\",\"REQINDATE\":\"09\\/01\\/2022 15:30:16\",\"APPUSERNO\":\"1000\",\"APPUSERNM\":\"المستخدم الرئيسي\",\"APPROVNO\":\"\",\"APPROVREM\":\"\",\"APPRVINDATE\":\"09\\/01\\/2022 15:30:21\",\"ISDONE\":\"0\",\"KINDNM\":\"Update Price\",\"POSNM\":\"POS2\",\"APPSTATUS\":\"Waiting\"},{\"APPKIND\":\"2\",\"POSNO\":\"2\",\"USERNO\":\"1000\",\"USERNM\":\"المستخدم الرئيسي\",\"REQNO\":\"R_2_1000_20220109153051\",\"WDATE\":\"14\\/07\\/2020\",\"TOTAL\":\"\",\"DISCOUNT\":\"\",\"REQINDATE\":\"09\\/01\\/2022 15:30:54\",\"APPUSERNO\":\"1000\",\"APPUSERNM\":\"المستخدم الرئيسي\",\"APPROVNO\":\"\",\"APPROVREM\":\"\",\"APPRVINDATE\":\"09\\/01\\/2022 15:31:32\",\"ISDONE\":\"0\",\"KINDNM\":\"Return\",\"POSNM\":\"POS2\",\"APPSTATUS\":\"Waiting\"},{\"APPKIND\":\"2\",\"POSNO\":\"2\",\"USERNO\":\"1000\",\"USERNM\":\"المستخدم الرئيسي\",\"REQNO\":\"R_2_1000_20220109153159\",\"WDATE\":\"14\\/07\\/2020\",\"TOTAL\":\"\",\"DISCOUNT\":\"\",\"REQINDATE\":\"09\\/01\\/2022 15:32:01\",\"APPUSERNO\":\"1000\",\"APPUSERNM\":\"المستخدم الرئيسي\",\"APPROVNO\":\"\",\"APPROVREM\":\"\",\"APPRVINDATE\":\"09\\/01\\/2022 15:32:07\",\"ISDONE\":\"0\",\"KINDNM\":\"Return\",\"POSNM\":\"POS2\",\"APPSTATUS\":\"Waiting\"},{\"APPKIND\":\"0\",\"POSNO\":\"2\",\"USERNO\":\"1000\",\"USERNM\":\"المستخدم الرئيسي\",\"REQNO\":\"D_2_1000_20220109171411\",\"WDATE\":\"14\\/07\\/2020\",\"TOTAL\":\"200.00\",\"DISCOUNT\":\"20.00\",\"REQINDATE\":\"09\\/01\\/2022 17:15:28\",\"APPUSERNO\":\"1000\",\"APPUSERNM\":\"المستخدم الرئيسي\",\"APPROVNO\":\"\",\"APPROVREM\":\"\",\"APPRVINDATE\":\"09\\/01\\/2022 17:16:53\",\"ISDONE\":\"0\",\"KINDNM\":\"Discount\",\"POSNM\":\"POS2\",\"APPSTATUS\":\"Waiting\"},{\"APPKIND\":\"0\",\"POSNO\":\"2\",\"USERNO\":\"1000\",\"USERNM\":\"المستخدم الرئيسي\",\"REQNO\":\"D_2_1000_20220109171741\",\"WDATE\":\"14\\/07\\/2020\",\"TOTAL\":\"200.00\",\"DISCOUNT\":\"20.00\",\"REQINDATE\":\"09\\/01\\/2022 17:17:48\",\"APPUSERNO\":\"1000\",\"APPUSERNM\":\"المستخدم الرئيسي\",\"APPROVNO\":\"\",\"APPROVREM\":\"\",\"APPRVINDATE\":\"09\\/01\\/2022 17:18:27\",\"ISDONE\":\"0\",\"KINDNM\":\"Discount\",\"POSNM\":\"POS2\",\"APPSTATUS\":\"Waiting\"},{\"APPKIND\":\"1\",\"POSNO\":\"2\",\"USERNO\":\"1000\",\"USERNM\":\"المستخدم الرئيسي\",\"REQNO\":\"U_2_1000_20220109172036\",\"WDATE\":\"14\\/07\\/2020\",\"TOTAL\":\"\",\"DISCOUNT\":\"\",\"REQINDATE\":\"09\\/01\\/2022 17:20:50\",\"APPUSERNO\":\"1000\",\"APPUSERNM\":\"المستخدم الرئيسي\",\"APPROVNO\":\"\",\"APPROVREM\":\"\",\"APPRVINDATE\":\"09\\/01\\/2022 17:21:50\",\"ISDONE\":\"0\",\"KINDNM\":\"Update Price\",\"POSNM\":\"POS2\",\"APPSTATUS\":\"Waiting\"},{\"APPKIND\":\"2\",\"POSNO\":\"2\",\"USERNO\":\"1000\",\"USERNM\":\"المستخدم الرئيسي\",\"REQNO\":\"R_2_1000_20220109172324\",\"WDATE\":\"14\\/07\\/2020\",\"TOTAL\":\"\",\"DISCOUNT\":\"\",\"REQINDATE\":\"09\\/01\\/2022 17:23:31\",\"APPUSERNO\":\"1000\",\"APPUSERNM\":\"المستخدم الرئيسي\",\"APPROVNO\":\"\",\"APPROVREM\":\"\",\"APPRVINDATE\":\"09\\/01\\/2022 17:23:55\",\"ISDONE\":\"0\",\"KINDNM\":\"Return\",\"POSNM\":\"POS2\",\"APPSTATUS\":\"Waiting\"},{\"APPKIND\":\"2\",\"POSNO\":\"119\",\"USERNO\":\"1000\",\"USERNM\":\"المستخدم الرئيسي\",\"REQNO\":\"R_119_1000_20220310170412\",\"WDATE\":\"02\\/03\\/2022\",\"TOTAL\":\"\",\"DISCOUNT\":\"\",\"REQINDATE\":\"10\\/03\\/2022 17:04:29\",\"APPUSERNO\":\"\",\"APPUSERNM\":\"\",\"APPROVNO\":\"\",\"APPROVREM\":\"\",\"APPRVINDATE\":\"\",\"ISDONE\":\"0\",\"KINDNM\":\"Return\",\"POSNM\":\"\",\"APPSTATUS\":\"Waiting\"},{\"APPKIND\":\"1\",\"POSNO\":\"119\",\"USERNO\":\"1000\",\"USERNM\":\"المستخدم الرئيسي\",\"REQNO\":\"U_119_1000_20220305173254\",\"WDATE\":\"02\\/03\\/2022\",\"TOTAL\":\"\",\"DISCOUNT\":\"\",\"REQINDATE\":\"05\\/03\\/2022 17:32:56\",\"APPUSERNO\":\"200\",\"APPUSERNM\":\"ALAA\",\"APPROVNO\":\"\",\"APPROVREM\":\"\",\"APPRVINDATE\":\"09\\/01\\/2022\",\"ISDONE\":\"0\",\"KINDNM\":\"Update Price\",\"POSNM\":\"\",\"APPSTATUS\":\"Waiting\"}]}";
//
//                s=s.substring(s.indexOf("["),s.lastIndexOf("]")+1);
//                Log.e("fffff",""+s+"");
//                Gson gson = new Gson();
////                try {
////                    JSONArray jsonArray=new JSONArray(s);
//
//                    Type collectionType = new TypeToken<Collection<ListOfOrderData>>(){}.getType();
//                    Collection<ListOfOrderData> enums = gson.fromJson(s, collectionType);
//
////                    CaptainClientTransfer gsonObj = gson.fromJson(jsonArray.getJSONObject().toString(), CaptainClientTransfer.class);
//                    clientOrders.clear();
//                    // captainClientTransfers.addAll(enums.getOrderList());
//                    clientOrders= (List<ListOfOrderData>) enums;
//                    MainActivity mainValetActivity = (MainActivity) context;
//                    mainValetActivity.listOfOrder(clientOrders);
//                    //isOk=true;
////                } catch (JSONException e) {
////                    e.printStackTrace();
////                }

            }
        }

    }


}
