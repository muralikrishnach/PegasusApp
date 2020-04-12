package com.pegasus.pegasus.view;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pegasus.pegasus.R;
import com.pegasus.pegasus.model.ShipmentDataDao;
import com.pegasus.pegasus.model.TitleParentData;
import com.pegasus.pegasus.model.TrackingDetailsDao;
import com.pegasus.pegasus.view.adapters.OpenShipmentAdapter;
import com.pegasus.pegasus.view.adapters.ShipmentDetailsAdapter;
import com.pegasus.pegasus.view.viewholders.ScreenNames;
import com.pegasus.pegasus.viewmodel.ShipmentDetailsViewModel;

import java.util.ArrayList;
import java.util.List;

public class ShipmentDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView recyclerView;
    private AppCompatTextView title;
    private AppCompatImageButton imgback,imgPower;


    private ShipmentDetailsViewModel shipmentDetailsViewModel;
    private Toolbar toolbar;

    ShipmentDetailsAdapter myAdapter;



    String WayBillNo = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);

        if(getIntent().getExtras()!=null){
            WayBillNo = getIntent().getExtras().getString("WayBillNo");
        }


        toolbar = findViewById(R.id.toolbar_widget);



        recyclerView = findViewById(R.id.recyclerView);
        title = findViewById(R.id.tvtitle);
        imgback = findViewById(R.id.imgBack);
        imgPower = findViewById(R.id.imgLogout);

        imgback.setVisibility(View.VISIBLE);
        imgPower.setVisibility(View.GONE);

        imgback.setOnClickListener(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(ShipmentDetailsActivity.this));

        setSupportActionBar(toolbar);
        title.setText("SHIPMENTS DETAILS");

        shipmentDetailsViewModel = ViewModelProviders.of(this).get(ShipmentDetailsViewModel.class);

        shipmentDetailsViewModel.getShipmentsData(WayBillNo).observe(this, new Observer<ShipmentDataDao>() {
            @Override
            public void onChanged(ShipmentDataDao shipmentDataDao) {
                List<TitleParentData> parentDataList = new ArrayList<>();
                parentDataList.add(new TitleParentData(ScreenNames.Head1,shipmentDataDao.getShipmentInfoDetailsDaoList()));
                parentDataList.add(new TitleParentData(ScreenNames.Head2,shipmentDataDao.getShipperInfoDetailsDaoList()));
                parentDataList.add(new TitleParentData(ScreenNames.Head3,shipmentDataDao.getConsigneeInfoDetailsDaoList()));
                parentDataList.add(new TitleParentData(ScreenNames.Head4,shipmentDataDao.getLineItemsDaoList()));

                myAdapter = new ShipmentDetailsAdapter(ShipmentDetailsActivity.this,parentDataList);
                recyclerView.addItemDecoration(new DividerItemDecoration(ShipmentDetailsActivity.this, LinearLayoutManager.VERTICAL));
                recyclerView.setHasFixedSize(true);
                recyclerView.setAdapter(myAdapter);



            }
        });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imgBack:

                    finish();

                break;
        }
    }
}
