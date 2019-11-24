package com.priyank.fas.SQLite;

import android.content.Context;

import com.priyank.fas.Constant.AppUtils;

public class CRUD {

    static Context context;
    static DatabaseHelper myDb;

    public CRUD(Context context) {
        this.context = context;
        myDb = new DatabaseHelper(context);
    }

    public static void addT1Data(String name, String date) {
        boolean isInserted = myDb.insertT1Data(name, date);
        if(isInserted == true) {
            AppUtils.Toast(context, "Data Inserted Successfully");
        }
        else {
            AppUtils.Toast(context, "Data not Inserted! Try again");
        }
    }

    public static void deleteT1Data(int id) {
        Integer deletedRows = myDb.deleteT1Data(id);
        myDb.close();
        if(deletedRows > 0) {
            AppUtils.Toast(context, "Data Deleted Successfully");
        }
        else {
            AppUtils.Toast(context, "Data not Deleted! Try again");
        }
    }


    public static void addT2Data(int festId, String itemName, String itemDate, int creditAmount, int debitAmount) {
        boolean isInserted = myDb.insertT2Data(festId, itemName, itemDate, creditAmount, debitAmount);
        if(isInserted == true) {
            AppUtils.Toast(context, "Data Inserted Successfully");
        }
        else {
            AppUtils.Toast(context, "Data not Inserted! Try again");
        }
    }

    public static void updateT2Data(int id, String name, String date, int credit, int debit) {
        boolean isUpdate = myDb.updateT2Data(id, name, date, credit, debit);
        myDb.close();
        if(isUpdate == true) {
            AppUtils.Toast(context, "Data Update Successfully");
        }
        else {
            AppUtils.Toast(context, "Data not Updated! Try again");
        }
    }

    public static void deleteT2Data(int id) {
        Integer deletedRows = myDb.deleteT2Data(id);
        myDb.close();
        if(deletedRows > 0) {
            AppUtils.Toast(context, "Data Deleted Successfully");
        }
        else {
            AppUtils.Toast(context, "Data not Deleted! Try again");
        }
    }
}
