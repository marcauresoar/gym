package com.gymproject.app.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gymproject.app.R;
import com.gymproject.app.dao.ExercicioTreinoDao;
import com.gymproject.app.dao.SerieTreinoDao;
import com.gymproject.app.models.ExercicioTreino;
import com.gymproject.app.models.SerieTreino;
import com.gymproject.app.models.Treino;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import io.realm.Realm;

public class TreinoAdapter extends RecyclerView.Adapter<TreinoAdapter.FichaViewHolder> {

    private List<Treino> items;
    private int selectedItem;

    public class FichaViewHolder extends RecyclerView.ViewHolder {
        public TextView data, hora, info;

        public FichaViewHolder(final View view) {
            super(view);
            data = (TextView) view.findViewById(R.id.data);
            hora = (TextView) view.findViewById(R.id.hora);
            info = (TextView) view.findViewById(R.id.info);
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
                .inflate(R.layout.card_treino, parent, false);
        return new FichaViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final FichaViewHolder holder, int position) {
        Treino model = items.get(position);

        String dataFormatada = "", nomeDia = "";
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = formatter.parse(model.getData());
            dataFormatada = new SimpleDateFormat("dd/MM/yyyy").format(date);
            nomeDia = new SimpleDateFormat("EEEE").format(date);
            nomeDia = traduzirDiaSemana(nomeDia);
            dataFormatada += " (" + nomeDia + ")";
        } catch (ParseException e) {
            e.printStackTrace();
        }

        holder.data.setText(dataFormatada);
        String hora = "Início: " + model.getHora_inicio().substring(0,5) + "h";
        if(!model.getHora_fim().isEmpty()){
            hora += " - Fim: " + model.getHora_fim().substring(0,5) + "h";
        }
        holder.hora.setText(hora);

        double seriesTotal = 0, seriesFeitas=0;
        List<ExercicioTreino> exercicios = ExercicioTreinoDao.getAll(Realm.getDefaultInstance(), model.getId());
        for(ExercicioTreino exercicio :exercicios){
            List<SerieTreino> series = SerieTreinoDao.getAll(Realm.getDefaultInstance(), exercicio.getId());
            seriesTotal += series.size();
            for(SerieTreino serie:series) {
                if(serie.isFeito()) {
                    seriesFeitas++;
                }
            }
        }
        double porc = seriesTotal > 0 ? Math.floor(seriesFeitas / seriesTotal * 100) : 0;

        holder.info.setText(exercicios.size() + " exercicios, " + seriesTotal + " series\n" + seriesFeitas + " series realizadas (" + (int) porc + "%)");
        holder.itemView.setSelected(selectedItem == position ? true : false);
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