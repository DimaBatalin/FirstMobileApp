package com.example.my_first_project;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private ImageButton Ibtn;
    private ImageView img;
    private Button btn;
    private TextView textViewName;
    private TextView textViewGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        btn = findViewById(R.id.button);
        textViewName = findViewById(R.id.name);
        textViewGroup = findViewById(R.id.group);
        Ibtn = findViewById(R.id.imageButton);
        img = findViewById(R.id.imageView);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            if (v != null) {
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            }
            return insets;
        });
    }

    public void btnClick(View view) {
        String currentText = btn.getText().toString();

        if ("Скрыть".equals(currentText)) {
            textViewGroup.setVisibility(INVISIBLE);
            textViewName.setVisibility(INVISIBLE);
            btn.setText("Показать");
        } else {
            textViewGroup.setVisibility(VISIBLE);
            textViewName.setVisibility(VISIBLE);
            btn.setText("Скрыть");
        }
    }

    public void switchVisible(View view) {
        if (img.getVisibility() == VISIBLE) {
            img.setVisibility(INVISIBLE);
        }
        else {
            img.setVisibility(VISIBLE);
        }
    }
}
