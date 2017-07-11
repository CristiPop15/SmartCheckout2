package com.example.cristian.smartcheckout.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.cristian.smartcheckout.Objects.Account;

/**
 * Created by Cristian on 7/7/2017.
 */

public class DBHandler extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String DATABASE_NAME = "smartcheckout.db";
    public static final String TABLE_ACCOUNT = "account";
    public static final String COL_ID = "_id";
    public static final String COL_EMAIL= "email";
    public static final String COL_PASSWORD = "password";

    public DBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE "+TABLE_ACCOUNT+" ( "+COL_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                COL_EMAIL+" TEXT, "+COL_PASSWORD+" TEXT);";

        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_ACCOUNT);
        onCreate(db);
    }


    //add Account
    public void rememberAccount(Account account){
        ContentValues values = new ContentValues();
        values.put("email", account.getEmail());
        values.put("password", account.getPassword());
        SQLiteDatabase db = getWritableDatabase();
        db.insert("account", null, values);

        Log.e("MainActivity", "save Account");

    }

    //delete current account
    public void deleteSavedAccount(){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM "+TABLE_ACCOUNT);
        Log.e("MainActivity", "delete account");
    }

    //get saved account
    public Account getCurrentAccount(){
        Account acc = new Account();

        Log.e("MainActivity", "Get current");

        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM "+TABLE_ACCOUNT+" WHERE 1;";

        Log.e("MainActivity", "query - "+query);

        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();

        while(!c.isAfterLast()){
            acc.setEmail(c.getString(c.getColumnIndex("email")));
            acc.setPassword(c.getString(c.getColumnIndex("password")));
            c.moveToNext();
        }

        Log.e("MainActivity", "Current account "+ acc.getEmail() + " "+acc.getPassword());
        return acc;
    }

}
