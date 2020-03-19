package com.example.adminportal.UserManagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.adminportal.Dashboard.AdminDashboard;
import com.example.adminportal.LogOutTimerUtil;
import com.example.adminportal.Login.MainActivity;
import com.example.adminportal.R;

public class AddReseller extends AppCompatActivity implements LogOutTimerUtil.LogOutListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reseller);
    }

    @Override
    protected void onStart() {
        super.onStart();
        LogOutTimerUtil.startLogoutTimer(this, this);
    }

    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        LogOutTimerUtil.startLogoutTimer(this, this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        LogOutTimerUtil.startLogoutTimer(this, this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogOutTimerUtil.startLogoutTimer(this, this);
    }

    @Override
    public void doLogout() {
        Intent intent = new Intent(AddReseller.this, MainActivity.class);
        startActivity(intent);
    }
}
