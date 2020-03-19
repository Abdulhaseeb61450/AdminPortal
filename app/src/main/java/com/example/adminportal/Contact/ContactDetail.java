package com.example.adminportal.Contact;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adminportal.Dashboard.AdminDashboard;
import com.example.adminportal.LogOutTimerUtil;
import com.example.adminportal.Login.MainActivity;
import com.example.adminportal.R;

public class ContactDetail extends AppCompatActivity implements LogOutTimerUtil.LogOutListener {

    TextView name,contactno;
    String NAME,CONTACT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_detail);

        Intent intent = getIntent();
        NAME = intent.getStringExtra("name");
        CONTACT = intent.getStringExtra("contact");
        name = findViewById(R.id.name);
        contactno = findViewById(R.id.contactNo);


        name.setText(NAME);
        contactno.setText(CONTACT);
    }

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
