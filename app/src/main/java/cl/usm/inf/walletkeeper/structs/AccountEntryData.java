package cl.usm.inf.walletkeeper.structs;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

import com.google.gson.Gson;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import cl.usm.inf.walletkeeper.R;

/**
 * Created by rescar on 07-10-17.
 */

public class AccountEntryData implements Comparable<AccountEntryData>{
    private float signedValue;
    private String name;
    private Category category;
    private Date date;

    public AccountEntryData(float value, String desc, Category cat, Date date){
        this.signedValue = value;
        this.name = desc;
        this.category = cat;
        this.date = date;
    }

    public Boolean isExpense() {
        return signedValue <= 0;
    }

    public float getSignedValue(){
        return this.signedValue;
    }

    public String getName() { return name; }

    public String getFormattedValue() {
        String rr;

        if(signedValue == (long)signedValue)
            rr = String.format(Locale.US, "$%d", Math.abs((long)signedValue));
        else
            rr = String.format(Locale.US, "$%s", Math.abs((long)signedValue));

        if(isExpense())
            rr = "-".concat(rr);
        return rr;
    }

    public int getValueColor(Context context) {
        //TODO
        if(isExpense())
            return ContextCompat.getColor(context, R.color.expenseColor);
        else
            return ContextCompat.getColor(context, R.color.incomeColor);
    }

    public String getDescriptionFormatted() {
        DateFormat dateInstance = SimpleDateFormat.getDateInstance();
        return name + " - " + dateInstance.format(date);
    }

    public Category getCategory(){
        return category;
    }

    public Drawable getIconCategory(Context context){
        int res;

        switch (category.getResId()){
            case 0 :
                res = R.drawable.ic_round_flat_film;
                break;
            case 1 :
                res = R.drawable.ic_round_flat_diamond;
                break;
            case 2 :
                res = R.drawable.ic_nobg_flat_heart;
                break;
            case 3 :
                res = R.drawable.ic_round_flat_zeppelin;
                break;
            case 4 :
                res = R.drawable.ic_round_flat_desing;
                break;
            case 5 :
                res = R.drawable.ic_round_flat_food;
                break;
            case 6 :
                res = R.drawable.ic_round_flat_github;
                break;
            case 7 :
                res = R.drawable.ic_round_flat_brush;
                break;
            default:
                res = R.mipmap.ic_launcher_round;
        }

        return ContextCompat.getDrawable(context, res);
    }

    // Compare by date
    @Override
    public int compareTo(AccountEntryData o) {
        int comparison = this.date.compareTo(o.date);
        if (comparison != 0){
            return comparison;
        }else{
            return (this.name.compareTo(o.name));
        }
    }

    public Date getDate() { return date; }

    @Override
    public String toString() { return new Gson().toJson(this); }

    @Override
    public int hashCode() {
        return (int)(signedValue*date.getTime());
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;   //If objects equal, is OK
        if (o instanceof AccountEntryData) {
            AccountEntryData that = (AccountEntryData) o;
            return (name == that.name && signedValue == that.signedValue && category == that.category && date.equals(that.date));
        }
        return false;
    }
}
