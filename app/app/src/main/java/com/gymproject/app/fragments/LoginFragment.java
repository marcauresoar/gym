package com.gymproject.app.fragments;

import android.graphics.Movie;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.gymproject.app.R;
import com.gymproject.app.classes.Status;
import com.gymproject.app.restful.RestfulAPI;
import com.gymproject.app.services.AcessoService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginFragment extends Fragment{

    EditText edtEmail;
    EditText edtSenha;
    Button btnLogin;

    public LoginFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        edtEmail = (EditText) view.findViewById(R.id.edtEmail);
        edtSenha = (EditText) view.findViewById(R.id.edtSenha);

        btnLogin = (Button) view.findViewById(R.id.btnAutenticar);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autenticarLogin();
            }
        });
    }

    public void autenticarLogin () {

        String email = edtEmail.getText().toString();
        String senha = edtSenha.getText().toString();

        AcessoService apiService =
                RestfulAPI.getClient().create(AcessoService.class);

        Call<Status> call = apiService.autenticarLogin(email, senha);
        call.enqueue(new Callback<Status>() {
            @Override
            public void onResponse(Call<Status>call, Response<Status> response) {
                Status status = response.body();
                if(status != null) {
                    Toast.makeText(getContext(), status.getMensagem(), Toast.LENGTH_SHORT).show();
                } else {
                    Log.i("ERRO", "Objeto nulo");
                }
                //Log.d("AuthLogin", status.getMensagem());
            }

            @Override
            public void onFailure(Call<Status>call, Throwable t) {
                // Log error here since request failed
                Log.e("AuthLogin", t.toString());
            }
        });

    }

}