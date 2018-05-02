package neoexa.com.VilledeMontpellier.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import neoexa.com.VilledeMontpellier.AddShopActivity;
import neoexa.com.VilledeMontpellier.R;

public class AdminShopsFragment extends Fragment implements View.OnClickListener {

    FloatingActionButton addShop;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_admin_shops, container, false);

        //Views
        rootView.findViewById(R.id.fab_add_shop).setOnClickListener(this);

        //Events


        return rootView;
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.fab_add_shop) {
            Intent toProfileIntent = new Intent (getActivity(), AddShopActivity.class);
            AdminShopsFragment.this.startActivity(toProfileIntent);
        }

    }
}
