package com.example.flints.sugarofmymeal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Locale;

import static android.provider.BaseColumns._ID;

public class DataBaseHelper extends SQLiteOpenHelper{

    public static final String DATABASE_NAME = "foodcomp.db";
    public static final String TABLE_NAME = "food_data";

    public static final String COL_NAME = "Nome";
    public static final String COL_GROUP = "Grupo";
    public static final String COL_ENERGYJ = "EnergiaJ";
    public static final String COL_ENERGYC = "EnergiaC";
    public static final String COL_LIPIDS = "Lípidos";
    public static final String COL_AGS = "Ácidos_gordos_saturados";
    public static final String COL_AGM = "Ácidos_gordos_monoinsaturados";
    public static final String COL_AGP = "Ácidos_gordos_polinsaturados";
    public static final String COL_AL = "Ácido_linoleico";
    public static final String COL_CH = "Hidratos_de_carbono";
    public static final String COL_OLIGO = "Oligossacáridos";
    public static final String COL_SACAROSE = "Sacarose";
    public static final String COL_LACTOSE = "Lactose";
    public static final String COL_AMID = "Amido";
    public static final String COL_SAL = "Sal";
    public static final String COL_FIBER = "Fibra_alimentar";
    public static final String COL_PROT = "Proteína";
    public static final String COL_ALCOHOL = "Álcool";
    public static final String COL_WATER = "Água";
    public static final String COL_AORG = "Ácidos_orgânicos";
    public static final String COL_COLESTEROL = "Colesterol";
    public static final String COL_VITAA = "Vitamina_A";
    public static final String COL_CAROT = "Caroteno";
    public static final String COL_VITAD = "Vitamina_D";
    public static final String COL_ATOCO = "a_tocoferol";
    public static final String COL_TIAMIN = "Tiamina";
    public static final String COL_RIBO = "Riboflavina";
    public static final String COL_E_NIACINA = "Equivalentes_de_niacina";
    public static final String COL_NIACINA = "Niacina";
    public static final String COL_VITAB6 = "Vitamina_B6";
    public static final String COL_VITAB12 = "Vitamina_B12";
    public static final String COL_VITAC = "Vitamina_C";
    public static final String COL_FOLAT = "Folatos";
    public static final String COL_CINZA = "Cinza";
    public static final String COL_SODIO = "Sódio";
    public static final String COL_POTASS = "Potássio";
    public static final String COL_CALCIO = "Cálcio";
    public static final String COL_FOSFORO = "Fósforo";
    public static final String COL_MAGNESIO = "Magnésio";
    public static final String COL_FERRO = "Ferro";
    public static final String COL_ZINC = "Zinco";

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String CREATE_SQLITE_DATABASE = "CREATE TABLE " + TABLE_NAME + " (" +
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_NAME + " TEXT NOT NULL," +
                COL_GROUP + " TEXT," +
                COL_ENERGYJ + " TEXT NOT NULL," +
                COL_ENERGYC + " TEXT NOT NULL," +
                COL_LIPIDS + " TEXT NOT NULL," +
                COL_AGS + " TEXT NOT NULL," +
                COL_AGM + " TEXT NOT NULL," +
                COL_AGP + " TEXT NOT NULL," +
                COL_AL + " TEXT NOT NULL," +
                COL_CH + " TEXT NOT NULL," +
                COL_OLIGO + " TEXT NOT NULL," +
                COL_SACAROSE + " TEXT NOT NULL," +
                COL_LACTOSE + " TEXT NOT NULL," +
                COL_AMID + " TEXT NOT NULL," +
                COL_SAL + " TEXT NOT NULL," +
                COL_FIBER + " TEXT NOT NULL," +
                COL_PROT + " TEXT NOT NULL," +
                COL_ALCOHOL + " TEXT NOT NULL," +
                COL_WATER + " TEXT NOT NULL," +
                COL_AORG + " TEXT NOT NULL," +
                COL_COLESTEROL + " TEXT NOT NULL," +
                COL_VITAA + " TEXT NOT NULL," +
                COL_CAROT + " TEXT NOT NULL," +
                COL_VITAD + " TEXT NOT NULL," +
                COL_ATOCO + " TEXT NOT NULL," +
                COL_TIAMIN + " TEXT NOT NULL," +
                COL_RIBO + " TEXT NOT NULL," +
                COL_E_NIACINA + " TEXT NOT NULL," +
                COL_NIACINA + " TEXT NOT NULL," +
                COL_VITAB6 + " TEXT NOT NULL," +
                COL_VITAB12 + " TEXT NOT NULL," +
                COL_VITAC + " TEXT NOT NULL," +
                COL_FOLAT + " TEXT NOT NULL," +
                COL_CINZA + " TEXT NOT NULL," +
                COL_SODIO + " TEXT NOT NULL," +
                COL_POTASS + " TEXT NOT NULL," +
                COL_CALCIO + " TEXT NOT NULL," +
                COL_FOSFORO + " TEXT NOT NULL," +
                COL_MAGNESIO + " TEXT NOT NULL," +
                COL_FERRO + " TEXT NOT NULL," +
                COL_ZINC + " TEXT NOT NULL);";

        sqLiteDatabase.execSQL(CREATE_SQLITE_DATABASE);
        sqLiteDatabase.setLocale(new Locale("pt_PT"));

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public boolean insertData(String[] str){
        if(str.length != 41) return false;

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_NAME, str[0]);
        contentValues.put(COL_GROUP, str[1]);
        contentValues.put(COL_ENERGYJ, str[2]);
        contentValues.put(COL_ENERGYC, str[3]);
        contentValues.put(COL_LIPIDS, str[4]);
        contentValues.put(COL_AGS, str[5]);
        contentValues.put(COL_AGM, str[6]);
        contentValues.put(COL_AGP, str[7]);
        contentValues.put(COL_AL, str[8]);
        contentValues.put(COL_CH, str[9]);
        contentValues.put(COL_OLIGO, str[10]);
        contentValues.put(COL_SACAROSE, str[11]);
        contentValues.put(COL_LACTOSE, str[12]);
        contentValues.put(COL_AMID, str[13]);
        contentValues.put(COL_SAL, str[14]);
        contentValues.put(COL_FIBER, str[15]);
        contentValues.put(COL_PROT, str[16]);
        contentValues.put(COL_ALCOHOL, str[17]);
        contentValues.put(COL_WATER, str[18]);
        contentValues.put(COL_AORG, str[19]);
        contentValues.put(COL_COLESTEROL, str[20]);
        contentValues.put(COL_VITAA, str[21]);
        contentValues.put(COL_CAROT, str[22]);
        contentValues.put(COL_VITAD, str[23]);
        contentValues.put(COL_ATOCO, str[24]);
        contentValues.put(COL_TIAMIN, str[25]);
        contentValues.put(COL_RIBO, str[26]);
        contentValues.put(COL_E_NIACINA, str[27]);
        contentValues.put(COL_NIACINA, str[28]);
        contentValues.put(COL_VITAB6, str[29]);
        contentValues.put(COL_VITAB12, str[30]);
        contentValues.put(COL_VITAC, str[31]);
        contentValues.put(COL_FOLAT, str[32]);
        contentValues.put(COL_CINZA, str[33]);
        contentValues.put(COL_SODIO, str[34]);
        contentValues.put(COL_POTASS, str[35]);
        contentValues.put(COL_CALCIO, str[36]);
        contentValues.put(COL_FOSFORO, str[37]);
        contentValues.put(COL_MAGNESIO, str[38]);
        contentValues.put(COL_FERRO, str[39]);
        contentValues.put(COL_ZINC, str[40]);

        Cursor cursor = db.rawQuery("SELECT Nome FROM food_data WHERE Nome=?", new String[]{str[0]});
        long result = 0;
        if (!cursor.moveToFirst())
            result = db.insert(TABLE_NAME, null, contentValues);
        cursor.close();
        return (result != -1);
    }
}
