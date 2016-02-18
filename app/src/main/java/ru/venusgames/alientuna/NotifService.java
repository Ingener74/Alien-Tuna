package ru.venusgames.alientuna;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.os.Handler;
import android.os.IBinder;
import android.provider.Contacts;
import android.provider.ContactsContract;
import android.support.v4.app.NotificationCompat.Builder;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

public class NotifService extends Service {

    private Builder builder;
    private NotificationManager notificationManager;

    public NotifService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        builder = new Builder(this).setSmallIcon(R.mipmap.ic_launcher).
                setContentTitle("Tuna notification").
                setContentText("Test notification").
                setAutoCancel(true).
                setContentIntent(PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), PendingIntent.FLAG_CANCEL_CURRENT));
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        getContentResolver().registerContentObserver(ContactsContract.Contacts.CONTENT_URI, true, new ContentObserver(new Handler()) {
            @Override
            public void onChange(boolean selfChange) {
                super.onChange(selfChange);
                builder.setContentText("Contact list changed");
                notificationManager.notify(2, builder.build());
            }
        });
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Log.d(getClass().getName(), "Start waiting");
                    Thread.sleep(5000);

                    Log.d(getClass().getName(), "End waiting");

                    builder.setContentText("Waiting done... test notification");
                    notificationManager.notify(1, builder.build());

                    Log.d(getClass().getName(), "Show notification");

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
