package cl.usm.inf.walletkeeper.adapters;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

import cl.usm.inf.walletkeeper.R;
import cl.usm.inf.walletkeeper.db.DbHelper;
import cl.usm.inf.walletkeeper.structs.AccountEntryData;
import cl.usm.inf.walletkeeper.structs.Category;


public class BudgetAdapter extends RecyclerView.Adapter<BudgetAdapter.ViewHolder> {
    private List<Category> itemsData;
    private Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtSecondLine;
        TextView txtFirstLine;
        ImageView imgIcon;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            txtFirstLine = (TextView) itemLayoutView.findViewById(R.id.listFirstLine);
            txtSecondLine = (TextView) itemLayoutView.findViewById(R.id.listSecondLine);
            imgIcon = (ImageView) itemLayoutView.findViewById(R.id.listIcon);
        }
    }

    public BudgetAdapter(Context context, List<Category> itemsData) {
        this.context = context;
        this.itemsData = itemsData;
        Collections.sort(this.itemsData);
    }

    @Override
    public BudgetAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item, null);

        // create ViewHolder
        return new ViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        SQLiteDatabase db = new DbHelper(context).getWritableDatabase();
        AccountEntryListAdapter aux = new AccountEntryListAdapter(context,
                DbHelper.GET_ENTRY(db));
        Category item = itemsData.get(position);
        String total = String.format(Locale.US, "$%d",
                Math.abs((long)aux.getTotalByCategory(item.getId())*-1));
        db.close();

        holder.txtFirstLine.setText(total);
        holder.txtSecondLine.setText(item.getName());
        holder.imgIcon.setImageDrawable(ContextCompat.getDrawable(context, item.getResId()));
    }

    @Override
    public int getItemCount() {
        return itemsData.size();
    }
}
