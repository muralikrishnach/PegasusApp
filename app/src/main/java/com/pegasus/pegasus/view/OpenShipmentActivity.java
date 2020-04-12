package com.pegasus.pegasus.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
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
    private AppCompatImageButton imgPower;

    private OpenShipMentViewModel openShipMentViewModel;
    Toolbar toolbar;

    OpenShipmentAdapter myAdapter;

    private String BillNo = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);

        toolbar = findViewById(R.id.toolbar_widget);
        recyclerView = findViewById(R.id.recyclerView);
        imgPower = findViewById(R.id.imgLogout);

        setSupportActionBar(toolbar);
        imgPower.setOnClickListener(this);


        recyclerView.setLayoutManager(new LinearLayoutManager(OpenShipmentActivity.this));


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

                myAdapter = new OpenShipmentAdapter(OpenShipmentActivity.this,titleParentDataList);
                recyclerView.setAdapter(myAdapter);
                recyclerView.addItemDecoration(new DividerItemDecoration(OpenShipmentActivity.this,LinearLayoutManager.VERTICAL));
                recyclerView.setHasFixedSize(true);


            }
        });

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.imgLogout:

                AlertDialog.Builder alert = new AlertDialog.Builder(OpenShipmentActivity.this);
                alert.setMessage("Are you sure you want to LogOut?");
                alert.setCancelable(false);
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Intent intent = new Intent(OpenShipmentActivity.this, LoginActivity.class);
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
        }
    }


}
