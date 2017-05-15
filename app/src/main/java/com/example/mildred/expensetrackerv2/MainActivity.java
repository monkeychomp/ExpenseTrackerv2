package com.example.mildred.expensetrackerv2;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.LinearLayout;

import com.example.mildred.expensetrackerv2.BudgetFragment.BudgetFragment;
import com.example.mildred.expensetrackerv2.DB.BudgetDBHelper;
import com.example.mildred.expensetrackerv2.BudgetFragment.BudgetDialogFragment;
import com.example.mildred.expensetrackerv2.Model.AllocationItemModel;
import com.example.mildred.expensetrackerv2.Model.BudgetItemModel;
import com.example.mildred.expensetrackerv2.Model.WishItemModel;
import com.example.mildred.expensetrackerv2.SettingsFragment.SettingsDialogFragment;
import com.example.mildred.expensetrackerv2.SettingsFragment.SettingsFragment;
import com.example.mildred.expensetrackerv2.WishFragment.WishDialogFragment;
import com.example.mildred.expensetrackerv2.WishFragment.WishFragment;


public class MainActivity extends AppCompatActivity implements BudgetDialogFragment.InputNameDialogListener,
                                                                WishDialogFragment.WishDialogListener,
                                                                 SettingsDialogFragment.AllocDialogListener
{
    public static ViewPager viewPager;
    private ViewPagerAdapter adapter;
    PagerSlidingTabStrip pagerSlidingTabStrip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_ACTION_BAR);
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_main);

        viewPager = new ViewPager(this);
        viewPager.setId(R.id.viewPager);
        viewPager.setOffscreenPageLimit(4);
        adapter = new ViewPagerAdapter(getSupportFragmentManager());

        viewPager.setAdapter(adapter);
       // viewPager.setAdapter(new ExpenseTrackerViewPagerAdapter(getSupportFragmentManager()));
        viewPager.setPageTransformer(false, new ZoomOutPageTransformer());
        viewPager.addOnPageChangeListener(pageChangeListener);
        pagerSlidingTabStrip = new PagerSlidingTabStrip(this);
        pagerSlidingTabStrip.setViewPager(viewPager);
        pagerSlidingTabStrip.setIndicatorColor(pagerSlidingTabStrip.getDividerColor());
        pagerSlidingTabStrip.setTextSize(50);



        LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.addView(pagerSlidingTabStrip);
        ll.addView(viewPager);
        ((LinearLayout.LayoutParams) viewPager.getLayoutParams()).weight = 1;
        setContentView(ll);

 //        setTitle("Hovedaktivitet");
        // Man kan trykke på app-ikonet i øverste venstre hjørne
        // (og det betyder at brugeren vil navigere op i hierakiet)
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }


    private ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {

        int currentPosition = 0;

        @Override
        public void onPageSelected(int newPosition) {

            FragmentLifeCycle fragmentToHide = (FragmentLifeCycle) adapter.getItem(currentPosition);
            fragmentToHide.onPauseFragment();

            FragmentLifeCycle fragmentToShow = (FragmentLifeCycle)adapter.getItem(newPosition);
            fragmentToShow.onResumeFragment();

            currentPosition = newPosition;
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        public void onPageScrollStateChanged(int arg0) { }
    };

    @Override
    public void onFinishBudgetDialog(BudgetItemModel item,int listPosition) {


        BudgetFragment bf = (BudgetFragment) getSupportFragmentManager().findFragmentByTag("android:switcher:" + viewPager.getId() + ":" +viewPager.getCurrentItem());
        if (item.getType()== BudgetItemModel.BudgetType.Income)
            bf.listAdapter.editChild(item,0,listPosition);
        else
            bf.listAdapter.editChild(item,1,listPosition);;

    }

    @Override
    public void onFinishBudgetDialog(BudgetItemModel item) {
        BudgetFragment bf = (BudgetFragment) getSupportFragmentManager().findFragmentByTag("android:switcher:" + viewPager.getId() + ":" +viewPager.getCurrentItem());

        if (item.getType()== BudgetItemModel.BudgetType.Income) {
            bf.listAdapter.addChild(item, 0);

        }
        else
            bf.listAdapter.addChild(item,1);
    }

    @Override
    public void onFinishWishDialog(WishItemModel item, boolean isOnEditMode, int wishType) {

        WishFragment bf = (WishFragment) adapter.getPrimaryItem();
        if(isOnEditMode)
        {
            Main_App.WishItem.editItem(item);
            bf.listAdapter.editChild(item);
        }
        else
        {
          //  Main_App.WishItem.addItem(item,item.getType());
            if(item.getType()== WishItemModel.WishType.Pleasure)
            bf.listAdapter.addChild(0,item);
            else
            {
                bf.listAdapter.addChild(1,item);
            }
        }
    }

    @Override
    public void onFinishAllocDialog(AllocationItemModel item, int listPosition) {
            SettingsFragment sf = (SettingsFragment) getSupportFragmentManager().findFragmentByTag("android:switcher:" + viewPager.getId() + ":" +viewPager.getCurrentItem());
            sf.ListAdapter.editItem(item,listPosition);
            //Main_App.Alloc.editItem(item,listPosition);
    }

    @Override
    public void onFinishAllocDialog(AllocationItemModel item) {
        SettingsFragment sf = (SettingsFragment) getSupportFragmentManager().findFragmentByTag("android:switcher:" + viewPager.getId() + ":" +viewPager.getCurrentItem());
        sf.ListAdapter.addItem(item);
        //Main_App.Alloc.addItem(item);
    }

    private class GetObjectsTask extends AsyncTask<BudgetItemModel, Void, Void> {
        BudgetDBHelper mydb = new BudgetDBHelper(getApplicationContext());

        @Override
        protected Void  doInBackground(BudgetItemModel... items){

             //Main_App.BudgetItem.items.add(items[0]);
            // mydb.insertItem(BudgetItemModel.BudgetType.valueOf(items[0].getType()),items[0].getName(),items[0].getAmount());
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }

}
