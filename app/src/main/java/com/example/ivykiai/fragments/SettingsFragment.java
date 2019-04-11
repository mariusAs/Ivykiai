package com.example.ivykiai.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Patterns;
import android.widget.EditText;
import android.widget.Toast;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;

import com.example.ivykiai.R;
import com.example.ivykiai.api.RetrofitClient;
import com.example.ivykiai.models.User;
import com.example.ivykiai.models.LoginResponse;
import com.example.ivykiai.storage.SharedPreferenceManager;
import com.example.ivykiai.activities.LoginActivity;
import com.example.ivykiai.activities.MainActivity;
import com.example.ivykiai.models.DefaultResponse;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingsFragment extends Fragment implements View.OnClickListener {

    private EditText editTextEmail, editTextName, editTextLastName, editTextPhoneNumber, editTextPersonalCode;
    private EditText editTextCurrentPassword, editTextNewPassword;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.settings_fragment, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editTextEmail = view.findViewById(R.id.editTextEmail);
        editTextName = view.findViewById(R.id.editTextName);
        editTextLastName = view.findViewById(R.id.editTextLastName);
        editTextPhoneNumber = view.findViewById(R.id.editTextPhoneNumber);
        editTextPersonalCode = view.findViewById(R.id.editTextPersonalCode);
        editTextCurrentPassword = view.findViewById(R.id.editTextCurrentPassword);
        editTextNewPassword = view.findViewById(R.id.editTextNewPassword);

        view.findViewById(R.id.buttonSave).setOnClickListener(this);
        view.findViewById(R.id.buttonChangePassword).setOnClickListener(this);
        view.findViewById(R.id.buttonLogout).setOnClickListener(this);
        //view.findViewById(R.id.buttonDelete).setOnClickListener(this);
    }

    private void updateProfile() {
        String email = editTextEmail.getText().toString().trim();
        String name = editTextName.getText().toString().trim();
        String last_name = editTextLastName.getText().toString().trim();
        String phone_number = editTextPhoneNumber.getText().toString().trim();
        String personal_code = editTextPersonalCode.getText().toString().trim();

        if (email.isEmpty()) {
            editTextEmail.setError("Įveskite el. paštą!");
            editTextEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Toks el. paštas jau egzistuoja!");
            editTextEmail.requestFocus();
            return;
        }

        if (name.isEmpty()) {
            editTextName.setError("Įveskite vardą!");
            editTextName.requestFocus();
            return;
        }

        if (last_name.isEmpty()) {
            editTextLastName.setError("Įveskite pavardę!");
            editTextLastName.requestFocus();
            return;
        }

        if (phone_number.isEmpty()) {
            editTextPhoneNumber.setError("Įveskiite telefono numerį!");
            editTextPhoneNumber.requestFocus();
            return;
        }

        if (personal_code.isEmpty()) {
            editTextPersonalCode.setError("Įveskite asmens kodą!");
            editTextPersonalCode.requestFocus();
            return;
        }


        User user = SharedPreferenceManager.getInstance(getActivity()).getUser();

        Call<LoginResponse> call = RetrofitClient.getInstance()
                .getApi().updateUser(
                        user.getId(),
                        email,
                        name,
                        last_name,
                        phone_number,
                        personal_code
                );

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_LONG).show();

                if (!response.body().isError()) {
                    SharedPreferenceManager.getInstance(getActivity()).saveUser(response.body().getUser());
                }

            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {

            }
        });
    }

    private void updatePassword() {
        String currentpassword = editTextCurrentPassword.getText().toString().trim();
        String newpassword = editTextNewPassword.getText().toString().trim();

        if (currentpassword.isEmpty()) {
            editTextCurrentPassword.setError("Įveskite slaptažodį!");
            editTextCurrentPassword.requestFocus();
            return;
        }

        if (newpassword.isEmpty()) {
            editTextNewPassword.setError("Įveskite naują slaptažodį!");
            editTextNewPassword.requestFocus();
            return;
        }


        User user = SharedPreferenceManager.getInstance(getActivity()).getUser();

        Call<DefaultResponse> call = RetrofitClient.getInstance().getApi()
                .updatePassword(currentpassword, newpassword, user.getEmail());

        call.enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {

                Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<DefaultResponse> call, Throwable t) {

            }
        });
    }

    private void logout() {
        SharedPreferenceManager.getInstance(getActivity()).clear();
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void deleteUser() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Are you sure?");
        builder.setMessage("This action is irreversible...");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                User user = SharedPreferenceManager.getInstance(getActivity()).getUser();
                Call<DefaultResponse> call = RetrofitClient.getInstance().getApi().deleteUser(user.getId());

                call.enqueue(new Callback<DefaultResponse>() {
                    @Override
                    public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {

                        if (!response.body().isErr()) {
                            SharedPreferenceManager.getInstance(getActivity()).clear();
                            SharedPreferenceManager.getInstance(getActivity()).clear();
                            Intent intent = new Intent(getActivity(), MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }

                        Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(Call<DefaultResponse> call, Throwable t) {

                    }
                });

            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog ad = builder.create();
        ad.show();
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonSave:
                updateProfile();
                break;
            case R.id.buttonChangePassword:
                updatePassword();
                break;
            case R.id.buttonLogout:
                logout();
                break;
            //case R.id.buttonDelete:
              //  deleteUser();
                //break;
        }
    }
}
