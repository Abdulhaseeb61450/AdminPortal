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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.adminportal.API.APIs;
import com.example.adminportal.Campaign.EndCampaign;
import com.example.adminportal.Campaign.FutureCampaign;
import com.example.adminportal.ComposeSMS.QuickSMS;
import com.example.adminportal.Contact.ContactManagement;
import com.example.adminportal.Dashboard.AdminDashboard;
import com.example.adminportal.LogOutTimerUtil;
import com.example.adminportal.Login.MainActivity;
import com.example.adminportal.R;
import com.example.adminportal.Campaign.TodayCampaign;
import com.example.adminportal.Campaign.ViewDetails;
import com.example.adminportal.Reports.OutboxSMS;
import com.example.adminportal.UserManagement.UsersList;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import static android.view.ViewGroup.FOCUS_BLOCK_DESCENDANTS;

public class Campaign extends AppCompatActivity implements LogOutTimerUtil.LogOutListener {

    private ProgressDialog progressDialog;
    public ListView lv;
    public static ArrayList<HashMap<String, String>> Today = new ArrayList<>();
    public static ArrayList<HashMap<String, String>> Future = new ArrayList<>();
    public static ArrayList<HashMap<String, String>> Previous = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campaign);


        progressDialog = new ProgressDialog(Campaign.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setTitle("ProgressDialog");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        progressDialog.setCancelable(false);

        isNetworkConnectionAvailable();

        progressDialog.dismiss();




        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        BottomNavigationView bottomNavlast = findViewById(R.id.bottom1_navigation);
        bottomNavlast.setOnNavigationItemSelectedListener(navListenerlast);
        bottomNavlast.getMenu().getItem(0).setCheckable(false);
        bottomNavlast.getMenu().getItem(1).setCheckable(false);
        bottomNavlast.getMenu().getItem(2).setCheckable(false);
        bottomNavlast.getMenu().getItem(3).setCheckable(false);
        bottomNavlast.getMenu().getItem(4).setCheckable(false);

        //I added this if statement to keep the selected fragment when rotating the device
        if (savedInstanceState == null) {
            //GetCurrentData();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new TodayCampaign()).commit();
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListenerlast =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    switch (item.getItemId()) {
                        case R.id.nav_dash:
                            item.setCheckable(true);
                            Intent dashintent = new Intent(Campaign.this,AdminDashboard.class);
                            startActivity(dashintent);
                            break;
                        case R.id.nav_contact:
                            item.setCheckable(true);
                            Intent ContactIntent = new Intent(Campaign.this, ContactManagement.class);
                            startActivity(ContactIntent);
                            break;
                        case R.id.nav_comp:
                            item.setCheckable(true);
                            Intent ComposeIntent = new Intent(Campaign.this, QuickSMS.class);
                            startActivity(ComposeIntent);
                            break;
                        case R.id.nav_repo:
                            item.setCheckable(true);
                            Intent ReportIntent = new Intent(Campaign.this, OutboxSMS.class);
                            startActivity(ReportIntent);
                            break;
                        case R.id.nav_api:
                            item.setCheckable(true);
                            Intent ApiIntent = new Intent(Campaign.this, APIs.class);
                            startActivity(ApiIntent);
                            break;
                    }

                    return true;
                }
            };

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()) {
                        case R.id.nav_home:
                            //GetCurrentData();
                            selectedFragment = new TodayCampaign();
                            break;
                        case R.id.nav_favorites:
                            //GetFutureData();
                            selectedFragment = new FutureCampaign();
                            break;
                        case R.id.nav_search:
                            //GetPreviousData();
                            selectedFragment = new EndCampaign();
                            break;
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();

                    return true;
                }
            };

    public void Get(View v) {
        switch (v.getId()) {
            case R.id.view:
                Intent intent = new Intent(Campaign.this, ViewDetails.class);
                startActivity(intent);
                break;
        }
        /*switch (v.getId()) {
            case R.id.play:

                Button play = findViewById(R.id.play);
                String PLAY = play.getText().toString();
                play.setText("End");
                Intent intent = new Intent(Campaign.this,StopDetails.class);
                startActivity(intent);
                break;
        }*/
    }

    private void GetCurrentData()
    {

        Today.clear();
        JSONObject params = new JSONObject();
        try {
            params.put("apiskey","DOf0c268c2a4315987s1er4t6");
            params.put("api","");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String URL = "";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL,params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try
                        {
                            JSONObject RESPONSE = response.getJSONObject("response");
                            for (int i = 0; i < response.length(); i++)
                            {

                                String campid = RESPONSE.getString("campid");
                                String mobilink = RESPONSE.getString("mobilink");
                                String telenor = RESPONSE.getString("telenor");
                                String zong = RESPONSE.getString("zong");
                                String username = RESPONSE.getString("username");
                                String sender = RESPONSE.getString("sender");
                                String campaigntime = RESPONSE.getString("campaigntime");
                                String totalsms = RESPONSE.getString("totalsms");
                                String warid = RESPONSE.getString("warid");
                                String ufone = RESPONSE.getString("ufone");
                                String others = RESPONSE.getString("others");
                                String totalsent = RESPONSE.getString("totalsent");
                                String remainingsms = RESPONSE.getString("remainingsms");

                                HashMap<String, String> Detail = new HashMap<>();
                                Detail.put("username",username);
                                Detail.put("sender",sender);
                                Detail.put("campaigntime",campaigntime);
                                Detail.put("totalsms",totalsms);
                                Detail.put("campid",campid);
                                Detail.put("mobilink",mobilink);
                                Detail.put("telenor",telenor);
                                Detail.put("zong",zong);
                                Detail.put("warid",warid);
                                Detail.put("ufone",ufone);
                                Detail.put("others",others);
                                Detail.put("totalsent",totalsent);
                                Detail.put("remainingsms",remainingsms);

                                Today.add(Detail);

                            }

                        }
                        catch (JSONException e)
                        {
                            e.printStackTrace();
                        }

                        progressDialog.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(Campaign.this,error.toString(), Toast.LENGTH_LONG).show();
                    }
                }){
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);
    }

    private void GetFutureData()
    {

        Future.clear();
        JSONObject params = new JSONObject();
        try {
            params.put("apiskey","DOf0c268c2a4315987s1er4t6");
            params.put("api","");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String URL = "";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL,params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try
                        {
                            JSONObject RESPONSE = response.getJSONObject("response");
                            for (int i = 0; i < response.length(); i++)
                            {
                                String campid = RESPONSE.getString("campid");
                                String mobilink = RESPONSE.getString("mobilink");
                                String telenor = RESPONSE.getString("telenor");
                                String zong = RESPONSE.getString("zong");
                                String username = RESPONSE.getString("username");
                                String sender = RESPONSE.getString("sender");
                                String campaigntime = RESPONSE.getString("campaigntime");
                                String totalsms = RESPONSE.getString("totalsms");
                                String warid = RESPONSE.getString("warid");
                                String ufone = RESPONSE.getString("ufone");
                                String others = RESPONSE.getString("others");
                                String totalsent = RESPONSE.getString("totalsent");
                                String remainingsms = RESPONSE.getString("remainingsms");

                                HashMap<String, String> Detail = new HashMap<>();
                                Detail.put("username",username);
                                Detail.put("sender",sender);
                                Detail.put("campaigntime",campaigntime);
                                Detail.put("totalsms",totalsms);
                                Detail.put("campid",campid);
                                Detail.put("mobilink",mobilink);
                                Detail.put("telenor",telenor);
                                Detail.put("zong",zong);
                                Detail.put("warid",warid);
                                Detail.put("ufone",ufone);
                                Detail.put("others",others);
                                Detail.put("totalsent",totalsent);
                                Detail.put("remainingsms",remainingsms);

                                Future.add(Detail);

                            }

                        }
                        catch (JSONException e)
                        {
                            e.printStackTrace();
                        }

                        progressDialog.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(Campaign.this,error.toString(), Toast.LENGTH_LONG).show();
                    }
                }){
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);
    }

    private void GetPreviousData()
    {

        Previous.clear();
        JSONObject params = new JSONObject();
        try {
            params.put("apiskey","DOf0c268c2a4315987s1er4t6");
            params.put("api","");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String URL = "";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL,params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try
                        {
                            JSONObject RESPONSE = response.getJSONObject("response");
                            for (int i = 0; i < response.length(); i++)
                            {
                                String campid = RESPONSE.getString("campid");
                                String mobilink = RESPONSE.getString("mobilink");
                                String telenor = RESPONSE.getString("telenor");
                                String zong = RESPONSE.getString("zong");
                                String username = RESPONSE.getString("username");
                                String sender = RESPONSE.getString("sender");
                                String campaigntime = RESPONSE.getString("campaigntime");
                                String totalsms = RESPONSE.getString("totalsms");
                                String warid = RESPONSE.getString("warid");
                                String ufone = RESPONSE.getString("ufone");
                                String others = RESPONSE.getString("others");
                                String totalsent = RESPONSE.getString("totalsent");
                                String remainingsms = RESPONSE.getString("remainingsms");

                                HashMap<String, String> Detail = new HashMap<>();
                                Detail.put("username",username);
                                Detail.put("sender",sender);
                                Detail.put("campaigntime",campaigntime);
                                Detail.put("totalsms",totalsms);
                                Detail.put("campid",campid);
                                Detail.put("mobilink",mobilink);
                                Detail.put("telenor",telenor);
                                Detail.put("zong",zong);
                                Detail.put("warid",warid);
                                Detail.put("ufone",ufone);
                                Detail.put("others",others);
                                Detail.put("totalsent",totalsent);
                                Detail.put("remainingsms",remainingsms);

                                Previous.add(Detail);

                            }

                        }
                        catch (JSONException e)
                        {
                            e.printStackTrace();
                        }

                        progressDialog.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(Campaign.this,error.toString(), Toast.LENGTH_LONG).show();
                    }
                }){
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);
    }


    public void checkPermission(String permission, int requestCode)
    {
        if (ContextCompat.checkSelfPermission(Campaign.this, permission) == PackageManager.PERMISSION_DENIED)
        {
            ActivityCompat.requestPermissions(Campaign.this,
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
        Intent intent = new Intent(Campaign.this, MainActivity.class);
        startActivity(intent);
    }
}
