package cl.usm.inf.walletkeeper.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import cl.usm.inf.walletkeeper.R;
import cl.usm.inf.walletkeeper.structs.Category;

/**
 * Created by rescar on 30-10-17.
 */

public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.ViewHolder>  {
    private Context context;
    private List<Category> itemsData;

    public CategoryListAdapter(Context context, List<Category> itemsData) {
        this.context = context;
        this.itemsData = itemsData;
        Collections.sort(this.itemsData);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtFirstLine;
        ImageView imgIcon;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            txtFirstLine = (TextView) itemLayoutView.findViewById(R.id.listFirstLine);
            imgIcon = (ImageView) itemLayoutView.findViewById(R.id.listIcon);
        }
    }

    @Override
    public CategoryListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.icon_title_list_item, null);

        // create ViewHolder
        return new ViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(CategoryListAdapter.ViewHolder viewHolder, int position) {
        Category item = itemsData.get(position);
        viewHolder.txtFirstLine.setText(item.getName());
        viewHolder.imgIcon.setImageDrawable(ContextCompat.getDrawable(context, item.getResId()));
    }

    @Override
    public int getItemCount() {
        return itemsData.size();
    }
}
