package com.gymproject.app.activities;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import com.gymproject.app.R;
import com.gymproject.app.dao.AvaliacaoDao;
import com.gymproject.app.models.Avaliacao;
import com.gymproject.app.sync.SyncService;
import com.gymproject.app.sync.event.SyncEvent;
import com.gymproject.app.sync.event.SyncStatus;
import com.gymproject.app.sync.event.SyncType;
import com.gymproject.app.utils.HashUtils;
import com.gymproject.app.utils.SessionUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import io.realm.Realm;

public class SalvarAvaliacaoActivity extends AppCompatActivity {

    Snackbar snackbar;
    CoordinatorLayout coordinatorLayout;

    EditText edtData, edtAltura, edtPeso;
    EditText edtPeitoral, edtBiceps, edtTriceps, edtSubescapular, edtSupraIliaca, edtAxilarMedia,
            edtAbdominal, edtCoxa, edtPanturrilhaMedial;
    EditText edtTorax, edtAbdomen, edtCintura, edtQuadril, edtBracoDireito, edtBracoEsquerdo, edtAntebracoDireito, edtAntebracoEsquerdo,
            edtCoxaDireita, edtCoxaEsquerda, edtPernaDireita, edtPernaEsquerda, edtOmbro, edtPescoco, edtPunho, edtJoelho, edtTornozelo;

    DatePickerDialog dialogData;

    SimpleDateFormat dateFormatter;

    Realm realm;

    String id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salvar_avaliacao);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        realm = Realm.getDefaultInstance();

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);

        dateFormatter = new SimpleDateFormat("dd/MM/yyyy");

        edtData = (EditText) findViewById(R.id.edtData);
        edtData.setInputType(InputType.TYPE_NULL);
        edtData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogData.show();
            }
        });
        edtData.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b) {
                    dialogData.show();
                }
            }
        });

        Calendar newCalendar1 = Calendar.getInstance();
        dialogData = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                edtData.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar1.get(Calendar.YEAR), newCalendar1.get(Calendar.MONTH), newCalendar1.get(Calendar.DAY_OF_MONTH));


        edtAltura = (EditText) findViewById(R.id.edtAltura);
        edtPeso = (EditText) findViewById(R.id.edtPeso);

        edtPeitoral = (EditText) findViewById(R.id.edtPeitoral);
        edtBiceps = (EditText) findViewById(R.id.edtBiceps);
        edtTriceps = (EditText) findViewById(R.id.edtTriceps);
        edtSubescapular = (EditText) findViewById(R.id.edtSubescapular);
        edtSupraIliaca = (EditText) findViewById(R.id.edtSupraIliaca);
        edtAxilarMedia = (EditText) findViewById(R.id.edtAxilarMedia);
        edtAbdominal = (EditText) findViewById(R.id.edtAbdominal);
        edtCoxa = (EditText) findViewById(R.id.edtCoxa);
        edtPanturrilhaMedial = (EditText) findViewById(R.id.edtPanturrilhaMedial);

        edtTorax = (EditText) findViewById(R.id.edtTorax);
        edtAbdomen = (EditText) findViewById(R.id.edtAbdomen);
        edtCintura = (EditText) findViewById(R.id.edtCintura);
        edtQuadril = (EditText) findViewById(R.id.edtQuadril);
        edtBracoDireito = (EditText) findViewById(R.id.edtBracoDireito);
        edtBracoEsquerdo = (EditText) findViewById(R.id.edtBracoEsquerdo);
        edtAntebracoDireito = (EditText) findViewById(R.id.edtAntebracoDireito);
        edtAntebracoEsquerdo = (EditText) findViewById(R.id.edtAntebracoEsquerdo);
        edtCoxaDireita = (EditText) findViewById(R.id.edtCoxaDireita);
        edtCoxaEsquerda = (EditText) findViewById(R.id.edtCoxaEsquerda);
        edtPernaDireita = (EditText) findViewById(R.id.edtPernaDireita);
        edtPernaEsquerda = (EditText) findViewById(R.id.edtPernaEsquerda);
        edtOmbro = (EditText) findViewById(R.id.edtOmbro);
        edtPescoco = (EditText) findViewById(R.id.edtPescoco);
        edtPunho = (EditText) findViewById(R.id.edtPunho);
        edtJoelho = (EditText) findViewById(R.id.edtJoelho);
        edtTornozelo = (EditText) findViewById(R.id.edtTornozelo);


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
        inflater.inflate(R.menu.menu_criar_avaliacao, menu);
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
        String data = edtData.getText().toString();
        String altura = edtAltura.getText().toString();
        String peso = edtPeso.getText().toString();
        if(data.isEmpty()){
            snackbar = Snackbar
                    .make(coordinatorLayout, "Por favor, preencha o campo Data.", Snackbar.LENGTH_LONG);
            snackbar.show();
        } else if(altura.isEmpty()){
            snackbar = Snackbar
                    .make(coordinatorLayout, "Por favor, preencha o campo Altura.", Snackbar.LENGTH_LONG);
            snackbar.show();
        } else if(peso.isEmpty()){
            snackbar = Snackbar
                    .make(coordinatorLayout, "Por favor, preencha o campo Peso.", Snackbar.LENGTH_LONG);
            snackbar.show();
        } else {
            // cria o registro e insere no realm
            Avaliacao avaliacao = new Avaliacao();
            String acao = "";
            if(!id.isEmpty()) {
                avaliacao.setId(id);
                acao = "update";
            }else{
                avaliacao.setId(HashUtils.generateId());
                acao = "insert";
            }

            try {
                DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                Date date = formatter.parse(data);
                data = new SimpleDateFormat("yyyy-MM-dd").format(date);
                System.out.println(data);
            } catch (Exception e){
                e.printStackTrace();
            }

            avaliacao.setData(data);
            avaliacao.setAltura(altura);
            avaliacao.setPeso(peso);

            avaliacao.setPeitoral(edtPeitoral.getText().toString());
            avaliacao.setBiceps(edtBiceps.getText().toString());
            avaliacao.setTriceps(edtTriceps.getText().toString());
            avaliacao.setSubescapular(edtSubescapular.getText().toString());
            avaliacao.setSupra_iliaca(edtSupraIliaca.getText().toString());
            avaliacao.setAxiliar_media(edtAxilarMedia.getText().toString());
            avaliacao.setAbdominal(edtAbdominal.getText().toString());
            avaliacao.setCoxa(edtCoxa.getText().toString());
            avaliacao.setPanturrilha_media(edtPanturrilhaMedial.getText().toString());

            avaliacao.setTorax(edtTorax.getText().toString());
            avaliacao.setAbdomen(edtAbdomen.getText().toString());
            avaliacao.setCintura(edtCintura.getText().toString());
            avaliacao.setQuadril(edtQuadril.getText().toString());
            avaliacao.setBraco_direito(edtBracoDireito.getText().toString());
            avaliacao.setBraco_esquerdo(edtBracoEsquerdo.getText().toString());
            avaliacao.setAntebraco_direito(edtAntebracoDireito.getText().toString());
            avaliacao.setAntebraco_esquerdo(edtAntebracoEsquerdo.getText().toString());
            avaliacao.setCoxa_direita(edtCoxaDireita.getText().toString());
            avaliacao.setCoxa_esquerda(edtCoxaEsquerda.getText().toString());
            avaliacao.setPerna_direita(edtPernaDireita.getText().toString());
            avaliacao.setPerna_esquerda(edtPernaEsquerda.getText().toString());
            avaliacao.setOmbro(edtOmbro.getText().toString());
            avaliacao.setPescoco(edtPescoco.getText().toString());
            avaliacao.setPunho(edtPunho.getText().toString());
            avaliacao.setJoelho(edtJoelho.getText().toString());
            avaliacao.setTornozelo(edtTornozelo.getText().toString());


            avaliacao.setUsuario(SessionUtils.getInstance(getApplicationContext()).getUsuario());
            AvaliacaoDao.save(avaliacao, acao);

            // dispara sync e fecha activity
            SyncEvent.send(SyncType.AVALIACOES, SyncStatus.COMPLETED);
            SyncService.request(SyncType.AVALIACOES);
            finish();
        }
    }

    public void preencherForm () {
        Avaliacao avaliacao = AvaliacaoDao.getById(realm, id);

        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dataFormatada = "";
        try {
            Date date = formatter.parse(avaliacao.getData());
            dataFormatada = new SimpleDateFormat("dd/MM/yyyy").format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        edtData.setText(dataFormatada);
        edtAltura.setText(avaliacao.getAltura());
        edtPeso.setText(avaliacao.getPeso());

        edtPeitoral.setText(avaliacao.getPeitoral());
        edtBiceps.setText(avaliacao.getBiceps());
        edtTriceps.setText(avaliacao.getTriceps());
        edtSubescapular.setText(avaliacao.getSubescapular());
        edtSupraIliaca.setText(avaliacao.getSupra_iliaca());
        edtAxilarMedia.setText(avaliacao.getAxiliar_media());
        edtAbdominal.setText(avaliacao.getAbdominal());
        edtCoxa.setText(avaliacao.getCoxa());
        edtPanturrilhaMedial.setText(avaliacao.getPanturrilha_media());

        edtTorax.setText(avaliacao.getTorax());
        edtAbdomen.setText(avaliacao.getAbdomen());
        edtCintura.setText(avaliacao.getCintura());
        edtQuadril.setText(avaliacao.getQuadril());
        edtBracoDireito.setText(avaliacao.getBraco_direito());
        edtBracoEsquerdo.setText(avaliacao.getBraco_esquerdo());
        edtAntebracoDireito.setText(avaliacao.getAntebraco_direito());
        edtAntebracoEsquerdo.setText(avaliacao.getAntebraco_esquerdo());
        edtCoxaDireita.setText(avaliacao.getCoxa_direita());
        edtCoxaEsquerda.setText(avaliacao.getCoxa_esquerda());
        edtPernaDireita.setText(avaliacao.getPerna_direita());
        edtPernaEsquerda.setText(avaliacao.getPerna_esquerda());
        edtOmbro.setText(avaliacao.getOmbro());
        edtPescoco.setText(avaliacao.getPescoco());
        edtPunho.setText(avaliacao.getPunho());
        edtJoelho.setText(avaliacao.getJoelho());
        edtTornozelo.setText(avaliacao.getTornozelo());


    }

}
