package cl.usm.inf.walletkeeper;

import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.androidplot.pie.PieChart;
import com.androidplot.pie.Segment;
import com.androidplot.pie.SegmentFormatter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cl.usm.inf.walletkeeper.db.DbHelper;
import cl.usm.inf.walletkeeper.structs.AccountEntryData;
import cl.usm.inf.walletkeeper.structs.Category;

public class PlotActivity extends AppCompatActivity {

    private PieChart pie;
    private Map<Category, List<AccountEntryData>> data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plot);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        pie = (PieChart) findViewById(R.id.plot);
        data = load();

        // enable the legend:
        pie.getLegend().setVisible(true);
        //pie.getRenderer(PieRenderer.class).setDonutSize(0f, PieRenderer.DonutMode.PERCENT);

        int iter = 0;
        for (Map.Entry<Category, List<AccountEntryData>> d : data.entrySet()){
            float[] c = {iter*(360.f/data.size())+0,1,1};
            iter++;
            SegmentFormatter sf = new SegmentFormatter(Color.HSVToColor(c));

            //sf.getLabelPaint().setShadowLayer(3, 0, 0, Color.BLACK);

            pie.addSegment(
                    new Segment(d.getKey().getName(), sumPrices(d.getValue())),
                    sf
            );
        }

    }

    private float sumPrices(List<AccountEntryData> ls){
        float val = 0;
        for (AccountEntryData d : ls){
            val += d.getSignedValue();
        }
        return Math.abs(val);
    }


    public Map<Category, List<AccountEntryData>> load(){
        Map<Category, List<AccountEntryData>> thing = new HashMap<>();

        SQLiteDatabase db = new DbHelper(this).getReadableDatabase();
        for (Category cat : DbHelper.GET_CATEGORY(db)){
            thing.put(cat, DbHelper.GET_ENTRY_BY_CATEGORY(db, cat));
        }
        db.close();
        return thing;
    }
}
