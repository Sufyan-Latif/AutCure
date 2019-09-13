package com.example.sufyanlatif.myapplication.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sufyanlatif.myapplication.R;
import com.example.sufyanlatif.myapplication.interfaces.OnUpdateInfoClick;

public class UpdateInfoAdapter extends RecyclerView.Adapter<UpdateInfoAdapter.UpdateInfoViewHolder> {

    String[] title, subtitle;
    int[] icons;
    OnUpdateInfoClick updateInfoClick;
    public UpdateInfoAdapter(String[] title, String[] subtitle, int[] icons, OnUpdateInfoClick updateInfoClick) {
        this.title= title;
        this.subtitle = subtitle;
        this.icons = icons;
        this.updateInfoClick = updateInfoClick;
    }

    @NonNull
    @Override
    public UpdateInfoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.update_info_layout, parent, false);
        return new UpdateInfoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UpdateInfoViewHolder holder, final int position) {
        final String currTitle = title[position];
        final String currSubtitle = subtitle[position];
        int currIcon= icons[position];

        holder.title.setText(currTitle);
        holder.subtitle.setText(currSubtitle);
        holder.icon.setImageResource(currIcon);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateInfoClick.onClick(position, currTitle, currSubtitle);
            }
        });
    }

    @Override
    public int getItemCount() {
        return title.length;
    }

    public class UpdateInfoViewHolder extends RecyclerView.ViewHolder{
        TextView title, subtitle;
        ImageView icon;

        public UpdateInfoViewHolder(@NonNull View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.update_info_icon);
            title = itemView.findViewById(R.id.update_info_title);
            subtitle = itemView.findViewById(R.id.update_info_subtitle);
        }
    }
}
