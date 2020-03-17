package com.example.adminportal.UserManagement;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.RadioButton;

import com.example.adminportal.R;

public class UsersList extends AppCompatActivity {

    int selectedRadioButtonID;
    public String Type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_list);

        RadioButton selectedRadioButton = (RadioButton) findViewById(selectedRadioButtonID);
        Type = selectedRadioButton.getText().toString().trim();
        Type = selectedRadioButton.getText().toString().trim();
    }
}
