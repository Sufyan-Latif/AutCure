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
import com.example.sufyanlatif.myapplication.interfaces.OnTeacherClick;
import com.example.sufyanlatif.myapplication.models.Teacher;
import com.example.sufyanlatif.myapplication.utils.Constants;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class TeacherAdapter extends RecyclerView.Adapter<TeacherAdapter.TeacherViewHolder> {

    private Context context;
    private ArrayList<Teacher> teacherArrayList;
    private OnTeacherClick teacherClick;

    public TeacherAdapter(Context context, ArrayList<Teacher> teacherArrayList, OnTeacherClick teacherClick){
        this.context = context;
        this.teacherArrayList = teacherArrayList;
        this.teacherClick = teacherClick;
    }

    @NonNull
    @Override
    public TeacherViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.teacher_list_item, viewGroup, false);
        return new TeacherViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TeacherViewHolder teacherViewHolder, int i) {
        final Teacher teacher = teacherArrayList.get(i);

        Glide.with(context).load(Constants.BASE_URL + "images/teacher"+ teacher.getId() +".jpeg")
                .skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).placeholder(R.drawable.boy).into(teacherViewHolder.imgTeacher);
        teacherViewHolder.nameTeacher.setText(teacher.getFirstName()+ " "+ teacher.getLastName());

        teacherViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                teacherClick.onClick(teacher.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return teacherArrayList.size();
    }

    class TeacherViewHolder extends RecyclerView.ViewHolder{

        CircleImageView imgTeacher;
        TextView nameTeacher;

        TeacherViewHolder(@NonNull View itemView) {
            super(itemView);
            imgTeacher = itemView.findViewById(R.id.imgTeacher);
            nameTeacher = itemView.findViewById(R.id.nameTeacher);
        }
    }
}
