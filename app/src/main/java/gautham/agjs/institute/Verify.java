package gautham.agjs.institute;

import android.content.Intent;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import java.util.List;

public class Verify extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);
    }

    public void connect_wifi(View v) {

        String ssid = "Austin";
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

        Intent intent = new Intent(Verify.this, Login.class);
        startActivity(intent);
    }
}
