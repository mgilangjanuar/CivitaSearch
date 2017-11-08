package com.mgilangjanuar.dev.civitasearch.modules.school.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mgilangjanuar.dev.civitasearch.R;
import com.mgilangjanuar.dev.civitasearch.modules.main.view.MainActivity;
import com.mgilangjanuar.dev.civitasearch.modules.school.model.SchoolModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mgilangjanuar (mgilangjanuar@gmail.com)
 *
 * @since 2017
 */

public class SchoolAdapter extends RecyclerView.Adapter<SchoolAdapter.SchoolViewHolder> {

    private List<SchoolModel> list;

    public SchoolAdapter(List<SchoolModel> list) {
        this.list = list;
    }


    @Override
    public SchoolViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SchoolViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_school, parent, false));
    }

    @Override
    public void onBindViewHolder(SchoolViewHolder holder, int position) {
        final SchoolModel model = list.get(position);
        holder.title.setText(model.getName());
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), MainActivity.class);
                intent.putExtra("id", model.getId());
                intent.putExtra("name", model.getName());
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class SchoolViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.title)
        TextView title;

        @BindView(R.id.layout_school)
        RelativeLayout layout;

        public SchoolViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
