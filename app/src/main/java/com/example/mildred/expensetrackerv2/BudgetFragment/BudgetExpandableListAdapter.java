package com.example.mildred.expensetrackerv2.BudgetFragment;

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

import com.example.mildred.expensetrackerv2.Main_App;
import com.example.mildred.expensetrackerv2.Model.BudgetItemModel;
import com.example.mildred.expensetrackerv2.R;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import android.widget.SeekBar;

import org.w3c.dom.Text;

public class BudgetExpandableListAdapter extends BaseExpandableListAdapter{
    private Context _context;
    private ArrayList<String> _listDataHeader;
    private HashMap<String, List<BudgetItemModel>> _listDataChild;
    BudgetFragment fragment;
    int _groupPosition;
    int _childPosition;


    public BudgetExpandableListAdapter(Context context, ArrayList<String> listDataHeader,
                                 HashMap<String, List<BudgetItemModel>> listChildData, BudgetFragment fragment) {

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


    public void removeChild(BudgetItemModel child, int groupPosition, int childPosition)
    {
        //remove from list
        this._listDataChild.get(this._listDataHeader.get(groupPosition)).remove(childPosition);
        Main_App.BudgetItem.removeItem(child);
        notifyDataSetChanged();
}

    public void editChild(BudgetItemModel editedItem, int groupPosition, int childPosition)
    {
        // get Child and update fields
        BudgetItemModel itemToBeEdited = (BudgetItemModel) getChild(groupPosition,childPosition);
        itemToBeEdited.setType(editedItem.getType());
        itemToBeEdited.setName(editedItem.getName());
        itemToBeEdited.setAmount(editedItem.getAmount());

        notifyDataSetChanged();
    }

    public void addChild(BudgetItemModel child,int groupPosition)
    {
        this._listDataChild.get(this._listDataHeader.get(groupPosition)).add(child);
        notifyDataSetChanged();

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

           TextView txtListChild = (TextView) convertView.findViewById(R.id.lblListItem);
            TextView txtListAmount = (TextView) convertView.findViewById(R.id.lblAmount);


            final BudgetItemModel item = (BudgetItemModel) getChild(groupPosition,childPosition);
   //         txtListChild.setText(item.getName()+" "+item.getAmount());
          txtListChild.setText(item.getName());
          txtListAmount.setText(Integer.toString(item.getAmount()));

        Button deleteBtn = (Button)convertView.findViewById(R.id.btn_delete);
           Button editBtn = (Button)convertView.findViewById(R.id.btn_edit);

           deleteBtn.setOnClickListener(new View.OnClickListener(){
               @Override
               public void onClick(View v) {
                   showWarningDialog(item,groupPosition,childPosition,v);

               }
           });
           editBtn.setOnClickListener(new View.OnClickListener(){
               @Override
               public void onClick(View v) {
                   showInputNameDialog(item,true,childPosition,groupPosition);

               }
           });

        return convertView;
    }

    private void showInputNameDialog(BudgetItemModel item, boolean editMode, int childPosition, int groupPosition) {
        final BudgetDialogFragment inputNameDialog = new BudgetDialogFragment();
        inputNameDialog.setCancelable(false);
        inputNameDialog.setItem(item);
        inputNameDialog.IsOnEditMode = editMode;
        inputNameDialog.listPosition = childPosition;
        inputNameDialog.BudgetType = groupPosition;
        inputNameDialog.show(fragment.getFragmentManager(), "Input Dialog");

    }

    private void showInputNameDialog(int groupPosition) {
        final BudgetDialogFragment inputNameDialog = new BudgetDialogFragment();
        inputNameDialog.setCancelable(false);
        inputNameDialog.IsOnEditMode= false;
        inputNameDialog.BudgetType = groupPosition;
        inputNameDialog.show(fragment.getFragmentManager(), "Input Dialog");

    }

    public void showWarningDialog(final BudgetItemModel child, final int groupPosition, final int childPosition, View view)
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
              showInputNameDialog(groupPosition);
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