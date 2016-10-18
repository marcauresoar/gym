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
import com.gymproject.app.adapters.AvaliacaoAdapter;
import com.gymproject.app.dao.AvaliacaoDao;
import com.gymproject.app.models.Avaliacao;
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

public class AvaliacaoActivity extends AppCompatActivity implements RecyclerView.OnItemTouchListener,
        View.OnClickListener,
        android.view.ActionMode.Callback {

    RecyclerView recyclerView;
    AvaliacaoAdapter adapter;
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
        setContentView(R.layout.activity_avaliacao);

        realm = Realm.getDefaultInstance();
        EventBusManager.register(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        adapter = new AvaliacaoAdapter(AvaliacaoDao.getAll(realm));


        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                SyncService.request(SyncType.AVALIACOES);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Click action
                Intent intent = new Intent(AvaliacaoActivity.this, SalvarAvaliacaoActivity.class);
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
        String dataFormatada = "";
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = formatter.parse(adapter.getItem(idx).getData());
            dataFormatada = new SimpleDateFormat("dd/MM/yyyy").format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String title = "Avaliação: " + dataFormatada;
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
        inflater.inflate(R.menu.menu_avaliacao_item, menu);
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
                Avaliacao fichaSelected = adapter.getItem(adapter.getSelectedItem());
                String fichaId = fichaSelected.getId();

                Intent intent = new Intent(AvaliacaoActivity.this, SalvarAvaliacaoActivity.class);
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
        Avaliacao fichaSelected = adapter.getItem(position);
        AvaliacaoDao.delete(fichaSelected);

        // atualiza listagem
        refreshLista();

        // dispara sync
        SyncEvent.send(SyncType.AVALIACOES, SyncStatus.COMPLETED);
        SyncService.request(SyncType.AVALIACOES);
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
            actionMode = startActionMode(AvaliacaoActivity.this);
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
        if (event.getType() == SyncType.AVALIACOES && event.getStatus() == SyncStatus.IN_PROGRESS) {
            if (!mSwipeRefreshLayout.isRefreshing()) {
                mSwipeRefreshLayout.setRefreshing(true);
            }
        } else if (event.getType() == SyncType.AVALIACOES && event.getStatus() == SyncStatus.COMPLETED) {
            mSwipeRefreshLayout.setRefreshing(false);
            refreshLista();
        }
    }

    public void refreshLista(){
        if(actionMode!=null) {
            actionMode.finish();
        }
        adapter.putData(AvaliacaoDao.getAll(realm));
    }

}
