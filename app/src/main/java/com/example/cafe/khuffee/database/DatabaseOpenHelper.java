package com.example.cafe.khuffee.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by ckkk on 2016-05-28.
 */
public class DatabaseOpenHelper extends SQLiteOpenHelper {
    private static DatabaseOpenHelper mInstance;
    private static SQLiteDatabase mDatabase;

    private DatabaseOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        Log.v(this.getClass().getSimpleName(), name);
    }

    private DatabaseOpenHelper(Context context) {
        super(context, DatabaseCreator.DATABASE_NAME, null, DatabaseCreator.DATABASE_VERSION);
        Log.v(this.getClass().getSimpleName(), DatabaseCreator.DATABASE_NAME);
    }

    private static void initialize(Context context) {
        if(mInstance == null) {
            mInstance = new DatabaseOpenHelper(context);
            mDatabase = mInstance.getWritableDatabase();
        }
    }

    public static final DatabaseOpenHelper getmInstance(Context context) {
        initialize(context);
        return mInstance;
    }

    public void close() {
        if(mInstance != null) {
            mDatabase.close();
            mInstance = null;
        }
    }

    public long insert(String tableName, ContentValues contentValues) {
        return mDatabase.insert(tableName, null, contentValues);
    }

    public Cursor get(String sql) {
        return mDatabase.rawQuery(sql, null);
    }

    public int getVersion() {
        return DatabaseCreator.DATABASE_VERSION;
    }

    /**
     * Called when the database is created for the first time. This is where the
     * creation of tables and the initial population of the tables should happen.
     *
     * @param db The database.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        DatabaseCreatorInterface databaseCreatorInterface = new DatabaseCreator();
        String[] tableCreateStatement = databaseCreatorInterface.getCreateTableStatement();

        if(tableCreateStatement != null && tableCreateStatement.length > 0) {
            for(int i = 0; i < tableCreateStatement.length; ++i) {
                db.execSQL(tableCreateStatement[i]);
            }
        }
    }

    /**
     * Called when the database needs to be upgraded. The implementation
     * should use this method to drop tables, add tables, or do anything else it
     * needs to upgrade to the new schema version.
     * <p>
     * <p>
     * The SQLite ALTER TABLE documentation can be found
     * <a href="http://sqlite.org/lang_altertable.html">here</a>. If you add new columns
     * you can use ALTER TABLE to insert them into a live table. If you rename or remove columns
     * you can use ALTER TABLE to rename the old table, then create the new table and then
     * populate the new table with the contents of the old table.
     * </p><p>
     * This method executes within a transaction.  If an exception is thrown, all changes
     * will automatically be rolled back.
     * </p>
     *
     * @param db         The database.
     * @param oldVersion The old database version.
     * @param newVersion The new database version.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
