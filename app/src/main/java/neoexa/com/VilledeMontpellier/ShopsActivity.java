package neoexa.com.VilledeMontpellier;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageButton;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import neoexa.com.VilledeMontpellier.Model.Shop;
import neoexa.com.VilledeMontpellier.Model.ShopAdapter;
import neoexa.com.VilledeMontpellier.Model.User;


public class ShopsActivity extends AppCompatActivity {
    private SearchView searchView;
    private ImageButton noFilterBtn;
    private ImageButton foodFilterBtn;
    private ImageButton marketFilterBtn;
    private ImageButton hotelFilterBtn;
    private ImageButton financeFilterBtn;
    private ImageButton favoriteFilterBtn;


    private ListView shopsListView ;
    private ArrayList<Shop> shops = new ArrayList<>() ;
    private ShopAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shops);

        //References
        searchView = (SearchView) findViewById(R.id.search_bar);
        noFilterBtn = (ImageButton) findViewById(R.id.noFilter_btn);
        foodFilterBtn = (ImageButton) findViewById(R.id.food_filter_btn);
        marketFilterBtn = (ImageButton) findViewById(R.id.market_filter_btn);
        hotelFilterBtn = (ImageButton) findViewById(R.id.hotel_filter_btn);
        financeFilterBtn = (ImageButton) findViewById(R.id.finance_filter_btn);
        favoriteFilterBtn = (ImageButton) findViewById(R.id.favorite_filter_btn);
        shopsListView = (ListView) findViewById(R.id.shopsListView);


        //Adapteur
        adapter = new ShopAdapter(this, shops);
        shopsListView.setAdapter(adapter);

        // Events
        shopsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(ShopsActivity.this, "Fragement", Toast.LENGTH_SHORT).show();
            }
        });

        shopsListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(ShopsActivity.this, "add to favorites", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

        noFilterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.noFilter();
            }
        });

        foodFilterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.filterRestaurant();
            }
        });

        marketFilterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.filterMarket();
            }
        });

        hotelFilterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.filterHotel();
            }
        });

        financeFilterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.filterFinance();
            }
        });

        favoriteFilterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.filterFavorite();
            }
        });

        //Functions



    }
}
