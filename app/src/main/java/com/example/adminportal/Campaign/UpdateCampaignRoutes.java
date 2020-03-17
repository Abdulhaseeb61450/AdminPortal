package com.example.adminportal.Campaign;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.adminportal.R;

import java.util.ArrayList;

public class UpdateCampaignRoutes extends AppCompatActivity {

    public ArrayAdapter<String> myAdaptor,myAdaptor1,myAdaptor2,myAdaptor3,myAdaptor4,myAdaptor5,myAdaptor6,myAdaptor7,myAdaptor8,myAdaptor9;
    Spinner Mobilink,Zong,Warid,Ufone,Telenor,MobilinkFlog,ZongFlog,WaridFlog,UfoneFlog,TelenorFlog;
    public ArrayList<String> route = new ArrayList<>();
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_campaign_routes);

        progressDialog = new ProgressDialog(UpdateCampaignRoutes.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setTitle("ProgressDialog");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        progressDialog.setCancelable(false);

        isNetworkConnectionAvailable();

        progressDialog.dismiss();

        Mobilink = findViewById(R.id.Mobilink);
        myAdaptor = new ArrayAdapter<String>(UpdateCampaignRoutes.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.names));
        myAdaptor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Mobilink.setAdapter(myAdaptor);

        Zong = findViewById(R.id.Zong);
        myAdaptor1 = new ArrayAdapter<String>(UpdateCampaignRoutes.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.names));
        myAdaptor1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Zong.setAdapter(myAdaptor1);


        Warid = findViewById(R.id.Warid);
        myAdaptor2 = new ArrayAdapter<String>(UpdateCampaignRoutes.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.names));
        myAdaptor2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Warid.setAdapter(myAdaptor2);

        Ufone = findViewById(R.id.Ufone);
        myAdaptor3 = new ArrayAdapter<String>(UpdateCampaignRoutes.this,
                android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.names));
        myAdaptor3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Ufone.setAdapter(myAdaptor3);

        Telenor = findViewById(R.id.Telenor);
        myAdaptor4 = new ArrayAdapter<String>(UpdateCampaignRoutes.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.names));
        myAdaptor4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Telenor.setAdapter(myAdaptor4);

        MobilinkFlog = findViewById(R.id.Mobilinkflog);
        myAdaptor5 = new ArrayAdapter<String>(UpdateCampaignRoutes.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.names));
        myAdaptor5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        MobilinkFlog.setAdapter(myAdaptor5);

        ZongFlog = findViewById(R.id.Zongflog);
        myAdaptor6 = new ArrayAdapter<String>(UpdateCampaignRoutes.this,
                android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.names));
        myAdaptor6.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ZongFlog.setAdapter(myAdaptor6);

        UfoneFlog = findViewById(R.id.Ufoneflog);
        myAdaptor7 = new ArrayAdapter<String>(UpdateCampaignRoutes.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.names));
        myAdaptor7.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        UfoneFlog.setAdapter(myAdaptor7);

        WaridFlog = findViewById(R.id.Waridflog);
        myAdaptor8 = new ArrayAdapter<String>(UpdateCampaignRoutes.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.names));
        myAdaptor8.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        WaridFlog.setAdapter(myAdaptor8);

        TelenorFlog = findViewById(R.id.Telenorflog);
        myAdaptor9 = new ArrayAdapter<String>(UpdateCampaignRoutes.this,
                android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.names));
        myAdaptor9.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        TelenorFlog.setAdapter(myAdaptor9);
    }

    public boolean isNetworkConnectionAvailable(){
        ConnectivityManager cm =
                (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnected();
        if(isConnected) {
            Log.d("Network", "Connected");
            return true;
        }
        else{
            checkNetworkConnection();
            Log.d("Network","Not Connected");
            return false;
        }
    }

    public void checkNetworkConnection(){
        AlertDialog.Builder builder =new AlertDialog.Builder(this);
        builder.setTitle("No internet Connection");
        builder.setMessage("Please turn on internet connection to continue");
        builder.setNegativeButton("close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
