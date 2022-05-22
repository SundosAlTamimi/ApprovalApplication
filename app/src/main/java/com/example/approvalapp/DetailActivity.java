package com.example.approvalapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.approvalapp.Json.ExportJson;
import com.example.approvalapp.Model.ListOfOrderData;

import org.w3c.dom.Text;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class DetailActivity extends AppCompatActivity {
    Button accept,rej;
    ListOfOrderData detailList;
    TextView userName,date,total,discount,KindNum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_two);
        initialization();


    }

    private void initialization() {

        accept=findViewById(R.id.acceptButton);
        rej=findViewById(R.id.rejectButton);
         userName=findViewById(R.id.userName);
         date=findViewById(R.id.date);
         total=findViewById(R.id.total);
         discount=findViewById(R.id.discount);
         KindNum=findViewById(R.id.KindNum);

        try {
            detailList = (ListOfOrderData) getIntent().getSerializableExtra("DetailData");

            userName.setText(""+detailList.getUSERNM());

            date.setText(""+detailList.getWDATE());
            total.setText(""+detailList.getTOTAL());
            discount.setText(""+detailList.getDISCOUNT());
            KindNum.setText(""+detailList.getKINDNM());


        }catch (Exception e){

        }

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {


                    ExportJson exportJson = new ExportJson(DetailActivity.this);
                    exportJson.ApprovalRaw(DetailActivity.this, detailList.getAPPKIND(),detailList.getPOSNO(),detailList.getUSERNO()
                            ,detailList.getUSERNM(),detailList.getREQNO(),2);

                }catch (Exception e){

                    SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(DetailActivity.this, SweetAlertDialog.SUCCESS_TYPE);
                    sweetAlertDialog.setTitleText("ERROR");
                    sweetAlertDialog.setContentText("Please Try Again");
                    sweetAlertDialog.setConfirmText("Ok");
                    sweetAlertDialog.setCanceledOnTouchOutside(false);
                    sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @SuppressLint("WrongConstant")
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {

                            sDialog.dismissWithAnimation();
                        }
                    });
                    sweetAlertDialog.show();

                }
            }
        });

        rej.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ExportJson exportJson = new ExportJson(DetailActivity.this);
                exportJson.ApprovalRaw(DetailActivity.this, detailList.getAPPKIND(),detailList.getPOSNO(),detailList.getUSERNO()
                        ,detailList.getUSERNM(),detailList.getREQNO(),4);

            }
        });




    }
    public void finishLayout(){
finish();
    }

}
