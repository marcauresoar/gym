package com.gymproject.app.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import com.gymproject.app.R;
import com.gymproject.app.adapters.TreinoAdapter;
import com.gymproject.app.dao.TreinoDao;
import com.gymproject.app.models.Treino;
import com.gymproject.app.sync.SyncService;
import com.gymproject.app.sync.event.EventBusManager;
import com.gymproject.app.sync.event.SyncEvent;
import com.gymproject.app.sync.event.SyncStatus;
import com.gymproject.app.sync.event.SyncType;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import io.realm.Realm;

public class TreinoActivity extends AppCompatActivity implements RecyclerView.OnItemTouchListener,
        View.OnClickListener,
        android.view.ActionMode.Callback {

    RecyclerView recyclerView;
    TreinoAdapter adapter;
    int lastSelectedItem = -1;
    GestureDetectorCompat gestureDetector;
    android.view.ActionMode actionMode;

    Snackbar snackbar;
    SwipeRefreshLayout mSwipeRefreshLayout;
    CoordinatorLayout coordinatorLayout;

    private Realm realm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_treino);

        realm = Realm.getDefaultInstance();
        EventBusManager.register(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        adapter = new TreinoAdapter(TreinoDao.getAll(realm));


        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                SyncService.request(SyncType.TREINOS);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Click action
                Intent intent = new Intent(TreinoActivity.this, SalvarTreinoActivity.class);
                startActivity(intent);
            }
        });

        recyclerView.addOnItemTouchListener(this);
        gestureDetector =
                new GestureDetectorCompat(this, new RecyclerViewOnGestureListener());

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

    private void myToggleSelection(int idx) {
        adapter.toggleSelection(idx);
        lastSelectedItem = idx;
        String dataFormatada = "", nomeDia = "";
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = formatter.parse(adapter.getItem(idx).getData());
            dataFormatada = new SimpleDateFormat("dd/MM/yyyy").format(date);
            nomeDia = new SimpleDateFormat("EEEE").format(date);
            nomeDia = traduzirDiaSemana(nomeDia);
            dataFormatada += " (" + nomeDia + ")";
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String title = "Treino: " + dataFormatada;
        actionMode.setTitle(title);
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
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        gestureDetector.onTouchEvent(e);
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean b) {

    }

    @Override
    public boolean onCreateActionMode(android.view.ActionMode actionMode, Menu menu) {
        // Inflate a menu resource providing context menu items
        MenuInflater inflater = actionMode.getMenuInflater();
        inflater.inflate(R.menu.menu_treino_item, menu);
        return true;
    }

    @Override
    public boolean onPrepareActionMode(android.view.ActionMode actionMode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(android.view.ActionMode actionMode, MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.action_editar:
                Treino fichaSelected = adapter.getItem(adapter.getSelectedItem());
                String fichaId = fichaSelected.getId();

                Intent intent = new Intent(TreinoActivity.this, SalvarTreinoActivity.class);
                Bundle b = new Bundle();
                b.putString("id", fichaId);
                intent.putExtras(b);
                startActivity(intent);
                return false;
            case R.id.action_deletar:
                new AlertDialog.Builder(this)
                        .setTitle("Atenção")
                        .setMessage("Você realmente deseja excluir esse registro?")
                        .setIcon(R.drawable.ic_warning)
                        .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                deletarFicha();
                            }})
                        .setNegativeButton("Não", null).show();
                return true;
            default:
                return false;
        }
    }

    public void deletarFicha () {
        int position = adapter.getSelectedItem();

        // deletar do realm
        Treino fichaSelected = adapter.getItem(position);
        TreinoDao.delete(fichaSelected);

        // atualiza listagem
        refreshLista();

        // dispara sync
        SyncEvent.send(SyncType.TREINOS, SyncStatus.COMPLETED);
        SyncService.request(SyncType.TREINOS);
    }

    @Override
    public void onDestroyActionMode(android.view.ActionMode actionMode) {
        this.actionMode = null;
        adapter.clearSelections();
    }

    @Override
    public void onClick(View view) {

    }

    private class RecyclerViewOnGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            View view = recyclerView.findChildViewUnder(e.getX(), e.getY());
            int idx = recyclerView.getChildAdapterPosition(view);
            if (actionMode != null) {
                if(idx >= 0 && idx != lastSelectedItem) {
                    myToggleSelection(idx);
                    return false;
                }
                actionMode.finish();
            } else {
                if(idx>=0) {
                    Treino fichaSelected = adapter.getItem(idx);
                    String fichaId = fichaSelected.getId();

                    Intent intent = new Intent(TreinoActivity.this, ExercicioTreinoActivity.class);
                    Bundle b = new Bundle();
                    b.putString("id", fichaId);
                    intent.putExtras(b);
                    startActivity(intent);
                }
            }
            return super.onSingleTapConfirmed(e);
        }

        public void onLongPress(MotionEvent e) {
            View view = recyclerView.findChildViewUnder(e.getX(), e.getY());
            if (actionMode != null) {
                return;
            }
            actionMode = startActionMode(TreinoActivity.this);
            int idx = recyclerView.getChildAdapterPosition(view);
            myToggleSelection(idx);
            super.onLongPress(e);
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
        if (event.getType() == SyncType.TREINOS && event.getStatus() == SyncStatus.IN_PROGRESS) {
            if (!mSwipeRefreshLayout.isRefreshing()) {
                mSwipeRefreshLayout.setRefreshing(true);
            }
        } else if (event.getType() == SyncType.TREINOS && event.getStatus() == SyncStatus.COMPLETED) {
            mSwipeRefreshLayout.setRefreshing(false);
            refreshLista();
        }
    }

    public void refreshLista(){
        if(actionMode!=null) {
            actionMode.finish();
        }
        adapter.putData(TreinoDao.getAll(realm));
    }

}
