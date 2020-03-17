package com.example.adminportal.Masking;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.adminportal.R;
import com.example.adminportal.Masking.UpdateRoutes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

public class MaskRoutes extends AppCompatActivity {

    public static String BASE_URL = "http://mobileapi.dotklick.com/sms/masking/masking_api.php?apiskey=DOf0c268c2a4315987s1er4t6&api=";
    ImageView imageView;
    String Id, temp;
    private static final int ACCESS_NETWORK_STATE = 101;
    private static final int INTERNET = 102;
    private ProgressDialog progressDialog;
    EditText search;
    private ListView lv;
    String Add;
    public ArrayAdapter<String> adapter;
    public List<String> Masklist, MASKLIST;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maskroutes);

        progressDialog = new ProgressDialog(MaskRoutes.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setTitle("ProgressDialog");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        progressDialog.setCancelable(false);

        isNetworkConnectionAvailable();

        GetData1();

        imageView = findViewById(R.id.simpleImageView);
        imageView.setImageResource(R.drawable.header);

        lv = (ListView) findViewById(R.id.list_view);
        search = (EditText) findViewById(R.id.inputSearch);


        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                MaskRoutes.this.adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                temp = lv.getItemAtPosition(position).toString().trim();
                Toast.makeText(MaskRoutes.this, temp, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(MaskRoutes.this, UpdateRoutes.class);
                intent.putExtra("temp", temp);
                startActivity(intent);

            }
        });


    }


    public void ADD(View view)
    {
        Add = search.getText().toString();
        AddMask();
    }

    private void GetData1() {
        JSONObject param = new JSONObject();
        try {
            param.put("apiskey","DOf0c268c2a4315987s1er4t6");
            param.put("api","getallmask");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final String URL = "http://mobileapi.dotklick.com/sms/masking/masking_api.php";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL, param,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject ids = response.getJSONObject("response");
                            Id = ids.getString("Masking_List");
                            Masklist = Arrays.asList(Id.split("\\s*,\\s*"));

                            adapter = new ArrayAdapter<String>(MaskRoutes.this, R.layout.list_item, R.id.mask_name, Masklist);
                            lv.setAdapter(adapter);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        adapter.notifyDataSetChanged();

                        progressDialog.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MaskRoutes.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }) {
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);
    }


    private void AddMask() {

        JSONObject param = new JSONObject();
        try {
            param.put("apiskey","DOf0c268c2a4315987s1er4t6");
            param.put("api","addmaskroute");
            param.put("mask",Add);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final String URL = "http://mobileapi.dotklick.com/sms/masking/masking_api.php";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL, param,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject RESPONSE = response.getJSONObject("response");
                            String STATUS = RESPONSE.getString("status");
                            if (STATUS.equals("Success"))
                            {
                                Toast.makeText(MaskRoutes.this,"ADDED",Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(MaskRoutes.this, MaskRoutes.class);
                                startActivity(intent);
                            }
                            else
                            {
                                Toast.makeText(MaskRoutes.this,"Unable To Add",Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MaskRoutes.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }) {
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);
    }



    public boolean isNetworkConnectionAvailable() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnected();
        if (isConnected) {
            Log.d("Network", "Connected");
            return true;
        } else {
            checkNetworkConnection();
            Log.d("Network", "Not Connected");
            return false;
        }
    }

    public void checkNetworkConnection() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
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
