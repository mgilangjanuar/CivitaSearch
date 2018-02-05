package com.mgilangjanuar.dev.civitasearch.modules.student.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.mgilangjanuar.dev.civitasearch.R;
import com.mgilangjanuar.dev.civitasearch.base.BaseActivity;
import com.mgilangjanuar.dev.civitasearch.util.LocalStorage;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class StudentEditorActivity extends BaseActivity {

    @BindView(R.id.select_type)
    Spinner typeSelect;

    @BindView(R.id.data_content)
    EditText contentText;

    @BindView(R.id.save)
    Button save;

    @BindView(R.id.cancel)
    Button cancel;

    private List<String> types = new ArrayList<>();

    @Override
    public int findLayout() {
        return R.layout.activity_student_editor;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle(getString(R.string.add_new_info));
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);

        types.add("Phone");
        types.add("Email");
        types.add("Address");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, types);

        typeSelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    contentText.setText("");
                    contentText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_PHONE);
                }
                else if (i == 1) {
                    contentText.setText("");
                    contentText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_WEB_EMAIL_ADDRESS);
                }
                else if (i == 2) {
                    contentText.setText("");
                    contentText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_LONG_MESSAGE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
//                Do nothing
            }
        });

        typeSelect.setAdapter(adapter);
    }

    @OnClick(R.id.save)
    void saveCLick() {
        LocalStorage.insert(this, getIntent().getStringExtra("uid"), typeSelect.getSelectedItem().toString(), contentText.getText().toString());

        Intent returningBack = new Intent(this, StudentDetailActivity.class).putExtra("uid", getIntent().getStringExtra("uid"));
        setResult(Activity.RESULT_OK, returningBack);
        finish();
    }

    @OnClick(R.id.cancel)
    void cancelClick() {
        onBackPressed();
    }
}
