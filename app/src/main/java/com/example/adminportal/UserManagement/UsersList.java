package com.example.adminportal.UserManagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.adminportal.Dashboard.AdminDashboard;
import com.example.adminportal.LogOutTimerUtil;
import com.example.adminportal.Login.MainActivity;
import com.example.adminportal.R;

public class UsersList extends AppCompatActivity implements LogOutTimerUtil.LogOutListener {

    int selectedId;
    public String Type;
    private RadioGroup Gender;
    private RadioButton radioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_list);

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
