package com.mgilangjanuar.dev.civitasearch.modules.about.adapter;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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

public class AboutAdapter extends RecyclerView.Adapter<AboutAdapter.AboutViewHolder> {

    List<ViewContentModel> list;

    public AboutAdapter(List<ViewContentModel> list) {
        this.list = list;
    }

    @Override
    public AboutViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AboutViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_search_result, parent, false));
    }

    @Override
    public void onBindViewHolder(final AboutViewHolder holder, int position) {
        final ViewContentModel model = list.get(position);
        if (model.getTitle() == null) {
            holder.title.setVisibility(TextView.GONE);
        } else {
            holder.title.setText(model.getTitle());
        }
        holder.content.setText(model.getContent());
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ("Civita Search API".equals(model.getTitle())) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.wasisto.com/civitasearch"));
                    holder.itemView.getContext().startActivity(intent);
                } else if ("GitHub".equals(model.getTitle())) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/mgilangjanuar/CivitaSearch"));
                    holder.itemView.getContext().startActivity(intent);
                } else if ("Play Store".equals(model.getTitle())) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.mgilangjanuar.dev.civitasearch"));
                    holder.itemView.getContext().startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class AboutViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.layout)
        LinearLayout layout;

        @BindView(R.id.title)
        TextView title;

        @BindView(R.id.content)
        TextView content;

        public AboutViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
