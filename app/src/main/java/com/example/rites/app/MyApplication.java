package com.example.rites.app;
import android.app.Application;
import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        setUpRealmConfig();
        Realm realm=Realm.getDefaultInstance();
        realm.close();
    }

    private void setUpRealmConfig(){
        RealmConfiguration config=new RealmConfiguration
                .Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);

    }
}
