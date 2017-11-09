package com.mgilangjanuar.dev.civitasearch.modules.student.view;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.mgilangjanuar.dev.civitasearch.R;
import com.mgilangjanuar.dev.civitasearch.base.BaseActivity;
import com.mgilangjanuar.dev.civitasearch.base.BaseFragment;
import com.mgilangjanuar.dev.civitasearch.modules.main.view.MainActivity;
import com.mgilangjanuar.dev.civitasearch.modules.school.model.ProgramModel;
import com.mgilangjanuar.dev.civitasearch.modules.student.adapter.StudentSearchAdapter;
import com.mgilangjanuar.dev.civitasearch.modules.student.listener.ProgramListener;
import com.mgilangjanuar.dev.civitasearch.modules.student.listener.StudentSearchListener;
import com.mgilangjanuar.dev.civitasearch.modules.student.presenter.StudentSearchPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


public class StudentSearchFragment extends BaseFragment implements ProgramListener, StudentSearchListener {

    @BindView(R.id.program_search)
    Spinner programSelect;

    @BindView(R.id.student_search)
    EditText studentSearch;

    @BindView(R.id.submit)
    Button submit;

    @BindView(R.id.students)
    RecyclerView students;

    private List<String> programs = new ArrayList<>();
    private List<ProgramModel> programModelList;
    private ProgressDialog progressDialog;
    private StudentSearchPresenter presenter;

    public static StudentSearchFragment newInstance() {
        return new StudentSearchFragment();
    }

    @Override
    public void initialize(@Nullable Bundle savedInstanceState) {
        super.initialize(savedInstanceState);
        presenter = new StudentSearchPresenter(((MainActivity) getActivity()).schoolId, this, this);

        ((BaseActivity) getActivity()).setupRecyclerView(students);

        programs.add(getString(R.string.select_all));
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, programs);
        programSelect.setAdapter(adapter);

        studentSearch.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    submit.performClick();
                    return true;
                }
                return false;
            }
        });

        presenter.searchPrograms();
    }

    @Override
    public int findLayout() {
        return R.layout.fragment_student_search;
    }

    @OnClick(R.id.submit)
    public void onSubmit() {
        ((BaseActivity) getActivity()).hideKeyboard();
        String student = studentSearch.getText().toString().trim();

        if (TextUtils.isEmpty(student)) {
            ((BaseActivity) getActivity()).showSnackbar(getString(R.string.cannot_empty));
        } else {
            progressDialog = ((BaseActivity) getActivity()).buildLoadingDialog();
            progressDialog.show();

            if (getString(R.string.select_all).equals(String.valueOf(programSelect.getSelectedItem()))) {
                presenter.searchStudents(student);
            } else {
                presenter.searchStudentsWithProgram(String.valueOf(programSelect.getSelectedItem()), student, programModelList);
            }
        }
    }

    @Override
    public void onRetrievePrograms(List<ProgramModel> list) {
        if (getContext() != null) {
            programModelList = list;
            for (ProgramModel program : list) {
                programs.add(program.getName());
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, programs);
            programSelect.setAdapter(adapter);
        }
    }

    @Override
    public void onRetrieveStudents(StudentSearchAdapter adapter) {
        progressDialog.cancel();
        if (adapter.getItemCount() == 0) {
            ((BaseActivity) getActivity()).showSnackbar(getString(R.string.empty_result));
        } else {
            students.setAdapter(adapter);
        }
    }

    @Override
    public void onError(String error) {
        if (progressDialog != null && progressDialog.isShowing()) progressDialog.cancel();
        if (getActivity() != null) {
            ((BaseActivity) getActivity()).showSnackbar(error);
        }
    }
}
