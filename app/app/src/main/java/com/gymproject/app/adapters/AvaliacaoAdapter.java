package com.gymproject.app.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gymproject.app.R;
import com.gymproject.app.models.Avaliacao;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AvaliacaoAdapter extends RecyclerView.Adapter<AvaliacaoAdapter.FichaViewHolder> {

    private List<Avaliacao> items;
    private int selectedItem;

    public class FichaViewHolder extends RecyclerView.ViewHolder {
        public TextView data, info;

        public FichaViewHolder(final View view) {
            super(view);
            data = (TextView) view.findViewById(R.id.data);
            info = (TextView) view.findViewById(R.id.info);
        }
    }

    public AvaliacaoAdapter(List<Avaliacao> items) {
        if (items == null) {
            throw new IllegalArgumentException("modelData must not be null");
        }
        this.items = items;
        this.selectedItem = -1;
    }

    public void putData(List<Avaliacao> newData){
        this.items.clear();
        if(newData!=null && newData.size()>0){
            for(Avaliacao item:newData){
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

    public Avaliacao getItem(int position) {
        return items.get(position);
    }

    @Override
    public FichaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_avaliacao, parent, false);
        return new FichaViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final FichaViewHolder holder, int position) {
        Avaliacao model = items.get(position);

        String dataFormatada = "";
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = formatter.parse(model.getData());
            dataFormatada = new SimpleDateFormat("dd/MM/yyyy").format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        holder.data.setText(dataFormatada);

        double altura = Double.valueOf(model.getAltura());
        double peso = Double.valueOf(model.getPeso());
        double imc = peso / Math.pow((altura / 100), 2);

        holder.info.setText("Altura: " + altura + ", Peso: " + peso + " (IMC: " + String.format( "%.2f", imc ) + ")");

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