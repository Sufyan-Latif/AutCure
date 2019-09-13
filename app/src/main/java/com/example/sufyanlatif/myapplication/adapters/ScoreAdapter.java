package com.example.sufyanlatif.myapplication.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sufyanlatif.myapplication.R;
import com.example.sufyanlatif.myapplication.models.Score;

import java.util.ArrayList;

public class ScoreAdapter extends RecyclerView.Adapter<ScoreAdapter.ScoreViewHolder> {

    ArrayList<Score> scoreArrayList;

    public ScoreAdapter(ArrayList<Score> scoreArrayList){
        this.scoreArrayList = scoreArrayList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ScoreViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.score_item, viewGroup, false);
        return new ScoreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScoreViewHolder scoreViewHolder, int i) {
        Score score = scoreArrayList.get(i);

        scoreViewHolder.tvCorrect.setText(score.getCorrect());
        scoreViewHolder.tvIncorrect.setText(score.getIncorrect());
    }

    @Override
    public int getItemCount() {
        return scoreArrayList.size();
    }

    class ScoreViewHolder extends RecyclerView.ViewHolder{

        TextView tvCorrect, tvIncorrect;
        ScoreViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCorrect = itemView.findViewById(R.id.tvmCorrect);
            tvIncorrect = itemView.findViewById(R.id.tvmIncorrect);
        }
    }
}
