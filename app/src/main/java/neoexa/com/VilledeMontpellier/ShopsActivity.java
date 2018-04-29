package neoexa.com.VilledeMontpellier;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
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

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
    private ImageButton nearbyFilterBtn;


    private ListView shopsListView;
    private ArrayList<Shop> shops = new ArrayList<>();
    private ArrayList<Shop> favoriteShops = new ArrayList<>();
    private ShopAdapter adapter;

    private DatabaseReference mDatabase;
    private DatabaseReference mDatabaseFav;
    private String uid;


    public static final int LOCATION_REQUEST = 1;
    private FusedLocationProviderClient mFusedLocationClient;
    private Location currentLoc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shops);

        //Current User
        FirebaseUser authUser = FirebaseAuth.getInstance().getCurrentUser();
        if (authUser != null) {
            uid = authUser.getUid();
        } else {
            Toast.makeText(ShopsActivity.this, "Erreur utilisateur non connecté !",
                    Toast.LENGTH_SHORT).show();
        }


        //Location
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        //References
        searchView = (SearchView) findViewById(R.id.search_bar);
        noFilterBtn = (ImageButton) findViewById(R.id.noFilter_btn);
        foodFilterBtn = (ImageButton) findViewById(R.id.food_filter_btn);
        marketFilterBtn = (ImageButton) findViewById(R.id.market_filter_btn);
        hotelFilterBtn = (ImageButton) findViewById(R.id.hotel_filter_btn);
        financeFilterBtn = (ImageButton) findViewById(R.id.finance_filter_btn);
        favoriteFilterBtn = (ImageButton) findViewById(R.id.favorite_filter_btn);
        nearbyFilterBtn = (ImageButton) findViewById(R.id.nearby_filter_btn);
        shopsListView = (ListView) findViewById(R.id.shopsListView);

        //Database
        mDatabase = FirebaseDatabase.getInstance().getReference("shops");
        mDatabaseFav = FirebaseDatabase.getInstance().getReference("favorites/" + uid);

        //Listener on DB

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                shops.clear();

                for (DataSnapshot shopsSnapshot : dataSnapshot.getChildren()) {
                    Shop retrievedShop = shopsSnapshot.getValue(Shop.class);
                    shops.add(retrievedShop);
                }

                //Adapteur
                adapter = new ShopAdapter(ShopsActivity.this, shops, favoriteShops);
                shopsListView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mDatabaseFav.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                favoriteShops.clear();

                for (DataSnapshot shopsSnapshot : dataSnapshot.getChildren()) {
                    Shop favShop = shopsSnapshot.getValue(Shop.class);
                    favoriteShops.add(favShop);
                }

                //Adapteur
                adapter = new ShopAdapter(ShopsActivity.this, shops, favoriteShops);
                shopsListView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //Get last known location
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION},
                    LOCATION_REQUEST);
            return;
        }
        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            currentLoc = new Location("");
                            currentLoc.setLongitude(location.getLongitude());
                            currentLoc.setLatitude(location.getLatitude());
                        }
                    }
                });


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
                Shop clickedShop = (Shop) parent.getAdapter().getItem(position);
                String key = mDatabaseFav.push().getKey();
                mDatabaseFav.child(key).setValue(clickedShop);
                Toast.makeText(ShopsActivity.this, "Added to favorites", Toast.LENGTH_SHORT).show();
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

        nearbyFilterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.filterNearby(currentLoc);
            }
        });

        }
}
