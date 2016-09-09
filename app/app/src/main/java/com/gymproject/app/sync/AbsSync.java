package com.gymproject.app.sync;

import android.content.Context;
import android.support.annotation.NonNull;

import com.gymproject.app.sync.event.SyncEvent;
import com.gymproject.app.sync.event.SyncStatus;
import com.gymproject.app.sync.event.SyncType;
import com.gymproject.app.utils.NetworkUtils;

abstract class AbsSync {

    private Context context;

    public AbsSync(@NonNull Context context) {
        this.context = context.getApplicationContext();
    }

    void sync() {
        if (NetworkUtils.isNetworkConnected(context)) {
            post();
        }
    }

    protected abstract SyncType getSyncType();

    protected abstract void post();
    protected abstract void get();

}
