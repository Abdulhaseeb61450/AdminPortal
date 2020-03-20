package com.example.adminportal.Campaign;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.adminportal.API.APIs;
import com.example.adminportal.ComposeSMS.QuickSMS;
import com.example.adminportal.Contact.ContactManagement;
import com.example.adminportal.Dashboard.AdminDashboard;
import com.example.adminportal.LogOutTimerUtil;
import com.example.adminportal.Login.MainActivity;
import com.example.adminportal.R;
import com.example.adminportal.Campaign.UpdateCampaignRoutes;
import com.example.adminportal.Reports.OutboxSMS;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONException;
import org.json.JSONObject;

public class StopDetails extends AppCompatActivity implements LogOutTimerUtil.LogOutListener  {

    TextView campid,sender,campaigntime,mobilink,telenor,zong,ufone,warid,others,totalsms,totalsent,remainingsms;
    public String CAMPID,SENDER,CAMPAIGNTIME,MOBILINK,TELENOR,ZONG,UFONE,WARID,OTHERS,TOTALSMS,TOTALSENT,REMAININGSMS;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stop_details);

        progressDialog = new ProgressDialog(StopDetails.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setTitle("ProgressDialog");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        progressDialog.setCancelable(false);

        isNetworkConnectionAvailable();

        progressDialog.dismiss();

        BottomNavigationView bottomNavlast = findViewById(R.id.bottom1_navigation);
        bottomNavlast.setOnNavigationItemSelectedListener(navListenerlast);
        bottomNavlast.getMenu().getItem(0).setCheckable(false);
        bottomNavlast.getMenu().getItem(1).setCheckable(false);
        bottomNavlast.getMenu().getItem(2).setCheckable(false);
        bottomNavlast.getMenu().getItem(3).setCheckable(false);
        bottomNavlast.getMenu().getItem(4).setCheckable(false);

        campid = findViewById(R.id.campid);
        sender = findViewById(R.id.sender);
        campaigntime = findViewById(R.id.campaigntime);
        mobilink = findViewById(R.id.mobilink);
        telenor = findViewById(R.id.telenor);
        zong = findViewById(R.id.zong);
        ufone = findViewById(R.id.ufone);
        warid = findViewById(R.id.warid);
        others = findViewById(R.id.others);
        totalsms = findViewById(R.id.totalsms);
        totalsent = findViewById(R.id.totalsent);
        remainingsms = findViewById(R.id.remainingsms);

        campid.setText("001");
        sender.setText("Dotklick");
        campaigntime.setText("02-02-2020 02-30 PM");
        mobilink.setText("SENT: 20" + "\nREMAINING: 30");
        zong.setText("SENT: 20" + "\nREMAINING: 30");
        ufone.setText("SENT: 20" + "\nREMAINING: 30");
        warid.setText("SENT: 20" + "\nREMAINING: 30");
        telenor.setText("SENT: 20" + "\nREMAINING: 30");
        others.setText("SENT: 20" + "\nREMAINING: 30");
        totalsms.setText("400");
        totalsent.setText("200");
        remainingsms.setText("200");

        //GetDetails();

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListenerlast =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    switch (item.getItemId()) {
                        case R.id.nav_dash:
                            item.setCheckable(true);
                            Intent dashintent = new Intent(StopDetails.this,AdminDashboard.class);
                            startActivity(dashintent);
                            break;
                        case R.id.nav_contact:
                            item.setCheckable(true);
                            Intent ContactIntent = new Intent(StopDetails.this, ContactManagement.class);
                            startActivity(ContactIntent);
                            break;
                        case R.id.nav_comp:
                            item.setCheckable(true);
                            Intent ComposeIntent = new Intent(StopDetails.this, QuickSMS.class);
                            startActivity(ComposeIntent);
                            break;
                        case R.id.nav_repo:
                            item.setCheckable(true);
                            Intent ReportIntent = new Intent(StopDetails.this, OutboxSMS.class);
                            startActivity(ReportIntent);
                            break;
                        case R.id.nav_api:
                            item.setCheckable(true);
                            Intent ApiIntent = new Intent(StopDetails.this, APIs.class);
                            startActivity(ApiIntent);
                            break;
                    }

                    return true;
                }
            };

    public void GetRoutes(View view)
    {
        Intent intent = new Intent(StopDetails.this, UpdateCampaignRoutes.class);
        startActivity(intent);
    }

    public void GetDetails()
    {

        JSONObject params = new JSONObject();
        String URL = "";

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                URL, params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (response.equals(null))
                        {
                            Toast.makeText(StopDetails.this,"res",Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            try
                            {
                                JSONObject Response = response.getJSONObject("response");
                                CAMPID = Response.get("campid").toString().trim();
                                SENDER = Response.get("sender").toString().trim();
                                CAMPAIGNTIME = Response.get("campaigntime").toString().trim();
                                MOBILINK = Response.get("mobilink").toString().trim();
                                TELENOR = Response.get("telenor").toString().trim();
                                ZONG = Response.get("zong").toString().trim();
                                UFONE = Response.get("ufone").toString().trim();
                                WARID = Response.get("warid").toString().trim();
                                OTHERS = Response.get("others").toString().trim();
                                TOTALSMS = Response.get("totalsms").toString().trim();
                                TOTALSENT = Response.get("totalsent").toString().trim();
                                REMAININGSMS = Response.get("remainingsms").toString().trim();

                                campid.setText(CAMPID);
                                sender.setText(SENDER);
                                campaigntime.setText(CAMPAIGNTIME);
                                mobilink.setText(MOBILINK);
                                zong.setText(ZONG);
                                ufone.setText(UFONE);
                                warid.setText(WARID);
                                telenor.setText(TELENOR);
                                others.setText(OTHERS);
                                totalsms.setText(TOTALSMS);
                                totalsent.setText(TOTALSENT);
                                remainingsms.setText(REMAININGSMS);


                            }
                            catch (JSONException e)
                            {
                                e.printStackTrace();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(StopDetails.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){

        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjReq);

    }

    public void checkPermission(String permission, int requestCode)
    {
        if (ContextCompat.checkSelfPermission(StopDetails.this, permission) == PackageManager.PERMISSION_DENIED)
        {
            ActivityCompat.requestPermissions(StopDetails.this,
                    new String[] { permission },
                    requestCode);
        }
        else { }
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
        Intent intent = new Intent(StopDetails.this, MainActivity.class);
        startActivity(intent);
    }

}
