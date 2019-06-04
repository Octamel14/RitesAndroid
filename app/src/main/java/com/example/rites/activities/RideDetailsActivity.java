package com.example.rites.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rites.API.API;
import com.example.rites.API.APIservice.SubeleService;
import com.example.rites.R;
import com.example.rites.adapters.Adapter_stops;
import com.example.rites.models.Host;
import com.example.rites.models.IntermediateStop;
import com.example.rites.models.Ride;
import com.example.rites.models.Vehicle;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RideDetailsActivity extends AppCompatActivity {

    private int opc;
    private String opc_filter;
    private String ride_id;
    private Host host;
    private String vehicle_id;

    private TextView h_name;
    private TextView id_ride;
    private TextView starting_point;
    private TextView destination;
    private TextView date;
    private TextView hour;
    private TextView room;
    private TextView cost;
    private TextView vehicle_model;
    private TextView vehicle_color;
    private TextView vehicle_plates;
    private TextView n_stops;
    private Button b_regresar;
    private Button b_solicitar;

    //Recycler view
    private RecyclerView recyclerView;
    private  RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager myLayoutManager;
    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_ride_details);

        //Initialice Layout components
        id_ride = (TextView) findViewById(R.id.id_ride) ;
        h_name = (TextView) findViewById(R.id.host_name);
        starting_point = (TextView) findViewById(R.id.textView_starting_point);
        destination = (TextView) findViewById(R.id.textView_destination);
        date = (TextView) findViewById(R.id.textView_date);
        hour = (TextView) findViewById(R.id.textView_hour);
        room = (TextView) findViewById(R.id.room);
        cost = (TextView) findViewById(R.id.textViewCost);
        vehicle_model = (TextView) findViewById(R.id.vehicle_model);
        vehicle_color = (TextView) findViewById(R.id.vehicle_color);
        vehicle_plates = (TextView) findViewById(R.id.vehicle_plates);
        n_stops = (TextView) findViewById(R.id.n_stops);
        b_regresar = (Button) findViewById(R.id.button_regresar);
        b_solicitar = (Button) findViewById(R.id.button_solicitar);
        //recylcer view
        recyclerView=findViewById(R.id.recyclerViewStop);
        myLayoutManager=new LinearLayoutManager(RideDetailsActivity.this);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(myLayoutManager);


        //Get Main Activity Params
        Bundle ride_basics = getIntent().getExtras();
        opc_filter = (String) ride_basics.getString("opc_filter"); //Cuando button=Regresar, regresar a la busqueda en la que se encontraba
        opc = (int) ride_basics.getInt("opc");
        ride_id = (String) ride_basics.getString("ride_id");
        host = (Host) ride_basics.getSerializable("host");
        Toast.makeText(RideDetailsActivity.this, opc_filter, Toast.LENGTH_LONG).show();
        //Set to Layout
        id_ride.setText("ID:"+ride_id);
        h_name.setText("Conductor: "+host.getFirst_name().toUpperCase() +" "+ host.getLast_name().toUpperCase());

        //Ride Model
        SubeleService service  = API.getApi().create(SubeleService.class);
        Call<List<Ride>> call_ride = service.getRideDetails(ride_id);
        call_ride.enqueue(new Callback<List<Ride>>() {
            @Override
            public void onResponse(Call<List<Ride>> call, Response<List<Ride>> response) {
                final List<Ride> ride = response.body();
                final int s = ride.size();

                starting_point.setText("Origen: "+ride.get(0).getStarting_point());
                destination.setText("Destino: "+ride.get(0).getDestination());
                date.setText("Día: "+ride.get(0).getDate());
                hour.setText("Hora: "+ride.get(0).getHour());
                room.setText("Lugares Disponibles: "+ride.get(0).getRoom());
                cost.setText("Costo: "+ride.get(0).getCost());
                n_stops.setText("Paradas Intermedias: "+ride.get(0).getN_stops());

                vehicle_id = ride.get(0).getVehicle();

            }

            @Override
            public void onFailure(Call<List<Ride>> call, Throwable t) {
                Toast.makeText(RideDetailsActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        if (opc==1) { //Si se veran detalles del vehiculo, para Vista de -mi riteActual o yo que se xd
           Call<List<Vehicle>> call_vehicle = service.getVehicle(vehicle_id);
           call_vehicle.enqueue(new Callback<List<Vehicle>>() {
               @Override
               public void onResponse(Call<List<Vehicle>> call, Response<List<Vehicle>> response) {
                   List<Vehicle> vehicle_item = response.body();
                   vehicle_model.setText("Modelo: "+vehicle_item.get(0).getModel());
                   vehicle_color.setText("Color: "+vehicle_item.get(0).getColor());
                   vehicle_plates.setText("Placas: "+vehicle_item.get(0).getPlates());
               }

               @Override
               public void onFailure(Call<List<Vehicle>> call, Throwable t) {
                   Toast.makeText(RideDetailsActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
               }
           });
        }

        Call<List<IntermediateStop>> call_stops = service.getStop(ride_id);
        call_stops.enqueue(new Callback<List<IntermediateStop>>() {
            @Override
            public void onResponse(Call<List<IntermediateStop>> call, Response<List<IntermediateStop>> response) {
                List<IntermediateStop> stops = response.body();
                adapter = new Adapter_stops(stops,R.layout.recycler_view_stop_item);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<IntermediateStop>> call, Throwable t) {
                Toast.makeText(RideDetailsActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        b_regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RideDetailsActivity.this,MainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("opc_filter",opc_filter);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        b_solicitar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //hacer post
            }
        });

    }
}
