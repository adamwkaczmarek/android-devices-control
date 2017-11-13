package com.devctrl.devicescontrol;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.JsonReader;
import android.widget.ListView;


import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import java.util.Arrays;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class DevicesActivity extends AppCompatActivity {

    private ListView deviceListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_devices);
        deviceListView = findViewById(R.id.device_list_view);

        // final List<Device> devices = Device.getFromFile("device.json",this);


    }

    @Override
    protected void onStart() {
        super.onStart();
        new HttpRequestTask(this).execute();
    }

    private class HttpRequestTask extends AsyncTask<Void, Void, List<Device>> {
        private Context context;

        public HttpRequestTask(Context context) {
            this.context = context;
        }

        @Override
        protected List<Device> doInBackground(Void... params) {
            try {
                final String url = "http://10.200.1.133:8082/api/device";
                URL endpoint = new URL(url);
                HttpURLConnection myConnection =
                        (HttpURLConnection) endpoint.openConnection();

                if (myConnection.getResponseCode() == 200) {
                    InputStream responseBody = myConnection.getInputStream();
                    InputStreamReader responseBodyReader =
                            new InputStreamReader(responseBody, "UTF-8");
                    JsonReader jsonReader = new JsonReader(responseBodyReader);
                    return Device.getFromJson(jsonReader);


                }

            } catch (IOException e ) {
                e.printStackTrace();
            }
            return null;

        }

        @Override
        protected void onPostExecute(List<Device> devices) {
            DeviceAdapter adapter = new DeviceAdapter(context, devices);
            deviceListView.setAdapter(adapter);
        }

    }


}
