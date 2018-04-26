package neoexa.com.VilledeMontpellier;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class AdminActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        findViewById(R.id.fab).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.fab) {
            Intent toProfileIntent = new Intent (AdminActivity.this, AddShopActivity.class);
            AdminActivity.this.startActivity(toProfileIntent);
        }

    }
}
