package com.example.mildred.expensetrackerv2.SettingsFragment;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import com.example.mildred.expensetrackerv2.Model.AllocationItemModel;
import com.example.mildred.expensetrackerv2.Model.BudgetItemModel;
import com.example.mildred.expensetrackerv2.Model.WishItemModel;
import com.example.mildred.expensetrackerv2.R;

public class SettingsDialogFragment extends DialogFragment {
    EditText txtname;
    Button btnDone,btnCancel;
    static AllocationItemModel model;
    String DialogboxTitle;
    public boolean isOnEditMode;
    public int listPosition;
    public void setItem(AllocationItemModel item) { model= item;}

    public interface AllocDialogListener {
        void onFinishAllocDialog(AllocationItemModel item, int listPosition);
        void onFinishAllocDialog(AllocationItemModel item);
    }

    //---empty constructor required
    public SettingsDialogFragment()
    {

    }

    //---set the title of the dialog window
    private void setDialogTitle(String title) {
        DialogboxTitle = title;
    }


    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle saveInstanceState){


        View view = inflater.inflate(
                R.layout.add_alloc_dialogfragment, container);



        //Find TextView control
        txtname = (EditText) view.findViewById(R.id.txtName);
        btnCancel = (Button) view.findViewById(R.id.btnCancel);
        btnDone = (Button) view.findViewById(R.id.btnDone);

        //---event handler for the button

        btnDone.setOnClickListener(new View.OnClickListener()
        {

            public void onClick(View view) {

                //---gets the calling activity
                AllocDialogListener activity = (AllocDialogListener) getActivity() ;
                if(isOnEditMode)
                {
                    model.setName(txtname.getText().toString());
                    activity.onFinishAllocDialog(model,listPosition);

                }else
                activity.onFinishAllocDialog(new AllocationItemModel(txtname.getText().toString(),0));

                //---dismiss the alert
                dismiss();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view) {

                dismiss();
            }
        });

       if(isOnEditMode)
           txtname.setText(model.getName());
        //---show the keyboard automatically
        txtname.requestFocus();
        getDialog().getWindow().setSoftInputMode(
                LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        //---set the title for the dialog
        getDialog().setTitle(DialogboxTitle);

        return view;
    }


  //overrides DialogFragment size
    @Override
    public void onResume() {
        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = LayoutParams.MATCH_PARENT;
        // params.width = 1000;
       // params.height = LayoutParams.MATCH_PARENT;
         params.height = 600;
        getDialog().getWindow().setAttributes((LayoutParams) params);

        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

}