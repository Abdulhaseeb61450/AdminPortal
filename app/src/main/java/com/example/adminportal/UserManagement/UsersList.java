package com.example.adminportal.UserManagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.adminportal.API.APIs;
import com.example.adminportal.ComposeSMS.QuickSMS;
import com.example.adminportal.Contact.ContactManagement;
import com.example.adminportal.Dashboard.AdminDashboard;
import com.example.adminportal.LogOutTimerUtil;
import com.example.adminportal.Login.MainActivity;
import com.example.adminportal.R;
import com.example.adminportal.Reports.OutboxSMS;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class UsersList extends AppCompatActivity implements LogOutTimerUtil.LogOutListener {

    int selectedId;
    public String Type;
    private RadioGroup Gender;
    private RadioButton radioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_list);

        BottomNavigationView bottomNav = findViewById(R.id.bottom1_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        bottomNav.getMenu().getItem(0).setCheckable(false);
        bottomNav.getMenu().getItem(1).setCheckable(false);
        bottomNav.getMenu().getItem(2).setCheckable(false);
        bottomNav.getMenu().getItem(3).setCheckable(false);
        bottomNav.getMenu().getItem(4).setCheckable(false);

        Gender = (RadioGroup) findViewById(R.id.Gender);
        selectedId = Gender.getCheckedRadioButtonId();
        radioButton = (RadioButton) findViewById(selectedId);
        Type = radioButton.getText().toString().trim();
        if (Type.equals("User"))
        {

        }
        else
        {

        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    switch (item.getItemId()) {
                        case R.id.nav_dash:
                            item.setCheckable(true);
                            Intent dashintent = new Intent(UsersList.this,AdminDashboard.class);
                            startActivity(dashintent);
                            break;
                        case R.id.nav_contact:
                            item.setCheckable(true);
                            Intent ContactIntent = new Intent(UsersList.this, ContactManagement.class);
                            startActivity(ContactIntent);
                            break;
                        case R.id.nav_comp:
                            item.setCheckable(true);
                            Intent ComposeIntent = new Intent(UsersList.this, QuickSMS.class);
                            startActivity(ComposeIntent);
                            break;
                        case R.id.nav_repo:
                            item.setCheckable(true);
                            Intent ReportIntent = new Intent(UsersList.this, OutboxSMS.class);
                            startActivity(ReportIntent);
                            break;
                        case R.id.nav_api:
                            item.setCheckable(true);
                            Intent ApiIntent = new Intent(UsersList.this, APIs.class);
                            startActivity(ApiIntent);
                            break;
                    }

                    return true;
                }
            };

    public void Add(View view)
    {
        Gender = (RadioGroup) findViewById(R.id.Gender);
        selectedId = Gender.getCheckedRadioButtonId();
        radioButton = (RadioButton) findViewById(selectedId);
        Type = radioButton.getText().toString().trim();
        if (Type.equals("User"))
        {
            Intent Userintent = new Intent(UsersList.this,AddUser.class);
            startActivity(Userintent);
        }
        else
        {
            Intent Userintent = new Intent(UsersList.this,AddReseller.class);
            startActivity(Userintent);
        }
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
        Intent intent = new Intent(UsersList.this, MainActivity.class);
        startActivity(intent);
    }
}
