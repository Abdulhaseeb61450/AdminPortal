package com.example.adminportal.Masking;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.adminportal.Dashboard.AdminDashboard;
import com.example.adminportal.LogOutTimerUtil;
import com.example.adminportal.Login.MainActivity;
import com.example.adminportal.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.adminportal.Masking.MaskRoutes.BASE_URL;

public class UpdateRoutes extends AppCompatActivity implements LogOutTimerUtil.LogOutListener {

    Spinner MobilinkPure,ZongPure,WaridPure,UfonePure,TelenorPure,MobilinkMNP,ZongMNP,WaridMNP,UfoneMNP,TelenorMNP;
    Button UpdateRoutes;
    TextView Masking;
    public String ResponseStatus;
    public String temp,mobilinkpk,mobilinkpv,zongpk,zongpv,ufonepk,ufonepv,waridpk,waridpv,telenorpk,telenorpv;
    public String mobilinkmk,mobilinkmv,zongmk,zongmv,ufonemk,ufonemv,waridmk,waridmv,telenormk,telenormv;
    public ArrayAdapter<Routes> myAdaptor,myAdaptor1,myAdaptor2,myAdaptor3,myAdaptor4,myAdaptor5,myAdaptor6,myAdaptor7,myAdaptor8,myAdaptor9;
    private ProgressDialog progressDialog;

    public ArrayList<String> ForComparision = new ArrayList<>();
    public static ArrayList<String> SelectedOptions = new ArrayList<>();
    public static ArrayList<String> SelectedCodes = new ArrayList<>();
    public ArrayList<Routes> route = new ArrayList<>();
    public ArrayList<SelectedRoutes> SELECTEDROUTES = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updateroutes);

        progressDialog = new ProgressDialog(UpdateRoutes.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setTitle("ProgressDialog");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        progressDialog.setCancelable(false);

        UpdateRoutes = findViewById(R.id.Update);
        Masking = findViewById(R.id.masking);

        temp = getIntent().getExtras().getString("temp");

        Masking.setText("Masking Routes: " + temp);

        GetDetails();


        MobilinkPure = findViewById(R.id.MobilinkPure);
        myAdaptor = new ArrayAdapter<Routes>(UpdateRoutes.this,
                android.R.layout.simple_list_item_1, route);
        myAdaptor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        MobilinkPure.setAdapter(myAdaptor);

        ZongPure = findViewById(R.id.ZongPure);
        myAdaptor1 = new ArrayAdapter<Routes>(UpdateRoutes.this,
                android.R.layout.simple_list_item_1, route);
        myAdaptor1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ZongPure.setAdapter(myAdaptor1);

        WaridPure = findViewById(R.id.WaridPure);
        myAdaptor2 = new ArrayAdapter<Routes>(UpdateRoutes.this,
                android.R.layout.simple_list_item_1, route);
        myAdaptor2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        WaridPure.setAdapter(myAdaptor2);

        UfonePure = findViewById(R.id.UfonePure);
        myAdaptor3 = new ArrayAdapter<Routes>(UpdateRoutes.this,
                android.R.layout.simple_list_item_1,route);
        myAdaptor3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        UfonePure.setAdapter(myAdaptor3);

        TelenorPure = findViewById(R.id.TelenorPure);
        myAdaptor4 = new ArrayAdapter<Routes>(UpdateRoutes.this,
                android.R.layout.simple_list_item_1, route);
        myAdaptor4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        TelenorPure.setAdapter(myAdaptor4);

        MobilinkMNP = findViewById(R.id.MobilinkMNP);
        myAdaptor5 = new ArrayAdapter<Routes>(UpdateRoutes.this,
                android.R.layout.simple_list_item_1, route);
        myAdaptor5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        MobilinkMNP.setAdapter(myAdaptor5);

        ZongMNP = findViewById(R.id.ZongMNP);
        myAdaptor6 = new ArrayAdapter<Routes>(UpdateRoutes.this,
                android.R.layout.simple_list_item_1,route);
        myAdaptor6.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ZongMNP.setAdapter(myAdaptor6);

        UfoneMNP = findViewById(R.id.UfoneMNP);
        myAdaptor7 = new ArrayAdapter<Routes>(UpdateRoutes.this,
                android.R.layout.simple_list_item_1, route);
        myAdaptor7.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        UfoneMNP.setAdapter(myAdaptor7);

        WaridMNP = findViewById(R.id.WaridMNP);
        myAdaptor8 = new ArrayAdapter<Routes>(UpdateRoutes.this,
                android.R.layout.simple_list_item_1, route);
        myAdaptor8.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        WaridMNP.setAdapter(myAdaptor8);

        TelenorMNP = findViewById(R.id.TelenorMNP);
        myAdaptor9 = new ArrayAdapter<Routes>(UpdateRoutes.this,
                android.R.layout.simple_list_item_1,route);
        myAdaptor9.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        TelenorMNP.setAdapter(myAdaptor9);

    }

    public void UpdateRoutes(View view)
    {
        mobilinkpk = ((Routes) MobilinkPure.getSelectedItem ()).getRouteKey ();
        mobilinkpv = ((Routes) MobilinkPure.getSelectedItem ()).getRouteValue ();
        zongpk = ((Routes) ZongPure.getSelectedItem ()).getRouteKey ();
        zongpv = ((Routes) ZongPure.getSelectedItem ()).getRouteValue ();
        ufonepk = ((Routes) UfonePure.getSelectedItem ()).getRouteKey ();
        ufonepv = ((Routes) UfonePure.getSelectedItem ()).getRouteValue ();
        waridpk = ((Routes) WaridPure.getSelectedItem ()).getRouteKey ();
        waridpv = ((Routes) WaridPure.getSelectedItem ()).getRouteValue ();
        telenorpk = ((Routes) TelenorPure.getSelectedItem ()).getRouteKey ();
        telenorpv = ((Routes) TelenorPure.getSelectedItem ()).getRouteValue ();
        mobilinkmk = ((Routes) MobilinkMNP.getSelectedItem ()).getRouteKey ();
        mobilinkmv = ((Routes) MobilinkMNP.getSelectedItem ()).getRouteValue ();
        zongmk = ((Routes) ZongMNP.getSelectedItem ()).getRouteKey ();
        zongmv = ((Routes) ZongMNP.getSelectedItem ()).getRouteValue ();
        ufonemk = ((Routes) UfoneMNP.getSelectedItem ()).getRouteKey ();
        ufonemv = ((Routes) UfoneMNP.getSelectedItem ()).getRouteValue ();
        waridmk = ((Routes) WaridMNP.getSelectedItem ()).getRouteKey ();
        waridmv = ((Routes) WaridMNP.getSelectedItem ()).getRouteValue ();
        telenormk = ((Routes) TelenorMNP.getSelectedItem ()).getRouteKey ();
        telenormv = ((Routes) TelenorMNP.getSelectedItem ()).getRouteValue ();

        SendUpdate();

    }

    private void SendUpdate()
    {
        JSONArray mask_routes = new JSONArray();
        JSONArray mobilinkpure = new JSONArray();
        JSONArray zongpure = new JSONArray();
        JSONArray ufonepure = new JSONArray();
        JSONArray waridpure = new JSONArray();
        JSONArray telenorpure = new JSONArray();
        JSONArray mobilinkmnp = new JSONArray();
        JSONArray zongmnp = new JSONArray();
        JSONArray ufonemnp = new JSONArray();
        JSONArray waridmnp = new JSONArray();
        JSONArray telenormnp = new JSONArray();
        JSONObject params1 = new JSONObject();
        JSONObject params = new JSONObject();
        try {
            mobilinkpure.put("9230");
            mobilinkpure.put(mobilinkpk);
            mobilinkpure.put(mobilinkpv);
            zongpure.put("9231");
            zongpure.put(zongpk);
            zongpure.put(zongpv);
            ufonepure.put("9233");
            ufonepure.put(ufonepk);
            ufonepure.put(ufonepv);
            waridpure.put("9232");
            waridpure.put(waridpk);
            waridpure.put(waridpv);
            telenorpure.put("9234");
            telenorpure.put(telenorpk);
            telenorpure.put(telenorpv);
            mobilinkmnp.put("1923");
            mobilinkmnp.put(mobilinkmk);
            mobilinkmnp.put(mobilinkmv);
            zongmnp.put("4923");
            zongmnp.put(zongmk);
            zongmnp.put(zongmv);
            ufonemnp.put("3923");
            ufonemnp.put(ufonemk);
            ufonemnp.put(ufonemv);
            waridmnp.put("7923");
            waridmnp.put(waridpk);
            waridmnp.put(waridpv);
            telenormnp.put("6923");
            telenormnp.put(telenormk);
            telenormnp.put(telenormv);
            mask_routes.put(mobilinkpure);
            mask_routes.put(zongpure);
            mask_routes.put(telenorpure);
            mask_routes.put(waridpure);
            mask_routes.put(ufonepure);
            mask_routes.put(mobilinkmnp);
            mask_routes.put(zongmnp);
            mask_routes.put(telenormnp);
            mask_routes.put(waridmnp);
            mask_routes.put(ufonemnp);
            params1.put("mask",temp);
            params1.put("mask_routes",mask_routes);
            params.put("apiskey","DOf0c268c2a4315987s1er4t6");
            params.put("api","updatemaskroute");
            params.put("data",params1);


        } catch (Exception e) {
            e.printStackTrace();
        }

        final String URL = "http://mobileapi.dotklick.com/sms/masking/masking_api.php";

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                URL, params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONObject RESPONSE = response.getJSONObject("response");
                            ResponseStatus = RESPONSE.getString("status");
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }

                        if (ResponseStatus.equals("Success"))
                        {
                            Toast.makeText(UpdateRoutes.this,"Updated Successfully",Toast.LENGTH_LONG).show();
                            Intent i = new Intent(getApplicationContext(), MaskRoutes.class);
                            startActivity(i);
                        }
                        else {
                            Toast.makeText(UpdateRoutes.this, "Unable to Update Please Check Your Internet Connection", Toast.LENGTH_LONG).show();

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(UpdateRoutes.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){

        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjReq);

    }



    private void GetDetails()
    {

        ForComparision.clear();
        SelectedOptions.clear();
        SelectedCodes.clear();

        JSONObject param = new JSONObject();
        try {
            param.put("apiskey","DOf0c268c2a4315987s1er4t6");
            param.put("api","getmaskroute");
            param.put("mask",temp);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final String URL = "http://mobileapi.dotklick.com/sms/masking/masking_api.php";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL,param,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try
                        {
                            JSONObject Wholeresponse= response.getJSONObject("response");
                            JSONArray routelists = Wholeresponse.getJSONArray("routes_list");
                                for (int i = 0; i < routelists.length();i++){
                                    JSONArray routelistitems = routelists.getJSONArray(i);
                                        String Value = routelistitems.getString(1);
                                        String Key = routelistitems.getString(0);

                                        ForComparision.add(Key);

                                        Routes routes = new Routes();
                                        routes.setRouteKey(Key);
                                        routes.setRouteValue(Value);

                                        route.add(routes);
                                }

                                try {
                                    JSONArray maproutes = Wholeresponse.getJSONArray("mask_routes");
                                    for (int j = 0; j < maproutes.length(); j++) {
                                        JSONArray maproutesitems = maproutes.getJSONArray(j);
                                        String Value = maproutesitems.getString(2);
                                        String Key = maproutesitems.getString(1);
                                        String Code = maproutesitems.getString(0);

                                        SelectedRoutes selectedRoutes = new SelectedRoutes();
                                        selectedRoutes.setSelectedRouteKey(Key);
                                        selectedRoutes.setSelectectedRouteValue(Value);
                                        selectedRoutes.setSelectedCode(Code);

                                        SELECTEDROUTES.add(selectedRoutes);

                                        SelectedOptions.add(Key);
                                        SelectedCodes.add(Code);


                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }


                        }
                        catch (JSONException e)
                        {
                            e.printStackTrace();
                        }

                        if (SelectedOptions.size()>0)
                        {
                            for (int i = 0; i < SelectedCodes.size(); i++) {
                                String test = SelectedCodes.get(i);
                                int test1 = ForComparision.indexOf(SelectedOptions.get(i));

                                if (test.equals("9230")) {
                                    MobilinkPure.setSelection(test1);
                                }
                                else if (test.equals("9234"))
                                {
                                    TelenorPure.setSelection(test1);
                                }
                                else if (test.equals("9231"))
                                {
                                    ZongPure.setSelection(test1);
                                }
                                else if (test.equals("9232"))
                                {
                                    WaridPure.setSelection(test1);
                                }
                                else if (test.equals("9233"))
                                {
                                    UfonePure.setSelection(test1);
                                }
                                else if (test.equals("1923"))
                                {
                                    MobilinkMNP.setSelection(test1);
                                }
                                else if (test.equals("7923"))
                                {
                                    WaridMNP.setSelection(test1);
                                }
                                else if (test.equals("6923"))
                                {
                                    TelenorMNP.setSelection(test1);
                                }
                                else if (test.equals("3923"))
                                {
                                    UfoneMNP.setSelection(test1);
                                }
                                else if (test.equals("4923"))
                                {
                                    ZongMNP.setSelection(test1);
                                }
                            }

                            myAdaptor.notifyDataSetChanged();
                            myAdaptor1.notifyDataSetChanged();
                            myAdaptor2.notifyDataSetChanged();
                            myAdaptor3.notifyDataSetChanged();
                            myAdaptor4.notifyDataSetChanged();
                            myAdaptor5.notifyDataSetChanged();
                            myAdaptor6.notifyDataSetChanged();
                            myAdaptor7.notifyDataSetChanged();
                            myAdaptor8.notifyDataSetChanged();
                            myAdaptor9.notifyDataSetChanged();
                        }
                        else
                        {

                        }

                        progressDialog.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Toast.makeText(Update.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);
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
        Intent intent = new Intent(UpdateRoutes.this, MainActivity.class);
        startActivity(intent);
    }
}
