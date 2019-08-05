package com.example.sufyanlatif.myapplication;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class UpdateInfoAdapter extends ArrayAdapter<String> {

    private Activity context;
    private Integer[] icon;
    private String[] title;
    private String[] subTitle;

    public UpdateInfoAdapter(Activity context, String[] title, String[] subTitle, Integer[] icon) {
        super(context, R.layout.update_info_layout, title);
        this.context = context;
        this.icon = icon;
        this.title = title;
        this.subTitle = subTitle;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.update_info_layout, null, true);

        TextView titleText = rowView.findViewById(R.id.update_info_title);
        ImageView imageView = rowView.findViewById(R.id.update_info_icon);
        TextView subtitleText = rowView.findViewById(R.id.update_info_subtitle);

        titleText.setText(title[position]);
        imageView.setImageResource(icon[position]);
        subtitleText.setText(subTitle[position]);
        return rowView;
    }
}
