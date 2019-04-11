package com.example.ivykiai.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ivykiai.R;
import com.example.ivykiai.storage.SharedPreferenceManager;

public class HomeFragment extends Fragment {

    private TextView textViewEmail, textViewName, textViewLastName, textViewPhoneNumber, textViewPersonalCode;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textViewEmail = view.findViewById(R.id.textViewEmail);
        textViewName = view.findViewById(R.id.textViewName);
        textViewLastName = view.findViewById(R.id.textViewLastName);
        textViewPhoneNumber = view.findViewById(R.id.textViewPhoneNumber);
        textViewPersonalCode = view.findViewById(R.id.textViewPersonalCode);

        textViewEmail.setText(SharedPreferenceManager.getInstance(getActivity()).getUser().getEmail());
        textViewName.setText(SharedPreferenceManager.getInstance(getActivity()).getUser().getName());
        textViewLastName.setText(SharedPreferenceManager.getInstance(getActivity()).getUser().getLast_Name());
        textViewPhoneNumber.setText(SharedPreferenceManager.getInstance(getActivity()).getUser().getPhone_number());
        textViewPersonalCode.setText(SharedPreferenceManager.getInstance(getActivity()).getUser().getPersonal_code());
    }
}