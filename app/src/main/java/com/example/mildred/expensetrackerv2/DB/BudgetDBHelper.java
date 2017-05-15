package com.example.mildred.expensetrackerv2.DB;

/**
 * Created by mildred on 15/04/2017.
 */

import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.content.ContentValues;
import android.content.Context;


import com.example.mildred.expensetrackerv2.Main_App;
import com.example.mildred.expensetrackerv2.Model.BudgetItemModel;

import java.util.ArrayList;


public class BudgetDBHelper extends  SQLiteOpenHelper {
    public static final String DATABASE_NAME = "database.db";
    public static final String BUDGET_TABLE_NAME = "budget";
    public static final String BUDGET_COLUMN_ID = "_id";
    public static final String BUDGET_COLUMN_TYPE = "type";
    public static final String BUDGET_COLUMN_NAME = "name";
    public static final String BUDGET_COLUMN_AMOUNT = "amount";


    public BudgetDBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        System.out.println("DbHelper construktør...");

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        System.out.println("DbHelper onCreate...");
        // TODO Auto-generated method stub
        String s = "create table " + BUDGET_TABLE_NAME + " ("
                + BUDGET_COLUMN_ID + " integer primary key, "
                + BUDGET_COLUMN_TYPE + " text not null, "
                + BUDGET_COLUMN_NAME + " text, "
                + BUDGET_COLUMN_AMOUNT + " text);";
        System.out.println(s);
        db.execSQL(s);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("drop table " + BUDGET_TABLE_NAME);
        this.onCreate(db);
    }

    public boolean insertItem(String type, String name, int amount) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("type", type);
            contentValues.put("name", name);
            contentValues.put("amount", amount);
            db.insert("budget", null, contentValues);
            db.close();
            System.out.println("Item added!!!!");
            return true;
        } catch (Exception ex) {

            return false;
        }

    }

    public void deleteItem(int ID)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(BUDGET_TABLE_NAME,BUDGET_COLUMN_ID+"="+ID,null);

    }
    public int numberOfRows() {
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, BUDGET_TABLE_NAME);
        return numRows;
    }

    public ArrayList<BudgetItemModel> getAllBudgetItems() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<BudgetItemModel> items = new ArrayList<>();

       Cursor cursor = db.query(BUDGET_TABLE_NAME,new String[]{BUDGET_COLUMN_ID,BUDGET_COLUMN_TYPE, BUDGET_COLUMN_NAME,
                BUDGET_COLUMN_AMOUNT}, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            BudgetItemModel item = cursorToBudgetItem(cursor);
            items.add(item);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return items;
    }

    private BudgetItemModel cursorToBudgetItem(Cursor cursor) {
        BudgetItemModel item = Main_App.BudgetItem;
        item.setID(cursor.getInt(0));
        item.setType(BudgetItemModel.BudgetType.valueOf(cursor.getString(1)));
        item.setName(cursor.getString(2));
        item.setAmount(cursor.getInt(3));
        return item;
    }

    @Override
    public synchronized void close() {
        super.close();
    }



}