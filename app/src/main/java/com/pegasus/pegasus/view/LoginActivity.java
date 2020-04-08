package com.pegasus.pegasus.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.textfield.TextInputEditText;
import com.pegasus.pegasus.R;
import com.pegasus.pegasus.model.LoginDao;
import com.pegasus.pegasus.model.LoginValidations;
import com.pegasus.pegasus.viewmodel.LoginViewModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import okhttp3.RequestBody;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    TextInputEditText editText_loginid,editText_password;
    Button btnLogin;
    private Context context;

    private LoginViewModel viewModel;
    private MutableLiveData<LoginValidations> userdata;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = LoginActivity.this;
        setContentView(R.layout.activity_login);

        editText_loginid = findViewById(R.id.etloginid);
        editText_password = findViewById(R.id.etpassword);
        btnLogin = findViewById(R.id.btnlogin);

        viewModel = ViewModelProviders.of(LoginActivity.this).get(LoginViewModel.class);
        viewModel.setContext(LoginActivity.this);
        btnLogin.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.btnlogin:

                LoginValidations data = new LoginValidations();
                data.setUserName(editText_loginid.getText().toString());
                data.setPassword(editText_password.getText().toString());
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
                                        Intent i = new Intent(LoginActivity.this,OpenShipmentActivity.class);
                                        i.putExtra("BillNo",response.getDataDao().getBillNo());
                                        startActivity(i);
                                    }
                                });
                                alert.show();
                               /* Toast.makeText(LoginActivity.this,"Login Succesfull with "+editText_loginid.getText().toString(),Toast.LENGTH_LONG).show();
                                finish();*/
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
                                /*Log.v("Status","false");
                                Toast.makeText(LoginActivity.this,"InValid Credentials",Toast.LENGTH_LONG).show();*/
                            }
                        }else {
                            btnLogin.setEnabled(true);
                        }
                    }
                });

                break;

        }

    }
}
