package com.example.flints.sugarofmymeal;

import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {

    DataBaseHelper myDb;
    ImageButton addFoodToList;
    AutoCompleteTextView searchFood;
    ListView listOfFood;
    Button calculateSugar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDb = new DataBaseHelper(this);
        searchFood = (AutoCompleteTextView) findViewById(R.id.searchBar);
        addFoodToList = (ImageButton) findViewById(R.id.addButton);
        listOfFood = (ListView) findViewById(R.id.listOfFoods);
        calculateSugar = (Button) findViewById(R.id.calculateButton);

        try {
            importCSVtoSQLiteDB(myDb);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String[] food = getAllNamesFromDB();
        ArrayAdapter<String> listOfSuggestions = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, food);
        searchFood.setAdapter(listOfSuggestions);
    }

    private String[] getAllNamesFromDB() {
        SQLiteDatabase db = myDb.getReadableDatabase();

        int i = 0;

        Cursor cursorNames = db.rawQuery("SELECT " + DataBaseHelper.COL_NAME + " FROM " + DataBaseHelper.TABLE_NAME + " ORDER BY 1", new String[]{});
        int nNames = cursorNames.getCount();
        String[] names = new String[nNames];
        System.out.println(cursorNames.getCount());
        cursorNames.moveToFirst();
        while (!cursorNames.isAfterLast()) {
            if (cursorNames.getString(0) != null) {
                names[i] = cursorNames.getString(0);
                i++;
            }
            cursorNames.moveToNext();
        }
        cursorNames.close();
        return names;
    }

    private void importCSVtoSQLiteDB(DataBaseHelper myDb) throws IOException {
        AssetManager assetManager = getAssets();
        InputStream assetFile = assetManager.open("database/FoodComp.csv");

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(assetFile));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] str = line.split(",");
                boolean isInserted = myDb.insertData(str);
                if (!isInserted)
                    Toast.makeText(MainActivity.this, "Insertion failed on " + str[0], Toast.LENGTH_LONG).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }


    public void addFoodToList(View view) {
        String nameToQuery = searchFood.getText().toString();
        if (nameToQuery.isEmpty()) return;
        SQLiteDatabase db = myDb.getReadableDatabase();
        /*Cursor cursor = db.rawQuery("SELECT " + DataBaseHelper.COL_NAME + " FROM " + DataBaseHelper.TABLE_NAME
                + " WHERE " + DataBaseHelper.COL_NAME + " LIKE ? COLLATE LOCALIZED ORDER BY CASE\n" + "WHEN Nome LIKE ? THEN 1" +
                "    WHEN Nome LIKE ? THEN 3 ELSE 2 END",
                new String[]{"%" + nameToQuery + "%", nameToQuery + "%", "%" + nameToQuery});
        System.out.println(cursor.getCount());
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            if (cursor.getString(0) != null) {
                System.out.println(cursor.getString(0));
            }
            cursor.moveToNext();
        }
        cursor.close();*/
    }

    public void calculateMealSugar(View view) {

    }

}
