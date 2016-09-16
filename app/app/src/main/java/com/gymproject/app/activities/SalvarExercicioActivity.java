package com.gymproject.app.activities;

import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.gymproject.app.R;
import com.gymproject.app.adapters.CustomSpinnerAdapter;
import com.gymproject.app.classes.ItemData;
import com.gymproject.app.dao.ExercicioDao;
import com.gymproject.app.dao.FichaDao;
import com.gymproject.app.models.Exercicio;
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

public class SalvarExercicioActivity extends AppCompatActivity {

    Snackbar snackbar;
    CoordinatorLayout coordinatorLayout;

    EditText edtNome;
    Spinner spnGrupo;

    Realm realm;

    String id = "";
    String ficha_id = "";

    ArrayList<ItemData> mItens;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salvar_exercicio);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        realm = Realm.getDefaultInstance();

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);

        mItens = new ArrayList<>();
        mItens.add(new ItemData("Abdominal",R.drawable.ic_ex_abdominal));
        mItens.add(new ItemData("Biceps",R.drawable.ic_ex_biceps));
        mItens.add(new ItemData("Costas",R.drawable.ic_ex_costa));
        mItens.add(new ItemData("Ombro",R.drawable.ic_ex_ombro));
        mItens.add(new ItemData("Outro",R.drawable.ic_generico));
        mItens.add(new ItemData("Peito",R.drawable.ic_ex_peito));
        mItens.add(new ItemData("Perna",R.drawable.ic_ex_perna));
        mItens.add(new ItemData("Triceps",R.drawable.ic_ex_triceps));

        edtNome = (EditText) findViewById(R.id.nome);
        spnGrupo =(Spinner)findViewById(R.id.spnGrupo);
        CustomSpinnerAdapter adapter=new CustomSpinnerAdapter(this,
                R.layout.row_grupo_muscular, R.id.text, mItens);
        spnGrupo.setAdapter(adapter);

        Bundle b = getIntent().getExtras();
        if(b!=null){
            id = b.getString("id");
            ficha_id = b.getString("ficha_id");
        }

        if(!id.isEmpty()) {
            preencherForm();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_criar_exercicio, menu);
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
        ItemData selectedItem = (ItemData) spnGrupo.getSelectedItem();
        String grupo_muscular = selectedItem.getText();
        if(nome.isEmpty()){
            snackbar = Snackbar
                    .make(coordinatorLayout, "Por favor, preencha o campo Nome.", Snackbar.LENGTH_LONG);
            snackbar.show();
        } else if (grupo_muscular.isEmpty()){
            snackbar = Snackbar
                    .make(coordinatorLayout, "Por favor, selecione o grupo muscular.", Snackbar.LENGTH_LONG);
            snackbar.show();
        } else {
            // cria o registro e insere no realm
            Exercicio exercicio = new Exercicio();
            String acao = "";
            if(!id.isEmpty()) {
                exercicio.setId(id);
                acao = "update";
            }else{
                exercicio.setId(HashUtils.generateId());
                acao = "insert";
            }
            exercicio.setNome(nome);
            exercicio.setGrupo_muscular(grupo_muscular);
            exercicio.setFicha(FichaDao.getById(realm, ficha_id));
            ExercicioDao.save(exercicio, acao);

            // dispara sync e fecha activity
            SyncEvent.send(SyncType.EXERCICIOS, SyncStatus.COMPLETED);
            SyncService.request(SyncType.EXERCICIOS);
            finish();
        }
    }

    public void preencherForm () {
        Exercicio exercicio = ExercicioDao.getById(realm, id);
        edtNome.setText(exercicio.getNome());
        for(int i = 0; i < mItens.size(); i++){
            ItemData item = (ItemData) spnGrupo.getItemAtPosition(i);
            if(item.getText().equals(exercicio.getGrupo_muscular())){
                spnGrupo.setSelection(i);
            }
        }
    }
}
