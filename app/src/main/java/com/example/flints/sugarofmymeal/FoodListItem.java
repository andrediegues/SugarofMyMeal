package com.example.flints.sugarofmymeal;

/**
 * Created by flints on 25/06/17.
 */

class FoodListItem {

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
        this.lipidsValue = components[2];
        this.caloriesValue = components[3];
        this.energyValue = components[4];
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

    public String get(int i) {
        switch (i) {
            case 0:
                getName();
                break;
            case 1:
                getGroup();
                break;
            case 2:
                getChValue();
                break;
            case 3:
                getLipidsValue();
                break;
            case 4:
                getCaloriesValue();
                break;
            case 5:
                getEnergyValue();
                break;
            case 6:
                getSaltValue();
                break;
            case 7:
                getProteinValue();
                break;
            case 8:
                getCholesterolValue();
                break;
            default:
                return null;
        }
        return null;
    }

    @Override
    public String toString() {
        return getName();
    }
}
