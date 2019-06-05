package com.example.rites.activities;

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
import android.widget.EditText;
import android.widget.Toast;

import com.example.rites.API.API;
import com.example.rites.API.APIservice.SubeleService;
import com.example.rites.R;
import com.example.rites.adapters.Adapter_rides;
import com.example.rites.models.Host;
import com.example.rites.models.LogedUser;
import com.example.rites.models.RideFilter;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider_);
        recyclerView=findViewById(R.id.recyclerViewRites);

        service = API.getApi().create(SubeleService.class);
        call = service.getRides();
        myLayoutManager=new LinearLayoutManager(Rider_Activity.this);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(myLayoutManager);

        fab=findViewById(R.id.floatingActionButton);
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
        else
        {
            Buscar();
        }
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
                        final String ride_id = rides.get(position).getId_ride();
                        final Host h= rides.get(position).getHost();
                        Toast.makeText(Rider_Activity.this, ride_id, Toast.LENGTH_LONG).show();
                        //ACCION kawai para cuando se le da click en un item de la lista minuto 3
                        Intent intent = new Intent(Rider_Activity.this, RideDetailsActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("ride_id",ride_id);
                        bundle.putInt("opc",0); //opc=0=no mostar detalles del vehiculo | 1=mostrar
                        bundle.putSerializable("host",h);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                });

                recyclerView.setAdapter(adapter);
            }
            @Override
            public void onFailure(Call<List<RideFilter>> call, Throwable t) {
                Toast.makeText(Rider_Activity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }

            //////////////////////////////////////////////////////////////////////////////////////////
        });
    }
}
