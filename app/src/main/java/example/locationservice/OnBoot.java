package example.locationservice;

import android.content.Context;
import android.content.Intent;
import android.content.BroadcastReceiver;

public class OnBoot extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        context.startService(new Intent(context, LocationService.class));
    }
}