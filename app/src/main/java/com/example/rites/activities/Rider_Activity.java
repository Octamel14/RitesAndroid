package com.example.rites.activities;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.rites.API.API;
import com.example.rites.API.APIservice.SubeleService;
import com.example.rites.R;
import com.example.rites.models.LogedUser;
import com.example.rites.models.Ride;
import com.example.rites.models.Vehicle;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.example.rites.adapters.Adapter_rides;
import com.example.rites.models.Host;
import com.example.rites.models.RideFilter;


public class Rider_Activity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private  RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager myLayoutManager;
    private Call<List<RideFilter>> call;
    private SubeleService service= null;
    private List<RideFilter> rides;

    private Realm realm;
    private RealmResults<LogedUser> userx;

    private FloatingActionButton fab;
    private List<Vehicle> vehicles;
    private List<String> vehiclesModel=new LinkedList<String>();
    private List<String> DayOptions=new LinkedList<String>();
    private Integer vehicle_id;
    private String startingPoint;
    private String destination;
    private String Fullhour;
    private  Integer room;
    private double cost;
    private Integer user_id;
    private String formattedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider_);
        recyclerView=findViewById(R.id.recyclerViewRites);

        service = API.getApi().create(SubeleService.class);
        //call = service.getRides();
        myLayoutManager=new LinearLayoutManager(Rider_Activity.this);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(myLayoutManager);

        fab=findViewById(R.id.floatingActionButton);
        DayOptions.add("Hoy");
        DayOptions.add("Mañana");
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Accion para crear un ride xb
                showAlertForCreatingRide("Crear nuevo ride");
            }
        });

        realm=Realm.getDefaultInstance();
        userx=realm.where(LogedUser.class).findAll();
        if(userx.size() == 0)
        {
            Toast.makeText(this, "No se pudo cargar información de usuario", Toast.LENGTH_SHORT).show();
            finish();
        }
        user_id=userx.get(0).getId_user();

        service= API.getApi().create(SubeleService.class);

    }

    @Override
    protected void onResume() {
        super.onResume();
        Call<List<Vehicle>> call = service.getVehicleByUserID(Integer.toString(userx.get(0).getId_user()));
        call.enqueue(new Callback<List<Vehicle>>() {
            @Override
            public void onResponse(Call<List<Vehicle>> call, Response<List<Vehicle>> response) {
                vehicles= response.body();
                if(vehicles.size()==0){
                    Toast.makeText(Rider_Activity.this, "Debes registrar un vehículo", Toast.LENGTH_SHORT);
                    Intent intent = new Intent(Rider_Activity.this, CreateVehicleActivity.class);
                    startActivity(intent);
                }
                else{
                    for(int i=0; i<vehicles.size(); i++){
                        vehiclesModel.add(vehicles.get(i).getModel());
                    }
                }

            }

            @Override
            public void onFailure(Call<List<Vehicle>> call, Throwable t) {
                Toast.makeText(Rider_Activity.this, "No se pudo conectar a la base de datos", Toast.LENGTH_SHORT).show();

            }
        });

        Buscar();
    }

    private void showAlertForCreatingRide(String title){
        final AlertDialog.Builder builder=new AlertDialog.Builder(this);
        if(title!=null) builder.setTitle(title);

        View viewInflated= LayoutInflater.from(this).inflate(R.layout.dialog_create_ride, null);
        builder.setView(viewInflated);
        final Spinner spinnerDay=viewInflated.findViewById(R.id.spinner_create_ride_Date);
        final Spinner spinnerCar=viewInflated.findViewById(R.id.spinner_create_ride_Vehicle);
        final EditText editTextStartingPoint=viewInflated.findViewById(R.id.textView_create_ride_starting_point);
        final EditText editTextDestination=viewInflated.findViewById(R.id.editText_create_ride_Destination);
        final TimePicker timePicker=viewInflated.findViewById(R.id.timePicker);
        final EditText editTextCost=viewInflated.findViewById(R.id.editText_create_ride_Cost);
        final EditText editTextRoom=viewInflated.findViewById(R.id.editText_create_ride_room);
        ArrayAdapter<String> adapterD=new ArrayAdapter<>(Rider_Activity.this, android.R.layout.simple_spinner_item, DayOptions);
        ArrayAdapter<String> adapterS= new ArrayAdapter<String>(Rider_Activity.this, android.R.layout.simple_spinner_item, vehiclesModel);

        adapterD.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterS.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCar.setAdapter(adapterS);
        spinnerDay.setAdapter(adapterD);
        builder.setPositiveButton("Crear ride", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }

        });
        final AlertDialog dialog=builder.create();
        dialog.show();

        dialog.getButton(Dialog.BUTTON_POSITIVE).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(editTextStartingPoint.length()!=0 && editTextCost.length()!=0 && editTextDestination.length()!=0 && editTextRoom.length()!=0){
                            startingPoint=editTextStartingPoint.getText().toString();
                            destination=editTextDestination.getText().toString();
                            cost=Double.parseDouble(editTextCost.getText().toString());
                            Integer room=Integer.parseInt(editTextRoom.getText().toString());
                            int hour=timePicker.getCurrentHour();
                            int minute=timePicker.getCurrentMinute();
                            Fullhour=hour+":"+minute+":00";

                            if(spinnerDay.getSelectedItemPosition()==0){
                                Date c = Calendar.getInstance().getTime();
                                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                                Calendar calendar=Calendar.getInstance();
                                calendar.setTime(c);
                                calendar.add(Calendar.DATE, 0);
                                formattedDate = df.format(calendar.getTime());
                            }
                            else{
                                Date c = Calendar.getInstance().getTime();
                                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                                Calendar calendar=Calendar.getInstance();
                                calendar.setTime(c);
                                calendar.add(Calendar.DATE, 1);
                                formattedDate = df.format(calendar.getTime());
                            }


                            Integer position=spinnerCar.getSelectedItemPosition();
                            vehicle_id=Integer.parseInt(vehicles.get(position).getId_vehicle());
                            SubeleService service= API.getApi().create(SubeleService.class);
                            Ride ride=new Ride(Integer.toString(0), startingPoint, formattedDate, Fullhour, Integer.toString(room), Integer.toString(0), Double.toString(cost), Integer.toString(user_id), Integer.toString(vehicle_id), destination, "True");
                            Call call=service.CreateRide(ride);
                            call.enqueue(new Callback() {
                                @Override
                                public void onResponse(Call call, Response response) {
                                    Ride ride= (Ride) response.body();

                                    String ride_id=ride.getId_ride();
                                    Host h=new Host(user_id, userx.get(0).getFirst_name(), userx.get(0).getLast_name());
                                    Buscar();

                                    Intent intent = new Intent(Rider_Activity.this, RideDetailsActivity.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putString("ride_id",ride_id);
                                    bundle.putInt("opc",1); //opc=0=no mostar detalles del vehiculo | 1=mostrar
                                    bundle.putSerializable("host",h);
                                    intent.putExtras(bundle);
                                    startActivity(intent);
                                    dialog.dismiss();
                                }

                                @Override
                                public void onFailure(Call call, Throwable t) {

                                }
                            });
                        }
                        else{
                            Toast.makeText(Rider_Activity.this, "Revise los datos", Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );
    }

    private void Buscar(){
        call=null;
        call = service.getRidesHost(Integer.toString(userx.get(0).getId_user()));

        call.enqueue(new Callback<List<RideFilter>>() {
            @Override
            public void onResponse(Call<List<RideFilter>> call, Response<List<RideFilter>> response) {
                rides=response.body();

                adapter=new Adapter_rides(rides, R.layout.recycler_view_rites_item, new Adapter_rides.OnItemClickListener() {
                    @Override
                    public void onItemClick(RideFilter name, int position) {

                        ///////////////////////////////////////////////////////////CODIGO para ver SOLICITUDES//////////////////////////////////////////////
                        Intent intent = new Intent(Rider_Activity.this, SolicitudesActivity.class);
                        intent.putExtra("id_ride", name.getId_ride());
                        startActivity(intent);
                    }
                }, Rider_Activity.this);

                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onFailure(Call<List<RideFilter>> call, Throwable t) {
                Toast.makeText(Rider_Activity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }

            //////////////////////////////////////////////////////////////////////////////////////////
        });
    }
}
