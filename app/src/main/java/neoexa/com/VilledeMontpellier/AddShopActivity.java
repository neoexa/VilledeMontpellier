package neoexa.com.VilledeMontpellier;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import fr.ganfra.materialspinner.MaterialSpinner;
import neoexa.com.VilledeMontpellier.Model.Shop;

public class AddShopActivity extends AppCompatActivity implements View.OnClickListener {

    private MaterialSpinner categorySpinner;
    private TextView nameTextView;
    private TextView addressTextView;
    private String selectedCategory;
    private ProgressDialog pd;
    private String uid;

    private DatabaseReference mDatabase;

    List<String> categories = new ArrayList<String>();
    ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_shop);
        initCategoriesArray();

        //Spinner&Adapter
        categorySpinner = (MaterialSpinner) findViewById(R.id.catSpinner);
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(adapter);

        //TextViews
        nameTextView = (TextView) findViewById(R.id.newName);
        addressTextView =(TextView) findViewById(R.id.newAddress);

        //Button
        findViewById(R.id.addShopBtn).setOnClickListener(this);

        //Database reference
        mDatabase = FirebaseDatabase.getInstance().getReference();

        //Current User
        FirebaseUser authUser = FirebaseAuth.getInstance().getCurrentUser();
        if (authUser != null) {
            uid = authUser.getUid();}
        else {
            Toast.makeText(AddShopActivity.this, "Erreur utilisateur non connecté !",
                    Toast.LENGTH_SHORT).show();
        }


        //Events
        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position != -1){
                    selectedCategory = categorySpinner.getItemAtPosition(position).toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



    }

    private boolean validateInput(){
        boolean valid = true;

        if (TextUtils.isEmpty(selectedCategory)) {
            valid = false;
        }

        String name = nameTextView.getText().toString();
        if (TextUtils.isEmpty(name)) {
            nameTextView.setError("Requis.");
            valid = false;
        } else {
            nameTextView.setError(null);
        }

        String adress = addressTextView.getText().toString();

        if (TextUtils.isEmpty(adress)) {
            addressTextView.setError("Requis.");
            valid = false;
        } else {
            addressTextView.setError(null);
        }

        return valid;
    }
    private void initCategoriesArray() {
        categories.add("finance");
        categories.add("hotel");
        categories.add("market");
        categories.add("food");
    }
    private void postShop(DatabaseReference mDatabase, String name, String address, String category){
        String key = mDatabase.child("shops").push().getKey();
        Log.e("Key", key);
        Shop newShop = new Shop(uid, name, address, category);
        mDatabase.child("shops").child(key).setValue(newShop);
        Intent toProfileIntent = new Intent (AddShopActivity.this, AdminActivity.class);
        AddShopActivity.this.startActivity(toProfileIntent);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.addShopBtn) {
            postShop(mDatabase, nameTextView.getText().toString(), addressTextView.getText().toString(), selectedCategory);
        }

    }
}
