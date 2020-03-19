package com.example.adminportal.MNP;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.adminportal.Dashboard.AdminDashboard;
import com.example.adminportal.LogOutTimerUtil;
import com.example.adminportal.Login.MainActivity;
import com.example.adminportal.Masking.Routes;
import com.example.adminportal.Masking.UpdateRoutes;
import com.example.adminportal.R;

import java.util.ArrayList;

public class MNP extends AppCompatActivity implements LogOutTimerUtil.LogOutListener {

    EditText mobileno;
    Spinner MNPList;
    public ArrayAdapter<Routes> myAdaptor;
    public ArrayList<Routes> route = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mnp);

        MNPList = findViewById(R.id.MNPSpinner);
        myAdaptor = new ArrayAdapter<Routes>(MNP.this,
                android.R.layout.simple_list_item_1, route);
        myAdaptor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        MNPList.setAdapter(myAdaptor);
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
        Intent intent = new Intent(MNP.this, MainActivity.class);
        startActivity(intent);
    }
}
