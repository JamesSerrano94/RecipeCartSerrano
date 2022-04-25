package com.example.recipecartnew;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "RecipeCart.db";
    public static final String USER_TABLE = "User_table";
    public static final String UCOL_1 = "Username";
    public static final String UCOL_1_type = "String";
    public static final String UCOL_2 = "Password";
    public static final String UCOL_2_type = "String";
    public static final String PANTRY_TABLE = "Pantry_table";
    public static final String PCOL_1 = "ID";
    public static final String PCOL_1_type = "String";
    public static final String PCOL_2 = "Ingredient";
    public static final String PCOL_2_type = "String";
    public static final String PCOL_3 = "amount";
    public static final String PCOL_3_type = "Integer";
    public static final String RECIPE_TABLE= "Recipe_table";
    public static final String RCOL_1 = "ID";
    public static final String RCOL_2 = "Recipe";
    public static final String RCOL_1_type = "String";
    public static final String RCOL_2_type = "String";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME,null,1);
        //creates database and table

    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(new StringBuilder().append("CREATE TABLE ").append(USER_TABLE)
                .append("(").append(UCOL_1).append(" ").append(UCOL_1_type)
                .append(" PRIMARY KEY, ").append(UCOL_2).append(" ").append(UCOL_2_type).append(")").toString());
        sqLiteDatabase.execSQL(new StringBuilder().append("CREATE TABLE ").append(PANTRY_TABLE)
                .append("(").append(PCOL_1).append(" ").append(PCOL_1_type)
                .append(", ").append(PCOL_2).append(" ").append(PCOL_2_type)
                .append(", ").append(PCOL_3).append(" ").append(PCOL_3_type)
                .append(", ").append("UNIQUE(").append(PCOL_1).append(",").append(PCOL_2)
                .append(")").append(", foreign key (").append(PCOL_1)
                .append(") REFERENCES USER_TABLE (").append(UCOL_1).append(")").append(")").toString());
        sqLiteDatabase.execSQL(new StringBuilder().append("CREATE TABLE ").append(RECIPE_TABLE)
                .append("(").append(RCOL_1).append(" ").append(RCOL_1_type)
                .append(" UNIQUE, ").append(RCOL_2).append(" ").append(RCOL_2_type)
                .append(" UNIQUE").append(")").toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+USER_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+PANTRY_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+RECIPE_TABLE);
        onCreate(sqLiteDatabase);
    }
    public boolean insertDataUser(String username, String password){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(UCOL_1,username);
        contentValues.put(UCOL_2,password);
        long result = sqLiteDatabase.insert(USER_TABLE,null,contentValues);
        if(result==-1) {
            return false;
        }
        return true;

    }
    public boolean insertDataPantry(String username, String ingredient, double amount){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PCOL_1,username);
        contentValues.put(PCOL_2,ingredient);
        contentValues.put(PCOL_3,amount);
        long result = sqLiteDatabase.insert(PANTRY_TABLE,null,contentValues);
        if(result==-1) {
            return false;
        }
        return true;

    }
    public void dropTables(){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+USER_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+PANTRY_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+RECIPE_TABLE);
        onCreate(sqLiteDatabase);
    }
    public boolean insertDataRecipe(String username, String ingredient){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PCOL_1,username);
        contentValues.put(PCOL_2,ingredient);
        long result = sqLiteDatabase.insert(PANTRY_TABLE,null,contentValues);
        if(result==-1) {
            return false;
        }
        return true;

    }


    public ArrayList<itemDescription> getAllPantryData(String username){

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        //Cursor res = sqLiteDatabase.rawQuery("SELECT * from "+ PANTRY_TABLE+ " WHERE " + PCOL_1 + "=" + username,null);
        ArrayList<itemDescription> pantryData  = new ArrayList<>();
        Cursor res = sqLiteDatabase.rawQuery("SELECT * from "+ PANTRY_TABLE+" WHERE "+PCOL_1 + "=\""+username+"\";",null);
        while (res.moveToNext()){
            itemDescription item = new itemDescription(res.getString(1),res.getDouble(2),"");
            pantryData.add(item);
        }
        sqLiteDatabase.close();
        return pantryData;
    }


    public boolean updatePantryData(String username, String ingredient, double amount){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PCOL_1,username);
        contentValues.put(PCOL_2,ingredient);
        contentValues.put(PCOL_3,amount);
        sqLiteDatabase.update(PANTRY_TABLE,contentValues,PCOL_1 +" = ? AND "+PCOL_2+"= ?",new String[] {username,ingredient});
        return true;
    }
    //used only if amount reached zero, else the updatePantryData will be called
    public Integer deletePantryData(String username, String ingredient){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        return sqLiteDatabase.delete(PANTRY_TABLE,PCOL_1+" = ? AND "+PCOL_2 + " = ?", new String[]{username,ingredient});
    }
    public Integer clearUserPantry(String username){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        return sqLiteDatabase.delete(PANTRY_TABLE,PCOL_1+" = ?", new String[]{username});
    }
}
