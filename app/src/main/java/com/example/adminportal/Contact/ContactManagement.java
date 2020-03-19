package com.example.adminportal.Contact;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.adminportal.Dashboard.AdminDashboard;
import com.example.adminportal.Database.DbHandler;
import com.example.adminportal.LogOutTimerUtil;
import com.example.adminportal.Login.MainActivity;
import com.example.adminportal.R;
import com.example.adminportal.Reports.OutboxSMS;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class ContactManagement extends AppCompatActivity implements LogOutTimerUtil.LogOutListener {

    public List<HashMap<String, String>> AllContactList = new ArrayList<>();
    public List<HashMap<String,String>> BackUp = new ArrayList<>();
    public List<HashMap<String,String>> AllContactList1 = new ArrayList<>();

    public SimpleAdapter adapter;
    private ListView lv;
    public EditText inputsearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_management);

        for (int i = 0; i < 5; i++)
        {
            HashMap<String,String> demo = new HashMap<String,String>();
            demo.put("name","ABDUL"+i);
            demo.put("contact","03102922930"+i);
            AllContactList.add(demo);
        }

        DbHandler db = new DbHandler(this);
        //AllContactList = db.AllContactList();

        lv = (ListView) findViewById(R.id.list_view);
        inputsearch = findViewById(R.id.inputSearch);

        lv.setTextFilterEnabled(true);
        BackUp.addAll(AllContactList);


        adapter = new SimpleAdapter(
                ContactManagement.this, AllContactList,
                R.layout.contact_list_item, new String[]{"name","contact"}, new int[]{
                R.id.contact_name,R.id.contact_no});
        lv.setAdapter(adapter);

        inputsearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                HashMap<String,String> map =(HashMap<String,String>)lv.getItemAtPosition(position);
                String name = map.get("name");
                String contact = map.get("contact");
                Intent intent = new Intent(ContactManagement.this,ContactDetail.class);
                intent.putExtra("name",name);
                intent.putExtra("contact",contact);
                startActivity(intent);

            }
        });

    }

    public void add(View view)
    {
        Intent intent = new Intent(ContactManagement.this,AddContact.class);
        startActivity(intent);
    }

    public void filter(String charText) {
        if (charText.equals(null)) { return; }
        AllContactList1.clear();
        charText = charText.toUpperCase(Locale.getDefault());
        for (HashMap hm : BackUp) {
             if (((String)hm.get("name")).contains(charText)) {
                 AllContactList1.add(hm);
                }
             else if (((String)hm.get("contact")).contains(charText))
             {
                 AllContactList1.add(hm);
             }
        }

        adapter = new SimpleAdapter(
                ContactManagement.this, AllContactList1,
                R.layout.contact_list_item, new String[]{"name","contact"}, new int[]{
                R.id.contact_name,R.id.contact_no});
        lv.setAdapter(adapter);

        adapter.notifyDataSetChanged();
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
        Intent intent = new Intent(ContactManagement.this, MainActivity.class);
        startActivity(intent);
    }
}
