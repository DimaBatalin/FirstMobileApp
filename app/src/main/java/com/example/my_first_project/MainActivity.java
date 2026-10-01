package com.example.my_first_project;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private EditText editTextNum1;
    private EditText editTextNum2;
    private TextView textViewSign;
    private TextView textViewResult;
    private TextView testTextView;

    private Button btnPlus;
    private Button btnMinus;
    private Button btnMultiply;
    private Button btnDivide;
    private Button btnClear;

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

        editTextNum1 = findViewById(R.id.editTextNumberDecimal);
        editTextNum2 = findViewById(R.id.editTextNumberDecimal2);
        textViewSign = findViewById(R.id.textViewSign);
        textViewResult = findViewById(R.id.textViewResult);
        testTextView = findViewById(R.id.testTextView);

        btnPlus = findViewById(R.id.btnPlus);
        btnMinus = findViewById(R.id.btnMinus);
        btnMultiply = findViewById(R.id.btnMultiply);
        btnDivide = findViewById(R.id.btnDivide);
        btnClear = findViewById(R.id.btnClear);

        btnPlus.setOnClickListener(v -> calculate('+'));
        btnMinus.setOnClickListener(v -> calculate('-'));
        btnMultiply.setOnClickListener(v -> calculate('*'));
        btnDivide.setOnClickListener(v -> calculate('/'));

        btnClear.setOnClickListener(v -> clearAll());
    }

    private void calculate(char operation) {
        textViewSign.setText(String.valueOf(operation));

        String str1 = editTextNum1.getText().toString().trim();
        String str2 = editTextNum2.getText().toString().trim();

        if (TextUtils.isEmpty(str1) || TextUtils.isEmpty(str2)) {
            Toast.makeText(this, "Введите оба числа", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            double num1 = Double.parseDouble(str1);
            double num2 = Double.parseDouble(str2);
            double result = 0;

            switch (operation) {
                case '+':
                    result = num1 + num2;
                    break;
                case '-':
                    result = num1 - num2;
                    break;
                case '*':
                    result = num1 * num2;
                    break;
                case '/':
                    if (num2 == 0) {
                        Toast.makeText(this, "Деление на ноль невозможно!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    result = num1 / num2;
                    break;
            }

            if (result == (long) result) {
                textViewResult.setText("= " + (long) result);
            } else {
                textViewResult.setText("= " + result);
            }

        } catch (NumberFormatException e) {
            Toast.makeText(this, "Ошибка ввода числа", Toast.LENGTH_SHORT).show();
        }
    }

    private void clearAll() {
        editTextNum1.setText("");
        editTextNum2.setText("");
        textViewSign.setText("+");
        textViewResult.setText("= 0");
    }
}
