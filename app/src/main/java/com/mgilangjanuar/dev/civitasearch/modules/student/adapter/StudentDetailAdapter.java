package com.mgilangjanuar.dev.civitasearch.modules.student.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mgilangjanuar.dev.civitasearch.R;
import com.mgilangjanuar.dev.civitasearch.modules.common.model.ViewContentModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mgilangjanuar (mgilangjanuar@gmail.com)
 *
 * @since 2017
 */

public class StudentDetailAdapter extends RecyclerView.Adapter<StudentDetailAdapter.StudentDetailViewHolder> {

    List<ViewContentModel> list;

    public StudentDetailAdapter(List<ViewContentModel> list) {
        this.list = list;
    }

    @Override
    public StudentDetailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new StudentDetailViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_search_result, parent, false));
    }

    @Override
    public void onBindViewHolder(StudentDetailViewHolder holder, int position) {
        ViewContentModel model = list.get(position);
        holder.title.setText(model.getTitle());
        holder.content.setText(model.getContent());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class StudentDetailViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.title)
        TextView title;

        @BindView(R.id.content)
        TextView content;

        public StudentDetailViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
