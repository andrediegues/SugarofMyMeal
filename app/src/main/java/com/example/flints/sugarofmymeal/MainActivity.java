package com.example.flints.sugarofmymeal;

import android.app.LauncherActivity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;
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
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    DataBaseHelper myDb;
    ImageButton addFoodToList;
    AutoCompleteTextView searchFood;
    ListView listOfFood;
    Button calculateSugar;
    ArrayList<String> foodList;
    ArrayList<FoodListItem> dataList;
    ArrayAdapter<String> listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);

        myDb = new DataBaseHelper(this);
        searchFood = (AutoCompleteTextView) findViewById(R.id.searchBar);
        foodList = new ArrayList<>();
        dataList = new ArrayList<>();
        listOfFood = (ListView) findViewById(R.id.listOfFoods);
        addFoodToList = (ImageButton) findViewById(R.id.addButton);
        addFoodToList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addFoodToList(view);
            }
        });
        calculateSugar = (Button) findViewById(R.id.calculateButton);
        calculateSugar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (foodList.isEmpty()){
                    Toast.makeText(MainActivity.this, "Selecione alimentos antes de fazer o cálculo", Toast.LENGTH_SHORT).show();
                    return;
                }
                calculateMealSugar(view);
            }
        });

        super.onCreate(savedInstanceState);
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
        String nameToAdd = searchFood.getText().toString();
        if (nameToAdd.isEmpty()) return;
        SQLiteDatabase db = myDb.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT " + DataBaseHelper.COL_GROUP + ", " + DataBaseHelper.COL_CH + ", " + DataBaseHelper.COL_ENERGYC
                        + ", " + DataBaseHelper.COL_ENERGYJ + ", " + DataBaseHelper.COL_LIPIDS + ", " + DataBaseHelper.COL_SAL
                        + ", " + DataBaseHelper.COL_PROT + ", " + DataBaseHelper.COL_COLESTEROL
                        + " FROM " + DataBaseHelper.TABLE_NAME
                        + " WHERE " + DataBaseHelper.COL_NAME + " = ? COLLATE NOCASE ORDER BY 1",
                new String[]{nameToAdd});
        if (!cursor.moveToFirst()) {
            Toast.makeText(MainActivity.this, "Selecione um alimento sugerido", Toast.LENGTH_LONG).show();
        } else {
            addItem(cursor, nameToAdd);
            listAdapter = new ArrayAdapter<String>(this, R.layout.list_item_header, R.id.food_name, foodList);
            listOfFood.setAdapter(listAdapter);
        }
        cursor.close();
    }

    private void addItem(Cursor cursor, String nameToAdd) {
        FoodListItem item;
        String[] components = new String[cursor.getColumnCount()];
        for (int i = 0; i < cursor.getColumnCount(); i++) {
            if (cursor.getString(i) != null) {
                components[i] = cursor.getString(i);
            }
        }
        item = new FoodListItem(nameToAdd, components);
        foodList.add(nameToAdd);
        dataList.add(item);
        searchFood.setText("");
    }

    public void calculateMealSugar(View view) {
        Intent i = new Intent(MainActivity.this, CalculateActivity.class);
        i.putExtra("dataList", dataList);
        startActivity(i);
    }

    public void removeFromList(View view) {
        int position = listOfFood.getPositionForView(view);
        String nameToDelete = foodList.get(position);
        foodList.remove(position);
        listAdapter.notifyDataSetChanged();
    }

}
