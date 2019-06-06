package com.example.rites.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.rites.API.API;
import com.example.rites.API.APIservice.SubeleService;
import com.example.rites.R;
import com.example.rites.adapters.Adapter_rides;
import com.example.rites.adapters.Adapter_solicitudes;
import com.example.rites.models.Host;
import com.example.rites.models.LogedUser;
import com.example.rites.models.RideFilter;
import com.example.rites.models.Solicitud;
import com.example.rites.models.User;
import com.example.rites.models.Vehicle;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.example.rites.R;

public class SolicitudesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private  RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager myLayoutManager;
    private Call<List<Solicitud>> call;
    private SubeleService service= null;
    private List<Solicitud> solicitudes;

    private String id_ride;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicitudes);
        recyclerView=findViewById(R.id.recyclerViewSolicitudes);

        id_ride = getIntent().getStringExtra("id_ride");

        service = API.getApi().create(SubeleService.class);
        myLayoutManager=new LinearLayoutManager(SolicitudesActivity.this);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(myLayoutManager);

        Buscar();

    }

    private void Buscar(){
        call=null;
        call = service.getSolicitudes(id_ride);

        call.enqueue(new Callback<List<Solicitud>>() {
            @Override
            public void onResponse(Call<List<Solicitud>> call, Response<List<Solicitud>> response) {
                solicitudes=response.body();

                adapter=new Adapter_solicitudes(solicitudes, R.layout.recycler_view_solicitudes_item, new Adapter_solicitudes.OnItemClickListener() {
                    @Override
                    public void onItemClick(Solicitud name, int position) {
                        final String sol_id = solicitudes.get(position).getGuest_id();
                        final Host user= solicitudes.get(position).getUser();

                    }
                });
                ((Adapter_solicitudes) adapter).setContext(SolicitudesActivity.this);

                recyclerView.setAdapter(adapter);
            }
            @Override
            public void onFailure(Call<List<Solicitud>> call, Throwable t) {
                Toast.makeText(SolicitudesActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }

            //////////////////////////////////////////////////////////////////////////////////////////
        });
    }
}
