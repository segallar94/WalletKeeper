package cl.usm.inf.walletkeeper.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cl.usm.inf.walletkeeper.R;
import cl.usm.inf.walletkeeper.structs.AccountEntryData;

public class AccountEntryListAdapter extends RecyclerView.Adapter<AccountEntryListAdapter.ViewHolder> {
    private Context context;
    private List<AccountEntryData> itemsData;

    // inner class to hold a reference to each item of RecyclerView
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

    public AccountEntryListAdapter(Context context, List<AccountEntryData> itemsData) {
        this.context = context;
        this.itemsData = itemsData;
        Collections.sort(this.itemsData);
    }

    // Create new views (invoked by the layout manager)
    @Override
    public AccountEntryListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.icon_titl_desc_list_item, null);

        // create ViewHolder
        return new ViewHolder(itemLayoutView);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        // - get data from your itemsData at this position
        // - replace the contents of the view with that itemsData
        viewHolder.txtFirstLine.setText(itemsData.get(position).getFormattedValue());
        viewHolder.txtFirstLine.setTextColor(itemsData.get(position).getValueColor(context));
        viewHolder.txtSecondLine.setText(itemsData.get(position).getDescriptionFormatted());
        viewHolder.imgIcon.setImageDrawable(itemsData.get(position).getIconCategory(context));
    }

    // Return the size of your itemsData (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return itemsData.size();
    }

    public void addItem(AccountEntryData item){
        itemsData.add(item);
        Collections.sort(this.itemsData);
    }

    public List<AccountEntryData> getListClone() {
        return (List<AccountEntryData> ) (new ArrayList<AccountEntryData>(itemsData));
    }

    public List<AccountEntryData> getByCategory(int cat){
        return getByCategory(cat, getListClone());
    }

    public float getTotalByCategory(int cat){
        return getTotalByCategory(cat, getListClone());
    }

    static public List<AccountEntryData> getByCategory(int cat, List<AccountEntryData> data){
        // EL filtrado mas penca de la vida dado que PUTOJAVA no deja ocupar lambdas no se por que
        List<AccountEntryData> ret = (new ArrayList<AccountEntryData>());
        for (AccountEntryData item : data) {
            if(item.getCategory().getId() == cat){
                ret.add(item);
            }
        }
        return ret;
    }

    static public float getTotalByCategory(int cat, List<AccountEntryData> data){
        List<AccountEntryData> catList = getByCategory(cat,data);
        float total = 0;
        for(AccountEntryData item : catList) {
            total = total + item.getSignedValue();
        }
        return total;
    }

    @Override
    public String toString(){
        return new Gson().toJson(this.itemsData);
    }
}