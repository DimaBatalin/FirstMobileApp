package com.example.my_first_project;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

public class TestFragment extends Fragment {

    private static final String[] ANSWERS = {"Service", "Activity", "BroadcastReceiver", "ContentProvider"};
    private static final int CORRECT_ANSWER = 1;

    private int selected = -1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_test, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Button btnAnswer = view.findViewById(R.id.btnAnswer);
        btnAnswer.setOnClickListener(v -> showAnswerDialog());
    }

    private void showAnswerDialog() {
        selected = -1;
        new AlertDialog.Builder(requireContext())
                .setTitle(R.string.test_dialog_title)
                .setSingleChoiceItems(ANSWERS, -1, (dialog, which) -> selected = which)
                .setPositiveButton(R.string.ok, (dialog, which) -> {
                    int message;
                    if (selected == -1) {
                        message = R.string.test_no_choice;
                    } else if (selected == CORRECT_ANSWER) {
                        message = R.string.test_correct;
                    } else {
                        message = R.string.test_wrong;
                    }
                    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton(R.string.cancel, null)
                .show();
    }
}
