package com.example.adminportal.MNP;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.adminportal.API.APIs;
import com.example.adminportal.ComposeSMS.QuickSMS;
import com.example.adminportal.Contact.ContactManagement;
import com.example.adminportal.Dashboard.AdminDashboard;
import com.example.adminportal.LogOutTimerUtil;
import com.example.adminportal.Login.MainActivity;
import com.example.adminportal.Masking.Routes;
import com.example.adminportal.Masking.UpdateRoutes;
import com.example.adminportal.R;
import com.example.adminportal.Reports.OutboxSMS;
import com.google.android.material.bottomnavigation.BottomNavigationView;

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

        BottomNavigationView bottomNav = findViewById(R.id.bottom1_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        bottomNav.getMenu().getItem(0).setCheckable(false);
        bottomNav.getMenu().getItem(1).setCheckable(false);
        bottomNav.getMenu().getItem(2).setCheckable(false);
        bottomNav.getMenu().getItem(3).setCheckable(false);
        bottomNav.getMenu().getItem(4).setCheckable(false);

        mobileno = findViewById(R.id.mobileno);

        MNPList = findViewById(R.id.MNPSpinner);
        myAdaptor = new ArrayAdapter<Routes>(MNP.this,
                android.R.layout.simple_list_item_1, route);
        myAdaptor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        MNPList.setAdapter(myAdaptor);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    switch (item.getItemId()) {
                        case R.id.nav_dash:
                            item.setCheckable(true);
                            Intent dashintent = new Intent(MNP.this,AdminDashboard.class);
                            startActivity(dashintent);
                            break;
                        case R.id.nav_contact:
                            item.setCheckable(true);
                            Intent ContactIntent = new Intent(MNP.this, ContactManagement.class);
                            startActivity(ContactIntent);
                            break;
                        case R.id.nav_comp:
                            item.setCheckable(true);
                            Intent ComposeIntent = new Intent(MNP.this, QuickSMS.class);
                            startActivity(ComposeIntent);
                            break;
                        case R.id.nav_repo:
                            item.setCheckable(true);
                            Intent ReportIntent = new Intent(MNP.this, OutboxSMS.class);
                            startActivity(ReportIntent);
                            break;
                        case R.id.nav_api:
                            item.setCheckable(true);
                            Intent ApiIntent = new Intent(MNP.this, APIs.class);
                            startActivity(ApiIntent);
                            break;
                    }

                    return true;
                }
            };

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
