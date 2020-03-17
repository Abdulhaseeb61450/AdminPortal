package com.example.adminportal.Contact;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adminportal.R;

public class ContactDetail extends AppCompatActivity {

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
}
