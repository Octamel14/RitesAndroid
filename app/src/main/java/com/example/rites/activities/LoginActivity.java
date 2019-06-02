package com.example.rites.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.rites.API.API;
import com.example.rites.API.APIservice.SubeleService;
import com.example.rites.R;
import com.example.rites.models.LogedUser;
import com.example.rites.models.User;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private Button buttonLogin;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private String email;
    private String password;
    private Realm realm;
    private RealmResults<LogedUser> userx;
    private Integer option;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        realm=Realm.getDefaultInstance();  //////////Inicializar DB interna


        userx=realm.where(LogedUser.class).findAll();  //Recuperar el valor de usuario
        if(userx.size()!=0){
            if(userx.get(0).getIs_rider()==Boolean.FALSE) {
                Intent intent=new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
            else{
                Intent intent=new Intent(LoginActivity.this, Rider_Activity.class);
                startActivity(intent);

            }

        }

        buttonLogin=findViewById(R.id.buttonLogin);
        editTextEmail=findViewById(R.id.editTextEmail);
        editTextPassword=findViewById(R.id.editTextPassword);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                password=editTextPassword.getText().toString();
                email=editTextEmail.getText().toString();
                SubeleService service= API.getApi().create(SubeleService.class);
                Call <List<User>> call = service.getUserLogin(email, password);
                call.enqueue(new Callback<List<User>>() {
                    @Override
                    public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                        final List<User> x=response.body();
                        if((x.size())==1){
                            realm.executeTransaction(new Realm.Transaction() {
                                @Override
                                public void execute(Realm realm) {
                                    LogedUser logedUser=new LogedUser(x.get(0).getId_user(), x.get(0).getFirst_name().toString(), x.get(0).getLast_name(), x.get(0).getEmail(), x.get(0).getPassword(), x.get(0).getIs_rider(), x.get(0).getRider_score(), x.get(0).getRiders_number(), x.get(0).getScored());
                                    realm.deleteAll();
                                    realm.copyToRealm(logedUser);

                                }
                            });
                            if(userx.get(0).getIs_rider()==Boolean.FALSE) {
                                Intent intent=new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                            }
                            else{
                                Intent intent=new Intent(LoginActivity.this, Rider_Activity.class);
                                startActivity(intent);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<List<User>> call, Throwable t) {
                        Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();

                    }
                });
            }
        });
    }


}
