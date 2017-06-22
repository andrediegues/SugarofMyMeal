package com.example.flints.sugarofmymeal;

import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    DataBaseHelper myDb;
    ImageButton addFoodToList;
    EditText searchFood;
    ListView listOfFood;
    Button calculateSugar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDb = new DataBaseHelper(this);
        searchFood = (EditText) findViewById(R.id.searchBar);
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
        Toast.makeText(MainActivity.this, "Database imported", Toast.LENGTH_LONG).show();
    }

    private void importCSVtoSQLiteDB(DataBaseHelper myDb) throws IOException {
        File csvFile = new File("FoodComp.csv");

        BufferedReader buffer = new BufferedReader(new FileReader(csvFile));
        String line = "";

        while ((line = buffer.readLine()) != null) {
            String[] str = line.split(",");
            boolean isInserted = myDb.insertData(str);
            if(!isInserted) Toast.makeText(MainActivity.this, "Insertion failed on " + str[0], Toast.LENGTH_LONG).show();
        }
    }

    public void addFoodToList(View view) {
        String nameToQuery = searchFood.getText().toString();
        SQLiteDatabase db = myDb.getReadableDatabase();
        //if(db.isDatabaseIntegrityOk()) Toast.makeText(MainActivity.this, "Integraty ok" , Toast.LENGTH_LONG).show();

        Cursor cursor = db.rawQuery("SELECT " + DataBaseHelper.COL_NAME + " FROM " + DataBaseHelper.TABLE_NAME
                + " WHERE " + DataBaseHelper.COL_NAME + "=?", new String[] {"Banana"});
        //query funciona mas nao apresenta nada, investigar como apresentar o resultado da query na listview
        //if (cursor.toString() > 0)
        //Log.i("cursor",cursor.getString(1));
       // else
       // Toast.makeText(MainActivity.this, "nao tem first", Toast.LENGTH_LONG).show();
    }

    public void calculateMealSugar(View view) {

    }

}
