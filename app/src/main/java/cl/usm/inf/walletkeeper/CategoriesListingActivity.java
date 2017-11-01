package cl.usm.inf.walletkeeper;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import cl.usm.inf.walletkeeper.adapters.CategoryListAdapter;
import cl.usm.inf.walletkeeper.db.DbHelper;

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
                Intent form = new Intent(CategoriesListingActivity.this, AddCategoryActivity.class);
                startActivityForResult(form, 1);
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

    @Override
    protected void onResume(){
        super.onResume();
        loadData();
    }

    public void loadData(){
        SQLiteDatabase db = new DbHelper(this).getReadableDatabase();
        //mRecordHistoryListAdapter = null;
        mCategoriesAdapter = new CategoryListAdapter(this, DbHelper.GET_CATEGORY(db));
        mCategoriesList.setAdapter(mCategoriesAdapter);
        db.close();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == 1){
            if  (resultCode == RESULT_OK){
                SQLiteDatabase db = new DbHelper(this).getWritableDatabase();
                String Title = data.getStringExtra("titulo-string");
                DbHelper.INSERT_CATEGORY(db,
                        data.getStringExtra("titulo-string"),
                        data.getIntExtra("res-int", R.mipmap.ic_launcher_round));
                mCategoriesAdapter.notifyDataSetChanged();
                db.close();
            }else if (resultCode == RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }

}
