package com.mgilangjanuar.dev.civitasearch.modules.student.adapter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mgilangjanuar.dev.civitasearch.R;
import com.mgilangjanuar.dev.civitasearch.modules.common.model.ViewContentModel;
import com.mgilangjanuar.dev.civitasearch.modules.student.model.StudentModel;

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
    public void onBindViewHolder(final StudentDetailViewHolder holder, int position) {
        final ViewContentModel model = list.get(position);
        holder.title.setText(model.getTitle());
        holder.content.setText(model.getContent());

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ("Status Histories".equals(model.getTitle())) {
                    StringBuilder data = new StringBuilder();
                    for (StudentModel.StatusHistory status : (List<StudentModel.StatusHistory>) model.getAdditional()) {
                        data.append("<h4>" + status.getTerm() + "</h4>");
                        data.append("<p>&nbsp;&nbsp;&nbsp; " + status.getStatus() + " - " + status.getCreditsTaken() + " credits</p><br />");
                    }
                    holder.showAlertDialog(Html.fromHtml(data.toString().trim()));
                } else if ("Course Histories".equals(model.getTitle())) {
                    StringBuilder data = new StringBuilder();
                    for (StudentModel.CourseHistory course : (List<StudentModel.CourseHistory>) model.getAdditional()) {
                        data.append("<h4>" + course.getTerm() + "</h4>");
                        data.append(course.toHtmlString() + "<br />");
                    }
                    holder.showAlertDialog(Html.fromHtml(data.toString().trim()));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class StudentDetailViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.layout)
        LinearLayout layout;

        @BindView(R.id.title)
        TextView title;

        @BindView(R.id.content)
        TextView content;

        public StudentDetailViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void showAlertDialog(CharSequence content) {
            AlertDialog alertDialog = new AlertDialog.Builder(itemView.getContext()).create();
            alertDialog.setTitle(title.getText().toString());
            alertDialog.setMessage(content);
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, itemView.getContext().getString(R.string.close), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            alertDialog.show();
        }
    }
}
