package com.example.my_first_project;

import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class FeedbackFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_feedback, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        TextInputLayout layoutName = view.findViewById(R.id.layoutName);
        TextInputLayout layoutEmail = view.findViewById(R.id.layoutEmail);
        TextInputEditText editName = view.findViewById(R.id.editName);
        TextInputEditText editEmail = view.findViewById(R.id.editEmail);
        Button btnSend = view.findViewById(R.id.btnSend);

        btnSend.setOnClickListener(v -> {
            String name = String.valueOf(editName.getText()).trim();
            String email = String.valueOf(editEmail.getText()).trim();

            layoutName.setError(name.isEmpty() ? getString(R.string.feedback_error_name) : null);
            boolean emailValid = Patterns.EMAIL_ADDRESS.matcher(email).matches();
            layoutEmail.setError(emailValid ? null : getString(R.string.feedback_error_email));
            if (name.isEmpty() || !emailValid) {
                return;
            }

            editName.setText("");
            editEmail.setText("");
            editName.clearFocus();
            editEmail.clearFocus();
            InputMethodManager imm = requireContext().getSystemService(InputMethodManager.class);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

            new AlertDialog.Builder(requireContext())
                    .setTitle(R.string.feedback_dialog_title)
                    .setMessage(getString(R.string.feedback_dialog_message, name, email))
                    .setPositiveButton(R.string.ok, null)
                    .show();
        });
    }
}
