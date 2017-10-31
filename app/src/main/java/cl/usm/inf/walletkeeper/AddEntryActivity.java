package cl.usm.inf.walletkeeper;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cl.usm.inf.walletkeeper.db.DbHelper;
import cl.usm.inf.walletkeeper.structs.Category;

/**
 * Created by sebastian on 08-10-17.
 */

public class AddEntryActivity extends AppCompatActivity {
    private EditText editValueBox, editDescBox;
    private CheckBox isIncome;
    private Spinner selectCatBox;
    private DatePicker datePick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_entry);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.add_entry_title);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        editValueBox = (EditText) findViewById(R.id.entryValue);
        editDescBox = (EditText) findViewById(R.id.entryDescription);
        isIncome = (CheckBox) findViewById(R.id.entryIsIncome);
        selectCatBox = (Spinner) findViewById(R.id.categorySelector);
        datePick = (DatePicker) findViewById(R.id.entryDate);
        datePick.setDescendantFocusability(TimePicker.FOCUS_BLOCK_DESCENDANTS);

        Calendar selectedDate = Calendar.getInstance();
        selectedDate.setTime(new Date());
        datePick.updateDate(selectedDate.get(Calendar.YEAR), selectedDate.get(Calendar.MONTH), selectedDate.get(Calendar.DAY_OF_MONTH));


        // Create an ArrayAdapter using the string array and a default spinner layout
        List<String> allCategories = new ArrayList<>();
        SQLiteDatabase db = new DbHelper(this).getWritableDatabase();
        for (Category cat : DbHelper.GET_CATEGORY(db)){
            allCategories.add(cat.getName());
        }
        db.close();

        ArrayAdapter<CharSequence> categoriesNames = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, allCategories);
        // Specify the layout to use when the list of choices appears
        categoriesNames.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        selectCatBox.setAdapter(categoriesNames);
    }

    public void onAddClick(View view) throws ParseException {
        if(!isEmpty(editDescBox) && !isEmpty(editValueBox)) {
            Intent i = new Intent();
            i.putExtra("nombre-string", editDescBox.getText().toString());
            i.putExtra("precio-float", Float.valueOf(editValueBox.getText().toString()));
            i.putExtra("ingreso-bool", isIncome.isChecked());
            i.putExtra("categoria-id", 1+ selectCatBox.getSelectedItemPosition());
            i.putExtra("fecha-year", datePick.getYear());
            i.putExtra("fecha-month", datePick.getMonth());
            i.putExtra("fecha-day", datePick.getDayOfMonth());
            setResult(Activity.RESULT_OK, i);
            finish();
        }else{
            Snackbar.make(view, R.string.no_empty_form_please, Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }

    public void onCancelClick(View view) {
        onBackPressed();
    }

    private boolean isEmpty(EditText myeditText) {
        return myeditText.getText().toString().trim().length() == 0;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}