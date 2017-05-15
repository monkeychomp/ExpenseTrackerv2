package com.example.mildred.expensetrackerv2.WishFragment;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import com.example.mildred.expensetrackerv2.Model.BudgetItemModel;
import com.example.mildred.expensetrackerv2.Model.WishItemModel;
import com.example.mildred.expensetrackerv2.R;

public class WishDialogFragment extends DialogFragment {
    EditText type,txtname,amount;
    Button btnDone,btnCancel;
    static WishItemModel model;
    String DialogboxTitle;
    static boolean IsOnEditMode = false;
    static int Type = 0;


    public interface WishDialogListener {
        void onFinishWishDialog(WishItemModel item, boolean isOnEditMode,int wishType);
    }

    //---empty constructor required
    public WishDialogFragment()
    {

    }

    //---set the title of the dialog window
    private void setDialogTitle(String title) {
        DialogboxTitle = title;
    }

    //--get the inputted text from user
    public void setItem(WishItemModel item) { model= item;}


    public boolean isNameInputDone;

    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle saveInstanceState){


        View view = inflater.inflate(
                R.layout.input_name_dialogfragment, container);


        //Find TextView control
        type = (EditText) view.findViewById(R.id.types);
        txtname = (EditText) view.findViewById(R.id.txtName);
        amount = (EditText) view.findViewById(R.id.amount);

        isNameInputDone = false;

        btnCancel = (Button) view.findViewById(R.id.btnCancel);
        btnDone = (Button) view.findViewById(R.id.btnDone);
        if(IsOnEditMode)
        {
            setDialogTitle("Edit Item");
            type.setText(model.getType().toString());
            txtname.setText(model.getName());
            amount.setText(model.getAmount());

        }
        else
            setDialogTitle("Add Item");


        if(Type==0)
            type.setText("Pleasure");
        else
            type.setText("Essential");

        //---event handler for the button

        btnDone.setOnClickListener(new View.OnClickListener()
        {

            public void onClick(View view) {

                //---gets the calling activity
                WishDialogListener activity = (WishDialogListener) getActivity() ;
                activity.onFinishWishDialog(new WishItemModel(WishItemModel.WishType.valueOf(type.getText().toString()),txtname.getText().toString(),Integer.parseInt(amount.getText().toString())),IsOnEditMode,Type);

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
        params.height = 1000;
        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);

        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

}