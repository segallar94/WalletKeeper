package cl.usm.inf.walletkeeper.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import cl.usm.inf.walletkeeper.R;
import cl.usm.inf.walletkeeper.db.WalletContract.AccountEntries;
import cl.usm.inf.walletkeeper.db.WalletContract.Categories;
import cl.usm.inf.walletkeeper.structs.AccountEntryData;
import cl.usm.inf.walletkeeper.structs.Category;

/**
 * Created by rescar on 19-10-17.
 */

public class DbHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 2;
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

        // Insert OCIO
            ContentValues values = new ContentValues();
            values.put(Categories.COLUMN_NAME_NAME, "Ocio");
            values.put(Categories.COLUMN_NAME_RESOURCEID, R.drawable.ic_round_flat_film);
            db.insert(Categories.TABLE_NAME, null, values);
        // Insert Hogar
            values = new ContentValues();
            values.put(Categories.COLUMN_NAME_NAME, "Hogar");
            values.put(Categories.COLUMN_NAME_RESOURCEID, R.drawable.ic_round_flat_diamond);
            db.insert(Categories.TABLE_NAME, null, values);
        // Insert SALUD
            values = new ContentValues();
            values.put(Categories.COLUMN_NAME_NAME, "Salud");
            values.put(Categories.COLUMN_NAME_RESOURCEID, R.drawable.ic_nobg_flat_heart);
            db.insert(Categories.TABLE_NAME, null, values);
        // Insert Indispensable
            values = new ContentValues();
            values.put(Categories.COLUMN_NAME_NAME, "Indispensable");
            values.put(Categories.COLUMN_NAME_RESOURCEID, R.drawable.ic_round_flat_zeppelin);
            db.insert(Categories.TABLE_NAME, null, values);

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
        values.put(WalletContract.AccountEntries.COLUMN_NAME_CATEGORY, entry.getCategory().getId());
        values.put(WalletContract.AccountEntries.COLUMN_NAME_DATE, entry.getDate().getTime());
        long result = db.insert(WalletContract.AccountEntries.TABLE_NAME, null, values);
        db.close();
        return result;
    }

    public static long INSERT_CATEGORY(Context context, Category cat){
        DbHelper database = new DbHelper(context);
        SQLiteDatabase db = database.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Categories.COLUMN_NAME_NAME, cat.getName());
        values.put(Categories.COLUMN_NAME_RESOURCEID, cat.getResId());
        long result = db.insert(WalletContract.Categories.TABLE_NAME, null, values);
        db.close();
        return result;
    }

    public static List<AccountEntryData> READ_ENTRIES(Context context) {
        List<Category> cats = READ_CATEGORIES(context);

        DbHelper database = new DbHelper(context);
        SQLiteDatabase db = database.getReadableDatabase();
        List<AccountEntryData> data = new ArrayList<AccountEntryData>();

        if (db != null) {
            Cursor c = db.rawQuery("SELECT * FROM " + AccountEntries.TABLE_NAME, null);
            if (c.moveToFirst()) {
                do {
                    data.add(new AccountEntryData(
                            c.getFloat(c.getColumnIndexOrThrow(AccountEntries.COLUMN_NAME_PRICE)),
                            c.getString(c.getColumnIndexOrThrow(AccountEntries.COLUMN_NAME_NAME)),
                            cats.get(c.getInt(c.getColumnIndexOrThrow(AccountEntries.COLUMN_NAME_CATEGORY))),
                            new Date(c.getInt((c.getColumnIndexOrThrow(AccountEntries.COLUMN_NAME_DATE))) * 1000L)
                    ));
                } while (c.moveToNext());
            }
            c.close();
            db.close();
        }

        Collections.sort(data);
        return data;
    }

    public static List<AccountEntryData> READ_ENTRIES_BY_CATEGORY(Context context, int catid) {
        List<Category> cats = READ_CATEGORIES(context);

        DbHelper database = new DbHelper(context);
        SQLiteDatabase db = database.getReadableDatabase();
        List<AccountEntryData> data = new ArrayList<AccountEntryData>();

        if (db != null) {
            Cursor c = db.rawQuery("SELECT * FROM " + AccountEntries.TABLE_NAME + " WHERE " + AccountEntries.COLUMN_NAME_CATEGORY + "=" + catid, null);
            if (c.moveToFirst()) {
                do {
                    data.add(new AccountEntryData(
                            c.getFloat(c.getColumnIndexOrThrow(AccountEntries.COLUMN_NAME_PRICE)),
                            c.getString(c.getColumnIndexOrThrow(AccountEntries.COLUMN_NAME_NAME)),
                            cats.get(c.getInt(c.getColumnIndexOrThrow(AccountEntries.COLUMN_NAME_CATEGORY))),
                            new Date(c.getInt((c.getColumnIndexOrThrow(AccountEntries.COLUMN_NAME_DATE))) * 1000L)
                    ));
                } while (c.moveToNext());
            }
            c.close();
            db.close();
        }

        Collections.sort(data);
        return data;
    }

    public static List<Category> READ_CATEGORIES(Context context) {
        DbHelper database = new DbHelper(context);
        SQLiteDatabase db = database.getReadableDatabase();
        List<Category> data = new ArrayList<Category>();

        if (db != null) {
            Cursor c = db.rawQuery("SELECT * FROM " + Categories.TABLE_NAME, null);
            if (c.moveToFirst()) {
                do {
                    data.add(new Category(
                            c.getInt(c.getColumnIndexOrThrow(Categories._ID)),
                            c.getString(c.getColumnIndexOrThrow(Categories.COLUMN_NAME_NAME)),
                            c.getInt(c.getColumnIndexOrThrow(Categories.COLUMN_NAME_RESOURCEID))
                    ));
                } while (c.moveToNext());
            }
            c.close();
            db.close();
        }

        Collections.sort(data);
        return data;
    }
}
