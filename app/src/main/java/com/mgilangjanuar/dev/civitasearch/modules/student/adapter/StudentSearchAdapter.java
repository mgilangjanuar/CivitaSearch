package com.mgilangjanuar.dev.civitasearch.modules.student.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mgilangjanuar.dev.civitasearch.R;
import com.mgilangjanuar.dev.civitasearch.modules.student.model.SearchModel;
import com.mgilangjanuar.dev.civitasearch.modules.student.view.StudentDetailActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mgilangjanuar (mgilangjanuar@gmail.com)
 *
 * @since 2017
 */

public class StudentSearchAdapter extends RecyclerView.Adapter<StudentSearchAdapter.StudentSearchViewHolder> {

    private List<SearchModel> list;

    public StudentSearchAdapter(List<SearchModel> list) {
        this.list = list;
    }

    @Override
    public StudentSearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new StudentSearchViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_student_search, parent, false));
    }

    @Override
    public void onBindViewHolder(StudentSearchViewHolder holder, int position) {
        final SearchModel model = list.get(position);
        holder.name.setText(model.getName());
        holder.studentId.setText(String.valueOf(model.getDegree()) + " - " + model.getStudentId());
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), StudentDetailActivity.class).putExtra("uid", model.getId());
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class StudentSearchViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.layout_student)
        LinearLayout layout;

        @BindView(R.id.name)
        TextView name;

        @BindView(R.id.student_id)
        TextView studentId;

        public StudentSearchViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
