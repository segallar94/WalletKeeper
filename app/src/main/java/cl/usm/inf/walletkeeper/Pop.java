package cl.usm.inf.walletkeeper;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import static cl.usm.inf.walletkeeper.R.array.categorias_id;
import static cl.usm.inf.walletkeeper.R.array.categorias_nombre;

/**
 * Created by sebastian on 08-10-17.
 */

public class Pop extends Activity
        implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    private EditText editText, editText2;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.popadd);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int)(width*.8),(int)(height*.8));

        editText = (EditText) findViewById(R.id.editText);
        editText2 = (EditText) findViewById(R.id.editText2);

        Button btn = (Button) findViewById(R.id.agregar);
        btn.setOnClickListener(this);

        spinner = (Spinner) findViewById(R.id.categorias);
        String item = spinner.getSelectedItem().toString();
        int spinner_pos = spinner.getSelectedItemPosition();
        String[] item_values = getResources().getStringArray(R.array.categorias_id);

        int categoria_id = Integer.valueOf(item_values[spinner_pos]);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                categorias_nombre, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
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
        i.putExtra("nombre",editText.getText().toString());
        i.putExtra("precio", Float.valueOf(editText2.getText().toString()));
        i.putExtra("categoria",spinner.getSelectedItemPosition());
        setResult(Activity.RESULT_OK,i);
        finish();
    }
}