package com.example.adminportal.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

public class DbHandler extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "PortalDatabase";
    private static final String TABLE_Contact = "Contact_Details";
    private static final String KEY_Name = "name";
    private static final String KEY_Contact = "contact";

    private static final String TABLE_API = "API";
    private static final String KEY_ID= "id";
    private static final String KEY_APINAME = "apiname";
    private static final String KEY_APIKEY = "apikey";
    private static final String KEY_IPS = "ips";
    private static final String KEY_CREATEDDATE = "createddate";
    private static final String KEY_STATUS = "status";
    private static final String KEY_ACTION = "action";

    private static final String TABLE_OUTBOX = "OutboxSMS";
    private static final String KEY_SMS_TYPE = "SMS Type";
    private static final String KEY_CAMPAIGN_NAME = "Campaign Name";
    private static final String KEY_SENDER = "Sender";
    private static final String KEY_RECIEVER = "Receiver";
    private static final String KEY_MESSAGE = "Message";
    private static final String KEY_SENT_TIME = "Sent Time";
    private static final String KEY_NETWORK_TIME = "Network Time";
    private static final String KEY_COST = "Cost";

    private static final String TABLE_SUMMARY = "Summary";
    private static final String KEY_DATE = "Date";
    private static final String KEY_MOBILINK = "Mobilink";
    private static final String KEY_UFONE = "Ufone";
    private static final String KEY_TELENOR = "Telenor";
    private static final String KEY_ZONG = "Zong";
    private static final String KEY_WARID = "Warid";
    private static final String KEY_TOTAL = "Total";

    private static final String TABLE_Activity = "Activity_Logs";
    private static final String KEY_DATE_TIME = "Date And Time";
    private static final String KEY_IP = "Ip";
    private static final String KEY_Activity = "Activity";



    public DbHandler(Context context){
        super(context,DB_NAME, null, DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db){

        String CREATE_TABLE = "CREATE TABLE " + TABLE_API + "("
                + KEY_ID + " TEXT,"
                + KEY_APINAME + " TEXT,"
                + KEY_APIKEY + " TEXT,"
                + KEY_IPS + " TEXT,"
                + KEY_CREATEDDATE + " TEXT,"
                + KEY_STATUS + " TEXT,"
                + KEY_ACTION + "TEXT"+ ")";
        db.execSQL(CREATE_TABLE);

        String CREATE_TABLE1 = "CREATE TABLE " + TABLE_Contact + "("
                + KEY_Name + " TEXT,"
                + KEY_Contact + " TEXT"+ ")";
        db.execSQL(CREATE_TABLE1);

        String CREATE_TABLE2 = "CREATE TABLE " + TABLE_OUTBOX + "("
                + KEY_SMS_TYPE + " TEXT,"
                + KEY_CAMPAIGN_NAME + " TEXT,"
                + KEY_SENDER + " TEXT,"
                + KEY_RECIEVER + " TEXT,"
                + KEY_MESSAGE + " TEXT,"
                + KEY_SENT_TIME + " TEXT,"
                + KEY_COST + " TEXT,"
                + KEY_NETWORK_TIME + "TEXT"+ ")";
        db.execSQL(CREATE_TABLE2);

        String CREATE_TABLE3 = "CREATE TABLE " + TABLE_SUMMARY + "("
                + KEY_DATE + " TEXT,"
                + KEY_SMS_TYPE + " TEXT,"
                + KEY_SENDER + " TEXT,"
                + KEY_MOBILINK + " TEXT,"
                + KEY_TELENOR + " TEXT,"
                + KEY_UFONE + " TEXT,"
                + KEY_WARID + " TEXT,"
                + KEY_ZONG + " TEXT,"
                + KEY_TOTAL + "TEXT"+ ")";
        db.execSQL(CREATE_TABLE3);

        String CREATE_TABLE4 = "CREATE TABLE " + TABLE_Activity + "("
                + KEY_DATE_TIME + " TEXT,"
                + KEY_ID + " TEXT,"
                + KEY_IP + " TEXT,"
                + KEY_Activity + " TEXT"+ ")";
        db.execSQL(CREATE_TABLE4);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Contact);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_API);
        onCreate(db);
    }


    //*******************************************************************************************************************************//
    //*******************************************************************************************************************************//
    public void InsertContactDetails(String name, String contact){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cValues = new ContentValues();
        cValues.put(KEY_Name, name);
        cValues.put(KEY_Contact, contact);
        long newRowId = db.insert(TABLE_Contact,null, cValues);
        db.close();
    }

    public ArrayList<HashMap<String, String>> AllContactList(){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> ContactList = new ArrayList<>();
        String query = "SELECT * FROM "+ TABLE_Contact;
        Cursor cursor = db.rawQuery(query, null);
            while (cursor.moveToNext()) {
                HashMap<String, String> Contact = new HashMap<>();
                Contact.put("name",cursor.getString(cursor.getColumnIndex(KEY_Name)));
                Contact.put("contact", cursor.getString(cursor.getColumnIndex(KEY_Contact)));
                ContactList.add(Contact);
            }
        cursor.close();
        return  ContactList;
    }
    //*******************************************************************************************************************************//
    //*******************************************************************************************************************************//
    public void InsertAPIDetails(String id, String apiname,String apikey, String ips,String createddate, String status,String action){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cValues = new ContentValues();
        cValues.put(KEY_ID, id);
        cValues.put(KEY_APINAME, apiname);
        cValues.put(KEY_APIKEY, apikey);
        cValues.put(KEY_IPS, ips);
        cValues.put(KEY_CREATEDDATE, createddate);
        cValues.put(KEY_STATUS, status);
        cValues.put(KEY_ACTION, action);
        long newRowId = db.insert(TABLE_API,null, cValues);
        db.close();
    }

    public ArrayList<HashMap<String, String>> AllAPIList(){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> ApiList = new ArrayList<>();
        String query = "SELECT * FROM "+ TABLE_API;
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            HashMap<String, String> Api = new HashMap<>();
            Api.put("id",cursor.getString(cursor.getColumnIndex(KEY_ID)));
            Api.put("apiname", cursor.getString(cursor.getColumnIndex(KEY_APINAME)));
            Api.put("apikey",cursor.getString(cursor.getColumnIndex(KEY_APIKEY)));
            Api.put("ips", cursor.getString(cursor.getColumnIndex(KEY_IPS)));
            Api.put("createddate",cursor.getString(cursor.getColumnIndex(KEY_CREATEDDATE)));
            Api.put("status", cursor.getString(cursor.getColumnIndex(KEY_STATUS)));
            Api.put("action", cursor.getString(cursor.getColumnIndex(KEY_ACTION)));
            ApiList.add(Api);
        }
        cursor.close();
        return  ApiList;
    }
    //*******************************************************************************************************************************//
    //*******************************************************************************************************************************//
    public void InsertOutboxSMSDetails(String smstype, String campaignname,String sender, String reciever,String message, String senttime,String cost,String networktime){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cValues = new ContentValues();
        cValues.put(KEY_SMS_TYPE, smstype);
        cValues.put(KEY_CAMPAIGN_NAME, campaignname);
        cValues.put(KEY_SENDER, sender);
        cValues.put(KEY_RECIEVER, reciever);
        cValues.put(KEY_MESSAGE, message);
        cValues.put(KEY_SENT_TIME, senttime);
        cValues.put(KEY_COST, cost);
        cValues.put(KEY_NETWORK_TIME, networktime);
        long newRowId = db.insert(TABLE_OUTBOX,null, cValues);
        db.close();
    }

    public ArrayList<HashMap<String, String>> AllOutboxSMSList(){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> OutboxList = new ArrayList<>();
        String query = "SELECT * FROM "+ TABLE_OUTBOX;
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            HashMap<String, String> Outbox = new HashMap<>();
            Outbox.put("smstype",cursor.getString(cursor.getColumnIndex(KEY_SMS_TYPE)));
            Outbox.put("campaignname", cursor.getString(cursor.getColumnIndex(KEY_CAMPAIGN_NAME)));
            Outbox.put("sender",cursor.getString(cursor.getColumnIndex(KEY_SENDER)));
            Outbox.put("reciever", cursor.getString(cursor.getColumnIndex(KEY_RECIEVER)));
            Outbox.put("message",cursor.getString(cursor.getColumnIndex(KEY_MESSAGE)));
            Outbox.put("senttime", cursor.getString(cursor.getColumnIndex(KEY_SENT_TIME)));
            Outbox.put("cost", cursor.getString(cursor.getColumnIndex(KEY_COST)));
            Outbox.put("networktime", cursor.getString(cursor.getColumnIndex(KEY_NETWORK_TIME)));
            OutboxList.add(Outbox);
        }
        cursor.close();
        return  OutboxList;
    }
    //*******************************************************************************************************************************//
    //*******************************************************************************************************************************//
    public void InsertSummaryDetails(String date, String smstype,String sender, String mobilink,String ufone, String zong,String warid,String telenor, String total){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cValues = new ContentValues();
        cValues.put(KEY_DATE, date);
        cValues.put(KEY_SMS_TYPE, smstype);
        cValues.put(KEY_SENDER, sender);
        cValues.put(KEY_MOBILINK, mobilink);
        cValues.put(KEY_UFONE, ufone);
        cValues.put(KEY_ZONG, zong);
        cValues.put(KEY_WARID, warid);
        cValues.put(KEY_TELENOR, telenor);
        cValues.put(KEY_TOTAL, total);
        long newRowId = db.insert(TABLE_SUMMARY,null, cValues);
        db.close();
    }

    public ArrayList<HashMap<String, String>> AllSummaryList(){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> SummaryList = new ArrayList<>();
        String query = "SELECT * FROM "+ TABLE_SUMMARY;
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            HashMap<String, String> Summary = new HashMap<>();
            Summary.put("date",cursor.getString(cursor.getColumnIndex(KEY_DATE)));
            Summary.put("smstype", cursor.getString(cursor.getColumnIndex(KEY_SMS_TYPE)));
            Summary.put("sender",cursor.getString(cursor.getColumnIndex(KEY_SENDER)));
            Summary.put("mobilink", cursor.getString(cursor.getColumnIndex(KEY_MOBILINK)));
            Summary.put("ufone",cursor.getString(cursor.getColumnIndex(KEY_UFONE)));
            Summary.put("zong", cursor.getString(cursor.getColumnIndex(KEY_ZONG)));
            Summary.put("warid", cursor.getString(cursor.getColumnIndex(KEY_WARID)));
            Summary.put("telenor", cursor.getString(cursor.getColumnIndex(KEY_TELENOR)));
            Summary.put("total", cursor.getString(cursor.getColumnIndex(KEY_TOTAL)));
            SummaryList.add(Summary);
        }
        cursor.close();
        return  SummaryList;
    }
    //*******************************************************************************************************************************//
    //*******************************************************************************************************************************//
    public void InsertActivityDetails(String datetime, String id,String ip, String activity){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cValues = new ContentValues();
        cValues.put(KEY_DATE_TIME, datetime);
        cValues.put(KEY_ID, id);
        cValues.put(KEY_IP, ip);
        cValues.put(KEY_Activity, activity);
        long newRowId = db.insert(TABLE_Activity,null, cValues);
        db.close();
    }

    public ArrayList<HashMap<String, String>> AllActivityList(){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> ActivityList = new ArrayList<>();
        String query = "SELECT * FROM "+ TABLE_Activity;
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            HashMap<String, String> Activity = new HashMap<>();
            Activity.put("datetime",cursor.getString(cursor.getColumnIndex(KEY_DATE_TIME)));
            Activity.put("id", cursor.getString(cursor.getColumnIndex(KEY_ID)));
            Activity.put("ip",cursor.getString(cursor.getColumnIndex(KEY_IP)));
            Activity.put("activity", cursor.getString(cursor.getColumnIndex(KEY_Activity)));
            ActivityList.add(Activity);
        }
        cursor.close();
        return  ActivityList;
    }
    //*******************************************************************************************************************************//
    //*******************************************************************************************************************************//

}