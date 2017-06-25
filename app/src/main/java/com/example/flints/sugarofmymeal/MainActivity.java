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

public class MainActivity extends AppCompatActivity implements ExpandableListView.OnChildClickListener {

    DataBaseHelper myDb;
    ImageButton addFoodToList;
    AutoCompleteTextView searchFood;
    ExpandableListView listOfFood;
    Button calculateSugar;
    ArrayList<FoodListItem> foodList;
    ExpandableListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);

        myDb = new DataBaseHelper(this);
        searchFood = (AutoCompleteTextView) findViewById(R.id.searchBar);
        addFoodToList = (ImageButton) findViewById(R.id.addButton);
        listOfFood = (ExpandableListView) findViewById(R.id.listOfFoods);
        calculateSugar = (Button) findViewById(R.id.calculateButton);
        foodList = new ArrayList<>();
        listOfFood.setOnChildClickListener(this);

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
        String nameToAdd = searchFood.getText().toString();
        if (nameToAdd.isEmpty()) return;
        SQLiteDatabase db = myDb.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + DataBaseHelper.COL_GROUP + ", " + DataBaseHelper.COL_CH + ", " + DataBaseHelper.COL_ENERGYC + ", " + DataBaseHelper.COL_ENERGYJ + ", " + DataBaseHelper.COL_LIPIDS + ", " + DataBaseHelper.COL_SAL + ", " + DataBaseHelper.COL_PROT + ", " + DataBaseHelper.COL_COLESTEROL + " FROM " + DataBaseHelper.TABLE_NAME
                        + " WHERE " + DataBaseHelper.COL_NAME + " = ? COLLATE NOCASE ORDER BY 1",
                new String[]{nameToAdd});
        //System.out.println(cursor.getCount());
        if (!cursor.moveToFirst()) {
            Toast.makeText(MainActivity.this, "Selecione um alimento sugerido", Toast.LENGTH_LONG).show();
        } else {
            addItem(cursor, nameToAdd);
            listAdapter = new ExpandableListAdapter(this, foodList);
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
                System.out.println(components[i]);
            }
        }
        item = new FoodListItem(nameToAdd, components);
        foodList.add(item);
        searchFood.setText("");
    }

    public void calculateMealSugar(View view) {

    }

    public void removeFromList(View view) {
        String nameToDelete = listOfFood.getExpandableListAdapter().getGroup(view.getId()).toString();
        System.out.println(nameToDelete);
        for (int i = 0; i < foodList.size(); i++) {
            System.out.println(foodList.get(i).getName());
            if (listAdapter.getGroup(i).toString().equals(nameToDelete)) {
                foodList.remove(i);
                listAdapter.notifyDataSetChanged();
                return;
            }
        }
    }

    @Override
    public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
        return false;
    }
}
