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
    public static final String COL_CH = "Hidratos_de_carbono";
    public static final String COL_SAL = "Sal";
    public static final String COL_PROT = "Proteína";
    public static final String COL_COLESTEROL = "Colesterol";

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
                COL_CH + " TEXT NOT NULL," +
                COL_SAL + " TEXT NOT NULL," +
                COL_PROT + " TEXT NOT NULL," +
                COL_COLESTEROL + " TEXT NOT NULL);";

        sqLiteDatabase.execSQL(CREATE_SQLITE_DATABASE);
        sqLiteDatabase.setLocale(new Locale("pt_PT"));

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public boolean insertData(String[] str, SQLiteDatabase db){
        if(str.length != 9) return false;

        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_NAME, str[0]);
        contentValues.put(COL_GROUP, str[1]);
        contentValues.put(COL_ENERGYJ, str[2]);
        contentValues.put(COL_ENERGYC, str[3]);
        contentValues.put(COL_LIPIDS, str[4]);
        contentValues.put(COL_CH, str[5]);
        contentValues.put(COL_SAL, str[6]);
        contentValues.put(COL_PROT, str[7]);
        contentValues.put(COL_COLESTEROL, str[8]);

        Cursor cursor = db.rawQuery("SELECT Nome FROM food_data WHERE Nome=?", new String[]{str[0]});
        long result = 0;
        if (!cursor.moveToFirst())
            result = db.insert(TABLE_NAME, null, contentValues);
        cursor.close();
        return (result != -1);
    }

    public boolean isDataAlreadyInserted(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT count(*) FROM " + TABLE_NAME, new String[] {});
        if (!cursor.moveToFirst()){
            return false;
        }
        else{
            int rows = Integer.valueOf(cursor.getString(0));
            return rows == 1109;
        }
    }
}
