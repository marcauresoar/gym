package com.gymproject.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import com.gymproject.app.R;
import com.gymproject.app.adapters.FichasAdapter;
import com.gymproject.app.classes.Status;
import com.gymproject.app.models.Ficha;
import com.gymproject.app.restful.RestfulAPI;
import com.gymproject.app.services.FichaService;
import com.gymproject.app.utils.SessionUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FichaActivity extends AppCompatActivity implements RecyclerView.OnItemTouchListener,
        View.OnClickListener,
        android.view.ActionMode.Callback {

    private RecyclerView recyclerView;
    private FichasAdapter adapter;
    private List<Ficha> fichaList;
    int lastSelectedItem = -1;
    GestureDetectorCompat gestureDetector;
    android.view.ActionMode actionMode;

    Snackbar snackbar;
    SwipeRefreshLayout mSwipeRefreshLayout;
    CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ficha);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        fichaList = new ArrayList<>();
        adapter = new FichasAdapter(this, fichaList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        listarFichas();

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                listarFichas();
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Click action
                Intent intent = new Intent(FichaActivity.this, GerenciarFichaActivity.class);
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

    public void listarFichas () {
        FichaService apiService =
                RestfulAPI.getClient().create(FichaService.class);

        Call<Status<List<Ficha>>> call = apiService.listarFichas(SessionUtils.getInstance(this).getIdUsuario());
        call.enqueue(new Callback<Status<List<Ficha>>>() {
            @Override
            public void onResponse(Call<Status<List<Ficha>>>call, Response<Status<List<Ficha>>> response) {
                Status status = response.body();
                if(status != null) {
                    if(status.getCodigo() == 1) {
                        List<Ficha> fichas = (List<Ficha>) status.getDados();
                        if (fichas.size() > 0) {
                            fichaList.clear();
                            for(Ficha ficha : fichas){
                                fichaList.add(ficha);
                            }
                            adapter.notifyDataSetChanged();
                        }
                    } else {
                        snackbar = Snackbar
                                .make(coordinatorLayout, status.getMensagem(), Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
                }else {
                    onFailure(call, new Throwable("Erro com os dados retornados do servidor!"));
                }
                mSwipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<Status<List<Ficha>>>call, Throwable t) {
                Log.e("listarFichas", t.toString());
                snackbar = Snackbar
                        .make(coordinatorLayout, "Houve um erro ao tentar realizar esta ação!", Snackbar.LENGTH_LONG);
                snackbar.show();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void myToggleSelection(int idx) {
        adapter.toggleSelection(idx);
        lastSelectedItem = idx;
        String title = "Ficha: " + adapter.getItem(idx).getNome();
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
        inflater.inflate(R.menu.menu_ficha_item, menu);
        return true;
    }

    @Override
    public boolean onPrepareActionMode(android.view.ActionMode actionMode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(android.view.ActionMode actionMode, MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.action_deletar:
                int selectedItem = adapter.getSelectedItem();
                adapter.removeData(selectedItem);
                actionMode.finish();
                return true;
            default:
                return false;
        }
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
            actionMode = startActionMode(FichaActivity.this);
            int idx = recyclerView.getChildAdapterPosition(view);
            myToggleSelection(idx);
            super.onLongPress(e);
        }
    }


}
