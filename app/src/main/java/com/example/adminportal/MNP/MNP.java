package com.example.adminportal.MNP;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.adminportal.Masking.Routes;
import com.example.adminportal.Masking.UpdateRoutes;
import com.example.adminportal.R;

import java.util.ArrayList;

public class MNP extends AppCompatActivity {

    EditText mobileno;
    Spinner MNPList;
    public ArrayAdapter<Routes> myAdaptor;
    public ArrayList<Routes> route = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mnp);

        MNPList = findViewById(R.id.MNPSpinner);
        myAdaptor = new ArrayAdapter<Routes>(MNP.this,
                android.R.layout.simple_list_item_1, route);
        myAdaptor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        MNPList.setAdapter(myAdaptor);
    }
}
