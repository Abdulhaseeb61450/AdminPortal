package com.example.adminportal.ComposeSMS;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.adminportal.R;

import java.util.Calendar;

public class CampaignSMS extends AppCompatActivity {

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


}
