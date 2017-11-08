package com.mgilangjanuar.dev.civitasearch.modules.lecturer.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mgilangjanuar.dev.civitasearch.R;
import com.mgilangjanuar.dev.civitasearch.modules.lecturer.model.SearchModel;
import com.mgilangjanuar.dev.civitasearch.modules.lecturer.view.LecturerDetailActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mgilangjanuar (mgilangjanuar@gmail.com)
 *
 * @since 2017
 */

public class LecturerSearchAdapter extends RecyclerView.Adapter<LecturerSearchAdapter.LecturerSearchViewHolder> {

    List<SearchModel> list;

    public LecturerSearchAdapter(List<SearchModel> list) {
        this.list = list;
    }

    @Override
    public LecturerSearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new LecturerSearchViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_lecturer_search, parent, false));
    }

    @Override
    public void onBindViewHolder(LecturerSearchViewHolder holder, int position) {
        final SearchModel model = list.get(position);
        holder.name.setText(model.getName());
        holder.status.setText(model.getStatus());
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), LecturerDetailActivity.class).putExtra("uid", model.getId());
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class LecturerSearchViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.layout_lecturer)
        LinearLayout layout;

        @BindView(R.id.name)
        TextView name;

        @BindView(R.id.status)
        TextView status;

        public LecturerSearchViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
