package com.gymproject.app.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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
import com.gymproject.app.adapters.ExercicioAdapter;
import com.gymproject.app.dao.ExercicioDao;
import com.gymproject.app.dao.FichaDao;
import com.gymproject.app.models.Exercicio;
import com.gymproject.app.models.Ficha;
import com.gymproject.app.sync.SyncService;
import com.gymproject.app.sync.event.EventBusManager;
import com.gymproject.app.sync.event.SyncEvent;
import com.gymproject.app.sync.event.SyncStatus;
import com.gymproject.app.sync.event.SyncType;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import io.realm.Realm;

public class ExercicioActivity extends AppCompatActivity  implements RecyclerView.OnItemTouchListener,
        View.OnClickListener,
        android.view.ActionMode.Callback {

    RecyclerView recyclerView;
    ExercicioAdapter adapter;
    int lastSelectedItem = -1;
    GestureDetectorCompat gestureDetector;
    android.view.ActionMode actionMode;

    Snackbar snackbar;
    SwipeRefreshLayout mSwipeRefreshLayout;
    CoordinatorLayout coordinatorLayout;

    private Realm realm;

    String id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercicio);

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

        Ficha ficha = FichaDao.getById(realm,id);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(ficha.getNome());

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        adapter = new ExercicioAdapter(this, ExercicioDao.getAll(realm, id));


        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                SyncService.request(SyncType.EXERCICIOS);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Click action
                Intent intent = new Intent(ExercicioActivity.this, SalvarExercicioActivity.class);
                Bundle b = new Bundle();
                b.putString("id", "");
                b.putString("ficha_id", id);
                intent.putExtras(b);
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
        String title = "Exercício: " + adapter.getItem(idx).getNome();
        actionMode.setTitle(title);
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
        inflater.inflate(R.menu.menu_exercicio_item, menu);
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
                Exercicio itemSelected = adapter.getItem(adapter.getSelectedItem());
                String exercicioId = itemSelected.getId();

                Intent intent = new Intent(ExercicioActivity.this, SalvarExercicioActivity.class);
                Bundle b = new Bundle();
                b.putString("id", exercicioId);
                b.putString("ficha_id", id);
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
                                deletarItem();
                            }})
                        .setNegativeButton("Não", null).show();
                return true;
            default:
                return false;
        }
    }

    public void deletarItem () {
        int position = adapter.getSelectedItem();

        // deletar do realm
        Exercicio itemSelected = adapter.getItem(position);
        ExercicioDao.delete(itemSelected);

        // atualiza listagem
        refreshLista();

        // dispara sync
        SyncEvent.send(SyncType.EXERCICIOS, SyncStatus.COMPLETED);
        SyncService.request(SyncType.EXERCICIOS);
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
            }
            return super.onSingleTapConfirmed(e);
        }

        public void onLongPress(MotionEvent e) {
            View view = recyclerView.findChildViewUnder(e.getX(), e.getY());
            if (actionMode != null) {
                return;
            }
            actionMode = startActionMode(ExercicioActivity.this);
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
        if (event.getType() == SyncType.EXERCICIOS && event.getStatus() == SyncStatus.IN_PROGRESS) {
            if (!mSwipeRefreshLayout.isRefreshing()) {
                mSwipeRefreshLayout.setRefreshing(true);
            }
        } else if (event.getType() == SyncType.EXERCICIOS && event.getStatus() == SyncStatus.COMPLETED) {
            mSwipeRefreshLayout.setRefreshing(false);
            refreshLista();
        }
    }

    public void refreshLista(){
        if(actionMode!=null) {
            actionMode.finish();
        }
        adapter.putData(ExercicioDao.getAll(realm, id));
    }

}
