package com.example.android.sunshine.sync;

import android.app.IntentService;
import android.content.Intent;

import com.example.android.sunshine.sync.SunshineSyncTask;

public class SunshineSyncIntentService extends IntentService {
    public SunshineSyncIntentService() {
        super("SunshineSyncIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        SunshineSyncTask.syncWeather(this);
    }
}