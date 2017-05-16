package com.ivianuu.spotify.auth;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.ivianuu.spotify.CurrentUser;
import com.ivianuu.spotify.Spotify;

/**
 * Author IVIanuu.
 */

public class AutoRefreshTokenReceiver extends BroadcastReceiver {

    private static final long REPEAT_TIME = 1000 * 60 * 45;

    public static void scheduleAlarm(Context context) {
        Intent intent = new Intent(context, AutoRefreshTokenReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 113, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                5000,
                REPEAT_TIME,
                pendingIntent);
    }

    @Override
    public void onReceive(@NonNull final Context context, Intent intent) {
        if (!CurrentUser.getInstance().isLoggedIn()) return;

        Spotify.getInstance().refreshToken();
        CurrentUser.getInstance().updateUserDataRx().subscribe();
    }
}
