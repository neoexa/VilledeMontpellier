package neoexa.com.VilledeMontpellier.Model;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import neoexa.com.VilledeMontpellier.R;

public class ShopAdapter extends BaseAdapter implements Filterable {
    Activity context;
    ArrayList<Shop> filteredShops;
    ArrayList<Shop> originalShops;
    private  static LayoutInflater inflater = null;


    public ShopAdapter (Activity context, ArrayList<Shop> shops){
        this.context = context;
        this.filteredShops = shops;
        this.originalShops = shops;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return filteredShops.size();
    }

    @Override
    public Shop getItem(int position) {
        return filteredShops.get(position);
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
        Shop selectedShop = filteredShops.get(position);
        textViewName.setText(selectedShop.name);
        textViewAdress.setText(selectedShop.address);
        return itemView;
    }

    //Filters

    //SearchView Filter
    @Override
    public Filter getFilter(){
        Filter filter = new Filter() {

            @Override
            public FilterResults performFiltering(CharSequence constraint) {

                constraint = constraint.toString().toLowerCase();

                FilterResults results = new FilterResults();

                final ArrayList<Shop> list = originalShops;

                int count = list.size();
                final ArrayList<Shop> nList = new ArrayList<Shop>(count);

                Shop filterableShop;

                for (int i = 0; i < count; i++) {
                    filterableShop = list.get(i);
                    if (filterableShop.getName().toLowerCase().startsWith(constraint.toString())) {
                            nList.add(filterableShop);
                        }
                    }

                    results.count = nList.size();
                    results.values = nList;
                    return results;
            }

            @SuppressWarnings("unchecked")
            @Override
            public void publishResults(CharSequence constraint, FilterResults results){
                filteredShops = (ArrayList<Shop>) results.values;
                notifyDataSetChanged();
            }
        };

        return filter;
    }

    //Shop Category Filter
    public void noFilter(){
        filteredShops = originalShops;
        notifyDataSetChanged();
    }

    public void filterRestaurant() {
        filterCategory("food");
    }

    public void filterMarket() {
        filterCategory("market");
    }

    public void filterHotel() {
        filterCategory("hotel");
    }

    public void filterFinance() {
        filterCategory("finance");
    }

    public void filterFavorite() {
    }

    private void filterCategory(String category){
        final ArrayList<Shop> list = originalShops;

        int count = list.size();
        filteredShops = new ArrayList<Shop>(count);
        Shop filterableShop;
        for (int i = 0; i < count; i++) {
            filterableShop = list.get(i);
            if (filterableShop.getCategory().toString().equals(category)) {
                Log.e("hi ", filterableShop.getCategory().toString());
                filteredShops.add(filterableShop);
            }
        }
        notifyDataSetChanged();
        Log.e("Values", filteredShops.toString());
    }
}
