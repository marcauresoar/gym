package com.gymproject.app.activities;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.EditText;

import com.gymproject.app.R;
import com.gymproject.app.dao.FichaDao;
import com.gymproject.app.models.Ficha;
import com.gymproject.app.sync.SyncService;
import com.gymproject.app.sync.event.SyncEvent;
import com.gymproject.app.sync.event.SyncStatus;
import com.gymproject.app.sync.event.SyncType;
import com.gymproject.app.utils.HashUtils;
import com.gymproject.app.utils.SessionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.realm.Realm;

public class CriarFichaActivity extends AppCompatActivity {

    Snackbar snackbar;
    CoordinatorLayout coordinatorLayout;

    EditText edtNome;
    CheckBox chkSegunda, chkTerca, chkQuarta, chkQuinta, chkSexta, chkSabado, chkDomingo;

    Realm realm;

    String id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_ficha);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        realm = Realm.getDefaultInstance();

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);

        edtNome = (EditText) findViewById(R.id.nome);
        chkSegunda = (CheckBox) findViewById(R.id.chkSegunda);
        chkTerca = (CheckBox) findViewById(R.id.chkTerca);
        chkQuarta = (CheckBox) findViewById(R.id.chkQuarta);
        chkQuinta = (CheckBox) findViewById(R.id.chkQuinta);
        chkSexta = (CheckBox) findViewById(R.id.chkSexta);
        chkSabado = (CheckBox) findViewById(R.id.chkSabado);
        chkDomingo = (CheckBox) findViewById(R.id.chkDomingo);

        Bundle b = getIntent().getExtras();
        if(b!=null){
            id = b.getString("id");
        }

        if(!id.isEmpty()) {
            preencherForm();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_criar_ficha, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_salvar:
                salvarRegistro();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void salvarRegistro(){
        String nome = edtNome.getText().toString();
        String dias_semana = buildDiasSemana();
        if(nome.isEmpty()){
            snackbar = Snackbar
                    .make(coordinatorLayout, "Por favor, preencha o campo Nome.", Snackbar.LENGTH_LONG);
            snackbar.show();
        } else if (dias_semana.isEmpty()){
            snackbar = Snackbar
                    .make(coordinatorLayout, "Por favor, selecione pelo menos 1 dia da semana.", Snackbar.LENGTH_LONG);
            snackbar.show();
        } else {
            // cria o registro e insere no realm
            Ficha ficha = new Ficha();
            String acao = "";
            if(!id.isEmpty()) {
                ficha.setId(id);
                acao = "update";
            }else{
                ficha.setId(HashUtils.generateId());
                acao = "insert";
            }
            ficha.setNome(nome);
            ficha.setDias_semana(dias_semana);
            ficha.setUsuario(SessionUtils.getInstance(getApplicationContext()).getUsuario());
            FichaDao.save(ficha, acao);

            // dispara sync e fecha activity
            SyncEvent.send(SyncType.FICHAS, SyncStatus.COMPLETED);
            SyncService.request(SyncType.FICHAS);
            finish();
        }
    }

    public String buildDiasSemana(){
        List<String> dias = new ArrayList<String>();
        if(chkSegunda.isChecked()) {
            dias.add("Segunda");
        }
        if(chkTerca.isChecked()) {
            dias.add("Terça");
        }
        if(chkQuarta.isChecked()) {
            dias.add("Quarta");
        }
        if(chkQuinta.isChecked()) {
            dias.add("Quinta");
        }
        if(chkSexta.isChecked()) {
            dias.add("Sexta");
        }
        if(chkSabado.isChecked()) {
            dias.add("Sabado");
        }
        if(chkDomingo.isChecked()) {
            dias.add("Domingo");
        }
        return TextUtils.join(",", dias);
    }

    public void preencherForm () {
        Ficha ficha = FichaDao.getById(realm, id);
        edtNome.setText(ficha.getNome());

        String diasSemana = ficha.getDias_semana();
        String[] diasArray = diasSemana.split(",");
        if(Arrays.asList(diasArray).contains("Segunda")){
            chkSegunda.setChecked(true);
        }
        if(Arrays.asList(diasArray).contains("Terça")){
            chkTerca.setChecked(true);
        }
        if(Arrays.asList(diasArray).contains("Quarta")){
            chkQuarta.setChecked(true);
        }
        if(Arrays.asList(diasArray).contains("Quinta")){
            chkQuinta.setChecked(true);
        }
        if(Arrays.asList(diasArray).contains("Sexta")){
            chkSexta.setChecked(true);
        }
        if(Arrays.asList(diasArray).contains("Sabado")){
            chkSabado.setChecked(true);
        }
        if(Arrays.asList(diasArray).contains("Domingo")){
            chkDomingo.setChecked(true);
        }
    }

}
