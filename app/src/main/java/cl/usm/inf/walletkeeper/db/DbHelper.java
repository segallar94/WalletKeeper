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
    private static final int DATABASE_VERSION = 3;
    private static final String COMMA = " , ";
    private static final String INTEGER = " INTEGER ";
    private static final String PRIMARY_KEY = " PRIMARY KEY ";
    private static final String TEXT = " TEXT ";
    private static final String UNIQUE = " UNIQUE ";
    private static final String REAL = " REAL ";
    private static final String AUTOINCREMENT = " AUTOINCREMENT ";
    private static final String WHERE = " WHERE ";
    private static final String EQUALS = "=";
    private static final String INNER_JOIN = " INNER JOIN ";
    private static final String ON = " ON ";

    private static final String SQL_CREATE_CATEGORIES =
            "CREATE TABLE " + Categories.TABLE_NAME + " (" +
                    Categories._ID + INTEGER + PRIMARY_KEY + AUTOINCREMENT + COMMA +
                    Categories.NAME + TEXT + UNIQUE + COMMA +
                    Categories.RESOURCEID + INTEGER +
                    " )";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + AccountEntries.TABLE_NAME + " (" +
                    AccountEntries._ID + INTEGER + PRIMARY_KEY + AUTOINCREMENT + COMMA +
                    AccountEntries.NAME + TEXT + COMMA +
                    AccountEntries.PRICE + REAL + COMMA +
                    AccountEntries.CATEGORY_ID + INTEGER + COMMA +
                    AccountEntries.DATE + INTEGER +
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
            values.put(Categories.NAME, "Ocio");
            values.put(Categories.RESOURCEID, R.drawable.ic_round_flat_film);
            db.insert(Categories.TABLE_NAME, null, values);
        // Insert Hogar
            values = new ContentValues();
            values.put(Categories.NAME, "Hogar");
            values.put(Categories.RESOURCEID, R.drawable.ic_round_flat_diamond);
            db.insert(Categories.TABLE_NAME, null, values);
        // Insert SALUD
            values = new ContentValues();
            values.put(Categories.NAME, "Salud");
            values.put(Categories.RESOURCEID, R.drawable.ic_nobg_flat_heart);
            db.insert(Categories.TABLE_NAME, null, values);
        // Insert Indispensable
            values = new ContentValues();
            values.put(Categories.NAME, "Indispensable");
            values.put(Categories.RESOURCEID, R.drawable.ic_round_flat_zeppelin);
            db.insert(Categories.TABLE_NAME, null, values);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + AccountEntries.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Categories.TABLE_NAME);
        onCreate(db);
    }

    public static long INSERT_ACCOUNT_ENTRY(SQLiteDatabase db, AccountEntryData entry){
        ContentValues values = new ContentValues();
        values.put(WalletContract.AccountEntries.NAME, entry.getName());
        values.put(WalletContract.AccountEntries.PRICE, entry.getSignedValue());
        values.put(WalletContract.AccountEntries.CATEGORY_ID, entry.getCategory().getId());
        values.put(WalletContract.AccountEntries.DATE, entry.getDate().getTime());
        return db.insert(WalletContract.AccountEntries.TABLE_NAME, null, values);
    }

    public static long INSERT_CATEGORY(SQLiteDatabase db, String name, int resid){
        ContentValues values = new ContentValues();
        values.put(Categories.NAME, name);
        values.put(Categories.RESOURCEID, resid);
        return db.insert(Categories.TABLE_NAME, null, values);
    }

    public static List<AccountEntryData> GET_ENTRY(SQLiteDatabase db) {
        List<AccountEntryData> data = new ArrayList<AccountEntryData>();

        Cursor c = db.rawQuery("SELECT * FROM " + AccountEntries.TABLE_NAME + INNER_JOIN + Categories.TABLE_NAME + ON + AccountEntries.CATEGORY_ID + EQUALS + Categories._ID, null);
        if (c.moveToFirst()) {
            do {
                data.add(new AccountEntryData(
                        c.getFloat(c.getColumnIndexOrThrow(AccountEntries.PRICE)),
                        c.getString(c.getColumnIndexOrThrow(AccountEntries.NAME)),
                        new Category(c.getInt(c.getColumnIndexOrThrow(Categories._ID)),
                                     c.getString(c.getColumnIndexOrThrow(Categories.NAME)),
                                     c.getInt(c.getColumnIndexOrThrow(Categories.RESOURCEID))
                                ),
                        new Date(c.getInt((c.getColumnIndexOrThrow(AccountEntries.DATE))) * 1000L)
                ));
            } while (c.moveToNext());
        }
        c.close();

        Collections.sort(data);
        return data;
    }

    public static List<Category> GET_CATEGORY(SQLiteDatabase db) {
        List<Category> data = new ArrayList<Category>();

        Cursor c = db.rawQuery("SELECT * FROM " + Categories.TABLE_NAME, null);
        if (c.moveToFirst()) {
            do {
                data.add(new Category(
                        c.getInt(c.getColumnIndexOrThrow(Categories._ID)),
                        c.getString(c.getColumnIndexOrThrow(Categories.NAME)),
                        c.getInt(c.getColumnIndexOrThrow(Categories.RESOURCEID))
                ));
            } while (c.moveToNext());
        }
        c.close();

        Collections.sort(data);
        return data;
    }

    public static Category GET_CATEGORY(SQLiteDatabase db, int ID) {
        if(ID == 0){
            return new Category(0,"Categoria", R.mipmap.ic_launcher_round);
        }else{
            Category data = null;
            Cursor c = db.rawQuery("SELECT * FROM " + Categories.TABLE_NAME + WHERE + Categories._ID + EQUALS + ID, null);
            if (c.moveToFirst()) {
                data = new Category(
                        c.getInt(c.getColumnIndexOrThrow(Categories._ID)),
                        c.getString(c.getColumnIndexOrThrow(Categories.NAME)),
                        c.getInt(c.getColumnIndexOrThrow(Categories.RESOURCEID))
                );
            }
            c.close();
            return data;
        }
    }
}
