package neoexa.com.VilledeMontpellier.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import neoexa.com.VilledeMontpellier.AddShopActivity;
import neoexa.com.VilledeMontpellier.Model.Shop;
import neoexa.com.VilledeMontpellier.Model.ShopAdapter;
import neoexa.com.VilledeMontpellier.R;
import neoexa.com.VilledeMontpellier.ShopsActivity;

public class AdminShopsFragment extends Fragment implements View.OnClickListener {

    FloatingActionButton addShop;
    private String uid;
    private DatabaseReference mDatabase;

    private ListView shopsListView;
    private ArrayList<Shop> myShops = new ArrayList<>();
    private ShopAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_admin_shops, container, false);

        //Current user
        FirebaseUser authUser = FirebaseAuth.getInstance().getCurrentUser();
        if (authUser != null) {
            uid = authUser.getUid();
        } else {
            Toast.makeText(getActivity(), "Erreur utilisateur non connecté !",
                    Toast.LENGTH_SHORT).show();
        }

        //Database
        mDatabase = FirebaseDatabase.getInstance().getReference("shops");


        //Views
        rootView.findViewById(R.id.fab_add_shop).setOnClickListener(this);
        shopsListView = (ListView) rootView.findViewById(R.id.adminshoplv);

        //Events
        //Listener on DB
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                myShops.clear();

                for (DataSnapshot shopsSnapshot : dataSnapshot.getChildren()) {
                    Shop retrievedShop = shopsSnapshot.getValue(Shop.class);
                    if (retrievedShop.userId == uid){
                        myShops.add(retrievedShop);}
                }

                //Adapteur
                adapter = new ShopAdapter(getActivity(), myShops, myShops);
                shopsListView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return rootView;
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.fab_add_shop) {
            Intent toProfileIntent = new Intent (getActivity(), AddShopActivity.class);
            AdminShopsFragment.this.startActivity(toProfileIntent);
        }

    }
}
