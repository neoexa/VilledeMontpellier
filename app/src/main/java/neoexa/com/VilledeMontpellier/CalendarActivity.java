package neoexa.com.VilledeMontpellier;

import android.content.Intent;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Query;
import com.google.firebase.database.Transaction;

import neoexa.com.VilledeMontpellier.Model.Event;
import neoexa.com.VilledeMontpellier.viewholder.EventViewHolder;

public class CalendarActivity extends AppCompatActivity {

    private static final String TAG = "CalendarActivity";

    private DatabaseReference mDatabase;

    private FirebaseRecyclerAdapter<Event, EventViewHolder> mAdapter;
    private RecyclerView mRecycler;
    private LinearLayoutManager mManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        mRecycler = (RecyclerView) findViewById(R.id.events_list);
        mRecycler.setHasFixedSize(true);

        mManager = new LinearLayoutManager(this);
        mManager.setReverseLayout(true);
        mManager.setStackFromEnd(true);
        mRecycler.setLayoutManager(mManager);

        // Set up FirebaseRecyclerAdapter with the Query
        Query postsQuery = getQuery(mDatabase);

        FirebaseRecyclerOptions options = new FirebaseRecyclerOptions.Builder<Event>()
                .setQuery(postsQuery, Event.class)
                .build();

        mAdapter = new FirebaseRecyclerAdapter<Event, EventViewHolder>(options) {

            @Override
            public EventViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
                LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
                return new EventViewHolder(inflater.inflate(R.layout.item_event, viewGroup, false));
            }

            @Override
            protected void onBindViewHolder(EventViewHolder viewHolder, int position, final Event model) {
                final DatabaseReference eventRef = getRef(position);

                // Set click listener for the whole event view
                final String postKey = eventRef.getKey();
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Launch CALENDARaCTIVITY
                        addEvent(v);
                    }
                });



                // Bind Event to ViewHolder, setting OnClickListener
                viewHolder.bindToEvent(model, new View.OnClickListener() {
                    @Override
                    public void onClick(View cardViewBtn) {

                        DatabaseReference globalEventRef = mDatabase.child("events").child(eventRef.getKey());
                        DatabaseReference userEventRef = mDatabase.child("user-events").child(model.uid).child(eventRef.getKey());

                        // Run two transactions
                        onAddCalendarClicked(globalEventRef);
                        onAddCalendarClicked(userEventRef);
                    }
                });
            }
        };
        mRecycler.setAdapter(mAdapter);

    }

    @Override
    public void onStart() {
        super.onStart();
        if (mAdapter != null) {
            mAdapter.startListening();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAdapter != null) {
            mAdapter.stopListening();
        }
    }


    public void addEvent(View v){
        Intent intent = new Intent(Intent.ACTION_INSERT)
                .setData(CalendarContract.Events.CONTENT_URI);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }

    public String getUid() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    public Query getQuery (DatabaseReference databaseReference){
        Query q = databaseReference.child("events").limitToFirst(10);
        return q;
    }

    private void onAddCalendarClicked(DatabaseReference eventRef) {
        eventRef.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                Event e = mutableData.getValue(Event.class);
                if (e == null) {
                    return Transaction.success(mutableData);
                }

                if (e.people.containsKey(getUid())) {
                    // Uncheck the event and remove self from people
                    e.people.remove(getUid());
                } else {
                    // Check the post and add self to people
                    e.peopleCount = e.peopleCount + 1;
                    e.people.put(getUid(), true);
                }

                // Set value and report transaction success
                mutableData.setValue(e);
                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean b,
                                   DataSnapshot dataSnapshot) {
                // Transaction completed
                Log.d(TAG, "eventTransaction:onComplete:" + databaseError);
            }
        });

    }

}


