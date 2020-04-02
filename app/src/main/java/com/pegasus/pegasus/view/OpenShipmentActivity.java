package com.pegasus.pegasus.view;

import android.content.Intent;
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
import com.pegasus.pegasus.model.OpenPODShipmentDetailsDao;
import com.pegasus.pegasus.viewmodel.OpenShipMentViewModel;

public class OpenShipmentActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView recyclerView;
    private Button btn;

    private OpenShipMentViewModel openShipMentViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);

        recyclerView = findViewById(R.id.recyclerView);
        btn = findViewById(R.id.button);

        btn.setOnClickListener(this);

        openShipMentViewModel = ViewModelProviders.of(this).get(OpenShipMentViewModel.class);

        openShipMentViewModel.getShipmentDetails().observe(this, new Observer<OpenPODShipmentDetailsDao>() {
            @Override
            public void onChanged(OpenPODShipmentDetailsDao openPODShipmentDetailsDao) {
                Log.v("Openshipment_count","Openshipment_count---"+openPODShipmentDetailsDao.getOpenshipment_count());
                Log.v("Podshipment_count","Podshipment_count---"+openPODShipmentDetailsDao.getPodshipment_count());
                Log.v("OpenShipmentsDao","OpenShipmentsDao size---"+openPODShipmentDetailsDao.getOpenShipmentsDaoList().size());
                Log.v("PODShipmentsDao","PODShipmentsDao size---"+openPODShipmentDetailsDao.getPodShipmentsDaoList().size());
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button:
                Intent i = new Intent(OpenShipmentActivity.this,ShipmentDetailsActivity.class);
                startActivity(i);
                break;
        }
    }
}
