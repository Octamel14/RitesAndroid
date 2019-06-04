package com.example.rites.activities;

import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
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

public class Rider_Activity extends AppCompatActivity {

    private FloatingActionButton fab;
    private Realm realm;
    private RealmResults<LogedUser> userx;
    private List<Vehicle> vehicles;
    private List<String> vehiclesModel=new LinkedList<String>();
    private List<String> DayOptions=new LinkedList<String>();

    private String startingPoint;
    private String destination;
    private String date;
    private String Carmodel;
    private String Fullhour;
    private  Integer room;
    private double cost;
    private Integer user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider_);
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
        realm=Realm.getDefaultInstance();  //////////Inicializar DB interna
        userx=realm.where(LogedUser.class).findAll();  //Recuperar el valor de usuario
        user_id=userx.get(0).getId_user();
        SubeleService service= API.getApi().create(SubeleService.class);
        Call<List<Vehicle>> call = service.getVehicleByUserID(Integer.toString(userx.get(0).getId_user()));
        call.enqueue(new Callback<List<Vehicle>>() {
            @Override
            public void onResponse(Call<List<Vehicle>> call, Response<List<Vehicle>> response) {
                vehicles= response.body();
                for(int i=0; i<vehicles.size(); i++){
                    vehiclesModel.add(vehicles.get(i).getModel());
                }

            }

            @Override
            public void onFailure(Call<List<Vehicle>> call, Throwable t) {

            }
        });


    }

    private void showAlertForCreatingRide(String title){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
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


                if(editTextStartingPoint.length()!=0 && editTextCost.length()!=0 && editTextDestination.length()!=0 && editTextRoom.length()!=0){
                startingPoint=editTextStartingPoint.getText().toString();
                destination=editTextDestination.getText().toString();
                cost=Double.parseDouble(editTextCost.getText().toString());
                Integer room=Integer.parseInt(editTextRoom.getText().toString());

                }
                else{
                    Toast.makeText(Rider_Activity.this, "Revise los datos", Toast.LENGTH_LONG).show();
                    return;
                }
                Integer position=spinnerCar.getSelectedItemPosition();
                Integer vehicle_id=Integer.parseInt(vehicles.get(position).getId_vehicle());

                Carmodel=spinnerCar.getSelectedItem().toString();
                date=spinnerDay.getSelectedItem().toString();
                int hour=timePicker.getCurrentHour();
                int minute=timePicker.getCurrentMinute();
                Fullhour=hour+":"+minute+":00";
                Date c = Calendar.getInstance().getTime();
                System.out.println("Current time => " + c);
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                String formattedDate = df.format(c);





                SubeleService service= API.getApi().create(SubeleService.class);
                Ride ride=new Ride(Integer.toString(0), startingPoint, formattedDate, Fullhour, Integer.toString(room), Integer.toString(0), Double.toString(cost), Integer.toString(user_id), Integer.toString(vehicle_id), destination, "False");
                Call call=service.CreateRide(ride);
                call.enqueue(new Callback() {
                    @Override
                    public void onResponse(Call call, Response response) {

                    }

                    @Override
                    public void onFailure(Call call, Throwable t) {

                    }
                });
            }


        });
        AlertDialog dialog=builder.create();
        dialog.show();
    }
}
