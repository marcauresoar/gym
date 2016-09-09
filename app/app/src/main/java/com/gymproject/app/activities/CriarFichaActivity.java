package com.gymproject.app.activities;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.gymproject.app.R;
import com.gymproject.app.models.UpdateFicha;
import com.gymproject.app.dao.FichaDao;
import com.gymproject.app.dao.UpdateFichaDao;
import com.gymproject.app.models.Ficha;
import com.gymproject.app.sync.SyncService;
import com.gymproject.app.sync.event.SyncEvent;
import com.gymproject.app.sync.event.SyncStatus;
import com.gymproject.app.sync.event.SyncType;
import com.gymproject.app.utils.SessionUtils;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;

public class CriarFichaActivity extends AppCompatActivity {

    Snackbar snackbar;
    CoordinatorLayout coordinatorLayout;

    EditText edtNome;
    CheckBox chkSegunda, chkTerca, chkQuarta, chkQuinta, chkSexta, chkSabado, chkDomingo;

    Realm realm;

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

        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                criarFicha();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void criarFicha(){
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
            realm.beginTransaction();

            Ficha ficha = new Ficha();
            ficha.setNome(nome);
            ficha.setDias_semana(dias_semana);
            ficha.setUsuario(SessionUtils.getInstance(getApplicationContext()).getUsuario());

            realm.copyToRealmOrUpdate(ficha);

            UpdateFicha update = new UpdateFicha();
            update.setAcao("insert");
            update.setFicha(ficha);

            realm.copyToRealmOrUpdate(update);
            realm.commitTransaction();

            List<UpdateFicha> list = UpdateFichaDao.getAll(Realm.getDefaultInstance());
            System.out.println(list.size());

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

}
