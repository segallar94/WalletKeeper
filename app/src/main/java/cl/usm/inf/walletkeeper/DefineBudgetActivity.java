package cl.usm.inf.walletkeeper;

import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

import cl.usm.inf.walletkeeper.adapters.CategorySeekbarAdapter;
import cl.usm.inf.walletkeeper.db.DbHelper;
import cl.usm.inf.walletkeeper.structs.Category;

public class DefineBudgetActivity extends AppCompatActivity
        implements View.OnClickListener {

    private EditText BudgetEntry;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.define_budget);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.settings);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Button btn = (Button) findViewById(R.id.defineBtn);
        btn.setOnClickListener(this);

        BudgetEntry = (EditText) findViewById(R.id.entryBudget);

        //Lista de seekbars
        SQLiteDatabase db = new DbHelper(this).getWritableDatabase();
        List<Category> categories = DbHelper.GET_CATEGORY(db);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerSaving);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new CategorySeekbarAdapter(this,categories);
        mRecyclerView.setAdapter(mAdapter);
        db.close();
    }

    @Override
    public void onClick(View view) {
        SharedPreferences.Editor spe = getSharedPreferences("PREF_NAME",MODE_PRIVATE).edit();
        spe.putString("budget",BudgetEntry.getText().toString());
        spe.putString("budget_ini",BudgetEntry.getText().toString());
        spe.apply();
        spe.commit();

        finish();
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        // or call onBackPressed()
        return true;
    }
}
