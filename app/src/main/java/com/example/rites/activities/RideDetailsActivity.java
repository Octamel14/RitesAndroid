package com.example.rites.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rites.API.API;
import com.example.rites.API.APIservice.SubeleService;
import com.example.rites.R;
import com.example.rites.adapters.Adapter_stops;
import com.example.rites.models.Host;
import com.example.rites.models.IntermediateStop;
import com.example.rites.models.LogedUser;
import com.example.rites.models.Ride;
import com.example.rites.models.RideGuest;
import com.example.rites.models.Vehicle;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RideDetailsActivity extends AppCompatActivity {

    private int opc;
    private String ride_id;
    private Host host;
    private String vehicle_id;

    private Realm realm;
    private RealmResults<LogedUser> userx;

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
    private Button b_solicitar;
    private Button btn_intermediate_stop;
    private Integer id_user;
    private Boolean is_rider;
    private String is_active;
    //Recycler view
    private RecyclerView recyclerView;
    private  RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager myLayoutManager;
    private List<IntermediateStop> stops;

    SubeleService service  = API.getApi().create(SubeleService.class);
    private List<Ride> ride = null;
    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_ride_details);

        //Initialice Layout components
        id_ride = (TextView) findViewById(R.id.id_ride) ;
        btn_intermediate_stop=findViewById(R.id.button_intermediate);
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
        b_solicitar = (Button) findViewById(R.id.button_solicitar);
        //recylcer view
        recyclerView=findViewById(R.id.recyclerViewStop);
        myLayoutManager=new LinearLayoutManager(RideDetailsActivity.this);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(myLayoutManager);

        realm= Realm.getDefaultInstance();
        userx=realm.where(LogedUser.class).findAll();
        id_user=userx.get(0).getId_user();
        b_solicitar.setVisibility(View.INVISIBLE);
        btn_intermediate_stop.setVisibility(View.INVISIBLE);


        //Get Main Activity Params
        Bundle ride_basics = getIntent().getExtras();
        opc = (int) ride_basics.getInt("opc");
        ride_id = (String) ride_basics.getString("ride_id");
        host = (Host) ride_basics.getSerializable("host");
        //Set to Layout
        id_ride.setText("ID:"+ride_id);
        h_name.setText("Conductor: "+host.getFirst_name().toUpperCase() +" "+ host.getLast_name().toUpperCase());

        //Ride Model
        Call<List<Ride>> call_ride = service.getRideDetails(ride_id);
        call_ride.enqueue(new Callback<List<Ride>>() {
            @Override
            public void onResponse(Call<List<Ride>> call, Response<List<Ride>> response) {
                 ride = response.body();

                starting_point.setText("Origen: "+ride.get(0).getStarting_point());
                destination.setText("Destino: "+ride.get(0).getDestination());
                date.setText("Día: "+ride.get(0).getDate());
                hour.setText("Hora: "+ride.get(0).getHour());
                room.setText("Lugares Disponibles: "+ride.get(0).getRoom());
                cost.setText("Costo: "+ride.get(0).getCost());
                n_stops.setText("Paradas Intermedias: "+ride.get(0).getN_stops());
                vehicle_id = ride.get(0).getVehicle();
                is_active= ride.get(0).getIs_active();
                //Deshabilitando botones de Unise ride///////////////////
                is_rider=userx.get(0).getIs_rider();
                if(is_rider==Boolean.TRUE ){
                    if(is_active=="true"){
                        btn_intermediate_stop.setVisibility(View.VISIBLE);
                        b_solicitar.setVisibility(View.INVISIBLE);
                    }
                }
                else{
                    btn_intermediate_stop.setVisibility(View.INVISIBLE);
                    b_solicitar.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<List<Ride>> call, Throwable t) {
                Toast.makeText(RideDetailsActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        btn_intermediate_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateIntermediateStop();
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
                 stops = response.body();
                adapter = new Adapter_stops(stops,R.layout.recycler_view_stop_item);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<IntermediateStop>> call, Throwable t) {
                Toast.makeText(RideDetailsActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        b_solicitar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder confirm_solicitud = new AlertDialog.Builder(RideDetailsActivity.this);
                confirm_solicitud.setMessage("¿Desea solicitar el Ride " + ride_id + "?")
                        .setCancelable(false)
                        .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                               // RideGuest guest = new RideGuest(Integer.toString(0), ride_id ,Integer.toString(userx.get(0).getId_user()), 0);
                                RideGuest guest = new RideGuest(Integer.toString(0), ride_id, Integer.toString(userx.get(0).getId_user()), 0);

                                //Vehicle vehicle = new Vehicle(Integer.toString(0), Integer.toString(userx.get(0).getId_user()),

                                sendGuest(guest);
                            }
                        })
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert = confirm_solicitud.create();
                alert.setTitle("Solicitud de Ride");
                alert.show();
            }
        });

    }

    public void sendGuest(RideGuest guest) {
        service.postGuest(guest).enqueue(new Callback<RideGuest>() {
            @Override
            public void onResponse(Call<RideGuest> call, final Response<RideGuest> response) {
                if(response.isSuccessful()) {
                    /*if (response.body() != null) {
                        updateRide();
                    }*/
                    Toast.makeText(RideDetailsActivity.this,"Solicitud enviada.", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(RideDetailsActivity.this,"Error al enviar solicitud.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RideGuest> call, Throwable t) {
                Toast.makeText(RideDetailsActivity.this,"No se pudo conectar al servidor.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /*private void updateRide(){
        final int new_room = Integer.valueOf(ride.get(0).getRoom()) -1;
        Ride up_ride = new Ride(ride.get(0).getId_ride(),ride.get(0).getStarting_point(),ride.get(0).getDate(),
                ride.get(0).getHour(),Integer.toString(new_room),ride.get(0).getN_stops(),ride.get(0).getCost(),
                ride.get(0).getHost(),ride.get(0).getVehicle(),ride.get(0).getDestination(),ride.get(0).getIs_active());

        Call<Ride> rideCall = service.putRide(ride_id,up_ride);
        rideCall.enqueue(new Callback<Ride>() {
            @Override
            public void onResponse(Call<Ride> call, Response<Ride> response) {
                Toast.makeText(RideDetailsActivity.this, "Solicitud enviada", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Call<Ride> call, Throwable t) {
                Toast.makeText(RideDetailsActivity.this,"No se pudo conectar al servidor.", Toast.LENGTH_SHORT).show();
            }
        });
    }*/

    private void CreateIntermediateStop(){

        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Crear parada intermedia");

        View viewInflated= LayoutInflater.from(this).inflate(R.layout.dialog_intermediate_stop, null);
        builder.setView(viewInflated);
        final EditText input=viewInflated.findViewById(R.id.editTextIntermediateStop);

        builder.setPositiveButton("Crear", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                String intermediateStoptext=input.getText().toString().trim();
                if(intermediateStoptext.length()==0)
                    Toast.makeText(RideDetailsActivity.this, "Ingresa una parada intermedia", Toast.LENGTH_SHORT).show();

                else{
                    IntermediateStop intermediateStop= intermediateStop=new IntermediateStop("0", ride_id, intermediateStoptext);
                    SubeleService service= API.getApi().create(SubeleService.class);
                    Call call=service.PostIntermediateStop(intermediateStop);
                    call.enqueue(new Callback() {
                        @Override
                        public void onResponse(Call call, Response response) {
                            Toast.makeText(RideDetailsActivity.this, "Parada intermedia creada", Toast.LENGTH_LONG).show();
                            stops.add((IntermediateStop) response.body());
                            adapter.notifyDataSetChanged();

                        }

                        @Override
                        public void onFailure(Call call, Throwable t) {

                        }
                    });

                }
            }
        });
        AlertDialog dialog=builder.create();
        dialog.show();
        }
    }

