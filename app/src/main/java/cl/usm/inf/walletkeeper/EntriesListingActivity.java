package cl.usm.inf.walletkeeper;

import android.app.Notification;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.Calendar;

import cl.usm.inf.walletkeeper.adapters.AccountEntryListAdapter;
import cl.usm.inf.walletkeeper.db.DbHelper;
import cl.usm.inf.walletkeeper.structs.AccountEntryData;
import cl.usm.inf.walletkeeper.structs.Category;

public class EntriesListingActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    // PARA LAS NOTIFICACIONES
    private NotificationUtils mNotificationUtils;

    /// AQUI ESTA LAs WEAs DE LISTA que necesitamos
    private RecyclerView mRecordHistoryListRecycler;
    private AccountEntryListAdapter mRecordHistoryListAdapter;
    private RecyclerView.LayoutManager mRecordHistoryListRecyclerLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent form = new Intent(EntriesListingActivity.this, AddEntryActivity.class);
                startActivityForResult(form, 1);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // ....Y AQUI ESTA EL RESTO
        mRecordHistoryListRecycler = (RecyclerView) findViewById(R.id.recordHistoryList);
        mRecordHistoryListRecyclerLayoutManager = new LinearLayoutManager(this);
        mRecordHistoryListRecycler.setLayoutManager(mRecordHistoryListRecyclerLayoutManager);
    }

    @Override
    protected void onResume(){
        super.onResume();
        loadData();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == 1){
            if  (resultCode == RESULT_OK){
                SQLiteDatabase db = new DbHelper(this).getWritableDatabase();

                String Name = data.getStringExtra("nombre-string");
                float Price = data.getFloatExtra("precio-float",0);
                int isExpense = (data.getBooleanExtra("ingreso-bool",false)) ? 1:-1;

                Calendar date = Calendar.getInstance();
                date.set(data.getIntExtra("fecha-year", 0), data.getIntExtra("fecha-month", 0), data.getIntExtra("fecha-day", 0));

                Category Cat = DbHelper.GET_CATEGORY(db, data.getIntExtra("categoria-id",1));

                DbHelper.INSERT_ACCOUNT_ENTRY(db, new AccountEntryData(
                        Price*isExpense,
                        Name,
                        Cat,
                        date.getTime()
                ));

                SharedPreferences.Editor new_budget = getSharedPreferences("PREF_NAME",MODE_PRIVATE).edit();
                SharedPreferences budget = getSharedPreferences("PREF_NAME",MODE_PRIVATE);
                new_budget.putString("budget",String.valueOf(Float.valueOf(budget.getString("budget","0")) + Price*isExpense));
                new_budget.apply();
                new_budget.commit();

                mRecordHistoryListAdapter.notifyDataSetChanged();
                db.close();
            }else if (resultCode == RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.budgetviewer) {
            Intent intent = new Intent(EntriesListingActivity.this,BudgetDisplay.class);
            startActivity(intent);

        } else if (id == R.id.nav_manage) { //aqui se define el presupuesto
            Intent intent = new Intent(EntriesListingActivity.this,DefineBudgetActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void loadData(){
        SQLiteDatabase db = new DbHelper(this).getWritableDatabase();
        //mRecordHistoryListAdapter = null;
        mRecordHistoryListAdapter = new AccountEntryListAdapter(this, DbHelper.GET_ENTRY(db));
        mRecordHistoryListRecycler.setAdapter(mRecordHistoryListAdapter);
        db.close();
        Log.w("gasto",String.valueOf(mRecordHistoryListAdapter.getTotalByCategory(0)));

        // NOTIFICACION

        mNotificationUtils = new NotificationUtils(this);

        SharedPreferences budget = getSharedPreferences("PREF_NAME",MODE_PRIVATE);
        float budget_val = Float.valueOf(budget.getString("budget","0"));
        float ocio_total = mRecordHistoryListAdapter.getTotalByCategory(0) * -1;

        if(ocio_total/budget_val >= 0.3){
            Notification.Builder nb = mNotificationUtils.
                    getAndroidChannelNotification("Alerta de gastos",
                            "Estás gastando mucho en Ocio, específicamente" +
                    ocio_total);
            mNotificationUtils.getManager().notify(101, nb.build());
        }
    }
}
