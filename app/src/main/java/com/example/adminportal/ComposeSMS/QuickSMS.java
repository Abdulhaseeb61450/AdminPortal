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
import com.example.adminportal.LogOutTimerUtil;
import com.example.adminportal.Login.MainActivity;
import com.example.adminportal.R;
import com.google.android.material.textfield.TextInputLayout;

import org.w3c.dom.Text;

public class QuickSMS extends AppCompatActivity implements LogOutTimerUtil.LogOutListener {

    EditText contactlist,message,senderid;
    TextView contactlimit,messagelimit;
    public int ascii,decreaselimit;
    public int limit = 160;
    public int COUNT = 0;

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
                contactlimit.setText("200/200");
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String st = s.toString();
                countChar(st,',');
                contactlimit.setText(200 - COUNT + "/200");
            }

            @Override
            public void afterTextChanged(Editable s) {

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

    public int countChar(String str, char c)
    {

        COUNT = 0;

        for(int i=0; i < str.length(); i++)
        {    if(str.charAt(i) == c)
            COUNT++;
        }

        return COUNT;
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
        Intent intent = new Intent(QuickSMS.this, MainActivity.class);
        startActivity(intent);
    }
}
