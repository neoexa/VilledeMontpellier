package neoexa.com.VilledeMontpellier.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import neoexa.com.VilledeMontpellier.AddEventActivity;
import neoexa.com.VilledeMontpellier.Model.Event;
import neoexa.com.VilledeMontpellier.R;
import neoexa.com.VilledeMontpellier.viewholder.EventViewHolder;

public class AdminEventFragment extends Fragment implements View.OnClickListener{
    private String uid;
    private DatabaseReference mDatabase;
    private FirebaseRecyclerAdapter<Event, EventViewHolder> mAdapter;
    private RecyclerView mRecycler;
    private LinearLayoutManager mManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_admin_events, container, false);

        //Views
        rootView.findViewById(R.id.fab_add_event).setOnClickListener(this);



        mDatabase = FirebaseDatabase.getInstance().getReference();

        mRecycler = (RecyclerView) rootView.findViewById(R.id.admin_events_list);
        mRecycler.setHasFixedSize(true);

        mManager = new LinearLayoutManager(getActivity());
        mManager.setReverseLayout(true);
        mManager.setStackFromEnd(true);
        mRecycler.setLayoutManager(mManager);


        // Set up FirebaseRecyclerAdapter with the Query

        Query eventsQuery = mDatabase.child("events").limitToFirst(1);

        FirebaseRecyclerOptions options = new FirebaseRecyclerOptions.Builder<Event>()
                .setQuery(eventsQuery, Event.class)
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
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //todo
                    }
                });

                // Bind Event to ViewHolder, setting OnClickListener
                viewHolder.bindToEvent(model, new View.OnClickListener() {
                    @Override
                    public void onClick(View cardViewBtn) {
                        //todo
                    }
                });
            }
        };
        mRecycler.setAdapter(mAdapter);


        return rootView;
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

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.fab_add_event) {
            Intent intent = new Intent(getActivity(), AddEventActivity.class);
            AdminEventFragment.this.startActivity(intent);
        }
    }
}
