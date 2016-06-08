package com.example.cafe.khuffee.database;

/**
 * Created by ckkk on 2016-05-28.
 */
public class DatabaseCreator implements DatabaseCreatorInterface {
    private final String CREATE_MENUITEMTABLE =
            "CREATE TABLE " + Databases.MenuitemTable.TABLE_NAME + "("
                    + Databases.MenuitemTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + Databases.MenuitemTable.COLUMN_TYPE + " INTEGER, "
                    + Databases.MenuitemTable.COLUMN_NAME_KOR + " TEXT NOT NULL, "
                    + Databases.MenuitemTable.COLUMN_NAME_ENG + " TEXT NOT NULL, "
                    + Databases.MenuitemTable.COLUMN_PRICE_HOT + " INTEGER, "
                    + Databases.MenuitemTable.COLUMN_PRICE_COLD + " INTEGER);";

    private final String CREATE_LOGTABLE = "CREATE TABLE " + Databases.LogTable.TABLE_NAME + "("
                    + Databases.LogTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + Databases.LogTable.COLUMN_ID + " TEXT NOT NULL, "
                    + Databases.LogTable.COLUMN_PASS + " TEXT NOT NULL, "
                    + Databases.LogTable.COLUMN_DATE + " NUMERIC NOT NULL);";

    private final String CREATE_USRTABLE = "CREATE TABLE " + Databases.UserTable.TABLE_NAME + "("
                    + Databases.UserTable.COLUMN_ID + " TEXT PRIMARY KEY, "
                    + Databases.UserTable.COLUMN_PASS + " TEXT NOT NULL, "
                    + Databases.UserTable.COLUMN_NAME + " TEXT NOT NULL, "
                    + Databases.UserTable.COLUMN_PHONE + " TEXT, "
                    + Databases.UserTable.COLUMN_COUPON + " INTEGER);";

    @Override
    public String[] getCreateTableStatement() {
        String[] tableStatement = {CREATE_MENUITEMTABLE, CREATE_LOGTABLE, CREATE_USRTABLE};
        return tableStatement;
    }
}