package neoexa.com.VilledeMontpellier;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TableLayout;
import android.widget.Toast;

public class AdminActivity extends AppCompatActivity implements View.OnClickListener {
    TabLayout tab;
    int currentTab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        currentTab = 0;

        //Tab layout
        tab = findViewById(R.id.tabs);
        tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                currentTab = tab.getPosition();
                Toast.makeText(AdminActivity.this, "Pos :"+currentTab, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        //FAButton
        findViewById(R.id.fab).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.fab) {
            if (currentTab == 1){
                Intent toProfileIntent = new Intent (AdminActivity.this, AddEventActivity.class);
                AdminActivity.this.startActivity(toProfileIntent);}
            else {
                Intent toProfileIntent = new Intent (AdminActivity.this, AddShopActivity.class);
                AdminActivity.this.startActivity(toProfileIntent);
            }
        }

    }
}
