package com.devctrl.devicescontrol;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by akaczmarek on 10.11.17.
 */

public class DeviceAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;
    private List<Device> mDataSource;

    public DeviceAdapter(Context mContext,List<Device> mDataSource) {
        this.mContext = mContext;
        this.mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);;
        this.mDataSource = mDataSource;
    }

    @Override
    public int getCount() {
        return mDataSource.size();
    }

    @Override
    public Object getItem(int position) {
        return mDataSource.get(position);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View rowView = mInflater.inflate(R.layout.device_row_layout, viewGroup, false);

        TextView nameTextView= (TextView) rowView.findViewById(R.id.deviceName);
        TextView descriptionTextView=(TextView) rowView.findViewById(R.id.deviceDescription);

        Device device = (Device)getItem(position);

        nameTextView.setText(device.getName().toUpperCase());
        descriptionTextView.setText(device.getDescription());


        return rowView;
    }
}
