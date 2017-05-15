package com.example.mildred.expensetrackerv2.SettingsFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import com.example.mildred.expensetrackerv2.FragmentLifeCycle;
import com.example.mildred.expensetrackerv2.Main_App;
import com.example.mildred.expensetrackerv2.Model.AllocationItemModel;
import com.example.mildred.expensetrackerv2.R;
import java.util.ArrayList;
import java.util.List;


public class SettingsFragment extends Fragment implements FragmentLifeCycle {
    public static SettingsFragment newInstance() {
        SettingsFragment fragment = new SettingsFragment();
        return fragment;
    }
     SeekBar sbSavings,sbPleasures;
     Button addButton, saveChangesButton;
     int percentSavings, percentPleasure;
     ListView _listView;
    public  SettingsListAdapter ListAdapter;
    ArrayList<AllocationItemModel> _items;
    TextView tvTotalPercent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        _listView = (ListView) view.findViewById(R.id.allocList);

        prepareListData();
        ListAdapter = new SettingsListAdapter(getActivity(),_items,this,view);
        _listView.setAdapter(ListAdapter);


        addButton =(Button) view.findViewById(R.id.addAllocationButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               showInputNameDialog();
            }
        });

        return view;
    }

    private void showInputNameDialog() {
        final SettingsDialogFragment inputNameDialog = new SettingsDialogFragment();
        inputNameDialog.setCancelable(false);
        inputNameDialog.show(getFragmentManager(), "Input Dialog");

    }

    public void enableSaveChangesButto()
    {
        saveChangesButton.setEnabled(true);
    }

    public void prepareListData() {

       _items = Main_App.Alloc.getItems();


    }


    @Override
    public void onPauseFragment() {

    }

    @Override
    public void onResumeFragment() {

    }
}