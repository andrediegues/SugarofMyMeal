package com.example.flints.sugarofmymeal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by flints on 02/07/17.
 */

public class CustomAdapter extends ArrayAdapter<FoodListItem> {

    public CustomAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public CustomAdapter(Context context, int resource, ArrayList<FoodListItem> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.list_item_body, null);
        }

        FoodListItem f = getItem(position);

        TextView name_n_group = (TextView) v.findViewById(R.id.name_n_group);
        TextView energy = (TextView) v.findViewById(R.id.energy_value);
        TextView calories = (TextView) v.findViewById(R.id.caloric_value);
        TextView protein = (TextView) v.findViewById(R.id.protein_value);
        TextView ch = (TextView) v.findViewById(R.id.ch_value);
        TextView lip = (TextView) v.findViewById(R.id.lipids_value);
        TextView choles = (TextView) v.findViewById(R.id.cholesterol_value);
        TextView salt = (TextView) v.findViewById(R.id.salt_value);

        if(!f.getGroup().equals("null")){
            name_n_group.setText(f.getName() + ", " + f.getGroup());
        }
        else{
            name_n_group.setText(f.getName());
        }

        energy.setText(f.getEnergyValue());
        calories.setText(f.getCaloriesValue());
        protein.setText(f.getProteinValue());
        ch.setText(f.getChValue());
        lip.setText(f.getLipidsValue());
        choles.setText(f.getCholesterolValue());
        salt.setText(f.getSaltValue());

        return v;
    }

}
