package com.TeamT3N.fukiosk.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.TeamT3N.fukiosk.R;
import com.TeamT3N.fukiosk.ui.dashboard.colleges.CAS;
import com.TeamT3N.fukiosk.ui.dashboard.colleges.CAgri;
import com.TeamT3N.fukiosk.ui.dashboard.colleges.CBA;
import com.TeamT3N.fukiosk.ui.dashboard.colleges.CC;
import com.TeamT3N.fukiosk.ui.dashboard.colleges.CCS;
import com.TeamT3N.fukiosk.ui.dashboard.colleges.CE;
import com.TeamT3N.fukiosk.ui.dashboard.colleges.CHM;
import com.TeamT3N.fukiosk.ui.dashboard.colleges.CN;
import com.TeamT3N.fukiosk.ui.dashboard.colleges.DAFa;
import com.TeamT3N.fukiosk.ui.dashboard.colleges.SIE;

public class DashboardFragment extends Fragment {
    private DashboardViewModel dashboardViewModel;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel = new
                ViewModelProvider(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);



        final TextView textView = root.findViewById(R.id.text_dashboard);
        dashboardViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {

            // For Colleges button
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);

                Button rellay_ccs = getView().findViewById(R.id.ccs);
                Button rellay_cba = getView().findViewById(R.id.cba);
                Button rellay_cc = getView().findViewById(R.id.cc);
                Button rellay_cas = getView().findViewById(R.id.cas);
                Button rellay_sie = getView().findViewById(R.id.sie);
                Button rellay_ca = getView().findViewById(R.id.ca);
                Button rellay_dafa = getView().findViewById(R.id.dafa);
                Button rellay_cn = getView().findViewById(R.id.cn);
                Button rellay_chm = getView().findViewById(R.id.chm);
                Button rellay_ce = getView().findViewById(R.id.ce);


                rellay_ccs.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(getActivity(), CCS.class);
                        startActivity(i);
                    }
                });
                rellay_cba.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(getActivity(), CBA.class);
                        startActivity(i);
                    }
                });
                rellay_cc.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(getActivity(), CC.class);
                        startActivity(i);
                    }
                });
                rellay_cas.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(getActivity(), CAS.class);
                        startActivity(i);
                    }
                });
                rellay_sie.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(getActivity(), SIE.class);
                        startActivity(i);
                    }
                });
                rellay_ca.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(getActivity(), CAgri.class);
                        startActivity(i);
                    }
                });
                rellay_dafa.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(getActivity(), DAFa.class);
                        startActivity(i);
                    }
                });
                rellay_cn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(getActivity(), CN.class);
                        startActivity(i);
                    }
                });
                rellay_chm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(getActivity(), CHM.class);
                        startActivity(i);
                    }
                });
                rellay_ce.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(getActivity(), CE.class);
                        startActivity(i);
                    }
                });

            }
            //End button function
        });

        return root;
    }
}
