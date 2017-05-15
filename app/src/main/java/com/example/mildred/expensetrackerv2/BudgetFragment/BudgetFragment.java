package com.example.mildred.expensetrackerv2.BudgetFragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import com.example.mildred.expensetrackerv2.FragmentLifeCycle;
import com.example.mildred.expensetrackerv2.Main_App;
import com.example.mildred.expensetrackerv2.Model.BudgetItemModel;
import com.example.mildred.expensetrackerv2.R;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class BudgetFragment extends  Fragment  implements FragmentLifeCycle {
    public static BudgetFragment newInstance() {
        BudgetFragment fragment = new BudgetFragment();
        return fragment;
    }

    public BudgetExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    ArrayList<String> listDataHeader;
    HashMap<String, List<BudgetItemModel>> listDataChild;
    List<BudgetItemModel> income = new ArrayList<>();
    List<BudgetItemModel> expenses = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_budget, container, false);
        expListView = (ExpandableListView) view.findViewById(R.id.lvExp);

        prepareListData();
        listAdapter = new BudgetExpandableListAdapter(getActivity(), listDataHeader, listDataChild,this);
        expListView.setAdapter(listAdapter);

        return view;
}

    /*
    * Preparing the list data
    */
    public void prepareListData() {

        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<String, List<BudgetItemModel>>();

        income = Main_App.BudgetItem.getIncomeList();
        expenses = Main_App.BudgetItem.getExpenseList();


        // Adding child data
        listDataHeader.add("Income");
        listDataHeader.add("Expenses");

        listDataChild.put(listDataHeader.get(0), income);
        listDataChild.put(listDataHeader.get(1), expenses);

    }

    @Override
    public void onPauseFragment() {

    }

    @Override
    public void onResumeFragment() {
     System.out.println();
    }
}

