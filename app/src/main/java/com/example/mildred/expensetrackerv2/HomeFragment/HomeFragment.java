package com.example.mildred.expensetrackerv2.HomeFragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.TextView;

import com.example.mildred.expensetrackerv2.Model.AllocationItemModel;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.example.mildred.expensetrackerv2.BudgetFragment.BudgetFragment;
import com.example.mildred.expensetrackerv2.FragmentLifeCycle;
import com.example.mildred.expensetrackerv2.Main_App;
import com.example.mildred.expensetrackerv2.R;
import com.example.mildred.expensetrackerv2.WishFragment.WishFragment;

import org.w3c.dom.Text;
import com.github.mikephil.charting.data.PieData;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements FragmentLifeCycle {


    WebView webView;
    int num1, num2, num3, num4, num5;
    TextView income, expense, balance, algoLink;

    PieChart pieChart ;
    ArrayList<Entry> entries ;
    ArrayList<String> PieEntryLabels ;
    PieDataSet pieDataSet ;
    PieData pieData ;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        income = (TextView) view.findViewById(R.id.income);
        expense = (TextView) view.findViewById(R.id.expense);
        balance = (TextView) view.findViewById(R.id.balance);
        algoLink = (TextView) view.findViewById(R.id.algorithmLink);
        algoLink.setClickable(true);
        String text = "<a href='https://en.wikipedia.org/wiki/Knapsack_problem'>Read on Knapsack algorithm </a>";
        algoLink.setText(Html.fromHtml(text));
        updateTable(view);
        updateChart(view);

        return view;
    }

    public void AddValuesToPIEENTRY(){

        ArrayList<AllocationItemModel> items = Main_App.Alloc.getItems();
        int index = 0;
        int totalPercent = 0;
        for(AllocationItemModel item:items)
        {
            entries.add(new BarEntry((float)item.getPercent(),index));
            PieEntryLabels.add(item.getName());
            index++;
            totalPercent += item.getPercent();

        }
        if(totalPercent<100) {
            entries.add(new BarEntry((float) (100 - totalPercent), index));
            PieEntryLabels.add("Unallocated");
        }

    }

    @Override
    public void onPauseFragment() {
        System.out.println("Pause");
    }

    @Override
    public void onResumeFragment() {

        //BudgetFragment bf = (BudgetFragment) getFragmentManager().findFragmentByTag("android:switcher:" + viewPager.getId() + ":" +viewPager.getCurrentItem());
         //updateView(getView());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
    }

    private void updateTable(View view)
  {
      int totalIncome = Main_App.BudgetItem.getTotalIncome();
      int totalExpense = Main_App.BudgetItem.getTotalExpenses();

      income.setText(Integer.toString(totalIncome));
      expense.setText(Integer.toString(totalExpense));
      balance.setText(Integer.toString(totalIncome-totalExpense));
  }

  private  void updateChart(View view)
  {
      pieChart = (PieChart) view.findViewById(R.id.chart1);
      pieChart.setCenterText("Budget Allocation");

      entries = new ArrayList<>();

      PieEntryLabels = new ArrayList<String>();

      AddValuesToPIEENTRY();


      pieDataSet = new PieDataSet(entries, "");

      pieData = new PieData(PieEntryLabels, pieDataSet);

      pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);

      pieChart.setData(pieData);

      pieChart.animateY(3000);

  }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        View view = getView();
        if(view!=null) {
            updateTable(getView());
            updateChart(view);
        }
    }
}


