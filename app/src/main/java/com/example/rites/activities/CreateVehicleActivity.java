package com.example.rites.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.rites.API.API;
import com.example.rites.API.APIservice.SubeleService;
import com.example.rites.R;
import com.example.rites.models.LogedUser;
import com.example.rites.models.User;
import com.example.rites.models.Vehicle;

import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateVehicleActivity extends AppCompatActivity {

    EditText etModel;
    EditText etColor;
    EditText etPlates;
    Button btCreate;

    private Realm realm;
    private RealmResults<LogedUser> userx;
    private SubeleService service= null;
    private Call<User> call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_vehicle);

        service = API.getApi().create(SubeleService.class);

        realm=Realm.getDefaultInstance();

        userx=realm.where(LogedUser.class).findAll();
        if(userx.size() == 0){
            Toast.makeText(this, "No se pudo encontrar su usuario", Toast.LENGTH_SHORT).show();
            finish();
        }

        etModel = findViewById(R.id.etModel);
        etColor = findViewById(R.id.etColor);
        etPlates = findViewById(R.id.etPlates);
        btCreate = findViewById(R.id.btCreate);

        setTitle("Agregar Vehículo");

        btCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(etModel.getText().toString()) || TextUtils.isEmpty(etColor.getText().toString())
                        || TextUtils.isEmpty(etPlates.getText().toString()))
                {
                    Toast.makeText(CreateVehicleActivity.this, "Por favor llene todos los campos", Toast.LENGTH_SHORT).show();
                    return;
                }

                call=null;
                Vehicle vehicle = new Vehicle(Integer.toString(0), Integer.toString(userx.get(0).getId_user()),
                        etModel.getText().toString(), etColor.getText().toString(), etPlates.getText().toString());
                sendPost(vehicle);
            }
        });
    }

    public void sendPost(Vehicle vehicle) {
        service.postVehicle(vehicle).enqueue(new Callback<Vehicle>() {
            @Override
            public void onResponse(Call<Vehicle> call, final Response<Vehicle> response) {
                if(response.isSuccessful()) {
                    if (response.body() != null) {
                        Toast.makeText(CreateVehicleActivity.this, "Vehículo creado.", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
                else{
                    Toast.makeText(CreateVehicleActivity.this,"Error al crear vehículo.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Vehicle> call, Throwable t) {
                Toast.makeText(CreateVehicleActivity.this,"No se pudo conectar al servidor.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
