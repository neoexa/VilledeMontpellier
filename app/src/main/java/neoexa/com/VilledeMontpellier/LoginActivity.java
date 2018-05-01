package neoexa.com.VilledeMontpellier;


import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;
import android.text.TextUtils;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import android.support.annotation.NonNull;
import android.content.Intent;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText editTextEmail;
    private EditText editTextPassword;


    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    private ProgressDialog pd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Views
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);

        //Buttons
        findViewById(R.id.loginButton).setOnClickListener(this);
        findViewById(R.id.textViewNewuser).setOnClickListener(this);

        //Service Auth & DB
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        pd = new ProgressDialog(LoginActivity.this);

    }

    @Override
    public void onStart() {
        super.onStart();

        // Check auth on Activity start
        if (mAuth.getCurrentUser() != null) {
            // Go to HomeActivity
            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
        }
    }



    //validation du formulaire
    private boolean validateInput() {
        boolean valid = true;

        String email = editTextEmail.getText().toString();
        if (TextUtils.isEmpty(email)) {
            editTextEmail.setError("Requis.");
            valid = false;
        } else {
            editTextEmail.setError(null);
        }

        String password = editTextPassword.getText().toString();
        if (TextUtils.isEmpty(password)) {
            editTextPassword.setError("Requis.");
            valid = false;
        } else {
            editTextPassword.setError(null);
        }

        return valid;
    }


    // connexion
    private void signIn() {
        if (!validateInput()) {
            return;
        }

        pd.show();
        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        pd.hide();
                        if (task.isSuccessful()) {
                            Intent toHomeIntent = new Intent (LoginActivity.this, HomeActivity.class);
                            LoginActivity.this.startActivity(toHomeIntent);
                        } else {
                            Toast.makeText(LoginActivity.this, "Connexion échoué.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    //New user
    private void signUp() {
        if (!validateInput()) {
            return;
        }

        pd.show();
        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        pd.hide();

                        if (task.isSuccessful()) {
                            Intent toProfileIntent = new Intent (LoginActivity.this, ProfileActivity.class);
                            LoginActivity.this.startActivity(toProfileIntent);
                        } else {
                            Toast.makeText(LoginActivity.this, "Sign Up Failed",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.loginButton) {
            signIn();
        } else if (i == R.id.textViewNewuser){
            signUp();
        }

    }
}
