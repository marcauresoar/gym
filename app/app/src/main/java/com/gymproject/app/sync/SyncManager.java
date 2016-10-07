package com.gymproject.app.sync;

import android.content.Context;
import android.support.annotation.NonNull;

import com.gymproject.app.sync.event.SyncType;

import java.util.HashMap;

class SyncManager {

    private HashMap<SyncType, AbsSync> syncMap;

    SyncManager(@NonNull Context context) {
        syncMap = new HashMap<>();
        syncMap.put(SyncType.FICHAS, new FichasSync(context));
        syncMap.put(SyncType.EXERCICIOS, new ExerciciosSync(context));
        syncMap.put(SyncType.TREINOS, new TreinosSync(context));
        syncMap.put(SyncType.EXERCICIOS_TREINOS, new ExerciciosSync(context));
    }

    void doSync(@NonNull SyncType syncType) {
        syncMap.get(syncType).sync();
    }
}
