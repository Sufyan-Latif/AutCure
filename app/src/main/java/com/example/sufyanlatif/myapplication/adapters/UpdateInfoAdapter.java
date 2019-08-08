package com.example.sufyanlatif.myapplication.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sufyanlatif.myapplication.R;

public class UpdateInfoAdapter extends RecyclerView.Adapter<UpdateInfoAdapter.UpdateInfoViewHolder> {

    String[] title, subtitle;
    int[] icons;
    public UpdateInfoAdapter(String[] title, String[] subtitle, int[] icons) {
        this.title= title;
        this.subtitle = subtitle;
        this.icons = icons;
    }

    @NonNull
    @Override
    public UpdateInfoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.update_info_layout, parent, false);
        return new UpdateInfoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UpdateInfoViewHolder holder, int position) {
        String currTitle = title[position];
        String currSubtitle = subtitle[position];
        int currIcon= icons[position];

        holder.title.setText(currTitle);
        holder.subtitle.setText(currSubtitle);
        holder.icon.setImageResource(currIcon);
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
