package cl.usm.inf.walletkeeper.structs;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import cl.usm.inf.walletkeeper.R;

/**
 * Created by rescar on 07-10-17.
 */

public class HistoryEntryData {
    private float val;
    private String desc;
    private int cat;

    public HistoryEntryData(float value, String desc, int cat){
        this.val = value;
        this.desc = desc;
        this.cat = cat;
    }

    public String getValue() {
        String rr;

        if(val == (long)val)
            rr = String.format("$%d", Math.abs((long)val));
        else
            rr = String.format("$%s", Math.abs((long)val));

        if(val < 0)
            rr = "-".concat(rr);
        return rr;
    }

    public int getValueColor(Context context) {
        if( val <= 0)
            return ContextCompat.getColor(context, R.color.colorExpense);
        else
            return ContextCompat.getColor(context, R.color.colorExpense);
    }

    public String getDescription() { return desc; }
}
