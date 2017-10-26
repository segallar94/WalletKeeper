package cl.usm.inf.walletkeeper;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class DefineBudgetActivity extends Activity
        implements View.OnClickListener {

    private EditText BudgetEntry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.define_budget);

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
}