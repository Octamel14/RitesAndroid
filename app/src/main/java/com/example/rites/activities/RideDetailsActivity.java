package com.example.rites.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rites.API.API;
import com.example.rites.API.APIservice.SubeleService;
import com.example.rites.DialogPuntuar;
import com.example.rites.R;
import com.example.rites.adapters.Adapter_stops;
import com.example.rites.models.Host;
import com.example.rites.models.IntermediateStop;
import com.example.rites.models.LogedUser;
import com.example.rites.models.Ride;
import com.example.rites.models.RideGuest;
import com.example.rites.models.RideGuestFiler;
import com.example.rites.models.User;
import com.example.rites.models.Vehicle;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RideDetailsActivity extends AppCompatActivity implements DialogPuntuar.onMultiChoiceListener {

    private int opc;
    private String ride_id;
    private Host host;
    private String vehicle_id;
    private String evaluated;
    private String score;
    private String guest_id;

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
    private TextView general_status;
    private Button b_solicitar;
    private Button b_puntuar;
    private Integer id_user;
    private Boolean is_rider;
    private String is_active;

    private Button btn_intermediate_stop;

    //Recycler view
    private RecyclerView recyclerView;
    private  RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager myLayoutManager;
    private List<IntermediateStop> stops;

    SubeleService service  = API.getApi().create(SubeleService.class);
    private List<Ride> ride = null;
    private List<User> user = null;
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
        general_status = (TextView) findViewById(R.id.textView_statusx);
        b_solicitar = (Button) findViewById(R.id.button_solicitar);
        b_puntuar = (Button) findViewById(R.id.button_puntuar);
        //recylcer view
        recyclerView=findViewById(R.id.recyclerViewStop);
        myLayoutManager=new LinearLayoutManager(RideDetailsActivity.this);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(myLayoutManager);

        realm= Realm.getDefaultInstance();  //////////Inicializar DB interna
        userx=realm.where(LogedUser.class).findAll();
        id_user = userx.get(0).getId_user();
        is_rider = userx.get(0).getIs_rider();
        general_status.setVisibility(View.INVISIBLE);
        b_solicitar.setVisibility(View.INVISIBLE);
        btn_intermediate_stop.setVisibility(View.INVISIBLE);


        //Get Main Activity Params
        Bundle ride_basics = getIntent().getExtras();
        opc = (int) ride_basics.getInt("opc");//status del no driver
        ride_id = (String) ride_basics.getString("ride_id");
        host = (Host) ride_basics.getSerializable("host");
        if (opc==1){
            evaluated = (String) ride_basics.getString("evaluated");
            guest_id = (String) ride_basics.getString("guest_id");
        }
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
                status(); //verificar status para No rider

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
                                Toast.makeText(RideDetailsActivity.this, "Solicitud enviada", Toast.LENGTH_SHORT).show();
                                RideGuest guest = new RideGuest(Integer.toString(0), ride_id, Integer.toString(userx.get(0).getId_user()), "0","false");

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

    public void status(){
        if (is_rider==Boolean.FALSE){
            general_status.setVisibility(View.VISIBLE);
            if(is_active=="true"){

                b_puntuar.setVisibility(View.INVISIBLE);
                switch (opc){
                    case 0:
                        general_status.setText("ESTADO DEL RIDE: EN ESPERA");
                        break;
                    case 1:
                        general_status.setText("ESTADO DEL RIDE: CONFIRMADO");
                        break;
                    case 2:
                        general_status.setText("ESTADO DEL RIDE: NEGADO");
                        break;
                }
            }
            else{
                switch (opc){
                    case 0:

                        b_puntuar.setVisibility(View.INVISIBLE);
                        general_status.setText("ESTADO DEL RIDE: RECHAZADO");//no lo aceptaron o rechazaron pero is_Active=flase
                        break;
                    case 1:
                        general_status.setText("ESTADO DEL RIDE: FINALIZADO");//lo aceptaron pero is_active=false
                        if (evaluated=="false"){ //si aun no se califica al conductor en ese ride
                            b_puntuar.setVisibility(View.VISIBLE);
                        }
                        break;
                    case 2:

                        b_puntuar.setVisibility(View.INVISIBLE);
                        general_status.setText("ESTADO DEL RIDE: RECHAZADO");
                        break;

                }
            }
        }

        b_puntuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                puntuar();
            }
        });
    }


    public void puntuar(){

        DialogFragment multiChoideDialog= new DialogPuntuar();
        multiChoideDialog.setCancelable(false);
        multiChoideDialog.show(getSupportFragmentManager(),"Puntuar conductor");

    }

    public void sendGuest(RideGuest guest) {
        service.postGuest(guest).enqueue(new Callback<RideGuest>() {
            @Override
            public void onResponse(Call<RideGuest> call, final Response<RideGuest> response) {
                if(response.isSuccessful()) {
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


    private void updateRideGuests(){
        final int new_room = Integer.valueOf(ride.get(0).getRoom()) -1;
        RideGuest up_ride = new RideGuest(guest_id,ride_id,String.valueOf(id_user),String.valueOf(opc),"true");

        Call<RideGuest> rideCall = service.putRideGuest(guest_id,up_ride);
        rideCall.enqueue(new Callback<RideGuest>() {
            @Override
            public void onResponse(Call<RideGuest> call, Response<RideGuest> response) {
                finish();
            }

            @Override
            public void onFailure(Call<RideGuest> call, Throwable t) {
                Toast.makeText(RideDetailsActivity.this,"No se pudo conectar al servidor.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateHost(String score) {
        //Get user
        Call <List<User>> call_user = service.getUserID(host.getId_user());

        call_user.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                user=response.body();
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(RideDetailsActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });
        //update ahora si
        final int new_scored = user.get(0).getScored()+1;
        final int new_rider_scored = user.get(0).getRider_score()+Integer.valueOf(score);
        User up_user = new User(user.get(0).getId_user(),user.get(0).getFirst_name(),user.get(0).getLast_name(),
                user.get(0).getEmail(),user.get(0).getPassword(),user.get(0).getIs_rider(),new_rider_scored,
                user.get(0).getRiders_number(),new_scored);

        Call<User> user_call = service.putUserID(user.get(0).getId_user(),up_user);
        user_call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                finish();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(RideDetailsActivity.this,"No se pudo conectar al servidor.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override//funcion del alert dialog para puntuar conductor
    public void onPositiveButtonClicked(String[] list, ArrayList<String> selectedItemList) {
        StringBuilder stringBuilder=new StringBuilder();
        stringBuilder.append("");
        for (String str:selectedItemList){
            score=str;
        }
        Toast.makeText(RideDetailsActivity.this,score, Toast.LENGTH_SHORT).show();
        updateRideGuests();
        updateHost(score);

    }

    @Override
    public void onNegativeButtonClicked() { //funcion del alert dialog para puntuar conductor
        Toast.makeText(RideDetailsActivity.this,"Dialog Cancel", Toast.LENGTH_SHORT).show();
    }
}
