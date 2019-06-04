package com.example.rites.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rites.R;

import java.nio.channels.FileLock;

public class FilterActivity extends AppCompatActivity {
    Button boton_filtro;
    EditText hour_view;
    EditText lugar_view;
    Button boton_buscar;
    TextView answer;
    private String respuesta;
    private String opc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filter);

        boton_filtro = (Button) findViewById(R.id.button_filtro);
        hour_view = (EditText) findViewById(R.id.timeView);
        lugar_view = (EditText) findViewById(R.id.placeView);
        boton_buscar = (Button) findViewById(R.id.boton_buscar);
        answer = (TextView) findViewById(R.id.answerText);

        hour_view.setVisibility(View.INVISIBLE);
        lugar_view.setVisibility(View.INVISIBLE);
        boton_filtro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hour_view.setVisibility(View.INVISIBLE);
                lugar_view.setVisibility(View.INVISIBLE);
                PopupMenu popupMenu = new PopupMenu(FilterActivity.this, boton_filtro);
                popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        Toast.makeText(FilterActivity.this,""+item.getTitle(),Toast.LENGTH_LONG).show();

                        switch (item.getItemId()){
                            case R.id.hour:
                                hour_view.setVisibility(View.VISIBLE);
                                respuesta=hour_view.getText().toString();
                                opc="hour";
                                answer.setText("HORA");
                                return  true;
                            case R.id.destination:
                                lugar_view.setVisibility(View.VISIBLE);
                                respuesta=lugar_view.getText().toString();
                                opc="destination";
                                answer.setText("DESTINO");
                                return  true;
                            case R.id.starting_point:
                                lugar_view.setVisibility(View.VISIBLE);
                                respuesta=lugar_view.getText().toString();
                                opc="starting_point";
                                answer.setText("ORIGEN");
                                return  true;
                            case R.id.intermediate:
                                lugar_view.setVisibility(View.VISIBLE);
                                respuesta=lugar_view.getText().toString();
                                opc="stop";
                                answer.setText("PARADA INTERMEDIA");
                                return  true;
                            default:
                                opc="non";
                                answer.setText("TODOS");
                                return true;
                        }


                    }
                });
                popupMenu.show();
            }
        });
        boton_buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (opc){
                    case "hour":
                        respuesta=hour_view.getText().toString();
                    default:
                        respuesta=lugar_view.getText().toString();
                }



            }
        });
    }
}
