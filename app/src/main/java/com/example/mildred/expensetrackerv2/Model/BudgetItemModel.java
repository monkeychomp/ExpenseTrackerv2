package com.example.mildred.expensetrackerv2.Model;

import android.os.AsyncTask;

import com.example.mildred.expensetrackerv2.DB.BudgetDBHelper;
import com.example.mildred.expensetrackerv2.Main_App;

import java.util.ArrayList;

/**
 * Created by mildred on 14/04/2017.
 */

public class BudgetItemModel {

    private BudgetType type;
    private String name;
    private int amount;
    private int ID;
    private static BudgetItemModel instance = null;

    private BudgetItemModel()
    {
      items.clear();

    }
    public static BudgetItemModel getInstance() {
        if (instance == null) {
            instance = new BudgetItemModel();
        }
        //TODO: load from database


        return instance;
    }

    public BudgetItemModel(BudgetType type, String name, int amount)
    {
        this.type=type;
        this.name = name;
        this.amount = amount;
    }

    public BudgetType getType() {
        return type;
    }

    public void setType(BudgetType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getID() {return ID;}

    public void setID(int ID) {this.ID = ID;}

    public ArrayList<BudgetItemModel> items = new ArrayList<>();
    ArrayList<BudgetItemModel> incomeList = new ArrayList<>();

    public ArrayList<BudgetItemModel> getIncomeList() {
        return incomeList;
    }

    public void setIncomeList(ArrayList<BudgetItemModel> incomeList) {
        this.incomeList = incomeList;
    }

    public ArrayList<BudgetItemModel> getExpenseList() {
        return expenseList;
    }

    public void setExpenseList(ArrayList<BudgetItemModel> expenseList) {
        this.expenseList = expenseList;
    }

    ArrayList<BudgetItemModel> expenseList = new ArrayList<>();


    public void editItem(BudgetItemModel model)
    {
        this.ID = model.getID();
        this.type = model.getType();
        this.name = model.getName();
        this.amount = model.getAmount();
        //TODO: call to DB

    }

    public enum BudgetType
    {
        Income,Expense;
    }



    public void addItem(BudgetItemModel item) {

         items.add(item);
    }

    public void removeItem(BudgetItemModel item)
    {
        items.remove(item);
    }



    public int getTotalIncome ()
    {
        int totalIncome = 0;
       for(BudgetItemModel income: incomeList)
       {
           totalIncome += income.getAmount();
       }
      return totalIncome;
    }

    public int getTotalExpenses ()
    {
        int totalExpense = 0;
        for(BudgetItemModel expense: expenseList)
        {
            totalExpense += expense.getAmount();
        }
        return totalExpense;
    }
}
