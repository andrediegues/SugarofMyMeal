package com.example.flints.sugarofmymeal;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by flints on 25/06/17.
 */

public class ExpandableListAdapter extends BaseExpandableListAdapter {
    private Context context;
    private ArrayList<FoodListItem> foodList;
    private LayoutInflater inflater;

    public ExpandableListAdapter(Context context, ArrayList<FoodListItem> foodList) {
        this.context = context;
        this.foodList = foodList;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getGroupCount() {
        return foodList.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return 6;
    }

    @Override
    public Object getGroup(int i) {
        return foodList.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return foodList.get(i).get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {

        view = inflater.inflate(R.layout.list_item_header, null);

        TextView foodName = (TextView) view.findViewById(R.id.food_name);
        TextView foodGroup = (TextView) view.findViewById(R.id.food_group);
        TextView foodCH = (TextView) view.findViewById(R.id.food_ch_value);

        foodName.setText(foodList.get(i).getName());
        if (foodList.get(i).getGroup() != null) {
            foodGroup.setText(foodList.get(i).getGroup());
        }
        foodCH.setText(foodList.get(i).getChValue());
        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.list_item_body, null);


        TextView foodEnergy = (TextView) view.findViewById(R.id.energy_value);
        TextView foodCalories = (TextView) view.findViewById(R.id.caloric_value);
        TextView foodLipids = (TextView) view.findViewById(R.id.lipids_value);
        TextView foodSalt = (TextView) view.findViewById(R.id.salt_value);
        TextView foodProtein = (TextView) view.findViewById(R.id.protein_value);
        TextView foodCholesterol = (TextView) view.findViewById(R.id.cholesterol_value);

        foodEnergy.setText(foodList.get(i).getEnergyValue());
        foodCalories.setText(foodList.get(i).getCaloriesValue());
        foodLipids.setText(foodList.get(i).getLipidsValue());
        foodProtein.setText(foodList.get(i).getProteinValue());
        foodCholesterol.setText(foodList.get(i).getCholesterolValue());
        foodSalt.setText(foodList.get(i).getSaltValue());
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }
}
