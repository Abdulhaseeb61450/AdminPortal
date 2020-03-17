package com.example.adminportal.Reports;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.print.PageRange;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintDocumentInfo;
import android.print.PrintManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ajts.androidmads.library.SQLiteToExcel;
import com.example.adminportal.API.ExportApi;
import com.example.adminportal.Campaign.Campaign;
import com.example.adminportal.Campaign.ViewDetails;
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

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class OutboxSMS extends AppCompatActivity implements ExportOptions.ExampleDialogListener {

    TextView outbox,summary,logs;
    private static final String TAG = "PdfCreatorActivity";
    final private int REQUEST_CODE_ASK_PERMISSIONS = 111;
    private File pdfFile;
    Context context;
    public ArrayList<HashMap<String, String>> Data = new ArrayList<>();
    public String ID,API_NAME,API_KEY,IPs,CREATED_DATE,STATUS,ACTION;

    public SimpleAdapter adapter;
    public List<HashMap<String,String>> OutboxList = new ArrayList<>();
    public List<HashMap<String,String>> BackUp = new ArrayList<>();
    public List<HashMap<String,String>> OutboxList1 = new ArrayList<>();

    String SearchFilter;
    Spinner myspinner;
    public ListView lv;
    EditText inputSearch;

    DbHandler dbHelper;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outbox_sms);

        inputSearch = findViewById(R.id.inputSearch);

        for (int i = 0; i < 5; i++) {
            HashMap<String, String> InsideData = new HashMap<>();
            InsideData.put("Date", "03-03-2020 01:33 PM");
            InsideData.put("SMS Type", "1");
            InsideData.put("Sender", "DotKlick");
            InsideData.put("Mobilink", "10");
            InsideData.put("Ufone", "20");
            InsideData.put("Telenor", "30");
            InsideData.put("Zong", "40");
            InsideData.put("Warid", "50");
            InsideData.put("Total", "200");

            Data.add(InsideData);
        }

        for (int i = 0; i < 5; i++) {
            HashMap<String, String> InsideData = new HashMap<>();
            InsideData.put("smstype", "regular" + i);
            InsideData.put("sender", "dotklick" + i);
            InsideData.put("reciever", "google" + i);
            InsideData.put("datetime", "03-03-2020 01:33 PM" + i);

            OutboxList.add(InsideData);
        }

        BackUp.addAll(OutboxList);

        inputSearch.addTextChangedListener(new TextWatcher() {
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

        dbHelper = new DbHandler(getApplicationContext());
        context = this;
        lv = findViewById(R.id.list_view);

        lv.setTextFilterEnabled(true);

        adapter = new SimpleAdapter(
                OutboxSMS.this, OutboxList,
                R.layout.list_item_outboxsms, new String[]{"smstype","sender","reciever","datetime"}, new int[]{
                R.id.SmsType,R.id.Sender,R.id.Reciever,R.id.DateTime});
        lv.setAdapter(adapter);


        myspinner = findViewById(R.id.Mobilink);
        ArrayAdapter<String> myAdaptor = new ArrayAdapter<String>(OutboxSMS.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.outbox));
        myAdaptor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        myspinner.setAdapter(myAdaptor);

        SearchFilter = myspinner.getSelectedItem().toString();

        outbox = findViewById(R.id.outbox);
        summary = findViewById(R.id.summary);
        logs = findViewById(R.id.logs);
        outbox.setTextColor(Color.parseColor("#eca400"));
        summary.setTextColor(Color.parseColor("#ffffff"));
        logs.setTextColor(Color.parseColor("#ffffff"));

        summary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OutboxSMS.this, Summary.class);
                startActivity(intent);
            }
        });

        logs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OutboxSMS.this, Logs.class);
                startActivity(intent);
            }
        });

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
        {
            try {
                createPdfWrapper();
                PRINTDOCUMENT();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (DocumentException e) {
                e.printStackTrace();
            }
        }
    }

    public void OPENEXPORTDIALOG(View view) {
        ExportOptions exampleDialog = new ExportOptions();
        exampleDialog.show(getSupportFragmentManager(), "example dialog");
    }


    public void EXPORT()
    {
        String directory_path = Environment.getExternalStorageDirectory().getPath() + "/Download";
        File file = new File(directory_path);
        if (!file.exists()) {
            file.mkdirs();
        }
        SQLiteToExcel sqliteToExcel = new SQLiteToExcel(getApplicationContext(), dbHelper.getDatabaseName(), directory_path);
        sqliteToExcel.exportSingleTable("OutboxSMS", "table3.xls", new SQLiteToExcel.ExportListener() {
            @Override
            public void onStart() {
                progressDialog = new ProgressDialog(OutboxSMS.this);
                progressDialog.setMessage("Loading...");
                progressDialog.setTitle("ProgressDialog");
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.show();
                progressDialog.setCancelable(false);
            }
            @Override
            public void onCompleted(String filePath) {
                progressDialog.dismiss();
                Toast.makeText(OutboxSMS.this, "Exported Successfully", Toast.LENGTH_LONG).show();

            }
            @Override
            public void onError(Exception e) {
                progressDialog.dismiss();
                Toast.makeText(OutboxSMS.this, "Unable to export due to :"+" "+e.toString(), Toast.LENGTH_LONG).show();
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
        Paragraph para = new Paragraph("Outbox Summary \n\n", f);
        para.setAlignment(Element.ALIGN_CENTER);
        document.add(para);
        document.add(table);

        document.close();
        Toast.makeText(OutboxSMS.this, "PDF CREATED", Toast.LENGTH_LONG).show();
        Log.e("safiya", Data.toString());
    }

    public void PRINTDOCUMENT()
    {
        PrintDocumentAdapter pda = new PrintDocumentAdapter(){

            @Override
            public void onWrite(PageRange[] pages, ParcelFileDescriptor destination, CancellationSignal cancellationSignal, WriteResultCallback callback){
                InputStream input = null;
                OutputStream output = null;

                try {

                    input = new FileInputStream(pdfFile);
                    output = new FileOutputStream(destination.getFileDescriptor());

                    byte[] buf = new byte[1024];
                    int bytesRead;

                    while ((bytesRead = input.read(buf)) > 0) {
                        output.write(buf, 0, bytesRead);
                    }

                    callback.onWriteFinished(new PageRange[]{PageRange.ALL_PAGES});

                } catch (FileNotFoundException ee){
                    //Catch exception
                } catch (Exception e) {
                    //Catch exception
                } finally {
                    try {
                        input.close();
                        output.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onLayout(PrintAttributes oldAttributes, PrintAttributes newAttributes, CancellationSignal cancellationSignal, LayoutResultCallback callback, Bundle extras){

                if (cancellationSignal.isCanceled()) {
                    callback.onLayoutCancelled();
                    return;
                }


                PrintDocumentInfo pdi = new PrintDocumentInfo.Builder("Name of file").setContentType(PrintDocumentInfo.CONTENT_TYPE_DOCUMENT).build();

                callback.onLayoutFinished(pdi, true);
            }
        };

        PrintManager printManager = (PrintManager) this.getSystemService(Context.PRINT_SERVICE);
        String jobName = this.getString(R.string.app_name) + " Document";
        printManager.print(jobName, pda, null);
    }

    public void filter(String charText) {
        if (charText.equals(null)) { return; }
        SearchFilter = myspinner.getSelectedItem().toString();
        OutboxList1.clear();
        charText = charText.toLowerCase(Locale.getDefault());
        for (HashMap hm : BackUp) {
            if (SearchFilter.equals("SMS Type"))
            {
                if (((String)hm.get("smstype")).contains(charText)) {
                    OutboxList1.add(hm);
                }
            }
            else if (SearchFilter.equals("Sender"))
            {
                if (((String)hm.get("sender")).contains(charText)) {
                    OutboxList1.add(hm);
                }
            }
            else if (SearchFilter.equals("Reciever"))
            {
                if (((String)hm.get("reciever")).contains(charText)) {
                    OutboxList1.add(hm);
                }
            }
            else
            {
                if (((String)hm.get("datetime")).contains(charText)) {
                    OutboxList1.add(hm);
                }
            }

        }

            adapter = new SimpleAdapter(
                    OutboxSMS.this, OutboxList1,
                    R.layout.list_item_outboxsms, new String[]{"smstype","sender","reciever","datetime"}, new int[]{
                    R.id.SmsType,R.id.Sender,R.id.Reciever,R.id.DateTime});
            lv.setAdapter(adapter);

            adapter.notifyDataSetChanged();
    }
}
