package com.pegasus.pegasus.model;

import androidx.annotation.Nullable;

import java.io.Serializable;

public class LoginValidations implements Serializable {

    @Nullable
    private String UserName = "";

    @Nullable
    private String Password = "";

    @Nullable
    public String getUserName() {
        return UserName;
    }

    public void setUserName(@Nullable String userName) {
        UserName = userName;
    }

    @Nullable
    public String getPassword() {
        return Password;
    }

    public void setPassword(@Nullable String password) {
        Password = password;
    }


    public boolean isValidUserName()
    {
        if(UserName != null && UserName.length() >= 0)
        {
            return true;
        }else {
            return false;
        }
    }

    public boolean isValidPassword()
    {
        if(Password != null && Password.length() >= 0)
        {
            return true;
        }else {
            return false;
        }
    }





}
