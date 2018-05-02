package neoexa.com.VilledeMontpellier;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Date;

import neoexa.com.VilledeMontpellier.Model.Event;

public class AddEventActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView event_title;
    private TextView event_desc;
    private TextView event_address;
    private TextView event_begin;
    private TextView event_end;
    private Date eBegin;
    private Date eEnd;

    private DatePickerDialog.OnDateSetListener beginDateSetListener;
    private DatePickerDialog.OnDateSetListener endDateSetListener;

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

        //DatePicker
        event_begin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int day = cal.get(Calendar.DAY_OF_MONTH);
                int month = cal.get(Calendar.MONTH);
                int year = cal.get(Calendar.YEAR);

                DatePickerDialog dialog =   new DatePickerDialog(AddEventActivity.this, R.style.NeoTheme, beginDateSetListener, year, month, day);
                dialog.show();
            }
        });

        beginDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                int mMonth = month + 1;
                eBegin = new Date(year , mMonth, dayOfMonth);
                String bd = dayOfMonth + "/" + mMonth + "/" + year;
                event_begin.setText(bd);
            }
        };

        event_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int day = cal.get(Calendar.DAY_OF_MONTH);
                int month = cal.get(Calendar.MONTH);
                int year = cal.get(Calendar.YEAR);

                DatePickerDialog dialog =   new DatePickerDialog(AddEventActivity.this, R.style.NeoTheme, endDateSetListener, year, month, day);
                dialog.show();

            }
        });

        endDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                int mMonth = month + 1;
                eEnd = new Date(year, mMonth, dayOfMonth);
                String bd = dayOfMonth + "/" + mMonth + "/" + year;
                event_end.setText(bd);
            }
        };

        //Button
        findViewById(R.id.addEventBtn).setOnClickListener(this);

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
        boolean valid = true;

        String title = event_title.getText().toString();
        if (TextUtils.isEmpty(title)) {
            event_title.setError("Requis.");
            valid = false;
        } else {
            event_title.setError(null);
        }

        String address = event_address.getText().toString();
        if (TextUtils.isEmpty(address)) {
            event_address.setError("Requis.");
            valid = false;
        } else {
            event_address.setError(null);
        }

        if (eBegin.toString() == null) {
            event_begin.setError("Requis.");
            valid = false;
        } else {
            event_begin.setError(null);
        }

        return valid;

    }

    private void postEvent(){
        if (!validateInput()) {
            return;
        }
        String title = event_title.getText().toString();
        String desc = event_desc.getText().toString();
        String address = event_address.getText().toString();

        String key = mDatabase.child("events").push().getKey();
        Event newEvent = new Event(uid, title, desc, address, eBegin, eEnd);
        mDatabase.child("events").child(key).setValue(newEvent);
        Intent toProfileIntent = new Intent (AddEventActivity.this, AdminActivity.class);
        AddEventActivity.this.startActivity(toProfileIntent);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.addEventBtn){
            postEvent();
        }
    }
}
