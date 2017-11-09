package com.mgilangjanuar.dev.civitasearch.modules.lecturer.adapter;

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
import com.mgilangjanuar.dev.civitasearch.modules.lecturer.model.LecturerModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mgilangjanuar (mgilangjanuar@gmail.com)
 *
 * @since 2017
 */

public class LecturerDetailAdapter extends RecyclerView.Adapter<LecturerDetailAdapter.LecturerDetailViewHolder> {

    List<ViewContentModel> list;

    public LecturerDetailAdapter(List<ViewContentModel> list) {
        this.list = list;
    }

    @Override
    public LecturerDetailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new LecturerDetailViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_search_result, parent, false));
    }

    @Override
    public void onBindViewHolder(final LecturerDetailViewHolder holder, int position) {
        final ViewContentModel model = list.get(position);
        holder.title.setText(model.getTitle());
        holder.content.setText(model.getContent());
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ("Education Histories".equals(model.getTitle())) {
                    StringBuilder data = new StringBuilder();
                    for (LecturerModel.EducationHistory education : (List<LecturerModel.EducationHistory>) model.getAdditional()) {
                        data.append("<h4>" + education.getYearGraduated() + "</h4>");
                        data.append("<p>" + education.getDegree() + " at " + education.getSchoolName() + "</p><br />");
                    }
                    holder.showAlertDialog(Html.fromHtml(data.toString().trim()));
                } else if ("Lecture Histories".equals(model.getTitle())) {
                    StringBuilder data = new StringBuilder();
                    for (LecturerModel.LectureHistory lecture : (List<LecturerModel.LectureHistory>) model.getAdditional()) {
                        data.append("<h4>" + lecture.getTerm() + "</h4>");
                        data.append("<p>" + lecture.getCourseName() + " (" + lecture.getCourseCode() + ")" + "</p><br />");
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

    public class LecturerDetailViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.layout)
        LinearLayout layout;

        @BindView(R.id.title)
        TextView title;

        @BindView(R.id.content)
        TextView content;

        public LecturerDetailViewHolder(View itemView) {
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
