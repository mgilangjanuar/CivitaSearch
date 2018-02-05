package com.mgilangjanuar.dev.civitasearch.modules.student.view;

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
import com.mgilangjanuar.dev.civitasearch.modules.student.adapter.StudentDetailAdapter;
import com.mgilangjanuar.dev.civitasearch.modules.student.listener.StudentDetailListener;
import com.mgilangjanuar.dev.civitasearch.modules.student.presenter.StudentDetailPresenter;
import com.mgilangjanuar.dev.civitasearch.util.BitmapStringTransform;
import com.mgilangjanuar.dev.civitasearch.util.LocalStorage;
import com.mgilangjanuar.dev.civitasearch.util.PermissionPerformer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class StudentDetailActivity extends BaseActivity implements StudentDetailListener {

    @BindView(R.id.student_image)
    CircleImageView studentImage;

    @BindView(R.id.edit_image)
    ImageButton editImage;

    @BindView(R.id.details)
    RecyclerView details;

    @BindView(R.id.contacts)
    RecyclerView contacts;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    private ProgressDialog progressDialog;
    private StudentDetailPresenter presenter = new StudentDetailPresenter(this);

    @Override
    public int findLayout() {
        return R.layout.activity_student_detail;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle(getString(R.string.student_detail));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        progressDialog = buildLoadingDialog();
        progressDialog.show();

        setupRecyclerView(details);
        setupRecyclerView(contacts);

        presenter.getStudent(getIntent().getStringExtra("uid"));
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onSuccess(StudentDetailAdapter adapter) {
        progressDialog.cancel();
        details.setAdapter(adapter);
        details.setNestedScrollingEnabled(false);

        loadDataFromStorage();
    }

    @Override
    public void onError(String error) {
        progressDialog.cancel();
        showSnackbar(error);
    }

    private void loadDataFromStorage() {
        List<String> phoneList = LocalStorage.select(this, getIntent().getStringExtra("uid"), "Phone");
        List<String> emailList = LocalStorage.select(this, getIntent().getStringExtra("uid"), "Email");
        List<String> addressList = LocalStorage.select(this, getIntent().getStringExtra("uid"), "Address");
        List<String> photo = LocalStorage.select(this, getIntent().getStringExtra("uid"), "Photo");

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

        if (photo.get(0) != null) {
            Bitmap image = BitmapStringTransform.toBitmap(photo.get(0));
            studentImage.setImageBitmap(image);
        }

        contacts.setAdapter(new StudentDetailAdapter(listAll, this));
        contacts.setNestedScrollingEnabled(false);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 0) {
                Bitmap image = (Bitmap) data.getExtras().get("data");
                String encoded = BitmapStringTransform.toString(image);

                LocalStorage.insert(this, getIntent().getStringExtra("uid"), "Photo", encoded);
                studentImage.setImageBitmap(BitmapStringTransform.toBitmap(encoded));
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
                studentImage.setImageBitmap(BitmapStringTransform.toBitmap(encoded));
            }
            else if (requestCode == 2)
                loadDataFromStorage();
        }
    }

    @OnClick(R.id.fab)
    void setFab() {
        Intent intent = new Intent(getApplicationContext(), StudentEditorActivity.class).putExtra("uid", getIntent().getStringExtra("uid"));
        startActivityForResult(intent, 2);
    }

    @OnClick(R.id.edit_image)
    void setEditImage() {
        final CharSequence[] OPTIONS = {"Take photo", "Choose from Gallery", "Remove"};

        AlertDialog.Builder menuOption = new AlertDialog.Builder(StudentDetailActivity.this);
        menuOption.setItems(OPTIONS, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                switch (i) {
                    case 0:
                        if (PermissionPerformer.checkPermissionCamera(StudentDetailActivity.this)) {
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(intent, 0);
                        }
                        break;
                    case 1:
                        if (PermissionPerformer.checkPermissionExternalStorage(StudentDetailActivity.this)) {
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
                        break;
                }
            }
        });
        menuOption.show();
    }
}
