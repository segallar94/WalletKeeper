package cl.usm.inf.walletkeeper.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import cl.usm.inf.walletkeeper.R;
import cl.usm.inf.walletkeeper.structs.Category;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by sebastian on 05-11-17.
 */

public class CategorySeekbarAdapter extends RecyclerView.
        Adapter<CategorySeekbarAdapter.ViewHolder> {
    private List<Category> itemsData;
    private Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView categoryName;
        SeekBar categorySeek;
        TextView savingPercent;

        public ViewHolder(View itemView) {
            super(itemView);
            categoryName = (TextView) itemView.findViewById(R.id.Category);
            categorySeek = (SeekBar) itemView.findViewById(R.id.SavingSeekBar);
            savingPercent = (TextView) itemView.findViewById(R.id.SavingPercent);
        }
    }

    public CategorySeekbarAdapter(Context context, List<Category> itemsData) {
        this.context = context;
        this.itemsData = itemsData;
        Collections.sort(this.itemsData);
    }

    @Override
    public CategorySeekbarAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_saving_item, null);

        // create ViewHolder
        return new CategorySeekbarAdapter.ViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(final CategorySeekbarAdapter.ViewHolder holder, int position) {
        Category item = itemsData.get(position);
        final String catName = item.getName();
        final SharedPreferences.Editor saving_set = context.getSharedPreferences("PREF_NAME",MODE_PRIVATE).edit();
        SharedPreferences saving_get = context.getSharedPreferences("PREF_NAME",MODE_PRIVATE);
        int percent = saving_get.getInt(catName,0);
        holder.categoryName.setText(catName);
        holder.categorySeek.setProgress(percent);
        holder.savingPercent.setText(percent + "%");
        holder.categorySeek.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    int i;
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                        holder.savingPercent.setText(progress + "%");
                        i = progress;
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        saving_set.putInt(catName,i);
                        saving_set.apply();
                        saving_set.commit();
                    }
                }
        );
    }

    @Override
    public int getItemCount() {
        return itemsData.size();
    }
}
