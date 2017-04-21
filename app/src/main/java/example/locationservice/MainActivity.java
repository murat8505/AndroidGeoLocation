package example.locationservice;

import android.content.Intent;
import android.os.Build;
import android.Manifest;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;

public class MainActivity extends AppCompatActivity {

    private final int PERMISSION_REQUEST_CODE = 123;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE)
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                startLoggerService();
    }

    private void checkAndrequestPermissions() {

        if(!Permissions.check(getApplicationContext())) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                requestPermissions(new String[]{
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.INTERNET}, PERMISSION_REQUEST_CODE);
        }
        else
            startLoggerService();

    }

    private void startLoggerService() {
        startService(new Intent(getApplicationContext(), LocationService.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkAndrequestPermissions();
    }
}
