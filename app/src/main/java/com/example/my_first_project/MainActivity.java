package com.example.my_first_project;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonToggleGroup;

public class MainActivity extends AppCompatActivity {

    private DBHelper dbHelper;
    private ProductAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        dbHelper = new DBHelper(this);

        ListView productList = findViewById(R.id.productList);
        productList.setEmptyView(findViewById(R.id.emptyView));
        adapter = new ProductAdapter(this);
        productList.setAdapter(adapter);

        // Кнопки категорий создаются по данным из БД: сколько категорий, столько и кнопок
        MaterialButtonToggleGroup categoryGroup = findViewById(R.id.categoryGroup);
        for (Category category : dbHelper.getCategories()) {
            MaterialButton button = new MaterialButton(this, null,
                    com.google.android.material.R.attr.materialButtonOutlinedStyle);
            button.setId((int) category.getId());
            button.setText(category.getName());
            button.setMaxLines(1);
            int padding = getResources().getDimensionPixelSize(R.dimen.category_button_padding);
            button.setPaddingRelative(padding, button.getPaddingTop(), padding, button.getPaddingBottom());
            // Свободное место делится поровну, а если кнопки не влезают — ряд прокручивается
            categoryGroup.addView(button, new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
        }

        // id кнопки совпадает с id категории в БД.
        // После поворота экрана выбор кнопки восстанавливается и снова вызывает этот слушатель.
        categoryGroup.addOnButtonCheckedListener((group, checkedId, isChecked) -> {
            if (isChecked) {
                showProducts(checkedId);
            }
        });
    }

    private void showProducts(long categoryId) {
        adapter.clear();
        adapter.addAll(dbHelper.getProducts(categoryId));
    }

    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }
}
