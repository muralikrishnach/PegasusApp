package com.pegasus.pegasus.view.viewholders;

import android.view.View;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;

import com.pegasus.pegasus.R;
import com.pegasus.pegasus.model.ConsigneeInfoDetailsDao;
import com.pegasus.pegasus.model.LineItemsDao;
import com.pegasus.pegasus.model.OpenShipmentsDao;
import com.pegasus.pegasus.model.ShipmentInfoDetailsDao;
import com.pegasus.pegasus.model.ShipperInfoDetailsDao;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

public class ShipmentDetailsViewHolder extends ChildViewHolder {


    public CardView shipmenentContent,shipperContent,consigneeContent,lineContent;
    public AppCompatTextView waybill,pickupdate,eta,deliverydate,currentstatus;
    public AppCompatTextView contactname,companyname,address,city,state,zip,country;
    public AppCompatTextView consigncontactname,consigncompanyname,consignaddress,consigncity,consignstate,consignzip,consigncountry;
    public AppCompatTextView pieces,content,weight;



    public ShipmentDetailsViewHolder(View view) {
        super(view);

        shipmenentContent = itemView.findViewById(R.id.shipmentcontent);
        shipperContent = itemView.findViewById(R.id.shippercontent);
        consigneeContent = itemView.findViewById(R.id.consigneecontent);
        lineContent = itemView.findViewById(R.id.lineitemcontent);

        waybill = itemView.findViewById(R.id.tvwaybillno);
        pickupdate = itemView.findViewById(R.id.tvpickupdate);
        eta    = itemView.findViewById(R.id.tveta);
        deliverydate = itemView.findViewById(R.id.tvactualdeliverydate);
        currentstatus  = itemView.findViewById(R.id.tvcurrentstatus);
        contactname = itemView.findViewById(R.id.tvcontactname);
        companyname = itemView.findViewById(R.id.tvcompanyname);
        address    = itemView.findViewById(R.id.tvaddress);
        city = itemView.findViewById(R.id.tvcity);
        state  = itemView.findViewById(R.id.tvstate);
        zip  = itemView.findViewById(R.id.tvzip);
        country  = itemView.findViewById(R.id.tvcountry);
        consigncontactname = itemView.findViewById(R.id.tvconsigncontactname);
        consigncompanyname = itemView.findViewById(R.id.tvconsigncompanyname);
        consignaddress    = itemView.findViewById(R.id.tvconsignaddress);
        consigncity = itemView.findViewById(R.id.tvconsigncity);
        consignstate  = itemView.findViewById(R.id.tvconsignstate);
        consignzip  = itemView.findViewById(R.id.tvconsignzip);
        consigncountry  = itemView.findViewById(R.id.tvconsigncountry);
        pieces  = itemView.findViewById(R.id.tvpieces);
        content  = itemView.findViewById(R.id.tvdetails);
        weight  = itemView.findViewById(R.id.tvweight);

    }

    public void setShimentInfo(Object object){
        ShipmentInfoDetailsDao shipmentInfoDetailsDao = (ShipmentInfoDetailsDao)object;

        waybill.setText(shipmentInfoDetailsDao.getWaybillNumber());
        pickupdate.setText(shipmentInfoDetailsDao.getPickupDateTime());
        eta.setText(shipmentInfoDetailsDao.getETADateTime());
        deliverydate.setText(shipmentInfoDetailsDao.getPODDateTime());
        currentstatus.setText(shipmentInfoDetailsDao.getStatus());

    }


    public void setShipperInfo(Object object){
        ShipperInfoDetailsDao shipperInfoDetailsDao = (ShipperInfoDetailsDao)object;

        contactname.setText(shipperInfoDetailsDao.getShipperContactName());
        companyname.setText(shipperInfoDetailsDao.getShipperCompanyName());
        address.setText(shipperInfoDetailsDao.getShipperAddress1());
        city.setText(shipperInfoDetailsDao.getShipperCity());
        state.setText(shipperInfoDetailsDao.getShipperState());
        zip.setText(shipperInfoDetailsDao.getShipperZipCode());
        country.setText(shipperInfoDetailsDao.getShipperCountry());

    }


    public void setConsigneeInfo(Object object){
        ConsigneeInfoDetailsDao consigneeInfoDetailsDao = (ConsigneeInfoDetailsDao)object;

        consigncontactname.setText(consigneeInfoDetailsDao.getConsigneeContactName());
        consigncompanyname.setText(consigneeInfoDetailsDao.getConsigneeCompanyName());
        consignaddress.setText(consigneeInfoDetailsDao.getConsigneeAddress1());
        consigncity.setText(consigneeInfoDetailsDao.getConsigneeCity());
        consignstate.setText(consigneeInfoDetailsDao.getConsigneeState());
        consignzip.setText(consigneeInfoDetailsDao.getConsigneeZipCode());
        consigncountry.setText(consigneeInfoDetailsDao.getConsigneeCountry());
    }

    public void setLineItemsInfo(Object object){
        LineItemsDao lineItemsDao = (LineItemsDao)object;

        pieces.setText(lineItemsDao.getPieces());
        content.setText(lineItemsDao.getDescription());
        weight.setText(lineItemsDao.getWeight());


    }


}
