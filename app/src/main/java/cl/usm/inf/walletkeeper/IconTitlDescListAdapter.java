package cl.usm.inf.walletkeeper;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import cl.usm.inf.walletkeeper.structs.HistoryEntryData;

public class IconTitlDescListAdapter extends RecyclerView.Adapter<IconTitlDescListAdapter.ViewHolder> {
    private Context context;
    private HistoryEntryData[] itemsData;

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

    public IconTitlDescListAdapter(Context context, HistoryEntryData[] itemsData) {
        this.context = context;
        this.itemsData = itemsData;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public IconTitlDescListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
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
        viewHolder.txtFirstLine.setText(itemsData[position].getValue());
        viewHolder.txtFirstLine.setTextColor(itemsData[position].getValueColor(context));
        viewHolder.txtSecondLine.setText(itemsData[position].getDescription());
        viewHolder.imgIcon.setImageDrawable(itemsData[position].getIconCategory(context));
    }

    // Return the size of your itemsData (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return itemsData.length;
    }
}