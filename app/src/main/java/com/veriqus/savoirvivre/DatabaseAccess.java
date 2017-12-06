package com.veriqus.savoirvivre;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class DatabaseAccess {
    private SQLiteOpenHelper openHelper;

    private SQLiteDatabase database;
    private SQLiteDatabase databaseQuiz;
    private static DatabaseAccess instance;


    private DatabaseAccess(Context context) {
        this.openHelper = new DatabaseOpenHelper(context);

    }

    public static DatabaseAccess getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseAccess(context);
        }
        return instance;
    }

    public void open() {
        this.database = openHelper.getWritableDatabase();
        this.databaseQuiz = openHelper.getReadableDatabase();
        //database.execSQL("ALTER TABLE entry ADD COLUMN savedArticle INTEGER");
    }

    public void close() {
        if (database != null) {
            this.database.close();
            this.openHelper.close();
        }

    }
    //return user lang - needed for rest of queries
    private String getLang(){
        String lang = Locale.getDefault().getLanguage().toLowerCase();

        if (    Locale.getDefault().getLanguage().toLowerCase() != ("pl") &&
                Locale.getDefault().getLanguage().toLowerCase() != ("eng")) {
            lang = "en";
        }
        return lang;
    }

    //gets list of all elements in selected row
    public List<String> getQuotes(String row, String title_or_content) {

        List<String> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT " + title_or_content + "_" + getLang() + " FROM entry WHERE category = \"" + row + "\"", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    public List<String> getQuotes2(String row, String title_or_content, String entry_or_quests) {

        List<String> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT " + title_or_content + "_" + getLang() + " FROM " + entry_or_quests + " WHERE category = \"" + row + "\"", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    public List<String> getQuizQuotes(String subCategory, String question_or_answer) {
        List<String> list = new ArrayList<>();
        Cursor cursor = databaseQuiz.rawQuery("SELECT " + question_or_answer + "_" + getLang() + " FROM quests WHERE sub_category = \"" + subCategory + "\"", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }



    //gets list of all elements in selected row based on type ("good" or "bad" habits)
    public List<String> getQuotes(String row, String title_or_content, String goodOrBad) {

        List<String> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT " + title_or_content + "_" + getLang() + " FROM entry WHERE category = \"" + row + "\" AND type = \"" + goodOrBad + "\"", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    //return list of saved articles
    public List<String> getSavedArticles(String title_or_content) {
        List<String> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT " + title_or_content + "_" + getLang() + " FROM entry WHERE saved = 1", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    //return content of an article based on title
    public String getArticleContent(String name) {
        String text = "";
        Cursor cursor = database.rawQuery("SELECT content_" + getLang() + " FROM entry WHERE title_" + getLang() + "= " + "\"" + name + "\"", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            text = cursor.getString(0);
            cursor.moveToNext();
        }
        cursor.close();
        return text;
    }

    public String getCategory(String name) {
        String text = "";
        Cursor cursor = database.rawQuery("SELECT category FROM entry WHERE _ID= " + "\"" + name + "\"", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            text = cursor.getString(0);
            cursor.moveToNext();
        }
        cursor.close();
        return text;
    }

    //return content of an article based on ID
    public String getArticleContentbyID(String ID) {
        String text = "";
        Cursor cursor = database.rawQuery("SELECT content_" + getLang() + " FROM entry WHERE _ID= " + "\"" + ID + "\"", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            text = cursor.getString(0);
            cursor.moveToNext();
        }
        cursor.close();
        return text;
    }

    //return content of an article based on ID
    public String getArticleTitlebyID(String ID) {
        String query = "SELECT title_" + getLang() + " FROM entry WHERE _ID= " + "\"" + ID + "\"";
        Log.i("query", query);
        String text = "";
        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            text = cursor.getString(0);
            cursor.moveToNext();
        }
        cursor.close();
        return text;
    }

    //return ID based on article name
    public String getArticleID(String name) {
        String text = "";
        Cursor cursor = database.rawQuery("SELECT _ID FROM entry WHERE title_" + getLang() + "= " + "\"" + name + "\"", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            text = cursor.getString(0);
            cursor.moveToNext();
        }
        cursor.close();
        return text;
    }

    //return byte representation of image stored in database
    public byte[] getImage(String name) {
        byte[] data = null;
        Cursor cursor = database.rawQuery("SELECT image FROM entry WHERE title_" + getLang() + "= " + "\"" + name + "\"", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            data = cursor.getBlob(0);
            break;  // Assumption: name is unique
        }
        cursor.close();
        return data;
    }


    public byte[] getQuizImage(String name) {
        byte[] data = null;
        Cursor cursor = database.rawQuery("SELECT image FROM quests WHERE question_" + getLang() + "= " + "\"" + name + "\"", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            data = cursor.getBlob(0);
            break;  // Assumption: name is unique
        }
        cursor.close();
        return data;
    }

    //return name of random article. if id is empty (row deleted), it goes again till it find non-empty story
    public String getRandomArticleName() {
        String randomName = "";
        Random random = new Random();
        int rand = random.nextInt(getMaxID()) + 1;
        Log.i("Random ",rand + "");
        Cursor cursor = database.rawQuery("SELECT title_" + getLang() + " FROM entry WHERE _ID=" + "\"" + rand + "\"", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            randomName = cursor.getString(0);
            cursor.moveToNext();
        }
        cursor.close();
        if (randomName == ""){
            randomName = getRandomArticleName();
        }
        return randomName;
    }

    //return number of rows in table
    public int getMaxID(){
        int maxID = 0;
        Cursor cursor = database.rawQuery("SELECT MAX(_ID) from entry", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            maxID = cursor.getInt(0);
            break;
        }
        cursor.close();
        return maxID;
    }

    //check if article is saved
    public boolean isSaved(String title) {
        boolean isSaved = false;
        int save = 0;
        Cursor cursor = database.rawQuery("SELECT saved FROM entry WHERE title_" + getLang() + "= " + "\"" + title + "\"", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            save = cursor.getInt(0);
            break;
        }
        cursor.close();
        if (save != 0 ) {
            isSaved = true;
        }
        return isSaved;
    }

    //saves specified article by putting 1 in database column for specified row
    public void save(String name, int save) {

        ContentValues cv = new ContentValues();
        cv.put("saved","" + save);

        database.update("entry", cv, "title_" + getLang() + "= " + "\"" + name + "\"", null);

    }


}