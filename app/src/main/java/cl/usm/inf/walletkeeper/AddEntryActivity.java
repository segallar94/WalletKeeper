package cl.usm.inf.walletkeeper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import static cl.usm.inf.walletkeeper.R.array.categories_list;

/**
 * Created by sebastian on 08-10-17.
 */

public class AddEntryActivity extends AppCompatActivity {

    private EditText editValueBox, editDescBox;
    private Spinner selectCatBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_entry);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.add_entry_title);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        editValueBox = (EditText) findViewById(R.id.entryValue);
        editDescBox = (EditText) findViewById(R.id.entryDescription);
        selectCatBox = (Spinner) findViewById(R.id.categorySelector);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, categories_list, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        selectCatBox.setAdapter(adapter);
    }

    public void onAddClick(View view) {
        if(!isEmpty(editDescBox) && !isEmpty(editValueBox)) {
            Intent i = new Intent();
            i.putExtra("nombre", editDescBox.getText().toString());
            i.putExtra("precio", Float.valueOf(editValueBox.getText().toString()));
            i.putExtra("categoria", selectCatBox.getSelectedItemPosition());
            setResult(Activity.RESULT_OK, i);
            finish();
        }else{
            Toast.makeText(this, R.string.no_empty_form_please,Toast.LENGTH_SHORT).show();
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