package com.example.flints.sugarofmymeal;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by flints on 21/06/17.
 */

public class CalculateActivity extends AppCompatActivity{

    ArrayList<FoodListItem> foodList;
    CustomAdapter adapter;
    ListView detailedMealList;
    TextView totalChValue;
    Button restart;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_calculate);
        super.onCreate(savedInstanceState);

        foodList = (ArrayList<FoodListItem>) getIntent().getSerializableExtra("dataList");

        float value = 0;
        for (int i = 0; i < foodList.size(); i++){
            value += foodList.get(i).getFloatValueOfCH();
        }
        String totalResult = String.format("%.2f g", value);
        detailedMealList = (ListView) findViewById(R.id.detailed_meal_list);
        adapter = new CustomAdapter(this, R.layout.list_item_body, foodList);
        detailedMealList.setAdapter(adapter);
        totalChValue = (TextView) findViewById(R.id.final_ch_value);
        totalChValue.setText(totalResult);
        restart = (Button) findViewById(R.id.restart_button);
        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CalculateActivity.this, MainActivity.class);
                finish();
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
