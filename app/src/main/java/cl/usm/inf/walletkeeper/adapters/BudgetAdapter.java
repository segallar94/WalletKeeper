package cl.usm.inf.walletkeeper.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cl.usm.inf.walletkeeper.R;


public class BudgetAdapter extends RecyclerView.Adapter<BudgetAdapter.ViewHolder> {

    private float budgetValue;
    private Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView budgetInfo;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            budgetInfo = (TextView) itemLayoutView.findViewById(R.id.budgetInfo);
        }
    }

    public BudgetAdapter(float BudgetValue, Context context){
        budgetValue = BudgetValue;
        this.context = context;
    }

    @Override
    public BudgetAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_budget_display, null);

        // create ViewHolder
        return new ViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.budgetInfo.setText((int) budgetValue);
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
