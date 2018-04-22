package neoexa.com.villedemontpellier.Model;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import neoexa.com.villedemontpellier.R;

public class ShopAdapter extends BaseAdapter {
    Activity context;
    ArrayList<Shop> shops;
    private  static LayoutInflater inflater = null;

    public ShopAdapter (Activity context, ArrayList<Shop> shops){
        this.context = context;
        this.shops = shops;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return shops.size();
    }

    @Override
    public Shop getItem(int position) {
        return shops.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = convertView;
        itemView = (itemView == null) ? inflater.inflate(R.layout.list_item, null): itemView;
        TextView textViewName = (TextView) itemView.findViewById(R.id.textViewName);
        TextView textViewAdress = (TextView) itemView.findViewById(R.id.textViewAdress);
        Shop selectedShop = shops.get(position);
        textViewName.setText(selectedShop.name);
        textViewAdress.setText(selectedShop.address);
        return itemView;
    }
}
