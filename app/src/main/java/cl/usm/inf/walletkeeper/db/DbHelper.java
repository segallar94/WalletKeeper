package cl.usm.inf.walletkeeper.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import cl.usm.inf.walletkeeper.db.WalletContract.AccountEntries;
import cl.usm.inf.walletkeeper.db.WalletContract.Categories;
import cl.usm.inf.walletkeeper.structs.AccountEntryData;

/**
 * Created by rescar on 19-10-17.
 */

public class DbHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String COMMA = " , ";
    private static final String INTEGER = " INTEGER ";
    private static final String PRIMARY_KEY = " PRIMARY_KEY KEY ";
    private static final String TEXT = " TEXT ";
    private static final String UNIQUE = " UNIQUE ";
    private static final String REAL = " REAL ";

    private static final String SQL_CREATE_CATEGORIES =
            "CREATE TABLE " + Categories.TABLE_NAME + " (" +
                    Categories._ID + INTEGER + PRIMARY_KEY + COMMA +
                    Categories.COLUMN_NAME_NAME + TEXT + UNIQUE + COMMA +
                    Categories.COLUMN_NAME_RESOURCEID + INTEGER +
                    " )";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + AccountEntries.TABLE_NAME + " (" +
                    AccountEntries._ID + INTEGER + PRIMARY_KEY + COMMA +
                    AccountEntries.COLUMN_NAME_NAME + TEXT + COMMA +
                    AccountEntries.COLUMN_NAME_PRICE + REAL + COMMA +
                    AccountEntries.COLUMN_NAME_CATEGORY  + INTEGER + COMMA +
                    AccountEntries.COLUMN_NAME_DATE  + INTEGER + /*COMMA +
                    "FOREIGN KEY("+ AccountEntries.COLUMN_NAME_CATEGORY +") REFERENCES "+ Categories.TABLE_NAME +"("+ Categories._ID +")" +*/
                    " )";

    public DbHelper(Context context){
        super(context, WalletContract.DBNAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_CATEGORIES);
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + AccountEntries.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Categories.TABLE_NAME);
        onCreate(db);
    }

    public static long INSERT_ACCOUNT_ENTRY(Context context, AccountEntryData entry){
        DbHelper database = new DbHelper(context);
        SQLiteDatabase db = database.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(WalletContract.AccountEntries.COLUMN_NAME_NAME, entry.getName());
        values.put(WalletContract.AccountEntries.COLUMN_NAME_PRICE, entry.getSignedValue());
        values.put(WalletContract.AccountEntries.COLUMN_NAME_CATEGORY, entry.getCategory());
        values.put(WalletContract.AccountEntries.COLUMN_NAME_DATE, entry.getDate().getTime());
        long result = db.insert(WalletContract.AccountEntries.TABLE_NAME, null, values);
        db.close();
        return result;
    }
}
