package com.example.fredlee.practiceapp;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.lang.String;
import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class GridViewAdapter extends ArrayAdapter<ImageItem> {
    private Context context;
    private int layoutResourceId;
    String timeStamp;
    private ArrayList data = new ArrayList();

    public GridViewAdapter(Context context, int layoutResourceId,
                           ArrayList data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new ViewHolder();
            holder.imageRate = (TextView) row.findViewById(R.id.text);
            holder.image = (ImageView) row.findViewById(R.id.image);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }
        ImageItem item = (ImageItem)data.get(position);
        timeStamp = new SimpleDateFormat("MM/dd/yyyy").format(item.getDate());
        holder.imageRate.setText(Double.toString(item.getRate()) + " | " + timeStamp);
        holder.image.setImageBitmap(item.getImage());
        return row;
    }

    static class ViewHolder {
        TextView imageRate;
        ImageView image;
    }
}