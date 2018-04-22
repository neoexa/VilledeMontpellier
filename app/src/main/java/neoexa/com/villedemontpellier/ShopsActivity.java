package neoexa.com.villedemontpellier;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageButton;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;

import neoexa.com.villedemontpellier.Model.Shop;
import neoexa.com.villedemontpellier.Model.ShopAdapter;


public class ShopsActivity extends AppCompatActivity {
    private SearchView searchView;
    private ImageButton foodFilter;
    private ImageButton marketFilter;
    private ImageButton hotelFilter;
    private ImageButton financeFilter;
    private ImageButton favoriteFilter;


    private ListView shopsListView ;
    private ArrayList<Shop> shops = new ArrayList<>() ;
    private ShopAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shops);

        //References
        searchView = (SearchView) findViewById(R.id.search_bar);
        foodFilter = (ImageButton) findViewById(R.id.food_filter_btn);
        marketFilter = (ImageButton) findViewById(R.id.market_filter_btn);
        hotelFilter = (ImageButton) findViewById(R.id.hotel_filter_btn);
        financeFilter = (ImageButton) findViewById(R.id.finance_filter_btn);
        favoriteFilter = (ImageButton) findViewById(R.id.favorite_filter_btn);
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
        //Evenements
        shopsListView.setOnItemClickListener((parent, view, position, id) -> {
            Toast.makeText(ShopsActivity.this,"click to item" + position, Toast.LENGTH_SHORT).show();
        });
    }
}
