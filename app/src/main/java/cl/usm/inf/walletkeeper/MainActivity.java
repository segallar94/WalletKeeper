package cl.usm.inf.walletkeeper;

import android.content.Intent;
import android.content.SharedPreferences;
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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import cl.usm.inf.walletkeeper.adapters.AccountEntryListAdapter;
import cl.usm.inf.walletkeeper.structs.AccountEntryData;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    /// AQUI ESTA LAs WEAs DE LISTA que necesitamos
    private RecyclerView mRecordHistoryListRecycler;
    private RecyclerView.Adapter mRecordHistoryListAdapter;
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
                startActivityForResult(new Intent(MainActivity.this,Pop.class),1);
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

            //CON DATOS DE PRUEBA
//        Date todaysDate = new Date();
//        AccountEntryData[] myDataset = {
        //        new AccountEntryData((float)(Math.random()*-100000),"Stefan Steffenson", (int)(Math.random()*10), todaysDate),
        //        new AccountEntryData((float)(Math.random()*-100000),"Johan Straus", (int)(Math.random()*10), todaysDate),
        //        new AccountEntryData((float)(Math.random()*-100000),"HA HA", (int)(Math.random()*10), todaysDate),
        //        new AccountEntryData((float)(Math.random()*-100000),"barsinso", (int)(Math.random()*10), todaysDate),
        //        new AccountEntryData((float)(Math.random()*-100000),"ricanmorti", (int)(Math.random()*10), todaysDate),
        //        new AccountEntryData((float)(Math.random()*-100000),"RELLENO1", (int)(Math.random()*10), todaysDate),
        //        new AccountEntryData((float)(Math.random()*-100000),"RELLENO2", (int)(Math.random()*10), todaysDate),
        //        new AccountEntryData((float)(Math.random()*-100000),"RELLENO3", (int)(Math.random()*10), todaysDate),
        //        new AccountEntryData((float)(Math.random()*-100000),"RELLENO4", (int)(Math.random()*10), todaysDate),
        //       new AccountEntryData((float)(Math.random()*-100000),"RELLENO5", (int)(Math.random()*10), todaysDate),
        //        new AccountEntryData((float)(Math.random()*-100000),"RELLENO6", (int)(Math.random()*10), todaysDate),
//                new AccountEntryData((float)(Math.random()*-100000),"claudio torres", (int)(Math.random()*10), todaysDate)
//        };

        SharedPreferences sp = getPreferences(MODE_PRIVATE);
        Type listType = new TypeToken<List<AccountEntryData>>(){}.getType();
        List<AccountEntryData> data = new Gson().fromJson(sp.getString("AccountantEntriesList", "[]"), listType);

//        if(data.isEmpty()){
//            for(AccountEntryData el : myDataset){
//                data.add(el);
//            }
//        }

        // specify an adapter
        mRecordHistoryListAdapter = new AccountEntryListAdapter(this, data);
        mRecordHistoryListRecycler.setAdapter(mRecordHistoryListAdapter);
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
        Date todaysDate = new Date();
        if(requestCode == 1){
            if  (resultCode == RESULT_OK){
                String nombre = data.getStringExtra("nombre");
                float precio = data.getFloatExtra("precio",0);
                int categoria = data.getIntExtra("categoria",1);
                new AccountEntryData(precio * -1,nombre,categoria,todaysDate);
                Log.w("nombre",nombre);
                Log.w("precio",String.valueOf(precio));
            }
            if (resultCode == RESULT_CANCELED) {
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

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    protected void onStop(){
        super.onStop();
        saveData();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        saveData();
    }

    public void saveData(){
        SharedPreferences.Editor spe = getPreferences(MODE_PRIVATE).edit();

        spe.putString("AccountantEntriesList", ((AccountEntryListAdapter) mRecordHistoryListAdapter).toString());
        spe.commit();
    }
}
