package com.example.my_first_project;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Готовая база данных лежит в assets/shop.db.
 * При первом запуске (или при смене DB_VERSION) она копируется во внутреннюю память приложения.
 */
public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "shop.db";
    // Если база в assets изменилась, нужно увеличить версию (и PRAGMA user_version в самой базе)
    private static final int DB_VERSION = 1;

    private final Context context;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context.getApplicationContext();
        copyDatabaseIfNeeded();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Таблицы уже есть в базе из assets
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Новая версия базы копируется из assets в copyDatabaseIfNeeded()
    }

    public List<Category> getCategories() {
        List<Category> categories = new ArrayList<>();
        try (Cursor cursor = getReadableDatabase().rawQuery(
                "SELECT _id, name FROM categories ORDER BY _id", null)) {
            while (cursor.moveToNext()) {
                categories.add(new Category(cursor.getLong(0), cursor.getString(1)));
            }
        }
        return categories;
    }

    public List<Product> getProducts(long categoryId) {
        List<Product> products = new ArrayList<>();
        try (Cursor cursor = getReadableDatabase().rawQuery(
                "SELECT name, description, price FROM products WHERE category_id = ? ORDER BY name",
                new String[]{String.valueOf(categoryId)})) {
            while (cursor.moveToNext()) {
                products.add(new Product(cursor.getString(0), cursor.getString(1), cursor.getInt(2)));
            }
        }
        return products;
    }

    private void copyDatabaseIfNeeded() {
        File dbFile = context.getDatabasePath(DB_NAME);
        if (dbFile.exists() && getFileVersion(dbFile) == DB_VERSION) {
            return;
        }

        File dir = dbFile.getParentFile();
        if (dir != null && !dir.exists() && !dir.mkdirs()) {
            throw new IllegalStateException("Не удалось создать папку для базы данных");
        }
        // Служебные файлы старой базы, если они остались
        for (String suffix : new String[]{"-journal", "-wal", "-shm"}) {
            new File(dbFile.getPath() + suffix).delete();
        }

        try (InputStream in = context.getAssets().open(DB_NAME);
             OutputStream out = new FileOutputStream(dbFile)) {
            byte[] buffer = new byte[8192];
            int length;
            while ((length = in.read(buffer)) > 0) {
                out.write(buffer, 0, length);
            }
        } catch (IOException e) {
            throw new IllegalStateException("Не удалось скопировать базу данных из assets", e);
        }
    }

    private static int getFileVersion(File dbFile) {
        try (SQLiteDatabase db = SQLiteDatabase.openDatabase(
                dbFile.getPath(), null, SQLiteDatabase.OPEN_READONLY)) {
            return db.getVersion();
        } catch (SQLiteException e) {
            return -1;
        }
    }
}
