package com.example.adminportal.Dashboard;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.example.adminportal.API.APIs;
import com.example.adminportal.Campaign.Campaign;
import com.example.adminportal.ComposeSMS.QuickSMS;
import com.example.adminportal.Contact.ContactManagement;
import com.example.adminportal.LiveReports.LiveReports;
import com.example.adminportal.LogOutTimerUtil;
import com.example.adminportal.Login.MainActivity;
import com.example.adminportal.MNP.MNP;
import com.example.adminportal.Masking.MaskRoutes;
import com.example.adminportal.R;
import com.example.adminportal.Reports.OutboxSMS;
import com.example.adminportal.UserManagement.UsersList;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import static com.example.adminportal.Login.MainActivity.mypreference;

public class AdminDashboard extends AppCompatActivity implements LogOutTimerUtil.LogOutListener {

    SharedPreferences sharedpreferences;
    int ALL_PERMISSIONS = 101;
    public  BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        sharedpreferences = getSharedPreferences(mypreference, Context.MODE_PRIVATE);

        requestPermission();

        BottomNavigationView bottomNav = findViewById(R.id.bottom1_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);


    }


    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    switch (item.getItemId()) {
                        case R.id.nav_dash:
                            Intent dashintent = new Intent(AdminDashboard.this,AdminDashboard.class);
                            startActivity(dashintent);
                            break;
                        case R.id.nav_contact:
                            Intent ContactIntent = new Intent(AdminDashboard.this,ContactManagement.class);
                            startActivity(ContactIntent);
                            break;
                        case R.id.nav_comp:
                            Intent ComposeIntent = new Intent(AdminDashboard.this,QuickSMS.class);
                            startActivity(ComposeIntent);
                            break;
                        case R.id.nav_repo:
                            Intent ReportIntent = new Intent(AdminDashboard.this,OutboxSMS.class);
                            startActivity(ReportIntent);
                            break;
                        case R.id.nav_api:
                            Intent ApiIntent = new Intent(AdminDashboard.this,APIs.class);
                            startActivity(ApiIntent);
                            break;
                    }

                    return true;
                }
            };



    public void MaskROutes(View view) { startActivity(new Intent(getApplicationContext(), MaskRoutes.class)); }

    public void Campaign(View view) { startActivity(new Intent(getApplicationContext(), Campaign.class)); }

    public void CONTACT(View view) { startActivity(new Intent(getApplicationContext(), ContactManagement.class)); }

    public void COMPOSESMS(View view) { startActivity(new Intent(getApplicationContext(), QuickSMS.class)); }

    public void APIS(View view) {
        startActivity(new Intent(getApplicationContext(), APIs.class));
    }

    public void MNP(View view) {
        startActivity(new Intent(getApplicationContext(), MNP.class));
    }

    public void USER(View view) { startActivity(new Intent(getApplicationContext(), UsersList.class)); }

    public void LIVE(View view) { startActivity(new Intent(getApplicationContext(), LiveReports.class)); }

    public void REPORTS(View view) { startActivity(new Intent(getApplicationContext(), OutboxSMS.class)); }



    private void requestPermission() {
        final String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
        ActivityCompat.requestPermissions(this, permissions, ALL_PERMISSIONS);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(AdminDashboard.this,"permission granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(AdminDashboard.this,"permission not granted", Toast.LENGTH_SHORT).show();
            }
        }

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogOutTimerUtil.startLogoutTimer(this, this);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.clear();
        editor.commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
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
       Intent intent = new Intent(AdminDashboard.this, MainActivity.class);
       startActivity(intent);
    }
}
