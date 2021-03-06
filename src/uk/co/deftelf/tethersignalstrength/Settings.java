package uk.co.deftelf.tethersignalstrength;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import org.json.JSONObject;
import org.json.JSONStringer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.DhcpInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class Settings extends Activity {

    static final int PORT = 31292;

    private WifiManager wifii;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        wifii = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        DhcpInfo d = wifii.getDhcpInfo();

        String s_dns1 = "DNS 1: " + intToIp(d.dns1);
        String s_dns2 = "DNS 2: " + intToIp(d.dns2);
        String s_gateway = "Default Gateway: " + intToIp(d.gateway);
        String s_ipAddress = "IP Address: " + intToIp(d.ipAddress);
        String s_leaseDuration = "Lease Time: " + d.leaseDuration;
        String s_netmask = "Subnet Mask: " + intToIp(d.netmask);
        String s_serverAddress = "Server IP: " + intToIp(d.serverAddress);

        Log.d("", "Network Info\n" + s_dns1 + "\n" + s_dns2 + "\n" + s_gateway + "\n" + s_ipAddress + "\n"
                + s_leaseDuration + "\n" + s_netmask + "\n" + s_serverAddress);

        setContentView(R.layout.settings);

        
        findViewById(R.id.startProvider).setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                

                stopService(new Intent(Settings.this, StrengthReaderService.class));
                stopService(new Intent(Settings.this, StrengthProviderService.class));
                startService(new Intent(Settings.this, StrengthProviderService.class));
                
                
            }

        });
        

        findViewById(R.id.startReader).setOnClickListener(new OnClickListener() {

            public void onClick(View v) {


                stopService(new Intent(Settings.this, StrengthReaderService.class));
                stopService(new Intent(Settings.this, StrengthProviderService.class));
                startService(new Intent(Settings.this, StrengthReaderService.class));
                
            }

        });
        
        findViewById(R.id.stop).setOnClickListener(new OnClickListener() {

            public void onClick(View v) {

                stopService(new Intent(Settings.this, StrengthReaderService.class));
                stopService(new Intent(Settings.this, StrengthProviderService.class));
            }

        });
        
        


    }

    public String intToIp(int i) {

        return (i & 0xFF) + "." + ((i >> 8) & 0xFF) + "." + ((i >> 16) & 0xFF) + "." + ((i >> 24) & 0xFF);
    }

    public static int toBars(int signalStrength) {
        switch (signalStrength) {
        case 0:
        case 1:
        case 2:
            return 0;
        case 3:
        case 4:
            return 1;
        case 5:
        case 6:
        case 7:
            return 2;
        case 8:
        case 9:
        case 10:
            return 3;
        case 99:
            return -1;
        default:
            return 4;
        }
    }
}
