package com.example.rites.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.rites.API.API;
import com.example.rites.API.APIservice.SubeleService;
import com.example.rites.R;
import com.example.rites.adapters.Adapter_rides;
import com.example.rites.models.Host;
import com.example.rites.models.LogedUser;
import com.example.rites.models.RideFilter;
import com.example.rites.models.RideGuest;
import com.example.rites.models.RideGuestFiler;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NoRiderActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager myLayoutManager;

    private SubeleService service= null;
    private Call<List<RideGuestFiler>> call_ride_guest;
    private Call<List<RideFilter>> call_ride_filter;
    private List<RideGuestFiler> rideGuestFilerList;
    private List<RideFilter> rideFilters;


    private Realm realm;
    private RealmResults<LogedUser> userx;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_norider);

        realm= Realm.getDefaultInstance();  //////////Inicializar DB interna
        userx=realm.where(LogedUser.class).findAll();

        recyclerView=findViewById(R.id.recyclerViewRites);
        myLayoutManager=new LinearLayoutManager(NoRiderActivity.this);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(myLayoutManager);
        //tool bar
        Toolbar toolbar = (Toolbar) findViewById(R.id.action_bar2);
        setSupportActionBar(toolbar);
        setTitle("Mis Rides");


        service = API.getApi().create(SubeleService.class);

        call_ride_filter = service.getRidesHost("-1");
        call_ride_filter.enqueue(new Callback<List<RideFilter>>() {
            @Override
            public void onResponse(Call<List<RideFilter>> call, Response<List<RideFilter>> response) {
                rideFilters=response.body();
            }

            @Override
            public void onFailure(Call<List<RideFilter>> call, Throwable t) {

            }
        });

        call_ride_guest = service.getGuestRides(userx.get(0).getId_user().toString());
        call_ride_guest.enqueue(new Callback<List<RideGuestFiler>>() {
            @Override
            public void onResponse(Call<List<RideGuestFiler>> call, Response<List<RideGuestFiler>> response) {

                final List<RideGuestFiler>  rides = response.body();
                int i=0;
                for(i=0;i<rides.size();i++){
                    rideFilters.add(rides.get(i).getRideFilter());
                }
                adapter=new Adapter_rides(rideFilters, R.layout.recycler_view_rites_item, new Adapter_rides.OnItemClickListener() {
                    @Override
                    public void onItemClick(RideFilter name, int position) {
                        final String ride_id = name.getId_ride().toString();
                        final Host h= name.getHost();
                        //Toast.makeText(NoRiderActivity.this, String.valueOf(position), Toast.LENGTH_LONG).show();
                        //ACCION kawai para cuando se le da click en un item de la lista minuto 3
                        Intent intent = new Intent(NoRiderActivity.this,RideDetailsActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("ride_id",ride_id);
                        final int opc = Integer.valueOf(rides.get(position).getStatus());
                        bundle.putInt("opc",opc); //opc=0=no mostar detalles del vehiculo | 1=mostrar
                        bundle.putSerializable("host",h);
                        if (opc==1){
                            bundle.putSerializable("evaluated",rides.get(position).getEvalueted());
                            bundle.putSerializable("guest_id",rides.get(position).getGuest_id());
                        }
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                }, NoRiderActivity.this);
                recyclerView.setAdapter(adapter);
                //Toast.makeText(NoRiderActivity.this, rideFilters.get(0).getDestination(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<List<RideGuestFiler>> call, Throwable t) {
                Toast.makeText(NoRiderActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

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
                //Toast.makeText(NoRiderActivity.this,"Mis Rides",Toast.LENGTH_LONG).show();
                break;
            case R.id.action_filtrarrides:
                Intent intent = new Intent(NoRiderActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
                //Toast.makeText(NoRiderActivity.this,"Rides Disponibles",Toast.LENGTH_LONG).show();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
