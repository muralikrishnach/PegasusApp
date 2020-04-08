package com.pegasus.pegasus.view.viewholders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;


import com.pegasus.pegasus.R;
import com.pegasus.pegasus.model.OpenShipmentsDao;
import com.pegasus.pegasus.model.PODShipmentsDao;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

import java.util.List;

public class ChildParentViewHolder extends ChildViewHolder {

     public AppCompatTextView wayillno,srctodest,status;
     public AppCompatImageButton imglocation;
     public CardView cardView;


    public ChildParentViewHolder(@NonNull View itemView) {
        super(itemView);
        wayillno = itemView.findViewById(R.id.tvwaybillno);
        srctodest = itemView.findViewById(R.id.tvsrcdest);
        status    = itemView.findViewById(R.id.tvstatus);
        imglocation = itemView.findViewById(R.id.btnLocation);
        cardView  = itemView.findViewById(R.id.card_view);
    }

    public void setOpenShipment(Object object){
        OpenShipmentsDao openShipmentsDao = (OpenShipmentsDao)object;
        wayillno.setText(openShipmentsDao.getWaybillNumber());
        srctodest.setText(openShipmentsDao.getShipperCity()+" to "+openShipmentsDao.getConsigneeCity());
        status.setText("Current Status - "+openShipmentsDao.getStatus());
    }

    public void setPODShipment(Object object){
        PODShipmentsDao podShipmentsDao = (PODShipmentsDao)object;
        wayillno.setText(podShipmentsDao.getWaybillNumber());
        srctodest.setText(podShipmentsDao.getShipperCity()+" to "+podShipmentsDao.getConsigneeCity());
        status.setText("Current Status - "+podShipmentsDao.getStatus());
    }


}
