package example.locationservice;

import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;

public class Sender extends Thread implements Runnable {

    private static final String TAG = "Sender";
    private static final String addressUri = "http://192.168.1.103:8080/geoadd";
    private static final String username = "mitkowski@gmail.com";
    private static final String password = "ZOMO1989";

    public Sender() {
        super("Sender thread");
    }

    public void run() {

        while(true) {
            try {
                Thread.sleep(1 * 15 * 1000);
                sendData();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isInternetAvailable() {

        try {
            InetAddress ipAddr = InetAddress.getByName("google.com");
            return !ipAddr.equals("");
        } catch (Exception e) {
            return false;
        }
    }

    private void sendData() {

        if(isInternetAvailable()) {

            for(LocationDAO location: LocationLogger.locations)
                if(!sendPost(location))
                    return;

            LocationLogger.locations.clear();
        }
    }

    private boolean sendPost(LocationDAO location) {

        Log.e("Sender", "" + location.toString());
        boolean result = false;

        String urlParameters = ""
                + "username=" + username
                + "&password=" + password
                + "&latitude=" + location.getLatitude()
                + "&longitude=" + location.getLongitude()
                + "&speed=" + location.getSpeed()
                + "&altitude=" + location.getAltitude()
                + "&address=" + location.getAddress()
                + "&date=" + location.getDate();

        urlParameters = urlParameters.replace(' ', '+');

        HttpURLConnection client = null;

        try {

            URL url = new URL(addressUri);

            client = (HttpURLConnection)url.openConnection();
            client.setRequestMethod("POST");
            client.setRequestProperty("Accept-Encoding", "identity");
            client.setDoOutput(true);

            DataOutputStream wr = new DataOutputStream(client.getOutputStream());
            wr.writeBytes(urlParameters);
            wr.flush();
            wr.close();

            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();


            Log.i(TAG, url.toString());
            Log.i(TAG, response.toString());

            result = (client.getResponseCode() == HttpURLConnection.HTTP_CREATED); // 201

        } catch(MalformedURLException error) {
            Log.e(TAG, error.getMessage());
        } catch(SocketTimeoutException error) {
            Log.e(TAG, error.getMessage());
        } catch (IOException error) {
            Log.e(TAG, error.getMessage());
        } finally {
            if(client != null)
                client.disconnect();
        }

        return result;
    }

}
