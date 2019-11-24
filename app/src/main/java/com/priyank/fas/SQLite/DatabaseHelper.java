package com.priyank.fas.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.priyank.fas.Constant.AppUtils;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "FestDB";

    private static final String TABLE_LIST = "festlist";
    private static final String TABLE_DETAIL = "festdetail";

    private static final String KEY_ID = "key_id";
    private static final String FEST_ID = "fest_id";
    private static final String FEST_NAME = "fest_name";
    private static final String FEST_DATE= "fest_date";

    private static final String ITEMS_NAME = "item_name";
    private static final String ITEMS_DATE = "item_date";
    private static final String CREDIT = "credit_amount";
    private static final String DEBIT = "debit_amount";

    private static final String CREATE_TABLE_LIST = "CREATE TABLE "
            + TABLE_LIST + "(" + KEY_ID + " INTEGER PRIMARY KEY," + FEST_NAME + " TEXT," + FEST_DATE + " TEXT" + ")";

    private static final String CREATE_TABLE_DETAIL = "CREATE TABLE " + TABLE_DETAIL
            + "(" + KEY_ID + " INTEGER PRIMARY KEY," + FEST_ID + " INTEGER," + ITEMS_NAME + " TEXT," + ITEMS_DATE + " TEXT," + CREDIT + " INTEGER," + DEBIT + " INTEGER," +
            "FOREIGN KEY(" + FEST_ID + ") REFERENCES " + TABLE_LIST + "(" + KEY_ID + "))";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_LIST);
        db.execSQL(CREATE_TABLE_DETAIL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LIST);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DETAIL);
        onCreate(db);
    }

    public boolean insertT1Data(String name, String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(FEST_NAME, name);
        contentValues.put(FEST_DATE, date);
        long result = db.insert(TABLE_LIST,null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    public Cursor getAllT1Data() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_LIST,null);
        return res;
    }

    public Cursor getT1DataById(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_LIST + " where KEY_ID = " + id,null);
        return res;
    }

    public Integer deleteT1Data (int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int id1 = db.delete(TABLE_LIST, "KEY_ID = ?",new String[] {Integer.toString(id)});
        int id2 = db.delete(TABLE_DETAIL, "FEST_ID = ?",new String[] {Integer.toString(id)});
        return id1;
    }



    public boolean insertT2Data(int festId, String itemName, String itemDate, int creditAmount, int debitAmount) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(FEST_ID, festId);
        contentValues.put(ITEMS_NAME, itemName);
        contentValues.put(ITEMS_DATE, itemDate);
        contentValues.put(CREDIT, creditAmount);
        contentValues.put(DEBIT, debitAmount);
        long result = db.insert(TABLE_DETAIL,null ,contentValues);

        if(result == -1)
            return false;
        else
            return true;
    }

    public Cursor getT2DataByForeignKey(int key) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_DETAIL + " where FEST_ID = " + key,null);
        return res;
    }

    public boolean updateT2Data(int id, String name, String date, int credit, int debit) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ITEMS_NAME, name);
        contentValues.put(ITEMS_DATE, date);
        contentValues.put(CREDIT, credit);
        contentValues.put(DEBIT, debit);
        db.update(TABLE_DETAIL, contentValues, "KEY_ID = ?",new String[] { Integer.toString(id) });
        return true;
    }

    public Integer deleteT2Data (int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int id1 = db.delete(TABLE_DETAIL, "KEY_ID = ?",new String[] {Integer.toString(id)});
        return id1;
    }
}
