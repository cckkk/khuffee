package com.example.cafe.khuffee.database;

/**
 * Created by ckkk on 2016-05-28.
 */
public interface DatabaseCreatorInterface {
    public static final String DATABASE_NAME = "cafe.db";
    public static final int DATABASE_VERSION = 1;

    public String[] getCreateTableStatement();
}
