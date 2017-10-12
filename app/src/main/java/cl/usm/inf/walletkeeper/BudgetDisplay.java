package cl.usm.inf.walletkeeper;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import cl.usm.inf.walletkeeper.adapters.BudgetAdapter;

public class BudgetDisplay extends Activity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget_display);
      //  mRecyclerView = (RecyclerView) findViewById(R.id.BudgetRecycler);

        SharedPreferences sp = getSharedPreferences("PREF_NAME",MODE_PRIVATE);
        String data = sp.getString("budget","0");
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
 //       mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
   //     mLayoutManager = new LinearLayoutManager(this);
     //   mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
//        mAdapter = new BudgetAdapter(data,this);
  //      mRecyclerView.setAdapter(mAdapter);
        TextView budget = (TextView) findViewById(R.id.budgetInfo);
        budget.setText(data);
    }
}
