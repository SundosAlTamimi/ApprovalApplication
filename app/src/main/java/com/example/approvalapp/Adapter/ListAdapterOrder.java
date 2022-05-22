package com.example.approvalapp.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.approvalapp.DetailActivity;
import com.example.approvalapp.Json.ExportJson;
import com.example.approvalapp.Model.ListOfOrderData;
import com.example.approvalapp.R;
import com.google.android.material.transition.Hold;


import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class ListAdapterOrder extends BaseAdapter {
    private Context context;
    List<ListOfOrderData> itemsList;

    public ListAdapterOrder(Context context, List<ListOfOrderData> itemsList) {
        this.context = context;
        this.itemsList = itemsList;
    }

    public ListAdapterOrder() {

    }

    public void setItemsList(List<ListOfOrderData> itemsList) {
        this.itemsList = itemsList;
    }

    @Override
    public int getCount() {
//        try {
            return itemsList.size();
//        } catch (Exception e) {
//            return 0;
//        }


    }


    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    private class ViewHolder {
        TextView capName, appUserName,posNo,total,disc;
        Button accept,rej;
        LinearLayout mainLinear;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {

        final ViewHolder holder = new ViewHolder();
        view = View.inflate(context, R.layout.captain_order_raw, null);

        holder.capName =  view.findViewById(R.id.capName);
        holder.appUserName =  view.findViewById(R.id.appUserName);
      holder.accept=view.findViewById(R.id.acceptButton);
        holder.rej=view.findViewById(R.id.rejectButton);
        holder.posNo=view.findViewById(R.id.posNo);
        holder.total=view.findViewById(R.id.total);
        holder.disc=view.findViewById(R.id.disc);
        holder.mainLinear=view.findViewById(R.id.mainLinear);

        holder.appUserName.setText(""+itemsList.get(i).getAPPUSERNM());
        holder.posNo.setText(""+itemsList.get(i).getPOSNM());
        holder.total.setText(""+itemsList.get(i).getTOTAL());
        holder.disc.setText(""+itemsList.get(i).getDISCOUNT());

        holder.capName.setText(itemsList.get(i).getUSERNM());
        holder.mainLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent detailIntent=new Intent(context, DetailActivity.class);
                detailIntent.putExtra("DetailData", itemsList.get(i));
                context.startActivity(detailIntent);
            }
        });

        holder.accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {


                    ExportJson exportJson = new ExportJson(context);
                    exportJson.ApprovalRaw(context, itemsList.get(i).getAPPKIND(),itemsList.get(i).POSNO,itemsList.get(i).getUSERNO()
                            ,itemsList.get(i).getUSERNM(),itemsList.get(i).getREQNO(),1);

                }catch (Exception e){

                    SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE);
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

        holder.rej.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ExportJson exportJson = new ExportJson(context);
                exportJson.ApprovalRaw(context, itemsList.get(i).getAPPKIND(),itemsList.get(i).getPOSNO(),itemsList.get(i).getUSERNO()
                        ,itemsList.get(i).getUSERNM(),itemsList.get(i).getREQNO(),3);

            }
        });

        return view;
    }




}

