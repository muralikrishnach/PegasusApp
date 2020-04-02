package com.pegasus.pegasus.view;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.pegasus.pegasus.R;
import com.pegasus.pegasus.model.ShipmentDataDao;
import com.pegasus.pegasus.model.TrackingDetailsDao;
import com.pegasus.pegasus.viewmodel.ShipmentDetailsViewModel;

public class ShipmentDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView recyclerView;
    private Button btn;

    private ShipmentDetailsViewModel shipmentDetailsViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);

        recyclerView = findViewById(R.id.recyclerView);
        btn = findViewById(R.id.button);

        btn.setOnClickListener(this);

        shipmentDetailsViewModel = ViewModelProviders.of(this).get(ShipmentDetailsViewModel.class);

        shipmentDetailsViewModel.getShipmentsData().observe(this, new Observer<ShipmentDataDao>() {
            @Override
            public void onChanged(ShipmentDataDao shipmentDataDao) {
                Log.v("LineItemsDao","LineItemsDao size---"+shipmentDataDao.getLineItemsDaoList().size());
                Log.v("Shippment INfo","Shippment INfo WayBillNo---"+shipmentDataDao.getShipmentInfoDetailsDao().getWaybillNumber());
                Log.v("Shippment INfo","Shippment INfo WayBillNo---"+shipmentDataDao.getShipmentInfoDetailsDao().getWaybillNumber());
                Log.v("ShipperContactName","ShipperContactName---"+shipmentDataDao.getShipperInfoDetailsDao().getShipperContactName());
                Log.v("ConsigneeContactName","ConsigneeContactName---"+shipmentDataDao.getConsigneeInfoDetailsDao().getConsigneeContactName());
                Log.v("Status","Status"+shipmentDataDao.isStatus());
            }
        });

        shipmentDetailsViewModel.getTrackingLiveData().observe(this, new Observer<TrackingDetailsDao>() {
            @Override
            public void onChanged(TrackingDetailsDao trackingDetailsDao) {
                Log.v("LineItemsDao","CoordinatesDaoList size---"+ trackingDetailsDao.getCoordinatesDaoList().size());
                Log.v("","Status"+trackingDetailsDao.isStatus());
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button:

                break;
        }
    }
}
