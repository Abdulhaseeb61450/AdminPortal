package com.example.adminportal.Campaign;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.fragment.app.Fragment;

import com.example.adminportal.Dashboard.AdminDashboard;
import com.example.adminportal.LogOutTimerUtil;
import com.example.adminportal.Login.MainActivity;
import com.example.adminportal.R;

import java.util.ArrayList;
import java.util.HashMap;

import static com.example.adminportal.Campaign.Campaign.Previous;


public class EndCampaign extends Fragment {

    public ListAdapter adapter;
    public ArrayList<HashMap<String, String>> StudentDetails = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.end_campaign, container, false);


        for (int i = 0; i<11;i++)
        {
            HashMap<String, String> Detail = new HashMap<>();
            Detail.put("username","ChinaGrill");
            Detail.put("sender","Dotklick");
            Detail.put("campaigntime","22-Jan-2020 01-00 PM");
            Detail.put("totalsms","250 = 150 + 100");

            StudentDetails.add(Detail);
        }

        ListView lv = (ListView) view.findViewById(R.id.endcampaign);

        adapter = new SimpleAdapter(
                getActivity(), StudentDetails,
                R.layout.list_layout, new String[]{"username","sender", "totalsms", "campaigntime"}, new int[]{
                R.id.username,R.id.sender,R.id.totalsms,R.id.campaigntime});
        lv.setAdapter(adapter);


        return view;
    }

}
