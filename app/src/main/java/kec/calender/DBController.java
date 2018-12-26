package kec.calender;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class DBController  extends SQLiteOpenHelper {



    public DBController(Context applicationcontext) {
        super(applicationcontext, "user.db", null, 1);
    }
    //Creates Table
    @Override
    public void onCreate(SQLiteDatabase database) {
        String query;
        query = "CREATE TABLE users ( userId DATE PRIMARY KEY UNIQUE, userName TEXT)";
        database.execSQL(query);
    }
    @Override
    public void onUpgrade(SQLiteDatabase database, int version_old, int current_version) {
        String query;
        query = "DROP TABLE IF EXISTS users";
        database.execSQL(query);
        onCreate(database);
    }

    public void clear() {


        SQLiteDatabase db = this.getWritableDatabase(); //get database
        db.execSQL("DELETE FROM users"); //delete all rows in a table
        db.close();
    }


    public boolean deleteAll() {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("DROP TABLE IF EXISTS users");
        return true;
    }


    /**
     * Inserts User into SQLite DB
     * @param queryValues
     */
    public void insertUser(HashMap<String, String> queryValues) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("userId", queryValues.get("userId"));
        values.put("userName", queryValues.get("userName"));
        database.insert("users", null, values);
        database.close();
    }


    public ArrayList<HashMap<String, String>> getSearchResults(String searchText) {
        Log.d("Search Text",searchText);
        ArrayList<HashMap<String, String>> usersList;
        usersList = new ArrayList<HashMap<String, String>>();
        String selectQuery = "Select * from users where userName like " + "'%" + searchText + "%'";
        //String selectQuery = "select * from users where userName like '%" + searchText + "%'";

        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("userId", cursor.getString(0));
                map.put("userName", cursor.getString(1));
                usersList.add(map);
            } while (cursor.moveToNext());
        }
        database.close();
        return usersList;
    }
    public ArrayList<HashMap<String, String>> getAllUsers() {
        ArrayList<HashMap<String, String>> usersList;
        usersList = new ArrayList<HashMap<String, String>>();
        Date date = new Date();
        String newDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
        String selectQuery = "select * from Users where userId >= date('now')";
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("userId", cursor.getString(0));
                map.put("userName", cursor.getString(1));
                usersList.add(map);
            } while (cursor.moveToNext());
        }
        database.close();
        return usersList;
    }

}
