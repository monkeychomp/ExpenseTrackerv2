package com.example.mildred.expensetrackerv2.SettingsFragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mildred.expensetrackerv2.Main_App;
import com.example.mildred.expensetrackerv2.Model.AllocationItemModel;
import com.example.mildred.expensetrackerv2.Model.BudgetItemModel;
import com.example.mildred.expensetrackerv2.Model.WishItemModel;
import com.example.mildred.expensetrackerv2.R;
import android.widget.SeekBar;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SettingsListAdapter extends BaseAdapter {
    private Activity activity;
    private ArrayList<AllocationItemModel> _data = new ArrayList<>();
    private static LayoutInflater inflater = null;
    AllocationItemModel tempValues = null;
    int progress = 0;
    SettingsFragment _fragment;
    Context _context;
    int _totalPercent,_position = 0;
    TextView tvTotalPercent;
    SeekBar sbAlloc;
     View _view;

    public SettingsListAdapter(Context context, ArrayList<AllocationItemModel> d, SettingsFragment fragment,View view) {

        /********** Take passed values **********/
        _data = d;
        _fragment = fragment;
        _context = context;
        _view = view;
        tvTotalPercent = (TextView) view.findViewById(R.id.tvTotalPercent);

    }

    public  int getTotalPercent() {
        int totalPercent=0;
        for (AllocationItemModel item : _data) {
            totalPercent += item.getPercent();
        }
        return totalPercent;
    }

    /******** What is the size of Passed Arraylist Size ************/
    public int getCount() {

        if (_data.size() <= 0)
            return 1;
        return _data.size();
    }

    public Object getItem(int position) {
        return _data.get(position);

    }

    public void addItem(AllocationItemModel item) {
        _data.add(item);
        notifyDataSetChanged();
    }

    public void editItem(AllocationItemModel item, int position)
    {
        AllocationItemModel itemToBeEdited = _data.get(position);
        itemToBeEdited.setName(item.getName());
        itemToBeEdited.setPercent(item.getPercent());


        notifyDataSetChanged();
    }


   private  void removeChild(int position) {
       _data.remove(position);
       notifyDataSetChanged();
   }

    public long getItemId(int position) {
        return position;
    }

    /****** Depends upon data size called for each row , Create each ListView row *****/
    public View getView(final int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                LayoutInflater infalInflater = (LayoutInflater) this._context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.alloc_list_item, null);
            }

            final AllocationItemModel model = (AllocationItemModel) getItem(position);
            _position = position;
            TextView name = (TextView) convertView.findViewById(R.id.allocItemText);
            Button deleteButton = (Button) convertView.findViewById(R.id.deleteAllocItemButton);

            deleteButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                showWarningDialog(model,position,v);
            }
           });

           Button editButton = (Button) convertView.findViewById(R.id.editAllocItemButton);
           editButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                showInputNameDialog(model,true,position);
            }
        });
            sbAlloc = (SeekBar) convertView.findViewById(R.id.sbAllocItem);
            final TextView percentText = (TextView) convertView.findViewById(R.id.allocItemPercentText);

            name.setText(model.getName());
             sbAlloc.setMax(100);
            sbAlloc.setProgress(model.getPercent());
            percentText.setText(Integer.toString(model.getPercent()) + "%");
            sbAlloc.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    percentText.setText(Integer.toString(progress) + "%");
                    //Main_App.Alloc.editItem(new AllocationItemModel(model.getName(),progress));
                    //editItem(model);
                    //    updatePercent(progress);
                    model.setPercent(progress);
                    tvTotalPercent.setText(Integer.toString(getTotalPercent())+"%");

                    //updateSeekBarMaxPercent(model);
                  //    _fragment.saveChangesButton.setEnabled(true);
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                  System.out.println("");
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    System.out.println("");
                }


            });


             sbAlloc.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    editItem(model,position);

                    return true;
                }

                    return false;
            }
        });

            return convertView;
    }

    private int updatePercent(int percent)
    {
        progress = percent;
        return progress;
    }



    private void showInputNameDialog(AllocationItemModel model,boolean isOnEditMode,int position) {
        final SettingsDialogFragment inputNameDialog = new SettingsDialogFragment();
        inputNameDialog.setCancelable(false);
        inputNameDialog.isOnEditMode = isOnEditMode;
        inputNameDialog.listPosition = position;
        inputNameDialog.setItem(model);
        inputNameDialog.show(_fragment.getFragmentManager(), "Input Dialog");

    }

    public void showWarningDialog(final AllocationItemModel child, final int childPosition, View view)
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(view.getContext());
        alert.setTitle("Alert!!");
        alert.setMessage("Are you sure to delete record");
        alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                removeChild(childPosition);
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
}

