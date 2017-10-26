package cl.usm.inf.walletkeeper;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;


public class NotificationService extends Service {
    public NotificationService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate(){
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent,int flag, int startId)
    {
        NotificationManager notificationManager
                = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent mIntent = new Intent(this, BudgetDisplay.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                intent.getIntExtra("notifId", 0), mIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setContentTitle("Alerta de gastos");
        builder.setContentText("Estás gastando mucho en Ocio");
        builder.setAutoCancel(true);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentIntent(pendingIntent);

        notificationManager.notify(intent.getIntExtra("notifId", 0), builder.build());

        //return super.onStartCommand(intent, flag, startId);
        return START_NOT_STICKY;
    }

}
