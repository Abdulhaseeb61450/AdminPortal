package com.example.adminportal.Contact;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adminportal.API.APIs;
import com.example.adminportal.ComposeSMS.QuickSMS;
import com.example.adminportal.Dashboard.AdminDashboard;
import com.example.adminportal.LogOutTimerUtil;
import com.example.adminportal.Login.MainActivity;
import com.example.adminportal.R;
import com.example.adminportal.Reports.OutboxSMS;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ContactDetail extends AppCompatActivity implements LogOutTimerUtil.LogOutListener {

    TextView name,contactno;
    String NAME,CONTACT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_detail);

        BottomNavigationView bottomNav = findViewById(R.id.bottom1_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        bottomNav.getMenu().getItem(1).setChecked(true);

        Intent intent = getIntent();
        NAME = intent.getStringExtra("name");
        CONTACT = intent.getStringExtra("contact");
        name = findViewById(R.id.name);
        contactno = findViewById(R.id.contactNo);


        name.setText(NAME);
        contactno.setText(CONTACT);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    switch (item.getItemId()) {
                        case R.id.nav_dash:
                            Intent dashintent = new Intent(ContactDetail.this,AdminDashboard.class);
                            startActivity(dashintent);
                            break;
                        case R.id.nav_contact:
                            Intent ContactIntent = new Intent(ContactDetail.this,ContactManagement.class);
                            startActivity(ContactIntent);
                            break;
                        case R.id.nav_comp:
                            Intent ComposeIntent = new Intent(ContactDetail.this, QuickSMS.class);
                            startActivity(ComposeIntent);
                            break;
                        case R.id.nav_repo:
                            Intent ReportIntent = new Intent(ContactDetail.this, OutboxSMS.class);
                            startActivity(ReportIntent);
                            break;
                        case R.id.nav_api:
                            Intent ApiIntent = new Intent(ContactDetail.this, APIs.class);
                            startActivity(ApiIntent);
                            break;
                    }

                    return true;
                }
            };

    public void UPDATE(View view)
    {
        Intent intent = new Intent(ContactDetail.this, com.example.adminportal.Contact.AddContact.class);
        startActivity(intent);
    }

    public void DELETE(View view)
    {
        Toast.makeText(ContactDetail.this,"DELETED",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(ContactDetail.this, com.example.adminportal.Contact.ContactManagement.class);
        startActivity(intent);
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
        Intent intent = new Intent(ContactDetail.this, MainActivity.class);
        startActivity(intent);
    }
}
