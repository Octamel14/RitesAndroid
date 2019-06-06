package com.example.rites.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rites.API.API;
import com.example.rites.API.APIservice.SubeleService;
import com.example.rites.R;
import com.example.rites.adapters.Adapter_rides;
import com.example.rites.models.Host;
import com.example.rites.models.Ride;
import com.example.rites.models.RideFilter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private  RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager myLayoutManager;

    //FILTRO
    private Button boton_filtro;
    private EditText hour_view;
    private EditText lugar_view;
    private Button boton_buscar;
    private TextView answer;
    private String respuesta_filter;
    private String opc_filter = "non";
    private Call<List<RideFilter>> call;
    private SubeleService service= null;
    private Host host;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView=findViewById(R.id.recyclerViewRites);
        //tool bar
        Toolbar toolbar = (Toolbar) findViewById(R.id.acctioin_bar);
        setSupportActionBar(toolbar);



        service = API.getApi().create(SubeleService.class);
        call = service.getRides();
        myLayoutManager=new LinearLayoutManager(MainActivity.this);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(myLayoutManager);

        boton_filtro = (Button) findViewById(R.id.button_filtro);
        hour_view = (EditText) findViewById(R.id.timeView);
        lugar_view = (EditText) findViewById(R.id.placeView);
        boton_buscar = (Button) findViewById(R.id.boton_buscar);
        answer = (TextView) findViewById(R.id.answerText);
        hour_view.setVisibility(View.INVISIBLE);
        lugar_view.setVisibility(View.INVISIBLE);

        Buscar();

        boton_filtro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hour_view.setVisibility(View.INVISIBLE);
                lugar_view.setVisibility(View.INVISIBLE);
                respuesta_filter="";
                PopupMenu popupMenu = new PopupMenu(MainActivity.this, boton_filtro);
                popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        Toast.makeText(MainActivity.this,""+item.getTitle(),Toast.LENGTH_LONG).show();

                        switch (item.getItemId()){
                            case R.id.hour:
                                hour_view.setVisibility(View.VISIBLE);
                                opc_filter="hour";
                                answer.setText("HORA");
                                return  true;
                            case R.id.destination:
                                lugar_view.setVisibility(View.VISIBLE);
                                opc_filter="destination";
                                answer.setText("DESTINO");
                                return  true;
                            case R.id.starting_point:
                                lugar_view.setVisibility(View.VISIBLE);
                                opc_filter="starting_point";
                                answer.setText("ORIGEN");
                                return  true;
                            case R.id.intermediate:
                                lugar_view.setVisibility(View.VISIBLE);
                                opc_filter="stop";
                                answer.setText("PARADA INTERMEDIA");
                                return  true;
                            case R.id.todos:
                                lugar_view.setVisibility(View.INVISIBLE);
                                hour_view.setVisibility(View.INVISIBLE);
                                opc_filter="non";
                                answer.setText("TODOS");
                                return  true;
                            default:
                                return false;
                        }


                    }
                });
                popupMenu.show();
            }
        });
        boton_buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Buscar();
            }


        });



       /* Ride ride=new Ride(0, "DICIS", "2019-05-26", "18:48:02", 4, 1, 30.0f, 1, 1, "Valle");
        SubeleService service= API.getApi().create(SubeleService.class);
        Call call=service.CreateRide(new PostRide(ride));
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                Toast.makeText(MainActivity.this, call.toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call call, Throwable t) {

            }
        });*/

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actionbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.action_misrides:
                Intent intent = new Intent(MainActivity.this,NoRiderActivity.class);
                startActivity(intent);
                //Toast.makeText(MainActivity.this,"Mis Rides",Toast.LENGTH_LONG).show();
                break;
            case R.id.action_filtrarrides:
                Toast.makeText(MainActivity.this,"Rides Disponibles",Toast.LENGTH_LONG).show();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void Buscar(){

        call=null;
        switch (opc_filter) {
            case "hour":
                answer.setText("HORA");
                call=null;
                respuesta_filter=hour_view.getText().toString();
                call = service.getRidesHour(respuesta_filter);
                break;
            case "destination":
                answer.setText("DESTINO");
                call=null;
                respuesta_filter=lugar_view.getText().toString();
                call = service.getRidesDestination(respuesta_filter);
                break;
            case "starting_point":
                answer.setText("ORIGEN");
                call=null;
                respuesta_filter=lugar_view.getText().toString();
                call = service.getRidesOrigin(respuesta_filter);
                break;
            case "stop":
                answer.setText("PARADA INTERMEDIA");
                call=null;
                respuesta_filter=lugar_view.getText().toString();
                call = service.getRidesStop(respuesta_filter);
                break;
            case "non":
                answer.setText("TODOS");
                call=null;
                call = service.getRides();
                break;
            default:
                call=null;
                call = service.getRides();
                break;
        }
        call.enqueue(new Callback<List<RideFilter>>() {
            @Override
            public void onResponse(Call<List<RideFilter>> call, Response<List<RideFilter>> response) {
                final List<RideFilter> rides=response.body();

                adapter=new Adapter_rides(rides, R.layout.recycler_view_rites_item, new Adapter_rides.OnItemClickListener() {
                    @Override
                    public void onItemClick(RideFilter name, int position) {
                        final String ride_id = rides.get(position).getId_ride().toString();
                        final Host h= rides.get(position).getHost();
                        //Toast.makeText(MainActivity.this, ride_id, Toast.LENGTH_LONG).show();
                        //ACCION kawai para cuando se le da click en un item de la lista minuto 3
                        Intent intent = new Intent(MainActivity.this,RideDetailsActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("ride_id",ride_id);
                        bundle.putInt("opc",1); //opc=0=no mostar detalles del vehiculo | 1=mostrar
                        bundle.putSerializable("host",h);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                }, MainActivity.this);

                recyclerView.setAdapter(adapter);
            }
            @Override
            public void onFailure(Call<List<RideFilter>> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }

            //////////////////////////////////////////////////////////////////////////////////////////
        });

    }

}

