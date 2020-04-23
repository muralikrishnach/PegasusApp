package com.pegasus.pegasus.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pegasus.pegasus.R;
import com.pegasus.pegasus.model.ConsigneeInfoDetailsDao;
import com.pegasus.pegasus.model.ShipmentDataDao;
import com.pegasus.pegasus.model.ShipmentInfoDetailsDao;
import com.pegasus.pegasus.model.ShipperInfoDetailsDao;
import com.pegasus.pegasus.model.TrackingDetailsDao;
import com.pegasus.pegasus.view.adapters.LineAdapter;
import com.pegasus.pegasus.viewmodel.ShipmentDetailsViewModel;
import com.pegasus.pegasus.viewmodel.TrackingViewModel;

public class ShipmentDetails extends AppCompatActivity implements View.OnClickListener {
    private TrackingViewModel trackingViewModel;
    TrackingDetailsDao trackingDetailsDaodata;
    private AppCompatImageButton imgback,imgPower;
    private AppCompatTextView head1,head2,head3,head4;
    private AppCompatButton separator1,separator2,separator3,separator4;
    private CardView shipmenentContent,shipperContent,consigneeContent;
    private AppCompatTextView waybill,pickupdate,eta,deliverydate,currentstatus,tvmaps;
    private AppCompatTextView contactname,companyname,address,city,state,zip,country;
    private AppCompatTextView consigncontactname,consigncompanyname,consignaddress,consigncity,consignstate,consignzip,consigncountry;
    private LinearLayout llScroll,lineContent;
    private RecyclerView recyclerView;
    LineAdapter lineAdapter;
    LayoutInflater inflater;
    private ShipmentDetailsViewModel shipmentDetailsViewModel;
    ShipmentDataDao shipmentData;
    private Toolbar toolbar;
    String WayBillNo = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailscontainer);
        inflater = getLayoutInflater();
        if(getIntent().getExtras()!=null){
            WayBillNo = getIntent().getExtras().getString("WayBillNo");
        }
        bindingViews();
        shipmentDetailsViewModel = ViewModelProviders.of(this).get(ShipmentDetailsViewModel.class);
        shipmentDetailsViewModel.getShipmentsData(WayBillNo).observe(this, new Observer<ShipmentDataDao>() {
            @Override
            public void onChanged(ShipmentDataDao shipmentDataDao) {
                shipmentData = shipmentDataDao;
                if(shipmentData!=null) {
                    shipmentDetailsView();
                }
            }
        });
    }

    private void bindingViews() {
        toolbar = findViewById(R.id.toolbar_widget);
        imgback = findViewById(R.id.imgBack);
        imgPower = findViewById(R.id.imgLogout);

        separator1 = findViewById(R.id.shipmentid);
        separator2 = findViewById(R.id.shipperid);
        separator3 = findViewById(R.id.consigneeseparate);
        separator4 = findViewById(R.id.lineseparate);

        head1 = findViewById(R.id.tvShipmentInfo);
        head2 = findViewById(R.id.tvShipperInfo);
        head3 = findViewById(R.id.tvConsigneeInfo);
        head4 = findViewById(R.id.tvLineInfo);

        shipmenentContent = findViewById(R.id.shipmentcontent);
        shipperContent = findViewById(R.id.shippercontent);
        consigneeContent = findViewById(R.id.consigneecontent);
        lineContent = findViewById(R.id.llLineContent);

        tvmaps = findViewById(R.id.tvmapsclick);
        waybill = findViewById(R.id.tvwaybillno);
        pickupdate = findViewById(R.id.tvpickupdate);
        eta    = findViewById(R.id.tveta);
        deliverydate = findViewById(R.id.tvactualdeliverydate);
        currentstatus  = findViewById(R.id.tvcurrentstatus);
        contactname = findViewById(R.id.tvcontactname);
        companyname = findViewById(R.id.tvcompanyname);
        address    = findViewById(R.id.tvaddress);
        city = findViewById(R.id.tvcity);
        state  = findViewById(R.id.tvstate);
        zip  = findViewById(R.id.tvzip);
        country  = findViewById(R.id.tvcountry);
        consigncontactname = findViewById(R.id.tvconsigncontactname);
        consigncompanyname = findViewById(R.id.tvconsigncompanyname);
        consignaddress    = findViewById(R.id.tvconsignaddress);
        consigncity = findViewById(R.id.tvconsigncity);
        consignstate  = findViewById(R.id.tvconsignstate);
        consignzip  = findViewById(R.id.tvconsignzip);
        consigncountry  = findViewById(R.id.tvconsigncountry);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(ShipmentDetails.this));

        setSupportActionBar(toolbar);

        imgback.setOnClickListener(this);
        imgPower.setOnClickListener(this);
        head1.setOnClickListener(this);
        head2.setOnClickListener(this);
        head3.setOnClickListener(this);
        head4.setOnClickListener(this);
        tvmaps.setOnClickListener(this);
    }

    private void shipmentDetailsView() {
        separator1.setBackgroundColor(ContextCompat.getColor(this, R.color.yellow));
        separator2.setBackgroundColor(ContextCompat.getColor(this, R.color.cerulean));
        separator3.setBackgroundColor(ContextCompat.getColor(this, R.color.cerulean));
        separator4.setBackgroundColor(ContextCompat.getColor(this, R.color.cerulean));

        head1.setBackgroundColor(ContextCompat.getColor(this, R.color.aegean));
        head2.setBackgroundColor(ContextCompat.getColor(this, R.color.cerulean));
        head3.setBackgroundColor(ContextCompat.getColor(this, R.color.cerulean));
        head4.setBackgroundColor(ContextCompat.getColor(this, R.color.cerulean));

        head1.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_up, 0);
        head2.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_down, 0);
        head3.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_down, 0);
        head4.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_down, 0);

        shipmenentContent.setVisibility(View.VISIBLE);
        shipperContent.setVisibility(View.GONE);
        consigneeContent.setVisibility(View.GONE);
        lineContent.setVisibility(View.GONE);

        ShipmentInfoDetailsDao shipmentInfoDetailsDao = shipmentData.getShipmentInfoDetailsDaoList().get(0);

        waybill.setText(shipmentInfoDetailsDao.getWaybillNumber());
        pickupdate.setText(shipmentInfoDetailsDao.getPickupDateTime());
        eta.setText(shipmentInfoDetailsDao.getETADateTime());
        deliverydate.setText(shipmentInfoDetailsDao.getPODDateTime());
        currentstatus.setText(shipmentInfoDetailsDao.getStatus());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tvShipmentInfo:
                if(shipmentData!=null) {
                    shipmentDetailsView();
                }
                break;
            case R.id.tvShipperInfo:
                if(shipmentData!=null) {
                    separator1.setBackgroundColor(ContextCompat.getColor(this, R.color.cerulean));
                    separator2.setBackgroundColor(ContextCompat.getColor(this, R.color.yellow));
                    separator3.setBackgroundColor(ContextCompat.getColor(this, R.color.cerulean));
                    separator4.setBackgroundColor(ContextCompat.getColor(this, R.color.cerulean));

                    head1.setBackgroundColor(ContextCompat.getColor(this, R.color.cerulean));
                    head2.setBackgroundColor(ContextCompat.getColor(this, R.color.aegean));
                    head3.setBackgroundColor(ContextCompat.getColor(this, R.color.cerulean));
                    head4.setBackgroundColor(ContextCompat.getColor(this, R.color.cerulean));

                    head1.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_down, 0);
                    head2.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_up, 0);
                    head3.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_down, 0);
                    head4.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_down, 0);

                    shipmenentContent.setVisibility(View.GONE);
                    shipperContent.setVisibility(View.VISIBLE);
                    consigneeContent.setVisibility(View.GONE);
                    lineContent.setVisibility(View.GONE);

                    ShipperInfoDetailsDao shipperInfoDetailsDao = shipmentData.getShipperInfoDetailsDaoList().get(0);

                    contactname.setText(shipperInfoDetailsDao.getShipperContactName());
                    companyname.setText(shipperInfoDetailsDao.getShipperCompanyName());
                    address.setText(shipperInfoDetailsDao.getShipperAddress1());
                    city.setText(shipperInfoDetailsDao.getShipperCity());
                    state.setText(shipperInfoDetailsDao.getShipperState());
                    zip.setText(shipperInfoDetailsDao.getShipperZipCode());
                    country.setText(shipperInfoDetailsDao.getShipperCountry());
                }
                break;
            case R.id.tvConsigneeInfo:
                if(shipmentData!=null) {
                    separator1.setBackgroundColor(ContextCompat.getColor(this, R.color.cerulean));
                    separator2.setBackgroundColor(ContextCompat.getColor(this, R.color.cerulean));
                    separator3.setBackgroundColor(ContextCompat.getColor(this, R.color.yellow));
                    separator4.setBackgroundColor(ContextCompat.getColor(this, R.color.cerulean));

                    head1.setBackgroundColor(ContextCompat.getColor(this, R.color.cerulean));
                    head2.setBackgroundColor(ContextCompat.getColor(this, R.color.cerulean));
                    head3.setBackgroundColor(ContextCompat.getColor(this, R.color.aegean));
                    head4.setBackgroundColor(ContextCompat.getColor(this, R.color.cerulean));

                    head1.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_down, 0);
                    head2.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_down, 0);
                    head3.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_up, 0);
                    head4.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_down, 0);

                    shipmenentContent.setVisibility(View.GONE);
                    shipperContent.setVisibility(View.GONE);
                    consigneeContent.setVisibility(View.VISIBLE);
                    lineContent.setVisibility(View.GONE);

                    ConsigneeInfoDetailsDao consigneeInfoDetailsDao = shipmentData.getConsigneeInfoDetailsDaoList().get(0);
                    consigncontactname.setText(consigneeInfoDetailsDao.getConsigneeContactName());
                    consigncompanyname.setText(consigneeInfoDetailsDao.getConsigneeCompanyName());
                    consignaddress.setText(consigneeInfoDetailsDao.getConsigneeAddress1());
                    consigncity.setText(consigneeInfoDetailsDao.getConsigneeCity());
                    consignstate.setText(consigneeInfoDetailsDao.getConsigneeState());
                    consignzip.setText(consigneeInfoDetailsDao.getConsigneeZipCode());
                    consigncountry.setText(consigneeInfoDetailsDao.getConsigneeCountry());

                }
                break;
            case R.id.tvLineInfo:
                if(shipmentData!=null) {
                    separator1.setBackgroundColor(ContextCompat.getColor(this, R.color.cerulean));
                    separator2.setBackgroundColor(ContextCompat.getColor(this, R.color.cerulean));
                    separator3.setBackgroundColor(ContextCompat.getColor(this, R.color.cerulean));
                    separator4.setBackgroundColor(ContextCompat.getColor(this, R.color.yellow));

                    head1.setBackgroundColor(ContextCompat.getColor(this, R.color.cerulean));
                    head2.setBackgroundColor(ContextCompat.getColor(this, R.color.cerulean));
                    head3.setBackgroundColor(ContextCompat.getColor(this, R.color.cerulean));
                    head4.setBackgroundColor(ContextCompat.getColor(this, R.color.aegean));

                    head1.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_down, 0);
                    head2.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_down, 0);
                    head3.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_down, 0);
                    head4.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_up, 0);

                    shipmenentContent.setVisibility(View.GONE);
                    shipperContent.setVisibility(View.GONE);
                    consigneeContent.setVisibility(View.GONE);
                    lineContent.setVisibility(View.VISIBLE);

                    lineAdapter = new LineAdapter(ShipmentDetails.this, shipmentData.getLineItemsDaoList());
                    recyclerView.setAdapter(lineAdapter);
                    recyclerView.addItemDecoration(new DividerItemDecoration(ShipmentDetails.this, LinearLayoutManager.VERTICAL));
                    recyclerView.setHasFixedSize(true);
                }
                break;
            case R.id.imgBack:
                finish();
                break;
            case R.id.imgLogout:
                AlertDialog.Builder alert = new AlertDialog.Builder(ShipmentDetails.this);
                alert.setMessage("Are you sure you want to Logout?");
                alert.setCancelable(false);
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Intent intent = new Intent(ShipmentDetails.this, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                });
                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alert.show();
                break;
            case R.id.tvmapsclick:
                trackingViewModel = ViewModelProviders.of(this).get(TrackingViewModel.class);
                trackingViewModel.getTrackingLiveData(WayBillNo).observe(this, new Observer<TrackingDetailsDao>() {
                    @Override
                    public void onChanged(TrackingDetailsDao trackingDetailsDao) {
                        trackingDetailsDaodata = trackingDetailsDao;
                        if(trackingDetailsDaodata!=null){
                            int size = trackingDetailsDao.getCoordinatesDaoList().size();
                            Intent i = new Intent(ShipmentDetails.this, MapsActivity.class);
                            i.putExtra("Tracking",trackingDetailsDaodata);
                            startActivity(i);
                        }
                    }
                });
                break;
        }
    }
}
