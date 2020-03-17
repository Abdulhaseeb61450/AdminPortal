package com.example.adminportal.API;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.adminportal.R;


public class ExportApi extends AppCompatDialogFragment {

    private ImageView csv,pdf,print,copy;
    private ExportApi.ExampleDialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.export_api_dialog, null);


        builder.setView(view)
                .setTitle("Export API's")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                })
                .setPositiveButton("", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });

        csv = view.findViewById(R.id.csv);
        pdf = view.findViewById(R.id.pdf);
        print = view.findViewById(R.id.print);
        copy = view.findViewById(R.id.copy);

        csv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.applyTexts("csv");
            }
        });

        pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.applyTexts("pdf");
            }
        });

        print.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.applyTexts("print");
            }
        });

        copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.applyTexts("copy");
            }
        });

        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (ExportApi.ExampleDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement ExampleDialogListener");
        }
    }

    public interface ExampleDialogListener {
        void applyTexts(String check);
    }

}
