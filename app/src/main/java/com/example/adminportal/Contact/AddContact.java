package com.example.adminportal.Contact;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.ajts.androidmads.library.ExcelToSQLite;
import com.ajts.androidmads.library.SQLiteToExcel;
import com.example.adminportal.Database.DbHandler;
import com.example.adminportal.R;

import java.io.File;

public class AddContact extends AppCompatActivity {

    DbHandler dbHelper;
    private ProgressDialog progressDialog;

    private Uri fileUri;
    private String filePath;

    public static final int PICKFILE_RESULT_CODE = 1;
    private static final int CAMERA_PERMISSION_CODE = 100;
    private static final int STORAGE_PERMISSION_CODE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        DbHandler db = new DbHandler(this);
        dbHelper = new DbHandler(getApplicationContext());

    }

    public void EXPORT(View view)
    {
        dbHelper.InsertContactDetails("abdul1","03102922930");
        dbHelper.InsertContactDetails("abdul2","03102922930");
        dbHelper.InsertContactDetails("abdul3","03102922930");
        dbHelper.InsertContactDetails("abdul4","03102922930");
        dbHelper.InsertContactDetails("abdul5","03102922930");
        dbHelper.InsertContactDetails("abdul6","03102922930");
        dbHelper.InsertContactDetails("abdul7","03102922930");
        dbHelper.InsertContactDetails("abdul8","03102922930");
        dbHelper.InsertContactDetails("abdul9","03102922930");
        dbHelper.InsertContactDetails("abdul10","03102922930");
        dbHelper.InsertContactDetails("abdul11","03102922930");
        dbHelper.InsertContactDetails("abdul12","03102922930");
        dbHelper.InsertContactDetails("abdul13","03102922930");
        dbHelper.InsertContactDetails("abdul14","03102922930");
        dbHelper.InsertContactDetails("abdul15","03102922930");
        dbHelper.InsertContactDetails("abdul16","03102922930");
        dbHelper.InsertContactDetails("abdul17","03102922930");
        dbHelper.InsertContactDetails("haseeb","02134191080");

        String directory_path = Environment.getExternalStorageDirectory().getPath() + "/Download";
        File file = new File(directory_path);
        if (!file.exists()) {
            file.mkdirs();
        }
        SQLiteToExcel sqliteToExcel = new SQLiteToExcel(getApplicationContext(), dbHelper.getDatabaseName(), directory_path);
        sqliteToExcel.exportSingleTable("Contact_Details", "table1.xls", new SQLiteToExcel.ExportListener() {
            @Override
            public void onStart() {
                progressDialog = new ProgressDialog(AddContact.this);
                progressDialog.setMessage("Loading...");
                progressDialog.setTitle("ProgressDialog");
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.show();
                progressDialog.setCancelable(false);
            }
            @Override
            public void onCompleted(String filePath) {
                progressDialog.dismiss();
                Toast.makeText(AddContact.this, "Exported Successfully", Toast.LENGTH_LONG).show();

            }
            @Override
            public void onError(Exception e) {
                progressDialog.dismiss();
                Toast.makeText(AddContact.this, "Unable to export due to :"+" "+e.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }


    public void GetFilePath(View view)
    {
        Intent chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
        chooseFile.setType("application/vnd.ms-excel");
        chooseFile = Intent.createChooser(chooseFile, "Choose a file");
        startActivityForResult(chooseFile, PICKFILE_RESULT_CODE);
    }

    public void IMPORT() {
        File file = new File(filePath);
        if (!file.exists()) {
            Toast.makeText(AddContact.this, "File Not Exist", Toast.LENGTH_LONG).show();
            return;

        }

        ExcelToSQLite excelToSQLite = new ExcelToSQLite(getApplicationContext(), "PortalDatabase", true);
        excelToSQLite.importFromFile(filePath, new ExcelToSQLite.ImportListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onCompleted(String dbName) {
                Toast.makeText(AddContact.this, "Imported Successfully", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(Exception e) {
                Toast.makeText(AddContact.this, "Unable to import due to :"+" "+e.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PICKFILE_RESULT_CODE:
                if (resultCode == -1) {
                    Uri uri = data.getData();
                    filePath = getPath(getApplicationContext(), uri);
                    IMPORT();
                }
        break;
        }
    }

    public static String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[] {
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

}
