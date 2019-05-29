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
import com.example.rites.models.Rides;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private  RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager myLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView=findViewById(R.id.recyclerViewRites);
        //List<Rides> rides=GetAllRides();

        SubeleService service= API.getApi().create(SubeleService.class);
        Call<List<Rides>> call = service.getRides2();
        List<Rides> rides=null;
        myLayoutManager=new LinearLayoutManager(MainActivity.this);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(myLayoutManager);
        //////////////////////////////////////RETROFIT/////////////////////////////////////////////////
        call.enqueue(new Callback<List<Rides>>() {
            @Override
            public void onResponse(Call<List<Rides>> call, Response<List<Rides>> response) {
                List<Rides> rides=response.body();

                adapter=new Adapter_rides(rides, R.layout.recycler_view_rites_item, new Adapter_rides.OnItemClickListener() {
                    @Override
                    public void onItemClick(Rides name, int position) {
                        Toast.makeText(MainActivity.this, "equisde", Toast.LENGTH_LONG).show();
                        //ACCION kawai para cuando se le da click en un item de la lista
                    }
                });

                recyclerView.setAdapter(adapter);
            }
            @Override
            public void onFailure(Call<List<Rides>> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }

            //////////////////////////////////////////////////////////////////////////////////////////
        });
    }

}
