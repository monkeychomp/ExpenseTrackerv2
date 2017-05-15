package com.example.mildred.expensetrackerv2.WishFragment;



import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.example.mildred.expensetrackerv2.FragmentLifeCycle;
import com.example.mildred.expensetrackerv2.Main_App;
import com.example.mildred.expensetrackerv2.Model.WishItemModel;
import com.example.mildred.expensetrackerv2.R;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WishFragment extends  Fragment implements FragmentLifeCycle {
    public static WishFragment newInstance() {
        WishFragment fragment = new WishFragment();
        return fragment;
    }

    public WishExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    ArrayList<String> listDataHeader;
    HashMap<String, ArrayList<WishItemModel>> listDataChild;
    ArrayList<WishItemModel> pleasureList = new ArrayList<>();
    ArrayList<WishItemModel> essentialList = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_wish, container, false);
        expListView = (ExpandableListView) view.findViewById(R.id.lvExp);

        prepareListData();
        listAdapter = new WishExpandableListAdapter(getActivity(), listDataHeader, listDataChild,this);
        expListView.setAdapter(listAdapter);

        return view;
    }

    /*
    * Preparing the list data
    */
    public void prepareListData() {

        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<String, ArrayList<WishItemModel>>();

        pleasureList = Main_App.WishItem.getPleasureList();
        essentialList = Main_App.WishItem.getEssentialList();


        // Adding child data
        listDataHeader.add("Pleasures");
        listDataHeader.add("Essentials");

        listDataChild.put(listDataHeader.get(0), pleasureList);
        listDataChild.put(listDataHeader.get(1), essentialList);

    }

    @Override
    public void onPauseFragment() {

    }

    @Override
    public void onResumeFragment() {
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
     prepareListData();
    }
}


