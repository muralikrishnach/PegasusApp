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
        this.UserName = userName;
    }

    @Nullable
    public String getPassword() {
        return Password;
    }

    public void setPassword(@Nullable String password) {
        this.Password = password;
    }


    public boolean isValidUserName()
    {
        if(this.UserName != null && this.UserName.length() >= 0)
        {
            return true;
        }else {
            return false;
        }
    }

    public boolean isValidPassword()
    {
        if(this.Password != null && this.Password.length() >= 0)
        {
            return true;
        }else {
            return false;
        }
    }





}
