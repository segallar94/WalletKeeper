package cl.usm.inf.walletkeeper.structs;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

import com.google.gson.Gson;

import java.util.Date;
import java.util.Locale;

import cl.usm.inf.walletkeeper.R;

/**
 * Created by rescar on 07-10-17.
 */

public class AccountEntryData implements Comparable<AccountEntryData>{
    private float val;
    private String desc;
    private int cat;
    private Date date;

    public AccountEntryData(float value, String desc, int cat, Date date){
        this.val = value;
        this.desc = desc;
        this.cat = cat;
        this.date = date;
    }

    public float getValue(){
        return this.val;
    }

    public String getValueFormatted() {
        String rr;

        if(val == (long)val)
            rr = String.format(Locale.US, "$%d", Math.abs((long)val));
        else
            rr = String.format(Locale.US, "$%s", Math.abs((long)val));

        if(val < 0)
            rr = "-".concat(rr);
        return rr;
    }

    public String getTitleFormatted(){
        return getValueFormatted();
    }

    public int getValueColor(Context context) {
        if( val <= 0)
            return ContextCompat.getColor(context, R.color.expenseColor);
        else
            return ContextCompat.getColor(context, R.color.incomeColor);
    }

    public String getDescription() { return desc; }

    public Drawable getIconCategory(Context context){
        int res;

        switch (cat){
            case 0 :
                res = R.drawable.ic_nobg_flat_heart;
                break;
            case 1 :
                res = R.drawable.ic_round_flat_brush;
                break;
            case 2 :
                res = R.drawable.ic_round_flat_desing;
                break;
            case 3 :
                res = R.drawable.ic_round_flat_diamond;
                break;
            case 4 :
                res = R.drawable.ic_round_flat_film;
                break;
            case 5 :
                res = R.drawable.ic_round_flat_food;
                break;
            case 6 :
                res = R.drawable.ic_round_flat_github;
                break;
            case 7 :
                res = R.drawable.ic_round_flat_zeppelin;
                break;
            default:
                res = R.mipmap.ic_launcher_round;
        }

        return ContextCompat.getDrawable(context, res);
    }

    // Compare by date
    @Override
    public int compareTo(AccountEntryData o) {
        return this.date.compareTo(o.date);
    }

    public Date getDate() { return date; }

    @Override
    public String toString() { return new Gson().toJson(this); }
}
