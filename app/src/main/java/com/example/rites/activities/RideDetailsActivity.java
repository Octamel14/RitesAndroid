package com.example.rites.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rites.API.API;
import com.example.rites.API.APIservice.SubeleService;
import com.example.rites.R;
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
    private String ride_id;
    private Host host;
    private String vehicle_id;

    private TextView h_firstname;
    private TextView h_lastname;
    private TextView id_ride;
    private TextView starting_point;
    private TextView destination;
    private TextView date;
    private TextView hour;
    private TextView room;
    private TextView cost;
    private TextView vehicle;
    private TextView n_stops;
    private TextView stops;
    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_ride_details);

        //Initialice Layout components
        id_ride = (TextView) findViewById(R.id.id_ride) ;
        h_firstname = (TextView) findViewById(R.id.host_firstname);
        h_lastname = (TextView) findViewById(R.id.host_lastname);
        starting_point = (TextView) findViewById(R.id.textView_starting_point);
        destination = (TextView) findViewById(R.id.destination);
        date = (TextView) findViewById(R.id.textView_date);
        hour = (TextView) findViewById(R.id.textView_hour);
        room = (TextView) findViewById(R.id.room);
        cost = (TextView) findViewById(R.id.textViewCost);
        vehicle = (TextView) findViewById(R.id.vehicle);
        n_stops = (TextView) findViewById(R.id.n_stops);
        stops = (TextView) findViewById(R.id.rv_stops);


        //Main Activity Params
        Bundle ride_basics = getIntent().getExtras();
        opc = (int) ride_basics.getInt("opc");
        ride_id = (String) ride_basics.getString("ride_id");
        host = (Host) ride_basics.getSerializable("host");

        //Ride Model
        SubeleService service  = API.getApi().create(SubeleService.class);
        Call<List<Ride>> call_ride = service.getRideDetails(ride_id);
        call_ride.enqueue(new Callback<List<Ride>>() {
            @Override
            public void onResponse(Call<List<Ride>> call, Response<List<Ride>> response) {
                List<Ride> ride = response.body();
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

        if (opc==1){ //Si se veran detalles del vehiculo, para Vista de -mi riteActual o yo que se xd
           Call<List<Vehicle>> call_vehicle = service.getVehicle(vehicle_id);
           call_vehicle.enqueue(new Callback<List<Vehicle>>() {
               @Override
               public void onResponse(Call<List<Vehicle>> call, Response<List<Vehicle>> response) {
                   List<Vehicle> vehicle_item = response.body();
                   vehicle.setText("Vehiculi_id: "+vehicle_id.toString());
                   //mostrar datos vehiculo
               }

               @Override
               public void onFailure(Call<List<Vehicle>> call, Throwable t) {

               }
           });
        }

        Call<List<IntermediateStop>> call_stops = service.getStop(ride_id);
        call_stops.enqueue(new Callback<List<IntermediateStop>>() {
            @Override
            public void onResponse(Call<List<IntermediateStop>> call, Response<List<IntermediateStop>> response) {
                List<IntermediateStop> stops = response.body();
                //recicler view de las stops;
            }

            @Override
            public void onFailure(Call<List<IntermediateStop>> call, Throwable t) {

            }
        });

        //Set to Layout
        id_ride.setText(ride_id);
        h_firstname.setText(host.getFirst_name());
        h_lastname.setText(host.getLast_name());

    }
}
