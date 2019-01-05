package gautham.agjs.institute;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Bundle;

import android.provider.Settings;
import android.support.v4.app.ActivityCompat;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import java.util.List;

public class Verify extends AppCompatActivity {

    Context context;
    Intent intent1;
    LocationManager locationManager ;
    boolean GpsStatus ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);


    }

    public void connect_wifi(View v) {

        String ssid = "AUSTIN";
        String pass = "asrjebas7498754624";
        WifiConfiguration wifiConfig = new WifiConfiguration();
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        wifiManager.setWifiEnabled(true);

        List<WifiConfiguration> list = wifiManager.getConfiguredNetworks();
        for (WifiConfiguration i : list) {
            if (i.SSID != null && i.SSID.equalsIgnoreCase("\"" + ssid + "\"")) {
                Toast.makeText(Verify.this, "Already Connected, Click on Next", Toast.LENGTH_SHORT).show();
            } else {
                wifiConfig.SSID = String.format("\"%s\"", ssid);
                wifiConfig.preSharedKey = String.format("\"%s\"", pass);

                //remember id
                int netId = wifiManager.addNetwork(wifiConfig);
                wifiManager.disconnect();
                wifiManager.enableNetwork(netId, true);
                wifiManager.reconnect();
                Toast.makeText(Verify.this, "Connected Click on Next", Toast.LENGTH_LONG).show();

            }
        }
    }


    public void next_verify(View v) {

        if (ActivityCompat.checkSelfPermission(Verify.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat
                .checkSelfPermission(Verify.this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(Verify.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        } else {

            context = getApplicationContext();
            GPSStatus();

            if(GpsStatus == true) {

                Toast.makeText(Verify.this,"Location Services Is Enabled",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Verify.this, Login.class);
                startActivity(intent);
            }
            else if(GpsStatus == false) {
                Toast.makeText(Verify.this, "Enable Location Services", Toast.LENGTH_SHORT).show();
                intent1 = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent1);
            }

        }
    }

    public void GPSStatus(){
        locationManager = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
        GpsStatus = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }


}
