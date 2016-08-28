package com.gymproject.app.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.gymproject.app.R;
import com.gymproject.app.activities.DashboardActivity;
import com.gymproject.app.classes.Status;
import com.gymproject.app.models.Usuario;
import com.gymproject.app.restful.RestfulAPI;
import com.gymproject.app.services.AcessoService;
import com.gymproject.app.utils.SessionUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginFragment extends Fragment{

    ProgressDialog progress;

    EditText edtEmail;
    EditText edtSenha;
    Button btnLogin;

    Snackbar snackbar;

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

        progress = ProgressDialog.show(getContext(), "Por favor, aguarde.",
                "Os dados estão sendo validados...", false);

        String email = edtEmail.getText().toString();
        String senha = edtSenha.getText().toString();

        AcessoService apiService =
                RestfulAPI.getClient().create(AcessoService.class);

        Call<Status<Usuario>> call = apiService.autenticarLogin(email, senha);
        call.enqueue(new Callback<Status<Usuario>>() {
            @Override
            public void onResponse(Call<Status<Usuario>>call, Response<Status<Usuario>> response) {
                Status status = response.body();
                if(status != null) {
                    if(status.getCodigo() == 1) {
                        Usuario usuario = (Usuario) status.getDados();
                        if (usuario.getId() > 0) {
                            SessionUtils.getInstance(getContext()).createLoginSession(usuario.getId().toString());
                            Intent intent = new Intent(getContext(), DashboardActivity.class);
                            startActivity(intent);
                            getActivity().finish();
                        }else {
                            onFailure(call, new Throwable("Erro com os dados retornados do servidor!"));
                        }
                    } else {
                        snackbar = Snackbar
                                .make(getView(), status.getMensagem(), Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
                }else {
                    onFailure(call, new Throwable("Erro com os dados retornados do servidor!"));
                }
                progress.dismiss();
            }

            @Override
            public void onFailure(Call<Status<Usuario>>call, Throwable t) {
                Log.e("autenticarLogin", t.toString());
                snackbar = Snackbar
                        .make(getView(), "Houve um erro ao tentar realizar esta ação!", Snackbar.LENGTH_LONG);
                snackbar.show();
                progress.dismiss();
            }
        });

    }

}