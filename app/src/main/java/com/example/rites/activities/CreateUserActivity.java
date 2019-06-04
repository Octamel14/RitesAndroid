package com.example.rites.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.rites.API.API;
import com.example.rites.API.APIservice.SubeleService;
import com.example.rites.models.LogedUser;
import com.example.rites.models.User;
import com.example.rites.R;

import org.w3c.dom.Text;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateUserActivity extends AppCompatActivity implements View.OnClickListener {

    EditText etName;
    EditText etLastname;
    EditText etEmail;
    EditText etPassword;
    EditText etConfPassword;
    CheckBox rbISRider;
    Button btCreate;

    private Realm realm;

    private SubeleService service= null;
    private Call<User> call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);

        realm=Realm.getDefaultInstance();

        service = API.getApi().create(SubeleService.class);

        etName = findViewById(R.id.etName);
        etLastname = findViewById(R.id.etLastname);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etConfPassword = findViewById(R.id.etConfPassword);
        btCreate = findViewById(R.id.btCreate);
        rbISRider = findViewById(R.id.rbIsRider);

        btCreate.setOnClickListener(this);

        setTitle("Crear Usuario");
    }


    @Override
    public void onClick(View v) {
        if(TextUtils.isEmpty(etName.getText().toString()) || TextUtils.isEmpty(etLastname.getText().toString())
                || TextUtils.isEmpty(etEmail.getText().toString()) || TextUtils.isEmpty(etPassword.getText().toString()))
        {
            Toast.makeText(this, "Por favor llene todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        if(!ValidatePassword()) {
            Toast.makeText(this, "El password no coincide", Toast.LENGTH_SHORT).show();
            return;
        }

        call=null;
        User user = new User(0, etName.getText().toString(), etLastname.getText().toString(),
                etEmail.getText().toString(), etPassword.getText().toString(), rbISRider.isChecked(),
                0, 0, 0);
        sendPost(user);

    }

    private boolean ValidatePassword()
    {
        return etPassword.getText().toString().equals(etConfPassword.getText().toString());
    }

    public void sendPost(User user) {
        service.postUser(user).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, final Response<User> response) {
                if(response.isSuccessful()) {
                    if (response.body() != null) {
                        Toast.makeText(CreateUserActivity.this, "Usuario creado.", Toast.LENGTH_SHORT).show();

                        realm.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                LogedUser logedUser = new LogedUser(response.body());
                                realm.deleteAll();
                                realm.copyToRealm(logedUser);
                            }
                        });

                        if (response.body().getIs_rider()) {
                            Intent intent = new Intent(CreateUserActivity.this, CreateVehicleActivity.class);
                            startActivity(intent);
                        }
                        finish();
                    }
                }
                else
                {
                    Toast.makeText(CreateUserActivity.this,"Error al crear usuario.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
               Toast.makeText(CreateUserActivity.this,"No se pudo conectar al servidor.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
