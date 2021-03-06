package cl.usm.inf.walletkeeper;

import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import cl.usm.inf.walletkeeper.adapters.AccountEntryListAdapter;
import cl.usm.inf.walletkeeper.adapters.BudgetAdapter;
import cl.usm.inf.walletkeeper.db.DbHelper;
import cl.usm.inf.walletkeeper.structs.AccountEntryData;
import cl.usm.inf.walletkeeper.structs.Category;

public class BudgetDisplay extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_budget_display);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.budget_hint);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        SharedPreferences sp = getSharedPreferences("PREF_NAME",MODE_PRIVATE);
        float data = Float.valueOf(sp.getString("budget","0"));

        TextView budget = (TextView) findViewById(R.id.budgetInfo);
        String budget_val;
        if(data == (long)data)
            budget_val = String.format(Locale.US, "$%d", Math.abs((long)data));
        else
            budget_val = String.format(Locale.US, "$%s", Math.abs((long)data));

        if(data < 0)
            budget_val = "-".concat(budget_val);
        budget.setText(budget_val);

        //Lista de categorias
        SQLiteDatabase db = new DbHelper(this).getWritableDatabase();
        List<Category> categories = DbHelper.GET_CATEGORY(db);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerCategory);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new BudgetAdapter(this,categories);
        mRecyclerView.setAdapter(mAdapter);
        db.close();
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        // or call onBackPressed()
        return true;
    }
}
