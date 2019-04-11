package com.example.ivykiai.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ivykiai.R;
import com.example.ivykiai.api.RetrofitClient;
import com.example.ivykiai.models.DefaultResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.example.ivykiai.storage.SharedPreferenceManager;



public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextEmail, editTextPassword, editTextName, editTextLastName, editTextPhoneNumber, editTextPersonalCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextName = findViewById(R.id.editTextName);
        editTextLastName = findViewById(R.id.editTextLastName);
        editTextPhoneNumber = findViewById(R.id.editTextPhoneNumber);
        editTextPersonalCode = findViewById(R.id.editTextPersonalCode);


        findViewById(R.id.buttonSignUp).setOnClickListener(this);
        findViewById(R.id.textViewLogin).setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(SharedPreferenceManager.getInstance(this).isLoggedIn()){
            Intent intent = new Intent(this, ProfileActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }


    private void userSignUp() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
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

        if (password.isEmpty()) {
            editTextPassword.setError("Įveskite slaptažodį!");
            editTextPassword.requestFocus();
            return;
        }

        if (password.length() < 6) {
            editTextPassword.setError("Slaptažodis turi būti bent 6 simbolių!");
            editTextPassword.requestFocus();
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
            editTextPhoneNumber.setError("Įveskite telefono numerį!");
            editTextPhoneNumber.requestFocus();
            return;
        }
        if (personal_code.isEmpty()) {
            editTextPersonalCode.setError("Įveskite asmens kodą!");
            editTextPersonalCode.requestFocus();
            return;
        }

        Call<DefaultResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .createUser(email, password, name, last_name, phone_number, personal_code);


        call.enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                if (response.code() == 201) {

                    DefaultResponse dr = response.body();
                    Toast.makeText(MainActivity.this, dr.getMsg(), Toast.LENGTH_LONG).show();

                } else if (response.code() == 422) {
                    Toast.makeText(MainActivity.this, "Toks vartotojas jau egzistuoja!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<DefaultResponse> call, Throwable t) {

                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });


    }




    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonSignUp:
                userSignUp();
                break;
            case R.id.textViewLogin:

                startActivity(new Intent(MainActivity.this, LoginActivity.class));

                break;
        }
    }
}