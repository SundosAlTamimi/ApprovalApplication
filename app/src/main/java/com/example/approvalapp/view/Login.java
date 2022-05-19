package com.example.approvalapp.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.approvalapp.MainActivity;
import com.example.approvalapp.R;
import com.example.approvalapp.model.UserInfo;
import com.example.approvalapp.retrofit.ApiLogin;
import com.example.approvalapp.retrofit.RetrofitInstance;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Login extends AppCompatActivity {

    EditText unameEdt,passwordEdt;
    Button login;
    ApiLogin myAPI;
    SweetAlertDialog pDialog,sweetDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initial();

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

}