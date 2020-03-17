package com.example.adminportal.Login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.adminportal.Dashboard.AdminDashboard;
import com.example.adminportal.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import static com.example.adminportal.Masking.MaskRoutes.BASE_URL;

public class MainActivity extends AppCompatActivity {

    EditText username,password;
    String USERNAME,PASSWORD,ResponseStatus,UserSaved,PassSaved;
    private static final int ACCESS_NETWORK_STATE = 101;
    private static final int INTERNET = 102;
    public static final String mypreference = "mypref";
    SharedPreferences sharedpreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedpreferences = getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        UserSaved = sharedpreferences.getString("username","0");
        PassSaved = sharedpreferences.getString("password","0");

        if (!UserSaved.equals("0") && !PassSaved.equals("0"))
        {
            Intent i = new Intent(getApplicationContext(), AdminDashboard.class);
            startActivity(i);
        }

        username = findViewById(R.id.Username);
        password = findViewById(R.id.Password);



    }


    public void Login(View view)
    {

        if (username.length()==0)
        {
            username.setError("Enter Username");
        }
        else if (password.length()==0)
        {
            password.setError("Enter Password");
        }
        else {

            USERNAME = username.getText().toString().trim();
            PASSWORD = password.getText().toString().trim();
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString("username", USERNAME);
            editor.putString("password", PASSWORD);
            editor.commit();
            Get();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.clear();
        editor.commit();
    }

    private void Get() {

        JSONObject params = new JSONObject();
        try {
            params.put("apiskey","DOf0c268c2a4315987s1er4t6");
            params.put("api","login");
            params.put("username",USERNAME);
            params.put("password",PASSWORD);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        String URL = "http://mobileapi.dotklick.com/sms/account/login.php";
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                URL, params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONObject RESPONSE = response.getJSONObject("response");
                            ResponseStatus = RESPONSE.getString("status");
                            if (ResponseStatus.equals("Success"))
                            {
                                Toast.makeText(MainActivity.this,"Login Successfull",Toast.LENGTH_LONG).show();
                                Intent i = new Intent(getApplicationContext(), AdminDashboard.class);
                                startActivity(i);
                            }
                            else {
                                Toast.makeText(MainActivity.this, "Please Enter Valid Details", Toast.LENGTH_LONG).show();

                            }
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){

        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjReq);
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}
