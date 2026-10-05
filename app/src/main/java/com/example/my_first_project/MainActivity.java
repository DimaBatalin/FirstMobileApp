package com.example.my_first_project;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private Button btn1;
    private Button btn2;
    private Button btn3;
    private Button btn4;

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

        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btn4 = findViewById(R.id.btn4);

        btn1.setOnClickListener(v ->
                Toast.makeText(MainActivity.this, "кнопка номер 1 нажата", Toast.LENGTH_SHORT).show()
        );

        btn2.setOnClickListener(v ->
                Toast.makeText(MainActivity.this, "кнопка номер 2 нажата", Toast.LENGTH_LONG).show()
        );

        btn3.setOnClickListener(v -> showDialog3());

        // Кнопка 4: Диалоговое окно с множественным выбором (Тест)
        btn4.setOnClickListener(v -> showDialogQuiz());
    }

    private void showDialog3() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("кнопка 3")
                .setIcon(R.drawable.test_icon)
                .setMessage("Вы действительно хотите изменить цвет текста кнопок?")
                .setPositiveButton("Да", (dialog, which) -> {
                    // "Да" - цвет текста всех кнопок делает красным
                    btn1.setTextColor(Color.RED);
                    btn2.setTextColor(Color.RED);
                    btn3.setTextColor(Color.RED);
                    btn4.setTextColor(Color.RED);
                    dialog.dismiss();
                })
                .setNegativeButton("Отмена", (dialog, which) -> {
                    dialog.dismiss();
                    Toast.makeText(MainActivity.this, "Окно закрыто", Toast.LENGTH_SHORT).show();
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showDialogQuiz() {
        String[] items = {"Заяц", "Волк", "Корова", "Тигр", "Олень"};
        boolean[] correctAnswers = {true, false, true, false, true};
        boolean[] selectedItems = new boolean[items.length];

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Выберите травоядных животных:")
                .setMultiChoiceItems(items, selectedItems, (dialog, which, isChecked) -> {
                    selectedItems[which] = isChecked;
                })
                .setPositiveButton("Проверить", (dialog, which) -> {
                    boolean isCorrect = true;
                    for (int i = 0; i < items.length; i++) {
                        if (selectedItems[i] != correctAnswers[i]) {
                            isCorrect = false;
                            break;
                        }
                    }

                    if (isCorrect) {
                        Toast.makeText(MainActivity.this, "Все верно!", Toast.LENGTH_SHORT).show();
                    } else {
                        btn1.setVisibility(View.INVISIBLE);
                        btn2.setVisibility(View.INVISIBLE);
                        btn3.setVisibility(View.INVISIBLE);
                        btn4.setVisibility(View.INVISIBLE);
                    }
                })
                .setNegativeButton("Отмена", (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
