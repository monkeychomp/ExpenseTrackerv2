package com.example.mildred.expensetrackerv2;

import android.app.Application;
import android.os.AsyncTask;

import com.example.mildred.expensetrackerv2.DB.BudgetDBHelper;
import com.example.mildred.expensetrackerv2.Model.AllocationItemModel;
import com.example.mildred.expensetrackerv2.Model.BudgetItemModel;
import com.example.mildred.expensetrackerv2.Model.WishItemModel;

import java.util.ArrayList;


/**
 * Created by mildred on 04/04/2017.
 */

public class Main_App extends Application {

    public static BudgetItemModel BudgetItem;
    public  static WishItemModel WishItem;
    public static AllocationItemModel Alloc;
    public static Main_App myapp;

    public Main_App()
    {
        myapp = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        BudgetItem = BudgetItemModel.getInstance();
        WishItem = WishItemModel.getInstance();
        Alloc = AllocationItemModel.getInstance();

    }

    private class GetObjectsTask extends AsyncTask<Void, Void, Void> {
        BudgetDBHelper mydb = new BudgetDBHelper(getApplicationContext());

        @Override
        protected Void  doInBackground(Void... params){

            BudgetItem.items = mydb.getAllBudgetItems();

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }

}