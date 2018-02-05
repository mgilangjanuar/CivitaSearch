package com.mgilangjanuar.dev.civitasearch.modules.lecturer.adapter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.provider.Telephony;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mgilangjanuar.dev.civitasearch.R;
import com.mgilangjanuar.dev.civitasearch.base.BaseActivity;
import com.mgilangjanuar.dev.civitasearch.modules.common.model.ViewContentModel;
import com.mgilangjanuar.dev.civitasearch.modules.lecturer.model.LecturerModel;
import com.mgilangjanuar.dev.civitasearch.util.LocalStorage;

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

    BaseActivity activity;

    public LecturerDetailAdapter(List<ViewContentModel> list) {
        this.list = list;
    }

    public LecturerDetailAdapter(List<ViewContentModel> list, BaseActivity activity) {
        this.list = list;
        this.activity = activity;
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
                } else if ("Phone".equals(model.getTitle())) {
//                    Open contact -> call
                    holder.showMenuDialog(0, model.getContent(), activity);
                } else if ("Email".equals(model.getTitle())) {
//                    Open Gmail -> new email
                    holder.showMenuDialog(1, model.getContent(), activity);
                } else if ("Address".equals(model.getTitle())) {
//                    Open Gmaps
                    holder.showMenuDialog(2, model.getContent(), activity);
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

        public void showMenuDialog(int index, final String value, final BaseActivity activity) {
            AlertDialog.Builder menuDialog = new AlertDialog.Builder(itemView.getContext());
            switch (index) {
                case 0:
                    menuDialog.setItems(new CharSequence[]{"Call", "SMS", "Add to Contacts", "Add to existing Contact", "Share", "Remove"}, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            switch (i) {
                                case 0:
//                                    call
                                    Intent dial = new Intent(Intent.ACTION_DIAL);
                                    dial.setData(Uri.parse("tel:" + value));
                                    itemView.getContext().startActivity(dial);
                                    break;
                                case 1:
//                                    sms
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                                        String defaultApp = Telephony.Sms.getDefaultSmsPackage(itemView.getContext());

                                        Intent smsTo = new Intent(Intent.ACTION_SENDTO);
                                        smsTo.setData(Uri.parse("smsto:" + value));
                                        if (defaultApp != null)
                                            smsTo.setPackage(defaultApp);

                                        itemView.getContext().startActivity(smsTo);
                                    }
                                    else {
                                        Intent smsTo = new Intent(Intent.ACTION_VIEW);
                                        smsTo.setType("vnd.android-dir/mms-sms");
                                        smsTo.putExtra("address", value);
                                        itemView.getContext().startActivity(smsTo);
                                    }
                                    break;
                                case 2:
//                                    add to contacts
                                    Intent addTo = new Intent(ContactsContract.Intents.SHOW_OR_CREATE_CONTACT);
                                    addTo.setData(Uri.parse("tel:" + value));
                                    itemView.getContext().startActivity(addTo);
                                    break;
                                case 3:
//                                    add to existing contact
                                    break;
                                case 4:
//                                    share
                                    Intent share = new Intent(Intent.ACTION_SEND);
                                    share.setType("text/plain");
                                    share.putExtra(Intent.EXTRA_TEXT, value);
                                    itemView.getContext().startActivity(Intent.createChooser(share, "Share via"));
                                    break;
                                case 5:
//                                    remove
                                    LocalStorage.remove(itemView.getContext(), activity.getIntent().getStringExtra("uid"), "Phone", value);

                                    Intent current = activity.getIntent().addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                    activity.finish();
                                    itemView.getContext().startActivity(current);
                                    break;
                            }
                        }
                    });
                    break;
                case 1:
                    menuDialog.setItems(new CharSequence[]{"Send an email", "Share", "Remove"}, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            switch (i) {
                                case 0:
//                                    send email
                                    Intent emailTo = new Intent(Intent.ACTION_SENDTO);
                                    emailTo.setType("text/plain");
                                    emailTo.setData(Uri.parse("mailto:" + value));
                                    itemView.getContext().startActivity(Intent.createChooser(emailTo, "Send email via"));
                                    break;
                                case 1:
//                                    share
                                    Intent share = new Intent(Intent.ACTION_SEND);
                                    share.setType("text/plain");
                                    share.putExtra(Intent.EXTRA_TEXT, value);
                                    itemView.getContext().startActivity(Intent.createChooser(share, "Share via"));
                                    break;
                                case 2:
//                                    remove
                                    LocalStorage.remove(itemView.getContext(), activity.getIntent().getStringExtra("uid"), "Email", value);

                                    Intent current = activity.getIntent().addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                    activity.finish();
                                    itemView.getContext().startActivity(current);
                                    break;
                            }
                        }
                    });
                    break;
                case 2:
                    menuDialog.setItems(new CharSequence[]{"Open in Google Maps", "Show directions", "Show directions (Drive mode)", "Share", "Remove"}, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            switch (i) {
                                case 0:
//                                    open
                                    Intent map = new Intent(Intent.ACTION_VIEW);
                                    map.setData(Uri.parse("https://www.google.com/maps/search/?api=1&query=" + value));
                                    itemView.getContext().startActivity(map);
                                    break;
                                case 1:
//                                    directions
                                    Intent direction = new Intent(Intent.ACTION_VIEW);
                                    direction.setData(Uri.parse("https://maps.google.com/maps?daddr=" + value));
                                    itemView.getContext().startActivity(direction);
                                    break;
                                case 2:
//                                    directions (Drive)
                                    Intent directionGo = new Intent(Intent.ACTION_VIEW);
                                    directionGo.setData(Uri.parse("google.navigation:q=" + value));
                                    itemView.getContext().startActivity(directionGo);
                                    break;
                                case 3:
//                                    share
                                    Intent share = new Intent(Intent.ACTION_SEND);
                                    share.setType("text/plain");
                                    share.putExtra(Intent.EXTRA_TEXT, value);
                                    itemView.getContext().startActivity(Intent.createChooser(share, "Share via"));
                                    break;
                                case 4:
//                                    remove
                                    LocalStorage.remove(itemView.getContext(), activity.getIntent().getStringExtra("uid"), "Address", value);

                                    Intent current = activity.getIntent().addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                    activity.finish();
                                    itemView.getContext().startActivity(current);
                                    break;
                            }
                        }
                    });
                    break;
            }
            menuDialog.show();
        }
    }
}
