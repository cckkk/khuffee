package com.example.cafe.khuffee.database;

import android.provider.BaseColumns;

/**
 * Created by ckkk on 2016-05-28.
 */
public class Databases {
    public static final class MenuitemTable implements BaseColumns {
        public static final String TABLE_NAME = "menuitem";
        public static final String COLUMN_TYPE = "type";
        public static final String COLUMN_NAME_KOR = "name_kor";
        public static final String COLUMN_NAME_ENG = "name_eng";
        public static final String COLUMN_PRICE_HOT = "price_hot";
        public static final String COLUMN_PRICE_COLD = "price_cold";
    }

    public static final class LogTable implements BaseColumns {
        public static final String TABLE_NAME = "log";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_PASS = "pass";
        public static final String COLUMN_DATE = "date";
    }

    public static final class UserTable implements BaseColumns {
        public static final String TABLE_NAME = "user";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_PASS = "password";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_PHONE = "phone";
        public static final String COLUMN_COUPON = "coupon";
    }
}