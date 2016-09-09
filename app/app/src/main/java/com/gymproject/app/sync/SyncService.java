package com.gymproject.app.sync;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.gymproject.app.sync.event.EventBusManager;
import com.gymproject.app.sync.event.SyncRequestEvent;
import com.gymproject.app.sync.event.SyncType;

import org.greenrobot.eventbus.Subscribe;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SyncService extends Service {

    private ExecutorService executor = null;
    private SyncManager syncManager = null;

    @Override
    public void onCreate() {
        Log.i("aa","SyncService onCreate");
        executor = Executors.newSingleThreadExecutor();
        syncManager = new SyncManager(getApplicationContext());
        EventBusManager.register(this);
    }

    @Override
    public void onDestroy() {
        Log.i("aa","SyncService onDestroy");
        EventBusManager.unregister(this);
        super.onDestroy();
    }

    @Subscribe
    public void onEvent(@NonNull final SyncRequestEvent event) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                syncManager.doSync(event.getSyncType());
            }
        });
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public static void start(@NonNull Context context) {
        Intent intent = new Intent(context, SyncService.class);
        context.startService(intent);
    }

    public static void request(@NonNull SyncType type) {
        EventBusManager.send(new SyncRequestEvent(type));
    }
}
