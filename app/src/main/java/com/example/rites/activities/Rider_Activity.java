package com.example.rites.activities;

import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.rites.R;

public class Rider_Activity extends AppCompatActivity {

    private FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider_);
        fab=findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Accion para crear un ride xb
                showAlertForCreatingRide("Crear nuevo ride");


            }
        });
    }

    private void showAlertForCreatingRide(String title){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        if(title!=null) builder.setTitle(title);

        View viewInflated= LayoutInflater.from(this).inflate(R.layout.dialog_create_ride, null);
        builder.setView(viewInflated);
        builder.setPositiveButton("Crear ride", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }


        });
        AlertDialog dialog=builder.create();
        dialog.show();
    }
}
