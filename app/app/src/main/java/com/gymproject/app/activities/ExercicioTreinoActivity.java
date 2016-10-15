package com.gymproject.app.activities;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.gymproject.app.R;
import com.gymproject.app.adapters.ExercicioTreinoAdapter;
import com.gymproject.app.dao.ExercicioDao;
import com.gymproject.app.dao.ExercicioTreinoDao;
import com.gymproject.app.dao.SerieDao;
import com.gymproject.app.dao.SerieTreinoDao;
import com.gymproject.app.dao.TreinoDao;
import com.gymproject.app.models.Exercicio;
import com.gymproject.app.models.ExercicioTreino;
import com.gymproject.app.models.Serie;
import com.gymproject.app.models.SerieTreino;
import com.gymproject.app.models.Treino;
import com.gymproject.app.sync.SyncService;
import com.gymproject.app.sync.event.EventBusManager;
import com.gymproject.app.sync.event.SyncEvent;
import com.gymproject.app.sync.event.SyncStatus;
import com.gymproject.app.sync.event.SyncType;
import com.gymproject.app.utils.HashUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import io.realm.Realm;

public class ExercicioTreinoActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ExercicioTreinoAdapter adapter;

    Snackbar snackbar;
    SwipeRefreshLayout mSwipeRefreshLayout;
    CoordinatorLayout coordinatorLayout;

    private Realm realm;

    String id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercicio_treino);

        Bundle b = getIntent().getExtras();
        if(b!=null){
            id = b.getString("id");
        }

        if(id.isEmpty()){
            snackbar = Snackbar
                    .make(coordinatorLayout, "Houve um erro interno. A aplicação irá finalizar esta tarefa.", Snackbar.LENGTH_LONG);
            snackbar.show();
            snackbar.setCallback(new Snackbar.Callback() {
                @Override
                public void onDismissed(Snackbar snackbar, int event) {
                    finish();
                }
            });
        }

        realm = Realm.getDefaultInstance();
        EventBusManager.register(this);

        Treino treino = TreinoDao.getById(realm,id);

        /*List<ExercicioTreino> exerciciosTreino = ExercicioTreinoDao.getAll(realm, treino.getId());
        if(exerciciosTreino.size() == 0) {
            List<Exercicio> exercicios = ExercicioDao.getAll(realm, treino.getFicha_id());
            System.out.println(treino.getFicha_id());
            for (Exercicio exercicio : exercicios) {
                ExercicioTreino novoExercicio = new ExercicioTreino();

                novoExercicio.setId(HashUtils.generateId());
                novoExercicio.setNome(exercicio.getNome());
                novoExercicio.setGrupo_muscular(exercicio.getGrupo_muscular());
                novoExercicio.setTreino(treino);
                ExercicioTreinoDao.save(novoExercicio, "insert");

                List<Serie> series = SerieDao.getAll(realm, exercicio.getId());
                for (Serie serie : series) {
                    SerieTreino novaSerie = new SerieTreino();

                    novaSerie.setId(HashUtils.generateId());
                    novaSerie.setTipo(serie.getTipo());
                    novaSerie.setPeso(serie.getPeso());
                    novaSerie.setRepeticoes(serie.getRepeticoes());
                    novaSerie.setTempo(serie.getTempo());
                    novaSerie.setExercicio_treino(novoExercicio);
                    SerieTreinoDao.save(novaSerie, "insert");
                }
            }
        }*/


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        String dataFormatada = "", nomeDia = "";
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = formatter.parse(treino.getData());
            dataFormatada = new SimpleDateFormat("dd/MM/yyyy").format(date);
            nomeDia = new SimpleDateFormat("EEEE").format(date);
            nomeDia = traduzirDiaSemana(nomeDia);
            dataFormatada += " (" + nomeDia + ")";
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String title = "Treino: " + dataFormatada;
        getSupportActionBar().setTitle(title);

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        adapter = new ExercicioTreinoAdapter(this, ExercicioTreinoDao.getAll(realm, id));


        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                SyncService.request(SyncType.EXERCICIOS_TREINOS);
            }
        });
    }

    public String traduzirDiaSemana (String nomeDia){
        if(nomeDia.equals("Sunday")){
            nomeDia = "Domingo";
        } else if(nomeDia.equals("Monday")){
            nomeDia = "Segunda feira";
        } else if(nomeDia.equals("Tuesday")){
            nomeDia = "Terça feira";
        } else if(nomeDia.equals("Wednesday")){
            nomeDia = "Quarta feira";
        } else if(nomeDia.equals("Thursday")){
            nomeDia = "Quinta feira";
        } else if(nomeDia.equals("Friday")){
            nomeDia = "Sexta feira";
        } else if(nomeDia.equals("Saturday")){
            nomeDia = "Sábado";
        }
        return nomeDia;
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

    @Override
    protected void onDestroy() {
        realm.close();
        EventBusManager.unregister(this);
        super.onDestroy();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(SyncEvent event) {
        if (event.getType() == SyncType.EXERCICIOS_TREINOS && event.getStatus() == SyncStatus.IN_PROGRESS) {
            if (!mSwipeRefreshLayout.isRefreshing()) {
                mSwipeRefreshLayout.setRefreshing(true);
            }
        } else if (event.getType() == SyncType.EXERCICIOS_TREINOS && event.getStatus() == SyncStatus.COMPLETED) {
            mSwipeRefreshLayout.setRefreshing(false);
            adapter.putData(ExercicioTreinoDao.getAll(realm, id));
        }
    }

}
