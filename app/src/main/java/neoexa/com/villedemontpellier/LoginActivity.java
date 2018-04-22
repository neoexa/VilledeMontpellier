package neoexa.com.villedemontpellier;


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
import com.google.firebase.auth.FirebaseUser;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText editTextEmail;
    private EditText editTextPassword;

    private FirebaseAuth mAuth;


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

        //Service Auth
        mAuth = FirebaseAuth.getInstance();
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
    private void signIn(String email, String password) {
        if (!validateInput()) {
            return;
        }
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Connexion Réussi
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent toHomeIntent = new Intent (LoginActivity.this, HomeActivity.class);
                            LoginActivity.this.startActivity(toHomeIntent);
                        } else {
                            // fail!!
                            Toast.makeText(LoginActivity.this, "Connexion échoué.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }


    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.loginButton) {
            signIn(editTextEmail.getText().toString(), editTextPassword.getText().toString());
        } else if (i == R.id.textViewNewuser){
            Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
            LoginActivity.this.startActivity(registerIntent);
        }

    }
}
