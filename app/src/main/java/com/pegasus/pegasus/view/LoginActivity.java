package com.pegasus.pegasus.view;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.textfield.TextInputEditText;
import com.pegasus.pegasus.R;
import com.pegasus.pegasus.model.LoginDao;
import com.pegasus.pegasus.model.LoginValidations;
import com.pegasus.pegasus.view.viewholders.ScreenNames;
import com.pegasus.pegasus.viewmodel.LoginViewModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import okhttp3.RequestBody;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private ConstraintLayout layout;
    TextInputEditText editText_loginid,editText_password;
    AppCompatButton btnLogin;
    private Context context;

    private AppCompatCheckBox checkrember;

    private LoginViewModel viewModel;
    private MutableLiveData<LoginValidations> userdata;

    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = LoginActivity.this;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        checkRunTimePermission();

        layout = findViewById(R.id.container);
        editText_loginid = findViewById(R.id.etloginid);
        editText_password = findViewById(R.id.etpassword);
        btnLogin = findViewById(R.id.btnlogin);
        checkrember = findViewById(R.id.checkRemeber);

        viewModel = ViewModelProviders.of(LoginActivity.this).get(LoginViewModel.class);
        viewModel.setContext(LoginActivity.this);
        btnLogin.setOnClickListener(this);



        Bitmap backgroundBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.login_bg);
        BitmapDrawable backgroundDrawable = new BitmapDrawable(getResources(), backgroundBitmap);
        backgroundDrawable.setGravity(Gravity.CENTER);
        layout.setBackground(backgroundDrawable);

        loginPreferences = getSharedPreferences(ScreenNames.MyPREFERENCES,Context.MODE_PRIVATE);


        editText_loginid.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String username = loginPreferences.getString(ScreenNames.Name, null);
                String password = loginPreferences.getString(ScreenNames.Pass, null);
                if(username!=null && !username.isEmpty() && username.equalsIgnoreCase(editText_loginid.getText().toString().trim())){
                    editText_password.setText(password);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.btnlogin:

                LoginValidations data = new LoginValidations();
                final String Name = editText_loginid.getText().toString().trim();
                final String PWD  = editText_password.getText().toString().trim();
                data.setUserName(Name);
                data.setPassword(PWD);
                viewModel.setUserdata(data);

                final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this, ProgressDialog.THEME_HOLO_DARK);
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("Authenticating...");
                progressDialog.show();

                btnLogin.setEnabled(false);

                viewModel.checkLogin().observe(this, new Observer<LoginDao>() {
                    @Override
                    public void onChanged(LoginDao loginDataDo) {
                        final LoginDao response = loginDataDo;
                        if(response!=null){
                            progressDialog.dismiss();
                            btnLogin.setEnabled(true);
                            AlertDialog.Builder alert = new AlertDialog.Builder(LoginActivity.this);
                            if(response.isStatus()){
                                alert.setMessage("Login Successfully");
                                alert.setCancelable(false);
                                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();

                                        if(checkrember.isChecked()) {
                                            loginPreferences = getSharedPreferences(ScreenNames.MyPREFERENCES, Context.MODE_PRIVATE);
                                            loginPrefsEditor = loginPreferences.edit();
                                            loginPrefsEditor.clear();
                                            loginPrefsEditor.apply();

                                            loginPrefsEditor = loginPreferences.edit();

                                            loginPrefsEditor.putString(ScreenNames.Name, Name);
                                            loginPrefsEditor.putString(ScreenNames.Pass, PWD);
                                            loginPrefsEditor.apply();
                                        }

                                        Intent i = new Intent(LoginActivity.this,OpenShipmentActivity.class);
                                        i.putExtra("BillNo",response.getDataDao().getBillNo());
                                        startActivity(i);
                                    }
                                });
                                alert.show();

                            }else {
                                alert.setMessage("Login Failed");
                                alert.setCancelable(false);
                                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                                alert.show();

                            }
                        }else {
                            btnLogin.setEnabled(true);
                        }
                    }
                });

                break;
        }
    }

    public void checkRunTimePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
                    ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED||
                    ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            } else {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_BACKGROUND_LOCATION},
                        10);
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 10) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                /*gpsTracker = new GPSTracker(context);*/
            } else {
                if (!ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.ACCESS_FINE_LOCATION)) {
                    // If User Checked 'Don't Show Again' checkbox for runtime permission, then navigate user to Settings
                    AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                    dialog.setTitle("Permission Required");
                    dialog.setCancelable(false);
                    dialog.setMessage("You have to Allow permission to access user location");
                    dialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent i = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.fromParts("package",
                                    context.getPackageName(), null));
                            //i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivityForResult(i, 1001);
                        }
                    });
                    AlertDialog alertDialog = dialog.create();
                    alertDialog.show();
                }
                //code for deny
            }
        }
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        switch (requestCode) {
            case 1001:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
                            ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                            || ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                    } else {
                        requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_BACKGROUND_LOCATION},10);
                    }
                }
                break;
            default:
                break;
        }
    }
}
