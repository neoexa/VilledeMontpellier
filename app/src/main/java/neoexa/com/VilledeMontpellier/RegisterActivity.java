package neoexa.com.VilledeMontpellier;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.text.TextUtils;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import android.support.annotation.NonNull;
import android.content.Intent;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity implements OnClickListener {

    private EditText editTextEmail;
    private EditText editTextPassword;

    private FirebaseAuth mAuth;

    private ProgressDialog pd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Views
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);

        //Button
        findViewById(R.id.registerButton).setOnClickListener(this);

        //Service Auth
        mAuth = FirebaseAuth.getInstance();

        //Loadingbar
         pd = new ProgressDialog(this);
         pd.setMessage("Chargement"); // mettre en ressource!
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

    private void registerAccount(String email, String password) {

        if (!validateInput()) {
            return;
            //si formulaire non valide sortie immédiate
        }

        pd.show();

        // Création compte
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent toProfileIntent = new Intent (RegisterActivity.this, ProfileActivity.class);
                            RegisterActivity.this.startActivity(toProfileIntent);

                        } else {
                            // fail!!!
                            Toast.makeText(RegisterActivity.this, "Connection échoué!.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        pd.hide();
                    }
                });
    }


    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.registerButton) {
            registerAccount(editTextEmail.getText().toString(), editTextPassword.getText().toString());
        }

    }
}
