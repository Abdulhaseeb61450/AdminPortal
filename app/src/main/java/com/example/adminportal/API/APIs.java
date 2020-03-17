package com.example.adminportal.API;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.ajts.androidmads.library.SQLiteToExcel;
import com.example.adminportal.Contact.AddContact;
import com.example.adminportal.Database.DbHandler;
import com.example.adminportal.Masking.MaskRoutes;
import com.example.adminportal.R;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class APIs extends AppCompatActivity implements ExportApi.ExampleDialogListener {

    Button dialog;
    ListView lv;
    EditText search;
    public ArrayAdapter<String> adapter;
    public List<String> APILIST = new ArrayList<>();
    DbHandler dbHelper;
    private ProgressDialog progressDialog;

    private static final String TAG = "PdfCreatorActivity";
    final private int REQUEST_CODE_ASK_PERMISSIONS = 111;
    private File pdfFile;
    Context context;
    public ArrayList<HashMap<String, String>> Data = new ArrayList<>();
    public String ID,API_NAME,API_KEY,IPs,CREATED_DATE,STATUS,ACTION;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apis);

        DbHandler db = new DbHandler(this);
        dbHelper = new DbHandler(getApplicationContext());
        context = this;

        for (int i = 0; i < 5; i++) {
            HashMap<String, String> InsideData = new HashMap<>();
            InsideData.put("ID", "1");
            InsideData.put("API_NAME", "LongCode Outbox");
            InsideData.put("API_KEY", "123456");
            InsideData.put("CREATED_DATE", "03-03-2020 01:33 PM");
            InsideData.put("IPs", "20");
            InsideData.put("STATUS", "Active");
            InsideData.put("ACTION", "None");

            Data.add(InsideData);
        }


        APILIST.clear();

        for (int i = 0; i < 100; i++)
        {
            APILIST.add("API"+i);
        }

        dialog = findViewById(R.id.Add);
        lv = (ListView) findViewById(R.id.list_view);
        search = (EditText) findViewById(R.id.inputSearch);

        adapter = new ArrayAdapter<String>(APIs.this, R.layout.api_list_layout, R.id.api_name, APILIST);
        lv.setAdapter(adapter);

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                APIs.this.adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });
    }


    public void OPENDIALOG(View view)
    {
        openDialog();
    }

    public void openDialog() {
        AddApi exampleDialog = new AddApi();
        exampleDialog.show(getSupportFragmentManager(), "example dialog");
    }

    public void OPENDIALOG1(View view)
    {
        OPENEXPORTDIALOG();
    }

    public void OPENEXPORTDIALOG() {
        ExportApi exampleDialog = new ExportApi();
        exampleDialog.show(getSupportFragmentManager(), "example dialog");
    }

    @Override
    public void applyTexts(String check) {
        if (check.equals("csv"))
        { EXPORT(); }
        else if (check.equals("pdf"))
        {
            try {
                createPdfWrapper();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (DocumentException e) {
                e.printStackTrace();
            }
        }
        else
        { }
    }

    public void EXPORT()
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
                progressDialog = new ProgressDialog(APIs.this);
                progressDialog.setMessage("Loading...");
                progressDialog.setTitle("ProgressDialog");
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.show();
                progressDialog.setCancelable(false);
            }
            @Override
            public void onCompleted(String filePath) {
                progressDialog.dismiss();
                Toast.makeText(APIs.this, "Exported Successfully", Toast.LENGTH_LONG).show();

            }
            @Override
            public void onError(Exception e) {
                progressDialog.dismiss();
                Toast.makeText(APIs.this, "Unable to export due to :"+" "+e.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void createPdfWrapper() throws FileNotFoundException, DocumentException {

        int hasWriteStoragePermission = ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (hasWriteStoragePermission != PackageManager.PERMISSION_GRANTED) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!shouldShowRequestPermissionRationale(Manifest.permission.WRITE_CONTACTS)) {
                    showMessageOKCancel("You need to allow access to Storage",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                                REQUEST_CODE_ASK_PERMISSIONS);
                                    }
                                }
                            });
                    return;
                }
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_CODE_ASK_PERMISSIONS);
            }
            return;
        } else {
            createPdf();
        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(context)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    private void createPdf() throws FileNotFoundException, DocumentException {

        File docsFolder = new File(Environment.getExternalStorageDirectory() + "/Documents");
        if (!docsFolder.exists()) {
            docsFolder.mkdir();
            Log.i(TAG, "Created a new directory for PDF");
        }

        Calendar calender = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");
        String datetime = simpleDateFormat.format(calender.getTime());

        String pdfname = datetime +" APIS.pdf";
        pdfFile = new File(docsFolder.getAbsolutePath(), pdfname);
        OutputStream output = new FileOutputStream(pdfFile);
        Document document = new Document(PageSize.A4);
        PdfPTable table = new PdfPTable(new float[]{3, 3, 3, 3, 3, 3, 3});
        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        table.getDefaultCell().setFixedHeight(20);
        table.setTotalWidth(PageSize.A4.getWidth());
        table.setWidthPercentage(100);
        table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell("ID");
        table.addCell("API NAME");
        table.addCell("API KEY");
        table.addCell("CREATED DATE");
        table.addCell("STATUS");
        table.addCell("ACTION");
        table.addCell("IPs");
        table.setHeaderRows(1);
        PdfPCell[] cells = table.getRow(0).getCells();
        for (int j = 0; j < cells.length; j++) {
            cells[j].setBackgroundColor(BaseColor.GRAY);
        }
        for (int i = 0; i < Data.size(); i++) {
            ID = Data.get(i).get("ID");
            API_NAME = Data.get(i).get("API_NAME");
            API_KEY = Data.get(i).get("API_KEY");
            IPs = Data.get(i).get("IPs");
            CREATED_DATE = Data.get(i).get("CREATED_DATE");
            STATUS = Data.get(i).get("STATUS");
            ACTION = Data.get(i).get("ACTION");

            table.addCell(String.valueOf(ID));
            table.addCell(String.valueOf(API_NAME));
            table.addCell(String.valueOf(API_KEY));
            table.addCell(String.valueOf(IPs));
            table.addCell(String.valueOf(CREATED_DATE));
            table.addCell(String.valueOf(STATUS));
            table.addCell(String.valueOf(ACTION));

        }

        PdfWriter.getInstance(document, output);
        document.open();
        Font f = new Font(Font.FontFamily.TIMES_ROMAN, 30.0f, Font.UNDERLINE, BaseColor.BLACK);
        document.add(new Paragraph("APIs \n\n", f));
        document.add(table);

        document.close();
        Toast.makeText(APIs.this, "PDF CREATED", Toast.LENGTH_LONG).show();
        Log.e("safiya", Data.toString());
    }
}
