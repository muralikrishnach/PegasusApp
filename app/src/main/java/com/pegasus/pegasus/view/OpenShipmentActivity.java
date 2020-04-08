package com.pegasus.pegasus.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pegasus.pegasus.R;
import com.pegasus.pegasus.model.OpenPODShipmentDetailsDao;
import com.pegasus.pegasus.model.OpenShipmentsDao;
import com.pegasus.pegasus.model.TitleParentData;
import com.pegasus.pegasus.model.repository.JsonParsing;
import com.pegasus.pegasus.view.adapters.OpenShipmentAdapter;
import com.pegasus.pegasus.viewmodel.OpenShipMentViewModel;

import java.util.ArrayList;
import java.util.List;

public class OpenShipmentActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView recyclerView;
    private Button btn;

    private OpenShipMentViewModel openShipMentViewModel;
    Toolbar toolbar;

    private String BillNo = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);

        toolbar = findViewById(R.id.toolbar_widget);
        recyclerView = findViewById(R.id.recyclerView);
        btn = findViewById(R.id.button);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("SHIPMENTS");

        btn.setOnClickListener(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        if(getIntent().getExtras()!=null){
            BillNo = getIntent().getExtras().getString("BillNo");
        }



        openShipMentViewModel = ViewModelProviders.of(this).get(OpenShipMentViewModel.class);

        openShipMentViewModel.getShipmentDetails(BillNo).observe(this, new Observer<OpenPODShipmentDetailsDao>() {
            @Override
            public void onChanged(OpenPODShipmentDetailsDao openPODShipmentDetailsDao) {
                List<TitleParentData> titleParentDataList = new ArrayList<>();
                titleParentDataList.add(new TitleParentData(JsonParsing.Head1,openPODShipmentDetailsDao.getOpenShipmentsDaoList()));
                titleParentDataList.add(new TitleParentData(JsonParsing.Head2,openPODShipmentDetailsDao.getPodShipmentsDaoList()));

                OpenShipmentAdapter myAdapter = new OpenShipmentAdapter(OpenShipmentActivity.this,titleParentDataList);
                recyclerView.setAdapter(myAdapter);
                recyclerView.addItemDecoration(new DividerItemDecoration(OpenShipmentActivity.this,LinearLayoutManager.VERTICAL));
                recyclerView.setHasFixedSize(true);


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
