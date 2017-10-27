package cl.usm.inf.walletkeeper;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class DefineBudgetActivity extends AppCompatActivity
        implements View.OnClickListener {

    private EditText BudgetEntry;

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
    }

    @Override
    public void onClick(View view) {
        SharedPreferences.Editor spe = getSharedPreferences("PREF_NAME",MODE_PRIVATE).edit();
        spe.putString("budget",BudgetEntry.getText().toString());
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
