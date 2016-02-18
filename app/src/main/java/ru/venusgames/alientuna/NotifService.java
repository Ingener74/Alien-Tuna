package ru.venusgames.alientuna;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat.Builder;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

public class NotifService extends Service {
    public NotifService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        final Context context = this;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Log.d(getClass().getName(), "Start waiting");
                    Thread.sleep(5000);

                    Log.d(getClass().getName(), "End waiting");

                    Builder builder = new Builder(context).setSmallIcon(R.mipmap.ic_launcher).
                            setContentTitle("Tuna notification").
                            setContentText("Test notification").
                            setAutoCancel(true).
                            setContentIntent(PendingIntent.getActivity(context, 0, new Intent(context, MainActivity.class), PendingIntent.FLAG_CANCEL_CURRENT));

                    NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
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
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
