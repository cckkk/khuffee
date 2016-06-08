package com.example.cafe.khuffee.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;


import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import classes.Menuitem;
import classes.User;


/**
 * Created by ckkk on 2016-05-28.
 */
public class Dao {
    private DatabaseOpenHelper databaseOpenHelper;

    public Dao(Context context) {
        databaseOpenHelper = DatabaseOpenHelper.getmInstance(context);
    }

    public int getVersion() {
        return databaseOpenHelper.getVersion();
    }
    public void close() {
        databaseOpenHelper.close();
    }

    public long insertMenuitem(Menuitem menuitem) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Databases.MenuitemTable.COLUMN_TYPE, menuitem.getmType());
        contentValues.put(Databases.MenuitemTable.COLUMN_NAME_KOR, menuitem.getmNameKor());
        contentValues.put(Databases.MenuitemTable.COLUMN_NAME_ENG, menuitem.getmNameEng());
        contentValues.put(Databases.MenuitemTable.COLUMN_PRICE_HOT, menuitem.getmPriceHot());
        contentValues.put(Databases.MenuitemTable.COLUMN_PRICE_COLD, menuitem.getmPriceCold());

        return databaseOpenHelper.insert(Databases.MenuitemTable.TABLE_NAME, contentValues);
    }

    public ArrayList<Menuitem> getMenuitem() {
        ArrayList<Menuitem> menuitems = new ArrayList<>();
        String sql = "SELECT * FROM " + Databases.MenuitemTable.TABLE_NAME + ";";
        Cursor cursor = databaseOpenHelper.get(sql);

        if(cursor.getCount() > 0) {
            cursor.moveToFirst();
            while(cursor.moveToNext()) {
                menuitems.add(new Menuitem(cursor.getInt(cursor.getColumnIndex(Databases.MenuitemTable.COLUMN_TYPE)),
                        cursor.getString(cursor.getColumnIndex(Databases.MenuitemTable.COLUMN_NAME_KOR)),
                        cursor.getString(cursor.getColumnIndex(Databases.MenuitemTable.COLUMN_NAME_ENG)),
                        cursor.getInt(cursor.getColumnIndex(Databases.MenuitemTable.COLUMN_PRICE_HOT)),
                        cursor.getInt(cursor.getColumnIndex(Databases.MenuitemTable.COLUMN_PRICE_COLD))));
            }
        }

        return menuitems;
    }

    public String[] getLog() {
        String[] log = null;
        String sql = "SELECT * FROM " + Databases.LogTable.TABLE_NAME + " ORDER BY " + Databases.LogTable.COLUMN_DATE + " DESC LIMIT 1;";
        Cursor cursor = databaseOpenHelper.get(sql);
        if(cursor.getCount() > 0) {
            cursor.moveToFirst();
            log = new String[2];
            log[0] = cursor.getString(cursor.getColumnIndex(Databases.LogTable.COLUMN_ID));
            log[1] = cursor.getString(cursor.getColumnIndex(Databases.LogTable.COLUMN_PASS));
        }
        return log;
    }

    public long insertLog(String id, String pass) {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ContentValues contentValues = new ContentValues();
        contentValues.put(Databases.LogTable.COLUMN_ID, id);
        contentValues.put(Databases.LogTable.COLUMN_PASS, pass);
        contentValues.put(Databases.LogTable.COLUMN_DATE, dateFormat.format(date));

        return databaseOpenHelper.insert(Databases.LogTable.TABLE_NAME, contentValues);
    }

    public User getUser(String user_id) {
        User user = null;
        String sql = "SELECT * FROM " + Databases.UserTable.TABLE_NAME + " WHERE "
                + Databases.UserTable.COLUMN_ID + " = '" + user_id + "';";
//        String sql = "SELECT * FROM " + Databases.UserTable.TABLE_NAME + ";";
        Cursor cursor = databaseOpenHelper.get(sql);
        if(cursor.getCount() > 0) {
            cursor.moveToFirst();
            user = new User(cursor.getString(cursor.getColumnIndex(Databases.UserTable.COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndex(Databases.UserTable.COLUMN_PASS)),
                    cursor.getString(cursor.getColumnIndex(Databases.UserTable.COLUMN_NAME)),
                    cursor.getString(cursor.getColumnIndex(Databases.UserTable.COLUMN_PHONE)),
                    cursor.getInt(cursor.getColumnIndex(Databases.UserTable.COLUMN_COUPON)));
        }
        return user;
    }

    public long insertUser(User user) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Databases.UserTable.COLUMN_ID, user.getmId());
        contentValues.put(Databases.UserTable.COLUMN_PASS, user.getmPassword());
        contentValues.put(Databases.UserTable.COLUMN_NAME, user.getmName());
        contentValues.put(Databases.UserTable.COLUMN_PHONE, user.getmCall());
        contentValues.put(Databases.UserTable.COLUMN_COUPON, user.getmCntCoupon());

        return databaseOpenHelper.insert(Databases.UserTable.TABLE_NAME, contentValues);
    }
}