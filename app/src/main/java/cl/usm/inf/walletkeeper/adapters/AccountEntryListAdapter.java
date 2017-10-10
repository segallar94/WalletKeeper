package cl.usm.inf.walletkeeper.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import cl.usm.inf.walletkeeper.R;
import cl.usm.inf.walletkeeper.structs.AccountEntryData;

public class AccountEntryListAdapter extends RecyclerView.Adapter<AccountEntryListAdapter.ViewHolder> {
    private Context context;
    private List<AccountEntryData> itemsData;

    // inner class to hold a reference to each item of RecyclerView
    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txtSecondLine;
        public TextView txtFirstLine;
        public ImageView imgIcon;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            txtFirstLine = (TextView) itemLayoutView.findViewById(R.id.listFirstLine);
            txtSecondLine = (TextView) itemLayoutView.findViewById(R.id.listSecondLine);
            imgIcon = (ImageView) itemLayoutView.findViewById(R.id.listIcon);
        }
    }

    public AccountEntryListAdapter(Context context, AccountEntryData[] itemsData) {
        this.context = context;
        this.itemsData = Arrays.asList(itemsData);
        Collections.sort(this.itemsData);
    }

    // Create new views (invoked by the layout manager)
    @Override
    public AccountEntryListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.icon_titl_desc_list_item, null);

        // create ViewHolder

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        // - get data from your itemsData at this position
        // - replace the contents of the view with that itemsData
        viewHolder.txtFirstLine.setText(itemsData.get(position).getValueFormatted());
        viewHolder.txtFirstLine.setTextColor(itemsData.get(position).getValueColor(context));
        viewHolder.txtSecondLine.setText(itemsData.get(position).getDescription());
        viewHolder.imgIcon.setImageDrawable(itemsData.get(position).getIconCategory(context));
    }

    // Return the size of your itemsData (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return itemsData.size();
    }
}