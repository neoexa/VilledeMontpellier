package neoexa.com.VilledeMontpellier.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import neoexa.com.VilledeMontpellier.AddEventActivity;
import neoexa.com.VilledeMontpellier.R;

public class AdminEventFragment extends Fragment implements View.OnClickListener{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_admin_events, container, false);

        //Views
        rootView.findViewById(R.id.fab_add_event).setOnClickListener(this);



        return rootView;
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
