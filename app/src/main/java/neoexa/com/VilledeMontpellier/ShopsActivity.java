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
        shops.add( new Shop("Hôtel Le Saint-Eloi", "27 Avenue du Professeur Grasset, 34090 Montpellier","43.624074, 3.867218", Shop.shopCat.hotel));
        shops.add( new Shop("Spizza 34", "Rue Hippolyte Rech, 34090 Montpellier","43.618725, 3.863433", Shop.shopCat.food));
        shops.add( new Shop("LCL Banque et Assurance", "69 Avenue de la Justice de Castelnau, 34090 Montpellier","43.627150, 3.870619", Shop.shopCat.finance));
        shops.add( new Shop("RU Triolet", "1061 Rue du Professeur Joseph Anglada, 34090 Montpellier","43.631398, 3.860227", Shop.shopCat.food));
        shops.add( new Shop("La Poste", "120 Rue Adrien Proby, 34090 Montpellier","43.631375, 3.856350", Shop.shopCat.finance));
        shops.add( new Shop("Self Copy", "100 Rue des Petetes, 34090 Montpellier","43.632019, 3.854548", Shop.shopCat.market));
        shops.add( new Shop("Hôtel Les Troènes", "17 Avenue Émile Bertin-Sans, 34090 Montpellier","43.629099, 3.863495", Shop.shopCat.hotel));
        shops.add( new Shop("Le Triolet", "Avenue Emile Diacon, 34090 Montpellier","43.628384, 3.859923", Shop.shopCat.market));
        shops.add( new Shop("Cité Universitaire Boutonnet", "119 Rue du Faubourg Boutonnet, 34090 Montpellier","43.623663, 3.871467", Shop.shopCat.hotel));
        shops.add( new Shop("100 Sushis", "49 Avenue du Professeur Grasset, 34090 Montpellier","43.626250, 3.866285", Shop.shopCat.food));
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
