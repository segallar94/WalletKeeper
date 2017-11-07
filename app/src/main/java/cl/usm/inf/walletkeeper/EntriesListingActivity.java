package cl.usm.inf.walletkeeper;

import android.app.Notification;
import android.app.PendingIntent;
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
import android.view.MenuItem;
import android.view.View;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

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

                //NOTIFICACION

                Intent resultIntent = new Intent(this, BudgetDisplay.class);
                PendingIntent resultPendingIntent =
                        PendingIntent.getActivity(
                                this,
                                0,
                                resultIntent,
                                PendingIntent.FLAG_UPDATE_CURRENT
                        );

                 mNotificationUtils = new NotificationUtils(this);
                int notificationId = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);

            /// TODO arreglarlo para que no considere los ingresos en el calculo

                float budget_val = Float.valueOf(budget.getString("budget_ini","0"));
                float percent = budget.getInt(Cat.getName(),0)/100;
                float total = (mRecordHistoryListAdapter.getTotalByCategory(Cat.getId()) + Price*isExpense )* -1;
                String totalString = String.format(Locale.US, "$%d",
                        Math.abs((long) total));

                if (percent != 0f){
                    if (total/budget_val >= percent) {
                        Notification.Builder nb = mNotificationUtils.
                                getAndroidChannelNotification("Alerta de gastos",
                                        "Estás gastando mucho en " + Cat.getName() +
                                                ", específicamente " +
                                                totalString);
                        nb.setContentIntent(resultPendingIntent);
                        mNotificationUtils.getManager().notify(notificationId, nb.build());
                    }
                }
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
            Intent intent = new Intent(this,PlotActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_gallery) {
            Intent intent = new Intent(this, CategoriesListingActivity.class);
            startActivity(intent);
        } else if (id == R.id.budgetviewer) {
            Intent intent = new Intent(this,BudgetDisplay.class);
            startActivity(intent);

        } else if (id == R.id.nav_manage) { //aqui se define el presupuesto
            Intent intent = new Intent(this,DefineBudgetActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void loadData(){
        SQLiteDatabase db = new DbHelper(this).getReadableDatabase();
        //mRecordHistoryListAdapter = null;
        mRecordHistoryListAdapter = new AccountEntryListAdapter(this, DbHelper.GET_ENTRY(db));
        mRecordHistoryListRecycler.setAdapter(mRecordHistoryListAdapter);
        db.close();

    }
}
