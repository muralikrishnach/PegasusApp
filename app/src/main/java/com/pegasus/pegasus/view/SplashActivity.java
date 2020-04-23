package com.pegasus.pegasus.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.pegasus.pegasus.R;
import com.pegasus.pegasus.util.Session;
import com.pegasus.pegasus.view.viewholders.ScreenNames;

public class SplashActivity extends AppCompatActivity {
    private Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        context = SplashActivity.this;
        String billNumber = Session.GetString(this, ScreenNames.UserBillNo);
        if (Session.GetString(this, ScreenNames.Name) != null &&
                Session.GetString(this, ScreenNames.Pass) != null && billNumber != null) {
            Intent i = new Intent(SplashActivity.this, OpenShipmentActivity.class);
            i.putExtra("BillNo", billNumber);
            startActivity(i);
        }else{
            Intent i = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(i);
        }
    }
}
