package cl.usm.inf.walletkeeper.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import cl.usm.inf.walletkeeper.R;

/**
 * Created by rescar on 31-10-17.on
 */

public class IconsButtonGridAdapter extends BaseAdapter implements AdapterView.OnItemClickListener{
    private Context context;
    private int selected = -1;
    private Integer[] mThumbIds = {
            R.drawable.ic_nobg_flat_heart, R.drawable.ic_round_flat_food,
            R.drawable.ic_menu_camera, R.drawable.ic_round_flat_github,
            R.drawable.ic_menu_gallery, R.drawable.ic_round_flat_gym,
            R.drawable.ic_menu_manage, R.drawable.ic_round_flat_zeppelin,
            R.drawable.ic_menu_send, R.mipmap.ic_launcher,
            R.drawable.ic_menu_share, R.mipmap.ic_launcher_round,
            R.drawable.ic_menu_slideshow, R.drawable.ic_round_flat_brush,
            R.drawable.ic_round_flat_desing, R.drawable.ic_round_flat_diamond,
            R.drawable.ic_round_flat_film
    };

    public IconsButtonGridAdapter(Context context){
        this.context = context;
    }


    @Override
    public int getCount() {
        return mThumbIds.length;
    }

    @Override
    public Object getItem(int position) {
        return mThumbIds[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(context);
            imageView.setAdjustViewBounds(true);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }

        if (selected != position) {
            imageView.setBackgroundColor(Color.TRANSPARENT);
            imageView.setSelected(false);
        }

        imageView.setImageResource(mThumbIds[position]);
        return imageView;
    }

    public int getSelectedRes() {
        return mThumbIds[selected];
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        selected = position;
        view.setBackgroundResource(R.drawable.grid_list_selector);
        view.setSelected(true);
        //Toast.makeText(context, "Clicked " + selected, Toast.LENGTH_SHORT).show();
   }
}
