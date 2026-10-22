package com.example.my_first_project;

import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class NightActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_night);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button btnNoText = findViewById(R.id.btnNoText);
        Button btnBack = findViewById(R.id.btnBack);

        btnNoText.setOnClickListener(v ->
            new AlertDialog.Builder(this)
                    .setTitle("Вопрос")
                    .setMessage("Ты спишь?")
                    .setPositiveButton("Да", (dialog, which) -> finishAffinity())
                    .setNegativeButton("Нет", (dialog, which) -> MainActivity.open(this))
                    .show()
        );

        btnBack.setOnClickListener(v -> MainActivity.open(this));
    }
}
