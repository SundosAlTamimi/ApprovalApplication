package com.example.approvalapp.Json;

import static androidx.core.content.ContextCompat.getSystemService;


import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;


import com.example.approvalapp.DetailActivity;
import com.example.approvalapp.MainActivity;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ExportJson {

    Context context;
    SweetAlertDialog swASingUp, swAsPay, swAsRate, sweetAlertDialogStatus,swANote, swATrans, sweetAlertDialogStatusDelete,sweetAlertDialogStatusDeleteBefore;
    String URL_TO_HIT;
    int Flag;

    public ExportJson(Context context) {
        this.context = context;
    }

    public void ApprovalRaw(Context context,String APPKIND,String POSNO,String USERNO,String USERNM,String REQNO,int Flag) {
this.Flag=Flag;
        if(Flag==1||Flag==2) {
            new Approval(context, APPKIND, POSNO, USERNO, USERNM, REQNO).execute();
        }else if(Flag==3||Flag==4) {
            new Reject(context, APPKIND, POSNO, USERNO, USERNM, REQNO).execute();
        }

    }


    private class Approval extends AsyncTask<String, String, String> {
        private String JsonResponse = null;
        private HttpURLConnection urlConnection = null;
        private BufferedReader reader = null;

        JSONObject jsonObject;
        String APPKIND, POSNO, USERNO, USERNM, REQNO;

        public Approval(Context context, String APPKIND,String POSNO,String USERNO,String USERNM,String REQNO) {

            //this.jsonObject = jsonObject;
            this.APPKIND=APPKIND;
            this.POSNO=POSNO;
            this.USERNO=USERNO;
            this.USERNM=USERNM;
            this.REQNO=REQNO;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            swATrans = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
            swATrans.getProgressHelper().setBarColor(Color.parseColor("#FDD835"));
            swATrans.setTitleText("Loading ...");
            swATrans.setCancelable(false);
            swATrans.show();

            //isOk = false;

        }

        @Override
        protected String doInBackground(String... params) {
            try {
//                valetDatabase = new ValetDatabase(context);
//                String ip = valetDatabase.getAllIPSetting();//192.168.1.101:81

                String link = "http://10.0.0.16:8080/BCIAPP/main.dll/MakeApproval?";


                String data = "APPKIND=" + APPKIND
                        + "&POSNO=" + POSNO
                        + "&USERNO=" + USERNO
                        + "&USERNM=" +USERNM
                        + "&REQNO=" + REQNO;
                URL url = new URL(link + data);


                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                httpURLConnection.setRequestMethod("POST");

//                DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
//                wr.writeBytes(data);
//                wr.flush();
//                wr.close();
                Log.e("url____", "" + link + data);

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                StringBuffer stringBuffer = new StringBuffer();

                while ((JsonResponse = bufferedReader.readLine()) != null) {
                    stringBuffer.append(JsonResponse + "\n");
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                Log.e("tag", "TAG_itemSwitch -->" + stringBuffer.toString());

                return stringBuffer.toString();

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("tag", "Error closing stream", e);
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);


            if (s != null) {
                if (s.contains("APPNUM")) {
                    swATrans.dismissWithAnimation();
//                    Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
//                    new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE);
//                    swATrans   .setTitleText("approved !!!");
//                    swATrans  .setContentText("");
//
////                            .setCancelButton("cancel", new SweetAlertDialog.OnSweetClickListener() {
////                                @Override
////                                public void onClick(SweetAlertDialog sweetAlertDialog) {
////                                    sweetAlertDialog.dismissWithAnimation();
////                                }
////                            })
//                    swATrans .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
//                                @Override
//                                public void onClick(SweetAlertDialog sweetAlertDialog) {
//                                    if(Flag==2||Flag==4){
//                                        DetailActivity detailActivity=(DetailActivity)context;
//
//                                        detailActivity.finishLayout();
//                                    }
//                                    sweetAlertDialog.dismissWithAnimation();
//
//
//                                }
//                            });
//                    swATrans.setCanceledOnTouchOutside(false);
                    Toast.makeText(context, "approved Successful", Toast.LENGTH_LONG).show();
                    if(Flag==2||Flag==4){
                        DetailActivity detailActivity=(DetailActivity)context;

                        detailActivity.finishLayout();
                    }
                    swATrans .show();

                    ImportJson importJson=new ImportJson(context);
                    importJson.getOrder();
                } else if (s.contains("Request already approved")||s.contains("Request already rejected")) {
                    swATrans.dismissWithAnimation();
//                    new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
//                            .setTitleText("Request already approved !!!")
//                            .setContentText("")
////                            .setCancelButton("cancel", new SweetAlertDialog.OnSweetClickListener() {
////                                @Override
////                                public void onClick(SweetAlertDialog sweetAlertDialog) {
////                                    sweetAlertDialog.dismissWithAnimation();
////                                }
////                            })
//                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
//                                @Override
//                                public void onClick(SweetAlertDialog sweetAlertDialog) {
//                                    if(Flag==2||Flag==4){
//                                        DetailActivity detailActivity=(DetailActivity)context;
//
//                                        detailActivity.finishLayout();
//                                    }
//
//                                    sweetAlertDialog.dismissWithAnimation();
//
//
//                                }
//                            })
//                            .show();

                    if(s.contains("Request already rejected")){

                        Toast.makeText(context, "Request already rejected", Toast.LENGTH_LONG).show();


                    }else if(s.contains("Request already approved")){
                        Toast.makeText(context, "Request already approved", Toast.LENGTH_LONG).show();

                    }
                    if(Flag==2||Flag==4){
                        DetailActivity detailActivity=(DetailActivity)context;

                        detailActivity.finishLayout();
                    }

                    ImportJson importJson=new ImportJson(context);
                    importJson.getOrder();
                    //


                }else{
                    swATrans.dismissWithAnimation();
                }
            } else {
                new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Please Try Again !!!")
                        .setContentText("")
                        .setCancelButton("cancel", new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismissWithAnimation();
                            }
                        })
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {

                                sweetAlertDialog.dismissWithAnimation();


                            }
                        })
                        .show();
                swATrans.dismissWithAnimation();
              //  isOk = true;
            }
        }
    }

    private class Reject extends AsyncTask<String, String, String> {
        private String JsonResponse = null;
        private HttpURLConnection urlConnection = null;
        private BufferedReader reader = null;

        JSONObject jsonObject;
        String APPKIND, POSNO, USERNO, USERNM, REQNO;

        public Reject(Context context, String APPKIND,String POSNO,String USERNO,String USERNM,String REQNO) {

            //this.jsonObject = jsonObject;
            this.APPKIND=APPKIND;
            this.POSNO=POSNO;
            this.USERNO=USERNO;
            this.USERNM=USERNM;
            this.REQNO=REQNO;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            swATrans = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
            swATrans.getProgressHelper().setBarColor(Color.parseColor("#FDD835"));
            swATrans.setTitleText("Loading ...");
            swATrans.setCancelable(false);
            swATrans.show();

            //isOk = false;

        }

        @Override
        protected String doInBackground(String... params) {
            try {
//                valetDatabase = new ValetDatabase(context);
//                String ip = valetDatabase.getAllIPSetting();//192.168.1.101:81

                String link = "http://10.0.0.16:8080/BCIAPP/main.dll/MakeReject?";


                String data = "APPKIND=" + APPKIND
                        + "&POSNO=" + POSNO
                        + "&USERNO=" + USERNO
                        + "&USERNM=" +USERNM
                        + "&REQNO=" + REQNO;
                URL url = new URL(link + data);


                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                httpURLConnection.setRequestMethod("POST");

//                DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
//                wr.writeBytes(data);
//                wr.flush();
//                wr.close();
                Log.e("url____", "" + link + data);

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                StringBuffer stringBuffer = new StringBuffer();

                while ((JsonResponse = bufferedReader.readLine()) != null) {
                    stringBuffer.append(JsonResponse + "\n");
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                Log.e("tag", "TAG_itemSwitch -->" + stringBuffer.toString());

                return stringBuffer.toString();

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("tag", "Error closing stream", e);
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);


            if (s != null) {
                if (s.contains("RJNUM")) {
                    swATrans.dismissWithAnimation();
//                    Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();

                    if(Flag==2||Flag==4){
                                        DetailActivity detailActivity=(DetailActivity)context;

                                        detailActivity.finishLayout();
                                    }

//                    new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE)
//                            .setTitleText(" rejected Successful !!!")
//                            .setContentText("")
////                            .setCancelButton("cancel", new SweetAlertDialog.OnSweetClickListener() {
////                                @Override
////                                public void onClick(SweetAlertDialog sweetAlertDialog) {
////
////
////                                    sweetAlertDialog.dismissWithAnimation();
////                                }
////                            })
//                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
//                                @Override
//                                public void onClick(SweetAlertDialog sweetAlertDialog) {
//
//                                    if(Flag==2||Flag==4){
//                                        DetailActivity detailActivity=(DetailActivity)context;
//
//                                        detailActivity.finishLayout();
//                                    }
//                                    sweetAlertDialog.dismissWithAnimation();
//
//
//                                }
//                            })
//                            .show();
                    Toast.makeText(context, "rejected Successful", Toast.LENGTH_LONG).show();

                    ImportJson importJson=new ImportJson(context);
                    importJson.getOrder();

                } else if (s.contains("Request already rejected")||s.contains("Request already approved")) {
                    swATrans.dismissWithAnimation();
//                    new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
//                            .setTitleText("Request already rejected !!!")
//                            .setContentText("")
////                            .setCancelButton("cancel", new SweetAlertDialog.OnSweetClickListener() {
////                                @Override
////                                public void onClick(SweetAlertDialog sweetAlertDialog) {
////                                    sweetAlertDialog.dismissWithAnimation();
////                                }
////                            })
//                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
//                                @Override
//                                public void onClick(SweetAlertDialog sweetAlertDialog) {
//
//                                    if(Flag==2||Flag==4){
//                                        DetailActivity detailActivity=(DetailActivity)context;
//
//                                        detailActivity.finishLayout();
//                                    }
//                                    sweetAlertDialog.dismissWithAnimation();
//
//
//                                }
//                            })
//                            .show();

                   if(s.contains("Request already rejected")){

                       Toast.makeText(context, "Request already rejected", Toast.LENGTH_LONG).show();


                   }else if(s.contains("Request already approved")){
                       Toast.makeText(context, "Request already approved", Toast.LENGTH_LONG).show();

                   }
                    if(Flag==2||Flag==4){
                                        DetailActivity detailActivity=(DetailActivity)context;

                                        detailActivity.finishLayout();
                                    }
                    ImportJson importJson=new ImportJson(context);
                    importJson.getOrder();

                    //


                }else{
                    swATrans.dismissWithAnimation();
                }
            } else {
                swATrans.dismissWithAnimation();

                new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Please Try Again !!!")
                        .setContentText("")
                        .setCancelButton("cancel", new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismissWithAnimation();
                            }
                        })
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {

                                sweetAlertDialog.dismissWithAnimation();


                            }
                        })
                        .show();
                //  isOk = true;
            }
        }
    }


}
