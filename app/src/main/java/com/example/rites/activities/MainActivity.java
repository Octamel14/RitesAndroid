package com.example.rites.activities;

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
import com.example.rites.models.Ride;
import com.example.rites.models.RideFilter;

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

        SubeleService service= API.getApi().create(SubeleService.class);
        Call<List<RideFilter>> call = service.getRides2();
        List<RideFilter> rides;

       /* Ride ride=new Ride(0, "DICIS", "2019-05-26", "18:48:02", 4, 1, 30.0f, 1, 1, "Valle");
        SubeleService service= API.getApi().create(SubeleService.class);
        Call call=service.CreateRide(new PostRide(ride));
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                Toast.makeText(MainActivity.this, call.toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call call, Throwable t) {

            }
        });*/
        myLayoutManager=new LinearLayoutManager(MainActivity.this);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(myLayoutManager);


        call.enqueue(new Callback<List<RideFilter>>() {
            @Override
            public void onResponse(Call<List<RideFilter>> call, Response<List<RideFilter>> response) {
                List<RideFilter> rides=response.body();

                adapter=new Adapter_rides(rides, R.layout.recycler_view_rites_item, new Adapter_rides.OnItemClickListener() {
                    @Override
                    public void onItemClick(RideFilter name, int position) {
                        Toast.makeText(MainActivity.this, "equisde", Toast.LENGTH_LONG).show();
                        //ACCION kawai para cuando se le da click en un item de la lista
                    }
                });

                recyclerView.setAdapter(adapter);
            }
            @Override
            public void onFailure(Call<List<RideFilter>> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }

            //////////////////////////////////////////////////////////////////////////////////////////
        });
    }

}
