package com.devctrl.devicescontrol;

import android.content.Context;
import android.util.JsonReader;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by akaczmarek on 10.11.17.
 */

public class Device {

    private Long id;
    private String name;
    private String description;
    private String ip;

    public Device(Long id, String name, String description, String ip) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.ip = ip;
    }

    public Device(JsonReader jsonReader) throws IOException {
        while (jsonReader.hasNext()) {
            String name = jsonReader.nextName();
            if (name.equals("id")) {
                this.id = jsonReader.nextLong();

            } else if (name.equals("name")) {
                this.name = jsonReader.nextString();

            } else if (name.equals("description")) {
                this.description = jsonReader.nextString();

            } else if (name.equals("ip")) {
                this.ip = jsonReader.nextString();
            } else {
                jsonReader.skipValue();
            }
        }
    }

    public static List<Device> getFromFile(String filename, Context context) throws JSONException {
        String jsonString = loadJsonFromAsset(filename, context);
        return getFromJson(jsonString);
    }

    public static List<Device> getFromJson(JsonReader jsonReader) throws IOException {
        List<Device> result = new ArrayList<>();

        jsonReader.beginArray();
        while (jsonReader.hasNext()) {
            jsonReader.beginObject();
            Device device = new Device(jsonReader);
            jsonReader.endObject();
            result.add(device);
        }
        jsonReader.endArray();

        return result;

    }

    public static List<Device> getFromJson(String json) throws JSONException {
        List<Device> devicesList = new ArrayList<>();
        JSONArray devices = new JSONArray(json);
        for (int i = 0; i < devices.length(); i++) {
            Device device = new Device(
                    devices.getJSONObject(i).getLong("id"),
                    devices.getJSONObject(i).getString("name"),
                    devices.getJSONObject(i).getString("description"),
                    devices.getJSONObject(i).getString("ip")
            );

            devicesList.add(device);
        }

        return devicesList;
    }

    private static String loadJsonFromAsset(String filename, Context context) {
        String json = null;

        try {
            InputStream is = context.getAssets().open(filename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (java.io.IOException ex) {
            ex.printStackTrace();
            return null;
        }

        return json;
    }


    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getIp() {
        return ip;
    }
}

