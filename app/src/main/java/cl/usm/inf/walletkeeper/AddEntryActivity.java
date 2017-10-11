package cl.usm.inf.walletkeeper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import static cl.usm.inf.walletkeeper.R.array.categorias_nombre;

/**
 * Created by sebastian on 08-10-17.
 */

public class AddEntryActivity extends Activity
        implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    private EditText editValueBox, editDescBox;
    private Spinner selectCatBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.entry_addition);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int)(width*.8),(int)(height*.8));

        editValueBox = (EditText) findViewById(R.id.entryValue);
        editDescBox = (EditText) findViewById(R.id.entryDescription);

        Button btn = (Button) findViewById(R.id.entryAddBtn);
        btn.setOnClickListener(this);

        selectCatBox = (Spinner) findViewById(R.id.categorySelector);
        String item = selectCatBox.getSelectedItem().toString();
        int spinner_pos = selectCatBox.getSelectedItemPosition();
        String[] item_values = getResources().getStringArray(R.array.categorias_id);

        int categoria_id = Integer.valueOf(item_values[spinner_pos]);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                categorias_nombre, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        selectCatBox.setAdapter(adapter);
        selectCatBox.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onClick(View view) {
        Intent i =  new Intent();
        i.putExtra("nombre", editDescBox.getText().toString() );
        i.putExtra("precio", Float.valueOf(editValueBox.getText().toString()));
        i.putExtra("categoria",selectCatBox.getSelectedItemPosition());
        setResult(Activity.RESULT_OK,i);

        finish();
    }
}