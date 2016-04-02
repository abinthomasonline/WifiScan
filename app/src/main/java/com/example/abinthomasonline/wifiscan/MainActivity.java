package com.example.abinthomasonline.wifiscan;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.content.Context;
import android.net.wifi.ScanResult;
import java.util.List;
import android.widget.ArrayAdapter;

public class MainActivity extends AppCompatActivity {

    ListView wifiList;
    WifiManager wifimanager;
    String wifiStringArray[];
    WifiReciever w;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        wifiList = (ListView)findViewById(R.id.list);

        wifimanager=(WifiManager)getSystemService(Context.WIFI_SERVICE);

        if (wifimanager.isWifiEnabled() == true) {
            System.out.println("wifi on");
            w = new WifiReciever();
            registerReceiver(w, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
            wifimanager.startScan();
        }
        else {
            wifimanager.setWifiEnabled(true);
            System.out.println("wifi was off");
            w = new WifiReciever();
            registerReceiver(w, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
            wifimanager.startScan();
        }




    }

    private class WifiReciever extends BroadcastReceiver {
        public void onReceive(Context c, Intent intent){
            List<ScanResult> wifiScanList = wifimanager.getScanResults();
            wifiStringArray = new String[wifiScanList.size()];

            for(int i = 0; i < wifiScanList.size(); i++) {
                wifiStringArray[i] = ((wifiScanList.get(i).BSSID).toString() + " " +(wifiScanList.get(i).level));
                System.out.println(wifiStringArray[i]);
            }

            wifiList.setAdapter(new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, wifiStringArray));
        }
    }

}
