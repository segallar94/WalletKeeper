package cl.usm.inf.walletkeeper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;

import java.text.ParseException;

import cl.usm.inf.walletkeeper.adapters.IconsButtonGridAdapter;

/**
 * Created by sebastian on 08-10-17.
 */

public class AddCategoryActivity extends AppCompatActivity {
    private EditText editTitleBox;
    private GridView gridview;
    private IconsButtonGridAdapter adapter = new IconsButtonGridAdapter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.add_category_title);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        gridview = (GridView) findViewById(R.id.gridView);
        editTitleBox = (EditText) findViewById(R.id.catTitle);
        gridview.setAdapter(adapter);
        gridview.setChoiceMode(GridView.CHOICE_MODE_SINGLE);
        gridview.setSelection(0);
        gridview.setItemChecked(0,true);
        gridview.setOnItemClickListener(adapter);
    }

    public void onAddClick(View view) throws ParseException {
        if(!isEmpty(editTitleBox) && adapter.getSelectedRes() != -1) {
            Intent i = new Intent();
            i.putExtra("titulo-string", editTitleBox.getText().toString());
            i.putExtra("res-int", adapter.getSelectedRes());
            setResult(Activity.RESULT_OK, i);
            finish();
        }else{
            Snackbar.make(view, R.string.no_empty_title_please, Snackbar.LENGTH_SHORT)
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