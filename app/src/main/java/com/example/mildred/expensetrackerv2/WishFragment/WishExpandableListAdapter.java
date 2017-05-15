package com.example.mildred.expensetrackerv2.WishFragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.mildred.expensetrackerv2.BudgetFragment.BudgetDialogFragment;
import com.example.mildred.expensetrackerv2.BudgetFragment.BudgetFragment;
import com.example.mildred.expensetrackerv2.DB.BudgetDBHelper;
import com.example.mildred.expensetrackerv2.Main_App;
import com.example.mildred.expensetrackerv2.Model.AllocationItemModel;
import com.example.mildred.expensetrackerv2.Model.BudgetItemModel;
import com.example.mildred.expensetrackerv2.Model.WishItemModel;
import com.example.mildred.expensetrackerv2.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WishExpandableListAdapter extends BaseExpandableListAdapter {
    private Context _context;
    private ArrayList<String> _listDataHeader;
    private HashMap<String, ArrayList<WishItemModel>> _listDataChild;
    WishFragment fragment;
    int _groupPosition;
    int _childPosition;
    TextView percentText;


    public WishExpandableListAdapter(Context context, ArrayList<String> listDataHeader,
                                       HashMap<String, ArrayList<WishItemModel>> listChildData, WishFragment fragment) {

        this.fragment= fragment;
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .get(childPosition);
    }


    public void removeChild(WishItemModel child, int groupPosition, int childPosition)
    {
        //remove from list
        this._listDataChild.get(this._listDataHeader.get(groupPosition)).remove(childPosition);
        notifyDataSetChanged();
    }

    public void editChild(WishItemModel editedItem)
    {
        // get Child and update fields
        WishItemModel itemToBeEdited = this._listDataChild.get(this._listDataHeader.get(_groupPosition)).get(_childPosition);
        itemToBeEdited.setType(editedItem.getType());
        itemToBeEdited.setName(editedItem.getName());
        itemToBeEdited.setAmount(editedItem.getAmount());

        notifyDataSetChanged();
    }

    public void addChild(int groupPosition, WishItemModel child)
    {
       // child.setPercentStatus(Main_App.WishItem.getPercentStatusFromList(groupPosition));
        this._listDataChild.get(this._listDataHeader.get(groupPosition)).add(child);
        notifyDataSetChanged();
        calculateBalanceAllocation(groupPosition);


    }

    private void calculateBalanceAllocation(int groupPosition)
    {
        ArrayList<WishItemModel> items = new ArrayList<>();
        ArrayList<AllocationItemModel> allocItems = new ArrayList<>();

        int amountAllocated=0;
        int income = Main_App.BudgetItem.getTotalIncome();
        int expense = Main_App.BudgetItem.getTotalExpenses();
        //int balance = income-expense;
        int balance = income-expense;
        int percentage;

        if(groupPosition==0) {
            items = _listDataChild.get("Pleasures");
            allocItems = Main_App.Alloc.getItems();

            for(AllocationItemModel item:allocItems)
            {
                if(item.getName().equals("Pleasures")) {
                    amountAllocated = (int)(balance * ((double)item.getPercent()/100));
                    break;
                }
            }

        }
            else if(groupPosition==1){
            items = _listDataChild.get("Essentials");
            allocItems = Main_App.Alloc.getItems();
            for(AllocationItemModel item:allocItems)
            {
                if(item.getName().equals("Essentials")) {
                    amountAllocated = (int)(balance * ((double)item.getPercent()/100));
                    break;
                }
            }

        }

        if(items.size()>0 && amountAllocated>0)
        {
            for(WishItemModel item: items)
            {
                if(item.getAmount()<amountAllocated)
                {
                    item.setPercentStatus(100);
                    amountAllocated-=item.getAmount();
                   // percentText.setText(Integer.toString(item.getPercentStatus()));
                }
                else
                {
                    int itemAmount = item.getAmount();
                    percentage = (int)(((double) amountAllocated/(double) itemAmount)* 100);
                    item.setPercentStatus((int)percentage);
                     //pleasureAmount = 0;
                     break;
                }
            }
            notifyDataSetChanged();
        }
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_item, null);
        }

        _groupPosition = groupPosition;
        _childPosition = childPosition;

        TextView txtListChild = (TextView) convertView
                .findViewById(R.id.lblListItem);

        final WishItemModel item = (WishItemModel) getChild(groupPosition,childPosition);
        txtListChild.setText(item.getName()+" "+item.getAmount());

        Button deleteBtn = (Button)convertView.findViewById(R.id.btn_delete);
        Button editBtn = (Button)convertView.findViewById(R.id.btn_edit);

 //       ProgressBar pbStatus = (ProgressBar) convertView.findViewById(R.id.pbstatus);
//        pbStatus.setVisibility(View.VISIBLE);
 //       pbStatus.setProgress(item.getPercentStatus());

        percentText = (TextView) convertView.findViewById(R.id.percentText);
        percentText.setVisibility(View.VISIBLE);
        calculateBalanceAllocation(groupPosition);
        //find the
        percentText.setText(Integer.toString(item.getPercentStatus())+"%");

        deleteBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                showWarningDialog(item,groupPosition,childPosition,v);

            }
        });
        editBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                showInputNameDialog(item,true,groupPosition);
                notifyDataSetChanged();
            }
        });

        return convertView;
    }

    private void showInputNameDialog(WishItemModel item, boolean editMode, int groupPosition) {
        final WishDialogFragment inputNameDialog = new WishDialogFragment();
        inputNameDialog.setCancelable(false);
        inputNameDialog.setItem(item);
        inputNameDialog.IsOnEditMode = editMode;
        inputNameDialog.Type = groupPosition;
        inputNameDialog.show(fragment.getFragmentManager(), "Input Dialog");

    }



    public void showWarningDialog(final WishItemModel child, final int groupPosition, final int childPosition, View view)
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(view.getContext());
        alert.setTitle("Alert!!");
        alert.setMessage("Are you sure to delete record");
        alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                removeChild(child,groupPosition,childPosition);
                dialog.dismiss();
            }
        });
        alert.setNegativeButton("NO", new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alert.show();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {

        String headerTitle = (String) getGroup(groupPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group, null);
        }

        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.lblListHeader);

        Button addButton = (Button) convertView.findViewById(R.id.btn_add);
        addButton.setFocusable(false);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInputNameDialog(null,false,groupPosition);
            }
        });

        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}