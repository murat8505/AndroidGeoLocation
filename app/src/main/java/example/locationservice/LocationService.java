package example.locationservice;

import android.app.IntentService;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Handler;
import android.util.Log;
import android.content.Context;

public class LocationService extends IntentService {

    public static final int locationUpdatesTime = 5000;
    private static final String provider = LocationManager.GPS_PROVIDER;  // LocationManager.NETWORK_PROVIDER

    public static LocationManager locationManager = null;
    private Context context = this;
    private LocationLogger locationLogger = null;
    private Handler handler = new Handler();

    private Runnable runnable = new Runnable() {
        public void run() {
            startLogging();
        }
    };

    public LocationService() {
        super("On Boot Service");
    }

    private void startLogging() {

        Sender sender = new Sender();
        sender.start();

        locationManager = (LocationManager) context.getSystemService(context.LOCATION_SERVICE);
        if(locationManager.isProviderEnabled(provider))
            if(Permissions.check(context))
                try {
                    locationLogger = new LocationLogger(context);
                    locationManager.requestLocationUpdates(provider, locationUpdatesTime, 0, locationLogger);
                } catch(SecurityException e) {
                    e.printStackTrace();
                }
    }

    private void stopLogging() {
        locationManager.removeUpdates(locationLogger);
    }

    @Override
    protected void onHandleIntent(Intent arg0) {

        handler.post(runnable);
    }
}
