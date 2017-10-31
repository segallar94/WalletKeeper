package cl.usm.inf.walletkeeper;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import cl.usm.inf.walletkeeper.adapters.CategoryListAdapter;

public class CategoriesListingActivity extends AppCompatActivity {
    private RecyclerView mCategoriesList;
    private CategoryListAdapter mCategoriesAdapter;
    private RecyclerView.LayoutManager mListAdapterLayourMan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories_listing);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mCategoriesList = (RecyclerView) findViewById(R.id.categoriesList);
        mListAdapterLayourMan = new LinearLayoutManager(this);
        mCategoriesList.setLayoutManager(mListAdapterLayourMan);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
