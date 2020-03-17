package com.example.adminportal.LiveReports;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.adminportal.R;

public class LiveReports extends AppCompatActivity {

    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_reports);

        lv = findViewById(R.id.list_view);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(LiveReports.this, ReportDetail.class);
                startActivity(intent);

            }
        });

    }
}
