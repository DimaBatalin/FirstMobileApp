package com.example.my_first_project;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    // Возврат на главный экран без накопления экранов в стеке
    public static void open(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            if (v != null) {
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            }
            return insets;
        });

        Button btnMorning = findViewById(R.id.btnMorning);
        Button btnDay = findViewById(R.id.btnDay);
        Button btnEvening = findViewById(R.id.btnEvening);
        Button btnNight = findViewById(R.id.btnNight);

        btnMorning.setOnClickListener(v -> startActivity(new Intent(this, MorningActivity.class)));
        btnDay.setOnClickListener(v -> startActivity(new Intent(this, DayActivity.class)));
        btnEvening.setOnClickListener(v -> startActivity(new Intent(this, EveningActivity.class)));
        btnNight.setOnClickListener(v -> startActivity(new Intent(this, NightActivity.class)));

        // На Android 13+ для пуш-уведомлений нужно разрешение пользователя
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU
                && ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.POST_NOTIFICATIONS}, 1);
        }
    }
}
