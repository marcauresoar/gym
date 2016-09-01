package com.gymproject.app.adapters;

import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bignerdranch.android.multiselector.MultiSelector;
import com.bignerdranch.android.multiselector.SingleSelector;
import com.bignerdranch.android.multiselector.SwappingHolder;
import com.gymproject.app.R;
import com.gymproject.app.models.Ficha;

import java.util.ArrayList;
import java.util.List;

public class FichasAdapter extends RecyclerView.Adapter<FichasAdapter.FichaViewHolder> {

    private Context mContext;
    private List<Ficha> fichaList;

    public class FichaViewHolder extends RecyclerView.ViewHolder {
        public TextView nome, dias_semana;

        public FichaViewHolder(final View view) {
            super(view);
            nome = (TextView) view.findViewById(R.id.nome);
            dias_semana = (TextView) view.findViewById(R.id.dias_semana);
        }
    }

    public FichasAdapter(Context mContext, List<Ficha> fichaList) {
        this.mContext = mContext;
        this.fichaList = fichaList;
    }

    @Override
    public FichaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ficha_card, parent, false);

        return new FichaViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final FichaViewHolder holder, int position) {
        Ficha ficha = fichaList.get(position);
        holder.nome.setText(ficha.getNome());
        String dias_semana =ficha.getDias_semana();
        holder.dias_semana.setText(dias_semana.replace(",", ", "));
    }


    @Override
    public int getItemCount() {
        return fichaList.size();
    }

}