package neoexa.com.VilledeMontpellier;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import neoexa.com.VilledeMontpellier.Model.Shop;
import neoexa.com.VilledeMontpellier.Model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.database.*;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView usernameTextView;
    private TextView telephoneTextView;
    private TextView addressTextView;

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //Input
        usernameTextView = (TextView) findViewById(R.id.usernameTV);
        telephoneTextView = (TextView) findViewById(R.id.telephoneTV);
        addressTextView = (TextView) findViewById(R.id.addressTV);

        //Button
        findViewById(R.id.profileBtn).setOnClickListener(this);

        //Database reference
        mDatabase = FirebaseDatabase.getInstance().getReference();

    }

    //validation du formulaire
    private boolean validateInput() {
        boolean valid = true;

        String username = usernameTextView.getText().toString();
        if (TextUtils.isEmpty(username)) {
            usernameTextView.setError("Requis.");
            valid = false;
        } else {
            usernameTextView.setError(null);
        }

        String telephone = telephoneTextView.getText().toString();
        if (TextUtils.isEmpty(telephone)) {
            telephoneTextView.setError("Requis.");
            valid = false;
        } else {
            telephoneTextView.setError(null);
        }

        String address = addressTextView.getText().toString();
        if (TextUtils.isEmpty(address)) {
            addressTextView.setError("Requis.");
            valid = false;
        } else {
            addressTextView.setError(null);
        }

        return valid;
    }

    private void registerUser(DatabaseReference mDatabase, String username, String telephone, String address){
        if (!validateInput()) {
            return;
        }
        FirebaseUser authUser = FirebaseAuth.getInstance().getCurrentUser();
        if (authUser != null) {
            String email = authUser.getEmail();
            String uid = authUser.getUid();
            User user = new User(username, email, telephone, address);
            mDatabase.child("users").child(uid).setValue(user);
            Intent toProfileIntent = new Intent (ProfileActivity.this, HomeActivity.class);
            ProfileActivity.this.startActivity(toProfileIntent);
        }
        else {
            Toast.makeText(ProfileActivity.this, "Erreur utilisateur non connecté !",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.profileBtn) {
            registerUser(mDatabase, usernameTextView.getText().toString(), telephoneTextView.getText().toString(), addressTextView.getText().toString());
        }

    }


}
