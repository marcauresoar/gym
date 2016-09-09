package com.gymproject.app.applications;

import android.app.Application;

import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class ApplicationRealm extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        RealmConfiguration config = new RealmConfiguration.Builder(this)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);

        FlowManager.init(new FlowConfig.Builder(this).openDatabasesOnInit(true).build());

    }
}
