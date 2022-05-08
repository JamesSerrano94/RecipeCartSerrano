package com.example.recipecartnew;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.Settings;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "RecipeCart.db";
    public static final String USER_TABLE = "User_table";
    public static final String UCOL_1 = "Username";
    public static final String STRING_type = "String";
    public static final String UCOL_2 = "Name";
    public static final String PANTRY_TABLE = "Pantry_table";
    public static final String PCOL_1 = "ID";
    public static final String PCOL_2 = "Ingredient";
    public static final String PCOL_3 = "amount";
    public static final String INT_type = "Integer";
    public static final String PCOL_4= "unit";
    public static final String DATABASE_GLOBAL_RECIPE = "GlobalRecipe.db";
    public static final String GlobalRecipe_TABLE = "GlobalRecipe_table";
    public static final String DATABASE_USER_RECIPE = "UserRecipe.db";
    public static final String UserRecipe_TABLE = "UserRecipe_table";
    private static final String RCOL1 = "Rno";
    private static final String RCOL2 = "Title";
    private static final String RCOL3 = "Ingredients";
    private static final String RCOL4 = "Instructions";
    private static final String RCOL5 = "Image_name";
    private static final String RCOL6 = "Description";
    private static final String RUOL = "username";
    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME,null,1);
        //creates database and table

    }


    /**
     * Tables for the database
     * User Table holds username(PK) and the user's name
     * Pantry Table: items user adds to their pantry
        Holds: Username+item name(CK), amounts, and units
     * Global Recipe Table: recipes available to all users.
        Holds: Recipe number(PK), title, ingredients, instructions, image
     * User Recipe Table: recipes added by user
        Holds: Username (FK), title, ingredients, instructions, image
     * @param sqLiteDatabase
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(new StringBuilder().append("CREATE TABLE ").append(USER_TABLE)
                .append("(").append(UCOL_1).append(" ").append(STRING_type)
                .append(" PRIMARY KEY, ").append(UCOL_2).append(" ").append(STRING_type).append(")").toString());
        sqLiteDatabase.execSQL(new StringBuilder().append("CREATE TABLE ").append(PANTRY_TABLE)
                .append("(").append(PCOL_1).append(" ").append(STRING_type)
                .append(", ").append(PCOL_2).append(" ").append(STRING_type)
                .append(", ").append(PCOL_3).append(" ").append(INT_type)
                .append(", ").append(PCOL_4).append(" ").append(STRING_type)
                .append(", ").append("UNIQUE(").append(PCOL_1).append(",").append(PCOL_2)
                .append(")").append(", foreign key (").append(PCOL_1)
                .append(") REFERENCES USER_TABLE (").append(UCOL_1).append(")").append(")").toString());
        sqLiteDatabase.execSQL(new StringBuilder().append("CREATE TABLE ").append(GlobalRecipe_TABLE)
                .append("(").append(RCOL1).append(" ").append(INT_type).append(" PRIMARY KEY AUTOINCREMENT, ")
                        .append(RCOL2).append(" ").append(STRING_type).append(", ")
                        .append(RCOL3).append(" ").append(STRING_type).append(", ")
                        .append(RCOL4).append(" ").append(STRING_type).append(", ")
                        .append(RCOL5).append(" ").append(INT_type)
                .append(")").toString());
        sqLiteDatabase.execSQL(new StringBuilder().append("CREATE TABLE ").append(UserRecipe_TABLE)
                .append("(").append(RUOL).append(" ").append(STRING_type).append(", ")
                .append(RCOL2).append(" ").append(STRING_type).append(", ")
                .append(RCOL3).append(" ").append(STRING_type).append(", ")
                .append(RCOL4).append(" ").append(STRING_type)
                .append(", ").append(RCOL5).append(" ").append(INT_type)
                .append(", foreign key (").append(RUOL)
                .append(") REFERENCES USER_TABLE (").append(UCOL_1).append(")").append(")").toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+USER_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+PANTRY_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+GlobalRecipe_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+UserRecipe_TABLE);
        onCreate(sqLiteDatabase);
    }

    /**
     * Drop All Database Tables.
     */
    public void dropTables(){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+USER_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+PANTRY_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+GlobalRecipe_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+UserRecipe_TABLE);
        onCreate(sqLiteDatabase);
    }

    /**
     * Insert data to the User Table
     * @param username unique String initialized when user registers.
     * @param name String initialized when user registers can be changed by user.
     * @return true if data was inserted, false otherwise.
     */
    public boolean insertDataUser(String username, String name){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(UCOL_1,username);
        contentValues.put(UCOL_2,name);
        long result = sqLiteDatabase.insert(USER_TABLE,null,contentValues);
        if(result==-1) {
            return false;
        }
        return true;
    }

    /**
     * Update user's name in the database.
     * @param username String that is Primary Key to locate the tuple in table.
     * @param name New String for use's name
     * @return true if user was able to check their name, false otherwise.
     */
    public boolean updateUserData(String username, String name){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PCOL_1,username);
        contentValues.put(PCOL_2,name);

        sqLiteDatabase.update(PANTRY_TABLE,contentValues,PCOL_1 +" = ?",new String[] {username});
        return true;
    }


    public boolean insertDataPantry(String username, String ingredient, double amount,String unit){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PCOL_1,username);
        contentValues.put(PCOL_2,ingredient);
        contentValues.put(PCOL_3,amount);
        contentValues.put(PCOL_4,unit);
        long result = sqLiteDatabase.insert(PANTRY_TABLE,null,contentValues);
        if(result==-1) {
            return false;
        }
        return true;

    }
    public boolean insertDataUserRecipe(String username, String title, String ingredient, String instructions, String image){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(RUOL, username);
        contentValues.put(RCOL2,title);
        contentValues.put(RCOL3,ingredient);
        contentValues.put(RCOL4,instructions);
        contentValues.put(RCOL5,image);
        long result = sqLiteDatabase.insert(UserRecipe_TABLE,null,contentValues);
        if(result==-1) {
            return false;
        }
        return true;
    }
    public boolean insertDataGlobalRecipe(String title, String ingredient, String Instructions, int image){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(RCOL2,title);
        contentValues.put(RCOL3,ingredient);
        contentValues.put(RCOL4,Instructions);
        contentValues.put(RCOL5,image);
        long result = sqLiteDatabase.insert(GlobalRecipe_TABLE,null,contentValues);
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
            itemDescription item = new itemDescription(res.getString(1),res.getDouble(2),res.getString(3));
            pantryData.add(item);
        }
        sqLiteDatabase.close();
        return pantryData;
    }
    public List<recipeDescription> getAllUserRecipes(String username){
        List<recipeDescription> recipes = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor res = db.rawQuery("select * from "+UserRecipe_TABLE+" WHERE "+RUOL + "=\""+username+"\";",null);
        try {
            while (res.moveToNext()){

                recipeDescription recipe = new recipeDescription(res.getString(1),res.getString(2),res.getString(3));
                recipes.add(recipe);
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to get posts from database");
        }
        return recipes;
    }
    public List<recipeDescription> getAllGlobalRecipes(){
        List<recipeDescription> recipes = new ArrayList<>();
        // "getReadableDatabase()" and "getWriteableDatabase()" return the same object (except under low
        // disk space scenarios)
        SQLiteDatabase db = getReadableDatabase();
        Cursor res = db.rawQuery("select * from "+GlobalRecipe_TABLE, null);
        try {
            while (res.moveToNext()){

                recipeDescription recipe = new recipeDescription(res.getString(1),res.getString(2),res.getString(3),res.getInt(4));
                recipes.add(recipe);
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to get posts from database");
        }
        return recipes;
    }
    public List<recipeDescription> getRecommendedRecipes(String username){
        List<recipeDescription> recipes = new ArrayList<>();
        List<recipeDescription> Userrecipes = getAllUserRecipes(username);
        List<recipeDescription> GlobalRecipes = getAllGlobalRecipes();
        ArrayList<itemDescription> pantryItems = getAllPantryData(username);
        for(int i = 0; i < pantryItems.size(); i++) {
            for(int j = 0; j < GlobalRecipes.size(); j++){
                if(GlobalRecipes.get(j).getIngredients().contains(pantryItems.get(i).getName())){
                    recipes.add(GlobalRecipes.get(j));
                }
            }
        }
        for(int i = 0; i < pantryItems.size(); i++) {
           for(int j = 0; j < Userrecipes.size(); j++){
               if(Userrecipes.get(j).getIngredients().contains(pantryItems.get(i).getName())){
                   if(!recipes.contains(Userrecipes.get(j))) {
                       recipes.add(Userrecipes.get(j));
                   }
               }
           }
        }

        return recipes;
    }


    public boolean updatePantryData(String username, String ingredient, double amount,String unit){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PCOL_1,username);
        contentValues.put(PCOL_2,ingredient);
        contentValues.put(PCOL_3,amount);
        contentValues.put(PCOL_4,unit);
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
