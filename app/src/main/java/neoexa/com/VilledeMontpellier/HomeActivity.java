package neoexa.com.VilledeMontpellier;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class HomeActivity extends FragmentActivity implements OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //CardViews
        findViewById(R.id.profilecardid).setOnClickListener(this);
        findViewById(R.id.communitiescardid).setOnClickListener(this);
        findViewById(R.id.shopscardid).setOnClickListener(this);
        findViewById(R.id.calendarcardid).setOnClickListener(this);
        findViewById(R.id.admincardid).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()) {
            case R.id.profilecardid : i = new Intent(this, ProfileActivity.class); startActivity(i);break;
            case R.id.communitiescardid : i = new Intent(this, GroupsActivity.class); startActivity(i);break;
            case R.id.shopscardid : i = new Intent(this, ShopsActivity.class); startActivity(i);break;
            case R.id.calendarcardid : i = new Intent(this, CalendarActivity.class); startActivity(i);break;
            case R.id.admincardid : i = new Intent(this, AdminActivity.class); startActivity(i);break;
        }
    }
}
