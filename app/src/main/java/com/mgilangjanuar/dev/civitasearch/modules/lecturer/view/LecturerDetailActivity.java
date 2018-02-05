package com.mgilangjanuar.dev.civitasearch.modules.lecturer.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageButton;

import com.mgilangjanuar.dev.civitasearch.R;
import com.mgilangjanuar.dev.civitasearch.base.BaseActivity;
import com.mgilangjanuar.dev.civitasearch.modules.common.model.ViewContentModel;
import com.mgilangjanuar.dev.civitasearch.modules.lecturer.adapter.LecturerDetailAdapter;
import com.mgilangjanuar.dev.civitasearch.modules.lecturer.listener.LecturerDetailListener;
import com.mgilangjanuar.dev.civitasearch.modules.lecturer.model.LecturerModel;
import com.mgilangjanuar.dev.civitasearch.modules.lecturer.presenter.LecturerDetailPresenter;
import com.mgilangjanuar.dev.civitasearch.util.BitmapStringTransform;
import com.mgilangjanuar.dev.civitasearch.util.LocalStorage;
import com.mgilangjanuar.dev.civitasearch.util.PermissionPerformer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class LecturerDetailActivity extends BaseActivity implements LecturerDetailListener {

    @BindView(R.id.lecturer_image)
    CircleImageView lecturerImage;

    @BindView(R.id.edit_image)
    ImageButton editImage;

    @BindView(R.id.details)
    RecyclerView details;

    @BindView(R.id.contacts)
    RecyclerView contacts;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    private ProgressDialog progressDialog;
    private LecturerDetailPresenter presenter = new LecturerDetailPresenter(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle(getString(R.string.lecturer_detail));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        setupRecyclerView(details);
        setupRecyclerView(contacts);

        progressDialog = buildLoadingDialog();
        progressDialog.show();

        presenter.getLecturer(getIntent().getStringExtra("uid"));
    }

    @Override
    public int findLayout() {
        return R.layout.activity_lecturer_detail;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onSuccess(LecturerDetailAdapter adapter, LecturerModel.Photo photo) {
        progressDialog.cancel();
        if (adapter.getItemCount() == 0) {
            showSnackbar(getString(R.string.empty_result));
        } else {
            details.setAdapter(adapter);
            details.setNestedScrollingEnabled(false);

            loadDataFromStorage();
            loadLecturerPhoto(photo);
        }
    }

    private void loadLecturerPhoto(LecturerModel.Photo photo) {
        List<String> photoStorage = LocalStorage.select(this, getIntent().getStringExtra("uid"), "Photo");

        if (photoStorage.get(0) != null) {
            Bitmap image = BitmapStringTransform.toBitmap(photoStorage.get(0));
            lecturerImage.setImageBitmap(image);
        }
        else {
            if (photo.getData() != null) {
                Bitmap bitmap = BitmapStringTransform.toBitmap(photo.getData());

                LocalStorage.insert(this, getIntent().getStringExtra("uid"), "Photo", BitmapStringTransform.toString(bitmap));

                lecturerImage.setImageBitmap(bitmap);
            }
        }
    }

    private void loadDataFromStorage() {
        List<String> phoneList = LocalStorage.select(this, getIntent().getStringExtra("uid"), "Phone");
        List<String> emailList = LocalStorage.select(this, getIntent().getStringExtra("uid"), "Email");
        List<String> addressList = LocalStorage.select(this, getIntent().getStringExtra("uid"), "Address");

        List<ViewContentModel> listAll = new ArrayList<>();
        for (String phone : phoneList) {
            listAll.add(new ViewContentModel("Phone", phone));
        }

        for (String email : emailList) {
            listAll.add(new ViewContentModel("Email", email));
        }

        for (String address : addressList) {
            listAll.add(new ViewContentModel("Address", address));
        }

        contacts.setAdapter(new LecturerDetailAdapter(listAll, this));
        contacts.setNestedScrollingEnabled(false);
    }

    @Override
    public void onError(String error) {
        progressDialog.cancel();
        showSnackbar(error);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 0) {
                Bitmap image = (Bitmap) data.getExtras().get("data");
                String encoded = BitmapStringTransform.toString(image);

                LocalStorage.insert(this, getIntent().getStringExtra("uid"), "Photo", encoded);
                lecturerImage.setImageBitmap(BitmapStringTransform.toBitmap(encoded));
            }
            else if (requestCode == 1) {
                Bitmap image = null;
                if (data != null) {
                    try {
                        image = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                String encoded = BitmapStringTransform.toString(image);

                LocalStorage.insert(this, getIntent().getStringExtra("uid"), "Photo", encoded);
                lecturerImage.setImageBitmap(BitmapStringTransform.toBitmap(encoded));
            }
            else if (requestCode == 2)
                loadDataFromStorage();
        }
    }

    @OnClick(R.id.edit_image)
    void setEditImage() {
        final CharSequence[] OPTIONS = {"Take photo", "Choose from Gallery", "Set from default"};

        AlertDialog.Builder menuOption = new AlertDialog.Builder(LecturerDetailActivity.this);
        menuOption.setItems(OPTIONS, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                switch (i) {
                    case 0:
                        if (PermissionPerformer.checkPermissionCamera(LecturerDetailActivity.this)) {
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(intent, 0);
                        }
                        break;
                    case 1:
                        if (PermissionPerformer.checkPermissionExternalStorage(LecturerDetailActivity.this)) {
                            Intent intent = new Intent();
                            intent.setType("image/*");
                            intent.setAction(Intent.ACTION_GET_CONTENT);
                            startActivityForResult(Intent.createChooser(intent, "Select image"), 1);
                        }
                        break;
                    case 2:
                        LocalStorage.remove(getApplicationContext(), getIntent().getStringExtra("uid"), "Photo", null);

                        Intent current = getIntent().addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        finish();
                        startActivity(current);
                }
            }
        });
        menuOption.show();
    }

    @OnClick(R.id.fab)
    void setFab() {
        Intent editor = new Intent(getApplicationContext(), LecturerEditorActivity.class).putExtra("uid", getIntent().getStringExtra("uid"));
        startActivityForResult(editor, 2);
    }
}
