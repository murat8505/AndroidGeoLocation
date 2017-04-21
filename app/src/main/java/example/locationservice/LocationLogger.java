package example.locationservice;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class LocationLogger implements LocationListener {

    private static final String TAG = "TAG: ";
    public static List<LocationDAO> locations = new ArrayList<>();
    private Context context;

    public LocationLogger(Context context) {
        this.context = context;
    }

    public ArrayList<String> getAddresses(Location location) {

        Geocoder geocoder = new Geocoder(context.getApplicationContext(), Locale.getDefault());
        List<Address> addresses = null;
        ArrayList<String> addressFragments = new ArrayList<String>();

        try {
            addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
        } catch (IOException ioException) {
            Log.e(TAG, "service_not_available", ioException);
        } catch (IllegalArgumentException illegalArgumentException) {
            Log.e(TAG, "invalid_lat_long_used", illegalArgumentException);
        }

        if (addresses == null || addresses.size()  == 0) {
            Log.e(TAG, "no_address_found");
        } else {
            Address address = addresses.get(0);
            for(int i = 0; i <= address.getMaxAddressLineIndex(); i++)
                addressFragments.add(address.getAddressLine(i));
        }

        return addressFragments;
    }

    @Override
    public void onLocationChanged(Location location) {

        ArrayList<String> addresses = getAddresses(location);
        String addressesString = new String();
        for(String address : addresses)
            addressesString += address + " ";

        locations.add(new LocationDAO(location.getLatitude(), location.getLongitude(), location.getSpeed(), location.getAltitude(), addressesString));
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {}

    @Override
    public void onProviderEnabled(String provider) {}

    @Override
    public void onProviderDisabled(String provider) {}

}
