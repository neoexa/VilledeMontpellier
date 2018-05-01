package neoexa.com.VilledeMontpellier.Model;

import android.app.Activity;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import neoexa.com.VilledeMontpellier.R;

public class ShopAdapter extends BaseAdapter implements Filterable {
    Activity context;
    ArrayList<Shop> filteredShops;
    ArrayList<Shop> originalShops;
    ArrayList<Shop> favoriteShops;
    private  static LayoutInflater inflater = null;


    public ShopAdapter (Activity context, ArrayList<Shop> shops, ArrayList<Shop> favoriteShops){
        this.context = context;
        this.filteredShops = shops;
        this.originalShops = shops;
        this.favoriteShops = favoriteShops;
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
        itemView = (itemView == null) ? inflater.inflate(R.layout.item_shop, null): itemView;
        TextView textViewName = (TextView) itemView.findViewById(R.id.textViewName);
        TextView textViewAddress = (TextView) itemView.findViewById(R.id.textViewAdress);
        Shop selectedShop = filteredShops.get(position);
        textViewName.setText(selectedShop.name);
        textViewAddress.setText(selectedShop.address);
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

    public void filterFavorite(){
        filteredShops = favoriteShops;
        notifyDataSetChanged();
    }

    public void filterNearby(Location currentLoc){

        final ArrayList<Shop> list = originalShops;
        int count = list.size();
        filteredShops = new ArrayList<Shop>(count);
        Shop sh;

        Geocoder coder = new Geocoder(this.context);
        List<Address> address;

        for (int i = 0; i < count; i++) {
            try {
                sh = list.get(i);
                address = coder.getFromLocationName(sh.getAddress(),1);
                Location loc = new Location("");
                loc.setAltitude(address.get(0).getLatitude());
                loc.setLongitude(address.get(0).getLongitude());
                float d = currentLoc.distanceTo(loc); //mètres
                if (d < 500){
                    filteredShops.add(sh);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        notifyDataSetChanged();
    }
}
