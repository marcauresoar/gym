package com.gymproject.app.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gymproject.app.R;
import com.gymproject.app.models.Treino;

import java.util.List;

import io.realm.Realm;

public class TreinoAdapter extends RecyclerView.Adapter<TreinoAdapter.FichaViewHolder> {

    private List<Treino> items;
    private int selectedItem;

    public class FichaViewHolder extends RecyclerView.ViewHolder {
        public TextView nome, dias_semana, num_exercicios;

        public FichaViewHolder(final View view) {
            super(view);
            nome = (TextView) view.findViewById(R.id.nome);
            dias_semana = (TextView) view.findViewById(R.id.dias_semana);
            num_exercicios = (TextView) view.findViewById(R.id.num_exercicios);
        }
    }

    public TreinoAdapter(List<Treino> items) {
        if (items == null) {
            throw new IllegalArgumentException("modelData must not be null");
        }
        this.items = items;
        this.selectedItem = -1;
    }

    public void putData(List<Treino> newData){
        this.items.clear();
        if(newData!=null && newData.size()>0){
            for(Treino item:newData){
                this.items.add(item);
            }
        }
        notifyDataSetChanged();
    }

    /**
     * Removes the item that currently is at the passed in position from the
     * underlying data set.
     *
     * @param position The index of the item to remove.
     */
    public void removeData(int position) {
        items.remove(position);
        notifyItemRemoved(position);
    }

    public Treino getItem(int position) {
        return items.get(position);
    }

    @Override
    public FichaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_ficha, parent, false);
        return new FichaViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final FichaViewHolder holder, int position) {
        Treino model = items.get(position);
        holder.nome.setText(model.getNome());
        holder.dias_semana.setText(model.getDias_semana().replace(",", ", "));
        holder.itemView.setSelected(selectedItem == position ? true : false);
        List<Exercicio> exercicios = ExercicioDao.getAll(Realm.getDefaultInstance(), model.getId());
        int num_exercicios = exercicios.size();
        String msg_exercicio = "";
        if(num_exercicios == 0){
            msg_exercicio = "Nenhum exercício cadastrado.";
        } else if (num_exercicios== 1){
            msg_exercicio = "1 exercício cadastrado.";
        } else {
            msg_exercicio = num_exercicios + " exercícios cadastrados.";
        }
        holder.num_exercicios.setText(msg_exercicio);
    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    public void toggleSelection(int pos) {
        if (selectedItem == pos) {
            selectedItem = -1;
        }
        else {
            selectedItem =  pos;
        }
        notifyDataSetChanged();
    }

    public void clearSelections() {
        selectedItem = -1;
        notifyDataSetChanged();
    }

    public int getSelectedItem() {
        return selectedItem;
    }

}