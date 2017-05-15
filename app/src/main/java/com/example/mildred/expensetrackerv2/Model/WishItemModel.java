package com.example.mildred.expensetrackerv2.Model;

import java.util.ArrayList;

/**
 * Created by mildred on 14/04/2017.
 */

public class WishItemModel {

    private WishType type;
    private String name;
    private int amount;
    private int ID;
    private static WishItemModel instance = null;
    private int percentStatus;
    public  enum  WishType {Pleasure,Essential; }

    private WishItemModel()
    {
        items.clear();
        pleasureList.clear();
        essentialList.clear();
    }
    public static WishItemModel getInstance() {
        if (instance == null) {
            instance = new WishItemModel();
        }
        return instance;
    }

    public WishItemModel(WishType type, String name, int amount)
    {
        this.type=type;
        this.name = name;
        this.amount = amount;
        this.percentStatus = 0;
    }

    public int getPercentStatus() {return this.percentStatus;}

    public void setPercentStatus(int percentStatus) {
        this.percentStatus = percentStatus;}

    public WishType getType() {
        return type;
    }

    public void setType(WishType type) {
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

    public ArrayList<WishItemModel> items = new ArrayList<>();
    public ArrayList<WishItemModel> pleasureList = new ArrayList<>();

    public ArrayList<WishItemModel> getPleasureList() {
        return pleasureList;
    }

    public void setPleasureList(ArrayList<WishItemModel> pleasureList) {
        this.pleasureList = pleasureList;
    }

    public ArrayList<WishItemModel> getEssentialList() {
        return essentialList;
    }

    public void setEssentialList(ArrayList<WishItemModel> essentialList) {
        this.essentialList = essentialList;
    }

    public ArrayList<WishItemModel> essentialList = new ArrayList<>();

     public int getTotalPleasure()
     {
         int totalPleasure = 0;
         for(WishItemModel item: pleasureList) {
             totalPleasure += item.getAmount();
         }
         return totalPleasure;
     }

    public int getTotalEssential()
    {
        int totalEssential = 0;
        for(WishItemModel item: essentialList) {
            totalEssential += item.getAmount();
        }
        return totalEssential;
    }

    public void editItem(WishItemModel model)
    {
        this.ID = model.getID();
        this.type = model.getType();
        this.name = model.getName();
        this.amount = model.getAmount();
        //TODO: call to DB

    }

    public void addItem(WishItemModel item, WishType type) {

        if (type==WishType.Pleasure)
        pleasureList.add(item);
        else
            essentialList.add(item);
        //item.setPercentStatus(items.indexOf(item));
        //TODO: call to DB

    }

}
