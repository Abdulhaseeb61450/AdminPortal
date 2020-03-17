package com.example.adminportal.ComposeSMS;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.adminportal.Dashboard.AdminDashboard;
import com.example.adminportal.R;
import com.google.android.material.textfield.TextInputLayout;

import org.w3c.dom.Text;

public class QuickSMS extends AppCompatActivity {

    EditText contactlist,message,senderid;
    TextView contactlimit,messagelimit;
    public int ascii,decreaselimit;
    public int limit = 160;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quick_sms);

        contactlist = findViewById(R.id.contactlist);
        contactlimit = findViewById(R.id.contactlimit);
        senderid = findViewById(R.id.senderid);
        message = findViewById(R.id.message);
        messagelimit = findViewById(R.id.messagelimit);

        contactlist.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                contactlimit.setText("0/200");
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                contactlimit.setText(200 - contactlist.getText().length() + "/200");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        message.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                messagelimit.setText("0/160");
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
                    char a = s.charAt(before);
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


    public void RESET(View view)
    {
        contactlist.setText("");
        message.setText("");
        senderid.setText("");

    }

    public void SEND(View view)
    {
        Intent intent = new Intent(QuickSMS.this, AdminDashboard.class);
        startActivity(intent);
    }
}
