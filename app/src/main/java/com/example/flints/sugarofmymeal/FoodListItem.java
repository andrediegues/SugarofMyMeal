package com.example.flints.sugarofmymeal;

import java.util.Scanner;

/**
 * Created by flints on 25/06/17.
 */

class FoodListItem implements java.io.Serializable{

    private String name;
    private String group;
    private String chValue;
    private String lipidsValue;
    private String caloriesValue;
    private String energyValue;
    private String saltValue;
    private String proteinValue;
    private String cholesterolValue;

    public FoodListItem(String nameToAdd, String[] components) {
        this.name = nameToAdd;
        this.group = components[0];
        this.chValue = components[1];
        this.caloriesValue = components[2];
        this.energyValue = components[3];
        this.lipidsValue = components[4];
        this.saltValue = components[5];
        this.proteinValue = components[6];
        this.cholesterolValue = components[7];
    }

    public String getName() {
        return name;
    }

    public String getGroup() {
        return group;
    }

    public String getChValue() {
        return chValue;
    }

    public String getLipidsValue() {
        return lipidsValue;
    }

    public String getEnergyValue() {
        return energyValue;
    }

    public String getCaloriesValue() {
        return caloriesValue;
    }

    public String getSaltValue() {
        return saltValue;
    }

    public String getProteinValue() {
        return proteinValue;
    }

    public String getCholesterolValue() {
        return cholesterolValue;
    }

    public float getFloatValueOfCH() {
        return new Float(this.getChValue().replaceAll("[^.0-9]", ""));
    }
}
