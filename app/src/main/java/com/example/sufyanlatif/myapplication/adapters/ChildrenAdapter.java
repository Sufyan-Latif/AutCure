package com.example.sufyanlatif.myapplication.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.sufyanlatif.myapplication.R;
import com.example.sufyanlatif.myapplication.interfaces.OnChildClick;
import com.example.sufyanlatif.myapplication.models.Child;
import com.example.sufyanlatif.myapplication.utils.Constants;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChildrenAdapter extends RecyclerView.Adapter<ChildrenAdapter.ChildrenViewHolder> {

    Context context;
    ArrayList<Child> childArrayList;
    OnChildClick childClick;

    public ChildrenAdapter(Context context, ArrayList<Child> childArrayList, OnChildClick childClick){
        this.context = context;
        this.childArrayList = childArrayList;
        this.childClick = childClick;
//        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ChildrenViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.children_list_item, viewGroup, false);
        return new ChildrenViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChildrenViewHolder childrenViewHolder, final int i) {
        final Child child = childArrayList.get(i);
//        childrenViewHolder.imgChild.setImageResource(child.);

        Glide.with(context).load(Constants.BASE_URL + "images/child"+ child.getId() +".jpeg")
                .skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).placeholder(R.drawable.boy).into(childrenViewHolder.imgChild);
        childrenViewHolder.nameChild.setText(child.getFirstName()+ " "+ child.getLastName());

        childrenViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                childClick.onClick(child.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return childArrayList.size();
    }

    class ChildrenViewHolder extends RecyclerView.ViewHolder{

        CircleImageView imgChild;
        TextView nameChild;
        ChildrenViewHolder(@NonNull View itemView) {
            super(itemView);
            imgChild = itemView.findViewById(R.id.imgChild);
            nameChild = itemView.findViewById(R.id.nameChild);
        }
    }
}
