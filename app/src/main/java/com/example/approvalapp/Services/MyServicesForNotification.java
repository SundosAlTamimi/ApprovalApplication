package com.example.approvalapp.Services;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.room.Room;

import com.example.approvalapp.Json.ImportJson;
import com.example.approvalapp.MainActivity;
import com.example.approvalapp.Model.ListOfOrderData;
import com.example.approvalapp.R;
import com.example.approvalapp.ROOM.AppDatabase;
import com.example.approvalapp.ROOM.UserDaoCard;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.HttpResponse;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.HttpClient;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.methods.HttpGet;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.conn.HttpHostConnectException;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.impl.client.DefaultHttpClient;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MyServicesForNotification extends Service {
    private static final String TAG = "BackgroundTimerServiceS";
    MediaPlayer player;
    static String id;
    public static final String ServiceIntent = "MyServices";
    public static boolean ServiceWork = true;
    Timer T ;
    String URL_TO_HIT="";

    List<ListOfOrderData> clientOrders;

    public IBinder onBind(Intent arg0) {
        Log.e(TAG, "onBind()");
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        //FirebaseApp.initializeApp(MyServicesForNotification.this);
     //   valetDatabase = new ValetDatabase(this);
        //userService=new UserService();
     //   id=valetDatabase.getAllUser();

        Log.e(TAG, "onCreate() , service started...");
        clientOrders=new ArrayList<>();

    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        //requestLocationUpdates();


        T = new Timer();

        T.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {

                    allTaskInFireBase();

                } catch (Exception e) {
                    Log.e("error in sticky", "no data in order header ...");
                }
//                message();

            }
        }, 10000, 3000);

        return START_STICKY;

    }


    public IBinder onUnBind(Intent arg0) {
        Log.e(TAG, "onUnBind()");
        return null;
    }

    @Override
    public boolean stopService(Intent name) {
        // TODO Auto-generated method stub


        return super.stopService(name);

    }

    public void onPause() {
        Log.e(TAG, "onPause()");
    }

    @Override
    public void onDestroy() {


        T = new Timer();

        T.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {

                    allTaskInFireBase();

                } catch (Exception e) {
                    Log.e("error in sticky", "no data in order header ...");
                }
//                message();

            }
        }, 10000, 3000);

        Log.e(TAG, "onCreate() , service stopped...");
    }

    @Override
    public void onLowMemory() {
        Log.e(TAG, "onLowMemory()");
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        T = new Timer();

        T.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {

                    allTaskInFireBase();

                } catch (Exception e) {
                    Log.e("error in sticky", "no data in order header ...");
                }
//                message();

            }
        }, 10000, 3000);
        Log.e(TAG, "In onTaskRemoved");
    }


    void allTaskInFireBase(){
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

                        Toast.makeText(MyServicesForNotification.this, "Ip Connection Failed ", Toast.LENGTH_LONG).show();
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
           // s="{\"StatusCode\":0,\"Descreption\":\"OK\",\"INFO\":[{\"APPKIND\":\"0\",\"POSNO\":\"2\",\"USERNO\":\"1000\",\"USERNM\":\"المستخدم الرئيسي\",\"REQNO\":\"D_2_1000_20211230184756\",\"WDATE\":\"14\\/07\\/2020\",\"TOTAL\":\"9.000\",\"DISCOUNT\":\"0.900\",\"REQINDATE\":\"30\\/12\\/2021 18:47:58\",\"APPUSERNO\":\"1000\",\"APPUSERNM\":\"المستخدم الرئيسي\",\"APPROVNO\":\"\",\"APPROVREM\":\"\",\"APPRVINDATE\":\"30\\/12\\/2021 19:03:39\",\"ISDONE\":\"0\",\"KINDNM\":\"Discount\",\"POSNM\":\"POS2\",\"APPSTATUS\":\"Waiting\"},{\"APPKIND\":\"0\",\"POSNO\":\"2\",\"USERNO\":\"1000\",\"USERNM\":\"المستخدم الرئيسي\",\"REQNO\":\"D_2_1000_20220108142222242\",\"WDATE\":\"14\\/07\\/2020\",\"TOTAL\":\"200.000\",\"DISCOUNT\":\"20.000\",\"REQINDATE\":\"08\\/01\\/2022 14:53:44\",\"APPUSERNO\":\"1000\",\"APPUSERNM\":\"المستخدم الرئيسي\",\"APPROVNO\":\"\",\"APPROVREM\":\"\",\"APPRVINDATE\":\"08\\/01\\/2022 14:57:52\",\"ISDONE\":\"0\",\"KINDNM\":\"Discount\",\"POSNM\":\"POS2\",\"APPSTATUS\":\"Waiting\"},{\"APPKIND\":\"0\",\"POSNO\":\"2\",\"USERNO\":\"1000\",\"USERNM\":\"المستخدم الرئيسي\",\"REQNO\":\"D_2_1000_20220108144543\",\"WDATE\":\"14\\/07\\/2020\",\"TOTAL\":\"200.00\",\"DISCOUNT\":\"20.00\",\"REQINDATE\":\"08\\/01\\/2022 14:46:10\",\"APPUSERNO\":\"1000\",\"APPUSERNM\":\"المستخدم الرئيسي\",\"APPROVNO\":\"\",\"APPROVREM\":\"\",\"APPRVINDATE\":\"08\\/01\\/2022 14:47:17\",\"ISDONE\":\"0\",\"KINDNM\":\"Discount\",\"POSNM\":\"POS2\",\"APPSTATUS\":\"Waiting\"},{\"APPKIND\":\"0\",\"POSNO\":\"2\",\"USERNO\":\"1000\",\"USERNM\":\"المستخدم الرئيسي\",\"REQNO\":\"D_2_1000_202201081144543\",\"WDATE\":\"14\\/07\\/2020\",\"TOTAL\":\"200.00\",\"DISCOUNT\":\"20.00\",\"REQINDATE\":\"08\\/01\\/2022 14:47:36\",\"APPUSERNO\":\"1000\",\"APPUSERNM\":\"المستخدم الرئيسي\",\"APPROVNO\":\"\",\"APPROVREM\":\"\",\"APPRVINDATE\":\"08\\/01\\/2022 14:48:00\",\"ISDONE\":\"0\",\"KINDNM\":\"Discount\",\"POSNM\":\"POS2\",\"APPSTATUS\":\"Waiting\"},{\"APPKIND\":\"0\",\"POSNO\":\"2\",\"USERNO\":\"1000\",\"USERNM\":\"المستخدم الرئيسي\",\"REQNO\":\"D_2_1000_20220108144809\",\"WDATE\":\"14\\/07\\/2020\",\"TOTAL\":\"200.00\",\"DISCOUNT\":\"20.00\",\"REQINDATE\":\"08\\/01\\/2022 14:48:13\",\"APPUSERNO\":\"1000\",\"APPUSERNM\":\"المستخدم الرئيسي\",\"APPROVNO\":\"\",\"APPROVREM\":\"\",\"APPRVINDATE\":\"08\\/01\\/2022 14:48:23\",\"ISDONE\":\"0\",\"KINDNM\":\"Discount\",\"POSNM\":\"POS2\",\"APPSTATUS\":\"Waiting\"},{\"APPKIND\":\"0\",\"POSNO\":\"2\",\"USERNO\":\"1000\",\"USERNM\":\"المستخدم الرئيسي\",\"REQNO\":\"D_2_1000_20220108145758\",\"WDATE\":\"14\\/07\\/2020\",\"TOTAL\":\"200.00\",\"DISCOUNT\":\"20.00\",\"REQINDATE\":\"08\\/01\\/2022 14:58:00\",\"APPUSERNO\":\"1000\",\"APPUSERNM\":\"المستخدم الرئيسي\",\"APPROVNO\":\"\",\"APPROVREM\":\"\",\"APPRVINDATE\":\"08\\/01\\/2022 14:58:07\",\"ISDONE\":\"0\",\"KINDNM\":\"Discount\",\"POSNM\":\"POS2\",\"APPSTATUS\":\"Waiting\"},{\"APPKIND\":\"0\",\"POSNO\":\"2\",\"USERNO\":\"1000\",\"USERNM\":\"المستخدم الرئيسي\",\"REQNO\":\"D_2_1000_20220108145905\",\"WDATE\":\"14\\/07\\/2020\",\"TOTAL\":\"200.00\",\"DISCOUNT\":\"20.00\",\"REQINDATE\":\"08\\/01\\/2022 14:59:10\",\"APPUSERNO\":\"1000\",\"APPUSERNM\":\"المستخدم الرئيسي\",\"APPROVNO\":\"\",\"APPROVREM\":\"\",\"APPRVINDATE\":\"08\\/01\\/2022 14:59:31\",\"ISDONE\":\"0\",\"KINDNM\":\"Discount\",\"POSNM\":\"POS2\",\"APPSTATUS\":\"Waiting\"},{\"APPKIND\":\"0\",\"POSNO\":\"2\",\"USERNO\":\"1000\",\"USERNM\":\"المستخدم الرئيسي\",\"REQNO\":\"D_2_1000_20220109152811\",\"WDATE\":\"14\\/07\\/2020\",\"TOTAL\":\"200.00\",\"DISCOUNT\":\"20.00\",\"REQINDATE\":\"09\\/01\\/2022 15:28:59\",\"APPUSERNO\":\"1000\",\"APPUSERNM\":\"المستخدم الرئيسي\",\"APPROVNO\":\"\",\"APPROVREM\":\"\",\"APPRVINDATE\":\"09\\/01\\/2022 15:29:18\",\"ISDONE\":\"0\",\"KINDNM\":\"Discount\",\"POSNM\":\"POS2\",\"APPSTATUS\":\"Waiting\"},{\"APPKIND\":\"1\",\"POSNO\":\"2\",\"USERNO\":\"1000\",\"USERNM\":\"المستخدم الرئيسي\",\"REQNO\":\"U_2_1000_20220109152944\",\"WDATE\":\"14\\/07\\/2020\",\"TOTAL\":\"\",\"DISCOUNT\":\"\",\"REQINDATE\":\"09\\/01\\/2022 15:29:47\",\"APPUSERNO\":\"1000\",\"APPUSERNM\":\"المستخدم الرئيسي\",\"APPROVNO\":\"\",\"APPROVREM\":\"\",\"APPRVINDATE\":\"09\\/01\\/2022 15:29:59\",\"ISDONE\":\"0\",\"KINDNM\":\"Update Price\",\"POSNM\":\"POS2\",\"APPSTATUS\":\"Waiting\"},{\"APPKIND\":\"1\",\"POSNO\":\"2\",\"USERNO\":\"1000\",\"USERNM\":\"المستخدم الرئيسي\",\"REQNO\":\"U_2_1000_20220109153012\",\"WDATE\":\"14\\/07\\/2020\",\"TOTAL\":\"\",\"DISCOUNT\":\"\",\"REQINDATE\":\"09\\/01\\/2022 15:30:16\",\"APPUSERNO\":\"1000\",\"APPUSERNM\":\"المستخدم الرئيسي\",\"APPROVNO\":\"\",\"APPROVREM\":\"\",\"APPRVINDATE\":\"09\\/01\\/2022 15:30:21\",\"ISDONE\":\"0\",\"KINDNM\":\"Update Price\",\"POSNM\":\"POS2\",\"APPSTATUS\":\"Waiting\"},{\"APPKIND\":\"2\",\"POSNO\":\"2\",\"USERNO\":\"1000\",\"USERNM\":\"المستخدم الرئيسي\",\"REQNO\":\"R_2_1000_20220109153051\",\"WDATE\":\"14\\/07\\/2020\",\"TOTAL\":\"\",\"DISCOUNT\":\"\",\"REQINDATE\":\"09\\/01\\/2022 15:30:54\",\"APPUSERNO\":\"1000\",\"APPUSERNM\":\"المستخدم الرئيسي\",\"APPROVNO\":\"\",\"APPROVREM\":\"\",\"APPRVINDATE\":\"09\\/01\\/2022 15:31:32\",\"ISDONE\":\"0\",\"KINDNM\":\"Return\",\"POSNM\":\"POS2\",\"APPSTATUS\":\"Waiting\"},{\"APPKIND\":\"2\",\"POSNO\":\"2\",\"USERNO\":\"1000\",\"USERNM\":\"المستخدم الرئيسي\",\"REQNO\":\"R_2_1000_20220109153159\",\"WDATE\":\"14\\/07\\/2020\",\"TOTAL\":\"\",\"DISCOUNT\":\"\",\"REQINDATE\":\"09\\/01\\/2022 15:32:01\",\"APPUSERNO\":\"1000\",\"APPUSERNM\":\"المستخدم الرئيسي\",\"APPROVNO\":\"\",\"APPROVREM\":\"\",\"APPRVINDATE\":\"09\\/01\\/2022 15:32:07\",\"ISDONE\":\"0\",\"KINDNM\":\"Return\",\"POSNM\":\"POS2\",\"APPSTATUS\":\"Waiting\"},{\"APPKIND\":\"0\",\"POSNO\":\"2\",\"USERNO\":\"1000\",\"USERNM\":\"المستخدم الرئيسي\",\"REQNO\":\"D_2_1000_20220109171411\",\"WDATE\":\"14\\/07\\/2020\",\"TOTAL\":\"200.00\",\"DISCOUNT\":\"20.00\",\"REQINDATE\":\"09\\/01\\/2022 17:15:28\",\"APPUSERNO\":\"1000\",\"APPUSERNM\":\"المستخدم الرئيسي\",\"APPROVNO\":\"\",\"APPROVREM\":\"\",\"APPRVINDATE\":\"09\\/01\\/2022 17:16:53\",\"ISDONE\":\"0\",\"KINDNM\":\"Discount\",\"POSNM\":\"POS2\",\"APPSTATUS\":\"Waiting\"},{\"APPKIND\":\"0\",\"POSNO\":\"2\",\"USERNO\":\"1000\",\"USERNM\":\"المستخدم الرئيسي\",\"REQNO\":\"D_2_1000_20220109171741\",\"WDATE\":\"14\\/07\\/2020\",\"TOTAL\":\"200.00\",\"DISCOUNT\":\"20.00\",\"REQINDATE\":\"09\\/01\\/2022 17:17:48\",\"APPUSERNO\":\"1000\",\"APPUSERNM\":\"المستخدم الرئيسي\",\"APPROVNO\":\"\",\"APPROVREM\":\"\",\"APPRVINDATE\":\"09\\/01\\/2022 17:18:27\",\"ISDONE\":\"0\",\"KINDNM\":\"Discount\",\"POSNM\":\"POS2\",\"APPSTATUS\":\"Waiting\"},{\"APPKIND\":\"1\",\"POSNO\":\"2\",\"USERNO\":\"1000\",\"USERNM\":\"المستخدم الرئيسي\",\"REQNO\":\"U_2_1000_20220109172036\",\"WDATE\":\"14\\/07\\/2020\",\"TOTAL\":\"\",\"DISCOUNT\":\"\",\"REQINDATE\":\"09\\/01\\/2022 17:20:50\",\"APPUSERNO\":\"1000\",\"APPUSERNM\":\"المستخدم الرئيسي\",\"APPROVNO\":\"\",\"APPROVREM\":\"\",\"APPRVINDATE\":\"09\\/01\\/2022 17:21:50\",\"ISDONE\":\"0\",\"KINDNM\":\"Update Price\",\"POSNM\":\"POS2\",\"APPSTATUS\":\"Waiting\"},{\"APPKIND\":\"2\",\"POSNO\":\"2\",\"USERNO\":\"1000\",\"USERNM\":\"المستخدم الرئيسي\",\"REQNO\":\"R_2_1000_20220109172324\",\"WDATE\":\"14\\/07\\/2020\",\"TOTAL\":\"\",\"DISCOUNT\":\"\",\"REQINDATE\":\"09\\/01\\/2022 17:23:31\",\"APPUSERNO\":\"1000\",\"APPUSERNM\":\"المستخدم الرئيسي\",\"APPROVNO\":\"\",\"APPROVREM\":\"\",\"APPRVINDATE\":\"09\\/01\\/2022 17:23:55\",\"ISDONE\":\"0\",\"KINDNM\":\"Return\",\"POSNM\":\"POS2\",\"APPSTATUS\":\"Waiting\"},{\"APPKIND\":\"2\",\"POSNO\":\"119\",\"USERNO\":\"1000\",\"USERNM\":\"المستخدم الرئيسي\",\"REQNO\":\"R_119_1000_20220310170412\",\"WDATE\":\"02\\/03\\/2022\",\"TOTAL\":\"\",\"DISCOUNT\":\"\",\"REQINDATE\":\"10\\/03\\/2022 17:04:29\",\"APPUSERNO\":\"\",\"APPUSERNM\":\"\",\"APPROVNO\":\"\",\"APPROVREM\":\"\",\"APPRVINDATE\":\"\",\"ISDONE\":\"0\",\"KINDNM\":\"Return\",\"POSNM\":\"\",\"APPSTATUS\":\"Waiting\"},{\"APPKIND\":\"1\",\"POSNO\":\"119\",\"USERNO\":\"1000\",\"USERNM\":\"المستخدم الرئيسي\",\"REQNO\":\"U_119_1000_20220305173254\",\"WDATE\":\"02\\/03\\/2022\",\"TOTAL\":\"\",\"DISCOUNT\":\"\",\"REQINDATE\":\"05\\/03\\/2022 17:32:56\",\"APPUSERNO\":\"200\",\"APPUSERNM\":\"ALAA\",\"APPROVNO\":\"\",\"APPROVREM\":\"\",\"APPRVINDATE\":\"09\\/01\\/2022\",\"ISDONE\":\"0\",\"KINDNM\":\"Update Price\",\"POSNM\":\"\",\"APPSTATUS\":\"Waiting\"}]}";

            if (s != null) {

                if (s.contains("APPKIND")) {


                    Gson gson = new Gson();
                    try {
//                        //JSONArray jsonArray=new JSONArray(s);
                        s=s.substring(s.indexOf("["),s.lastIndexOf("]")+1);
                        //Log.e("fffff",""+s+"");
                        Type collectionType = new TypeToken<Collection<ListOfOrderData>>(){}.getType();
                        Collection<ListOfOrderData> enums = gson.fromJson(s, collectionType);

//                    CaptainClientTransfer gsonObj = gson.fromJson(jsonArray.getJSONObject().toString(), CaptainClientTransfer.class);
                        clientOrders.clear();
                        // captainClientTransfers.addAll(enums.getOrderList());
                        clientOrders= (List<ListOfOrderData>) enums;
                        if(!getAllReq(clientOrders)){
                            Intent intent = new Intent(MyServicesForNotification.this, MyServicesForNotification.class);

                            showNotification(MyServicesForNotification.this,"Notification","More Order",intent);

                            saveNotificationReq(clientOrders);
                        }


//                        //isOk=true;

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {

                    Log.e("onPostExecute", "" + s.toString());
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


    public void showNotification(Context context, String title, String body, Intent intent) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        int notificationId = 1;
        String channelId = "channel-03";
        String channelName = "Channel Name";
        int importance = NotificationManager.IMPORTANCE_HIGH;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(
                    channelId, channelName, importance);
            notificationManager.createNotificationChannel(mChannel);
        }

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(body);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addNextIntent(intent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(
                0,
                PendingIntent.FLAG_UPDATE_CURRENT
        );
        mBuilder.setContentIntent(resultPendingIntent);

        notificationManager.notify(notificationId, mBuilder.build());
    }


    public void saveNotificationReq(List<ListOfOrderData>list){
        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "ApprovalDBase") .fallbackToDestructiveMigration().allowMainThreadQueries().build();

        UserDaoCard userDao = db.itemCard();

         userDao.deleteAll();
        userDao.insertAll(list);

        // List<UserModel> users = userDao.getAll();


    }


    public boolean getAllReq(List<ListOfOrderData>list){
        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "ApprovalDBase") .fallbackToDestructiveMigration().allowMainThreadQueries().build();

        UserDaoCard userDao = db.itemCard();

         List<String> users = userDao.getAll();

         boolean isContain=false;
         for(int i=0;i<list.size();i++){
             if(users.contains(list.get(i).getREQNO())){
                 isContain=true;
             }else {
                 isContain=false;
                 break;
             }
         }
Log.e("servise",""+isContain);
         return isContain;

    }


}