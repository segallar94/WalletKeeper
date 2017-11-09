package cl.usm.inf.walletkeeper;

import android.animation.ValueAnimator;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.androidplot.pie.PieChart;
import com.androidplot.pie.PieRenderer;
import com.androidplot.pie.Segment;
import com.androidplot.pie.SegmentFormatter;
import com.androidplot.ui.DynamicTableModel;
import com.androidplot.ui.SizeMode;
import com.androidplot.ui.TableOrder;
import com.androidplot.util.PixelUtils;

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


        Paint texPaint = new Paint();
        texPaint.setColor(Color.BLACK);
        texPaint.setTextSize(PixelUtils.dpToPix(18));
        pie.getLegend().setTextPaint(texPaint);

        pie.setTitle("Gasto Por Categoria");
        pie.getTitle().setMargins(PixelUtils.dpToPix(0), PixelUtils.dpToPix(0), PixelUtils.dpToPix(0), PixelUtils.dpToPix(0));
        pie.getTitle().setPadding(PixelUtils.dpToPix(0), PixelUtils.dpToPix(10), PixelUtils.dpToPix(0), PixelUtils.dpToPix(0));

        // enable the legend:
        pie.getLegend().setVisible(true);
        pie.getLegend().setTableModel(new DynamicTableModel(2, (int)Math.ceil(data.size()/2.f), TableOrder.ROW_MAJOR));
        pie.getLegend().setWidth(0.8f, SizeMode.RELATIVE);
        pie.getLegend().setHeight(.2f, SizeMode.RELATIVE);
        pie.getPie().setPadding(PixelUtils.dpToPix(0), PixelUtils.dpToPix(0), PixelUtils.dpToPix(0), PixelUtils.dpToPix(0));
        pie.getPie().setMargins(PixelUtils.dpToPix(0), PixelUtils.dpToPix(0), PixelUtils.dpToPix(0), PixelUtils.dpToPix(100)); // T R U C A Z 0

        int iter = 0;
        for (Map.Entry<Category, List<AccountEntryData>> d : data.entrySet()){
            float[] c = {iter++*(360.f/data.size())+0,1,1};
            SegmentFormatter sf = new SegmentFormatter(Color.HSVToColor(c));
            pie.addSegment(
                    new Segment(d.getKey().getName(), sumPrices(d.getValue())),
                    sf
            );
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        setupIntroAnimation();
    }

    private float sumPrices(List<AccountEntryData> ls){
        float val = 0;
        for (AccountEntryData d : ls){
            if(d.isExpense()) {
                val += d.getSignedValue();
            }
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

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    protected void setupIntroAnimation() {

        final PieRenderer renderer = pie.getRenderer(PieRenderer.class);
        // start with a zero degrees pie:

        renderer.setExtentDegs(0);
        renderer.setDonutSize(1, PieRenderer.DonutMode.PERCENT);

        // animate a scale value from a starting val of 0 to a final value of 1:
        ValueAnimator animator = ValueAnimator.ofFloat(0, 1);

        // use an animation pattern that begins and ends slowly:
        animator.setInterpolator(new AccelerateDecelerateInterpolator());

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float scale = valueAnimator.getAnimatedFraction();
                renderer.setExtentDegs(360 * scale);
                renderer.setDonutSize(1-scale, PieRenderer.DonutMode.PERCENT);
                pie.redraw();
            }
        });

        // the animation will run for 1.5 seconds:
        animator.setDuration(1500);
        animator.start();
    }

}
