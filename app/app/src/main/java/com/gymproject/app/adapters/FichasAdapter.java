package com.gymproject.app.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gymproject.app.R;
import com.gymproject.app.models.Ficha;

import java.util.List;

public class FichasAdapter extends RecyclerView.Adapter<FichasAdapter.FichaViewHolder> {

    private Context mContext;
    private List<Ficha> items;
    private int selectedItem;

    public class FichaViewHolder extends RecyclerView.ViewHolder {
        public TextView nome, dias_semana;

        public FichaViewHolder(final View view) {
            super(view);
            nome = (TextView) view.findViewById(R.id.nome);
            dias_semana = (TextView) view.findViewById(R.id.dias_semana);
        }
    }

    public FichasAdapter(Context mContext, List<Ficha> items) {
        this.mContext = mContext;
        if (items == null) {
            throw new IllegalArgumentException("modelData must not be null");
        }
        this.items = items;
        this.selectedItem = -1;
    }

    /**
     * Adds and item into the underlying data set
     * at the position passed into the method.
     *
     * @param newModelData The item to add to the data set.
     * @param position The index of the item to remove.
     */
    public void addData(Ficha newModelData, int position) {
        items.add(position, newModelData);
        notifyItemInserted(position);
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

    public Ficha getItem(int position) {
        return items.get(position);
    }

    @Override
    public FichaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ficha_card, parent, false);
        return new FichaViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final FichaViewHolder holder, int position) {
        Ficha model = items.get(position);
        holder.nome.setText(model.getNome());
        holder.dias_semana.setText(model.getDias_semana().replace(",", ", "));
        holder.itemView.setSelected(selectedItem == position ? true : false);
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