package neoexa.com.VilledeMontpellier;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddEventActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView event_title;
    private TextView event_desc;
    private TextView event_address;
    private TextView event_begin;
    private TextView event_end;

    private DatabaseReference mDatabase;
    private FirebaseUser authUser;

    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        //TextViews
        event_title = (TextView) findViewById(R.id.event_title);
        event_desc = (TextView) findViewById(R.id.event_desc);
        event_address = (TextView) findViewById(R.id.event_address);
        event_begin = (TextView) findViewById(R.id.event_begin);
        event_end = (TextView) findViewById(R.id.event_end);

        //Button
        findViewById(R.id.addShopBtn).setOnClickListener(this);

        //Database reference
        mDatabase = FirebaseDatabase.getInstance().getReference();

        //Current User
        authUser = FirebaseAuth.getInstance().getCurrentUser();

        if (authUser != null) {
            uid = authUser.getUid();}
        else {
            Toast.makeText(AddEventActivity.this, "Erreur utilisateur non connecté !",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private boolean validateInput(){
        return true;
    }
    private void postEvent(){

    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.addEventBtn) {
            postEvent();
        }
    }
}
