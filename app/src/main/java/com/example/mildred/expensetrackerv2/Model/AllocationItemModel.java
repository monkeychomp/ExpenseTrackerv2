package com.example.mildred.expensetrackerv2.Model;

import android.os.Parcel;
import android.os.Parcelable;
import android.renderscript.Allocation;

import com.example.mildred.expensetrackerv2.Main_App;
import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;

/**
 * Created by mildred on 14/04/2017.
 */

public class AllocationItemModel{


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPercent() {
        return percent;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    private String name;
    private int percent;
    private int ID;
    private static AllocationItemModel instance = null;

    public static ArrayList<AllocationItemModel> getItems() {
        return items;
    }

    public static void setItems(ArrayList<AllocationItemModel> items) {
        AllocationItemModel.items = items;
    }

    public static  ArrayList<AllocationItemModel> items = new ArrayList<>();
    private int totalPercent;

    private AllocationItemModel()
    {

    }
    public AllocationItemModel(String name, int percent)
    {
        this.name = name;
        this.percent = percent;
    }
    public static AllocationItemModel getInstance() {
        if (instance == null) {
            instance = new AllocationItemModel();
        }
        loadInitialValues();
        return instance;
    }


    private static void  loadInitialValues()
    {
        items.add(new AllocationItemModel("Savings",50));
        items.add(new AllocationItemModel("Pleasures",20));
        items.add(new AllocationItemModel("Essentials",30));

    }

    public void addItem(AllocationItemModel model)
    {
        items.add(model);
    }

    public void editItem(AllocationItemModel model, int position) {

        AllocationItemModel item = items.get(position);
        item.setName(model.getName());
        item.setPercent(model.getPercent());

    }

}
