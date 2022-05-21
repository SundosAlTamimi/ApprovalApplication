package com.example.approvalapp.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.approvalapp.MainActivity;
import com.example.approvalapp.R;
import com.example.approvalapp.Model.UserInfo;
import com.example.approvalapp.retrofit.ApiLogin;
import com.example.approvalapp.retrofit.RetrofitInstance;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Login extends AppCompatActivity {
    private EditText ipEdt;
    EditText unameEdt,passwordEdt;
    Button login;
    ApiLogin myAPI;
    SweetAlertDialog pDialog,sweetDialog;
    public final static String SETTINGS_PREFERENCES = "SETTINGS_PREFERENCES";
    public final static String IPAdd_PREF = "IP_Address";
    String ipAddress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initial();
        if (!checkIpSettings())
            showSettingsDialog();


        findViewById(R.id.request_ip).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSettingsDialog();
            }
        });
    }

    private void initial() {
        unameEdt=findViewById(R.id.unameEdt);
        passwordEdt=findViewById(R.id.passEdt);
        login=findViewById(R.id.login);
        login.setOnClickListener(onClick);
    }
    View.OnClickListener onClick=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(view.getId()==R.id.login){
                validateUserPass();
            }
        }
    };

    private void validateUserPass() {
        String user="",password="";
        user=unameEdt.getText().toString().trim();
        password=passwordEdt.getText().toString().trim();
        if(user.length()!=0)
        {
            if(passwordEdt.length()!=0)
            {
                loginUser();
                fetchCallData(user,password);
            }else {
                passwordEdt.setError("*Required");
            }
        }else {
            unameEdt.setError("*Required");
        }
    }

    private void loginUser() {
        String link = "http://10.0.0.16:8080/BCIAPP/main.dll/" ;
        Retrofit retrofit = RetrofitInstance.getInstance(link);
        myAPI = retrofit.create(ApiLogin.class);
    }
    public void fetchCallData(String user, String password) {
        pDialog = new SweetAlertDialog(Login.this, SweetAlertDialog.PROGRESS_TYPE);

        pDialog.getProgressHelper().setBarColor(Color.parseColor("#31AFB4"));
        pDialog.setTitleText("Loading ...");
        pDialog.setCancelable(false);
        pDialog.show();
        Call<UserInfo> myData = myAPI.gaUserInfo(user,password);

        myData.enqueue(new Callback<UserInfo>() {
            @Override
            public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                pDialog.dismissWithAnimation();
                Log.e("onResponse", "=" + response.body().toString());
                if (!response.isSuccessful()) {

                } else {
                    Log.e("onResponse", "not=" + response.body().getStatusCode());

                    if(response.body().getStatusCode().equals("0"))
                    {
                        Log.e("onResponse", "not=" + response.body().getUserName());
                        Intent intent=new Intent(Login.this, MainActivity.class);
                        startActivity(intent);

                    }else if(response.body().getStatusCode().equals("11"))
                    {
                        showSweetInvalidUser(getResources().getString(R.string.invalid_user));
                    }
                    else if(response.body().getStatusCode().equals("12"))
                    {
                        showSweetInvalidUser(getResources().getString(R.string.invalid_pass));
                    }

                }
            }

            @Override
            public void onFailure(Call<UserInfo> call, Throwable throwable) {
                pDialog.dismissWithAnimation();
                Toast.makeText(Login.this, "throwable"+throwable.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showSweetInvalidUser(String message) {
        sweetDialog = new SweetAlertDialog(Login.this, SweetAlertDialog.WARNING_TYPE);
        sweetDialog.setTitleText(""+message);
        sweetDialog.setCancelable(true);
        sweetDialog.show();

    }
    void showSettingsDialog() {

        final Dialog ip_settings_dialog = new Dialog(Login.this);
        ip_settings_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        ip_settings_dialog.setCancelable(false);
        ip_settings_dialog.setContentView(R.layout.settingdailog);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(ip_settings_dialog.getWindow().getAttributes());
        lp.width = (int) (getResources().getDisplayMetrics().widthPixels / 1.19);
        ip_settings_dialog.getWindow().setAttributes(lp);

        ip_settings_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ip_settings_dialog.show();






        ipEdt = ip_settings_dialog.findViewById(R.id.ipEdt);

        AppCompatButton okBtn, cancelBtn;
        okBtn = ip_settings_dialog.findViewById(R.id.okBtn);
        cancelBtn = ip_settings_dialog.findViewById(R.id.cancelBtn);

        SharedPreferences sharedPref = getSharedPreferences(SETTINGS_PREFERENCES, MODE_PRIVATE);

        ipEdt.setText(sharedPref.getString(IPAdd_PREF, ""));

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ip_settings_dialog.dismiss();

            }
        });

        ipEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                ipEdt.setError(null);

            }
        });





        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String ipAddress = ipEdt.getText().toString().trim();


                if (!ipAddress.equals("")) {





                            SharedPreferences.Editor editor = getSharedPreferences(SETTINGS_PREFERENCES, MODE_PRIVATE).edit();
                            editor.putString(IPAdd_PREF, ipAddress);

                            editor.apply();



                            ip_settings_dialog.dismiss();





                } else {

                    ipEdt.setError(getString(R.string.required));


                }

            }
        });

    }
    private boolean checkIpSettings() {

        SharedPreferences sharedPref = getSharedPreferences(SETTINGS_PREFERENCES, MODE_PRIVATE);
        ipAddress = sharedPref.getString(IPAdd_PREF, "");
        Log.e("IP_PREF", ipAddress + "");

        return !(ipAddress + "").trim().equals("");

    }
}