package com.example.adminportal.ComposeSMS;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.example.adminportal.R;

import java.util.Calendar;

public class CampaignSMS extends AppCompatActivity {

    Calendar calendar;
    DatePickerDialog picker;
    EditText settime;
    String amPm,DATE,TIME;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campaign_sms);

        settime = findViewById(R.id.settime);

        settime.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
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
            }
        });
    }
}
