package com.example.adminportal.ComposeSMS;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.adminportal.API.APIs;
import com.example.adminportal.Contact.ContactManagement;
import com.example.adminportal.Dashboard.AdminDashboard;
import com.example.adminportal.LogOutTimerUtil;
import com.example.adminportal.Login.MainActivity;
import com.example.adminportal.R;
import com.example.adminportal.Reports.OutboxSMS;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Calendar;

public class CampaignSMS extends AppCompatActivity implements LogOutTimerUtil.LogOutListener {

    Calendar calendar;
    DatePickerDialog picker;
    EditText settime,message;
    String amPm,DATE,TIME;
    TextView messagelimit;
    public int ascii,decreaselimit;
    public int limit = 160;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campaign_sms);

        BottomNavigationView bottomNav = findViewById(R.id.bottom1_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        bottomNav.getMenu().getItem(2).setChecked(true);

        settime = findViewById(R.id.settime);
        messagelimit = findViewById(R.id.messagelimit);
        message = findViewById(R.id.message);

        settime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
                int currentMinute = calendar.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(CampaignSMS.this, new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {

                        if (hourOfDay >= 12) {
                            amPm = " PM";
                        } else {
                            amPm = " AM";
                        }
                        DATE = (String.format("%02d:%02d", hourOfDay, minutes) + amPm);

                    }
                }, currentHour, currentMinute, false);
                //final Calendar cldr = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                picker = new DatePickerDialog(CampaignSMS.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                TIME = (year + "-" + (monthOfYear + 1) + "-" + dayOfMonth).toString();
                                settime.setText(DATE + " " + TIME);
                            }
                        }, year, month, day);
                picker.show();
                timePickerDialog.show();

            }
        });

        message.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                messagelimit.setText("160/160");
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (s.length()==0)
                {
                    messagelimit.setText("160/160");
                    return;
                }
                else if (before==0)
                {
                    char a = s.charAt(start);
                    ascii = (int) a;
                    if (ascii>=1575 && ascii<=1746)
                    {
                        decreaselimit = limit-2;
                        messagelimit.setText(decreaselimit + "/160");
                        limit = decreaselimit;
                    }
                    else
                    {
                        messagelimit.setText(160 - message.getText().length() + "/160");
                    }
                }
                else
                {
                    char a = s.charAt(count);
                    ascii = (int) a;
                    if (ascii>=1575 && ascii<=1746)
                    {
                        int decreaselimit = limit+2;
                        messagelimit.setText(decreaselimit + "/160");
                        limit = decreaselimit;
                    }
                    else
                    {
                        messagelimit.setText(160 - message.getText().length() + "/160");
                    }
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    public void SEND(View view)
    {

    }

    public void RESET(View view)
    {

    }


    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()) {
                        case R.id.nav_dash:
                            Intent dashintent = new Intent(CampaignSMS.this,AdminDashboard.class);
                            startActivity(dashintent);
                            break;
                        case R.id.nav_contact:
                            Intent ContactIntent = new Intent(CampaignSMS.this, ContactManagement.class);
                            startActivity(ContactIntent);
                            break;
                        case R.id.nav_comp:
                            Intent ComposeIntent = new Intent(CampaignSMS.this,QuickSMS.class);
                            startActivity(ComposeIntent);
                            break;
                        case R.id.nav_repo:
                            Intent ReportIntent = new Intent(CampaignSMS.this, OutboxSMS.class);
                            startActivity(ReportIntent);
                            break;
                        case R.id.nav_api:
                            Intent ApiIntent = new Intent(CampaignSMS.this, APIs.class);
                            startActivity(ApiIntent);
                            break;
                    }

                    /*getSupportFragmentManager().beginTransaction().replace(R.id.fragment1_container,
                            selectedFragment).commit();*/

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
        Intent intent = new Intent(CampaignSMS.this, MainActivity.class);
        startActivity(intent);
    }
}
