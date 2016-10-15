package com.gymproject.app.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.gymproject.app.R;
import com.gymproject.app.classes.ItemData;
import com.gymproject.app.dao.ExercicioTreinoDao;
import com.gymproject.app.dao.SerieTreinoDao;
import com.gymproject.app.dao.UpdateSerieDao;
import com.gymproject.app.dao.UpdateSerieTreinoDao;
import com.gymproject.app.models.ExercicioTreino;
import com.gymproject.app.models.SerieTreino;
import com.gymproject.app.models.UpdateSerieTreino;
import com.gymproject.app.sync.SyncService;
import com.gymproject.app.sync.event.SyncEvent;
import com.gymproject.app.sync.event.SyncStatus;
import com.gymproject.app.sync.event.SyncType;
import com.gymproject.app.utils.HashUtils;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;

public class ExercicioTreinoAdapter extends RecyclerView.Adapter<ExercicioTreinoAdapter.ExercicioViewHolder> {

    private Context mContext;
    private List<ExercicioTreino> items;
    private int selectedItem;

    public class ExercicioViewHolder extends RecyclerView.ViewHolder {
        public TextView nome, grupo_muscular;
        public ImageView imgExercicio;
        public LinearLayout container_series;

        public ExercicioViewHolder(final View view) {
            super(view);
            nome = (TextView) view.findViewById(R.id.nome);
            grupo_muscular = (TextView) view.findViewById(R.id.grupo_muscular);
            imgExercicio = (ImageView) view.findViewById(R.id.imgExercicio);
            container_series = (LinearLayout) view.findViewById(R.id.container_series);
        }
    }

    public ExercicioTreinoAdapter(Context context, List<ExercicioTreino> items) {
        if (items == null) {
            throw new IllegalArgumentException("modelData must not be null");
        }
        this.mContext = context;
        this.items = items;
        this.selectedItem = -1;
    }

    public void putData(List<ExercicioTreino> newData){
        this.items.clear();
        if(newData!=null && newData.size()>0){
            for(ExercicioTreino item : newData){
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

    public ExercicioTreino getItem(int position) {
        return items.get(position);
    }

    @Override
    public ExercicioViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_exercicio_treino, parent, false);
        return new ExercicioViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ExercicioViewHolder holder, int position) {
        final ExercicioTreino model = items.get(position);
        String grupo = model.getGrupo_muscular();
        holder.nome.setText(model.getNome());
        holder.grupo_muscular.setText(grupo);
        if(grupo.equals("Peito")){
            holder.imgExercicio.setImageResource(R.drawable.ic_ex_peito);
        } else if(grupo.equals("Ombro")){
            holder.imgExercicio.setImageResource(R.drawable.ic_ex_ombro);
        } else if(grupo.equals("Triceps")){
            holder.imgExercicio.setImageResource(R.drawable.ic_ex_triceps);
        } else if(grupo.equals("Perna")){
            holder.imgExercicio.setImageResource(R.drawable.ic_ex_perna);
        } else if(grupo.equals("Abdominal")){
            holder.imgExercicio.setImageResource(R.drawable.ic_ex_abdominal);
        } else if(grupo.equals("Costas")){
            holder.imgExercicio.setImageResource(R.drawable.ic_ex_costa);
        } else if(grupo.equals("Biceps")){
            holder.imgExercicio.setImageResource(R.drawable.ic_ex_biceps);
        } else {
            holder.imgExercicio.setImageResource(R.drawable.ic_generico);
        }
        holder.itemView.setSelected(selectedItem == position ? true : false);

        LayoutInflater vi = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        List<SerieTreino> series = SerieTreinoDao.getAll(Realm.getDefaultInstance(), model.getId());
        holder.container_series.removeAllViews();
        if(series.size() > 0 ){
            holder.container_series.removeAllViews();

            for(final SerieTreino serie : series){
                final View v = vi.inflate(R.layout.card_serie_treino, null);

                ImageView imgSerie = (ImageView) v.findViewById(R.id.imgSerie);
                if(serie.getTipo().equals("tempo")) {
                    imgSerie.setImageResource(R.drawable.ic_tempo);
                } else if(serie.getTipo().equals("peso")) {
                    imgSerie.setImageResource(R.drawable.ic_peso);
                }

                TextView textSerie = (TextView) v.findViewById(R.id.txtSerie);
                if(serie.getTipo().equals("tempo")) {
                    textSerie.setText(serie.getTempo() + "s");
                } else if(serie.getTipo().equals("peso")) {
                    textSerie.setText(serie.getRepeticoes() + "x de " + serie.getPeso() + "kg");
                }

                final CheckBox chkSerieTreino = (CheckBox) v.findViewById(R.id.chkSerieTreino);
                chkSerieTreino.setChecked(serie.isFeito());

                chkSerieTreino.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        Realm realm = Realm.getDefaultInstance();
                        realm.beginTransaction();

                        serie.setFeito(b);

                        realm.commitTransaction();

                        UpdateSerieTreino update = new UpdateSerieTreino();
                        update.setAcao("update");
                        update.setId(serie.getId());
                        update.setSerie_treino(serie);

                        UpdateSerieTreinoDao.save(update);

                        SyncService.request(SyncType.EXERCICIOS_TREINOS);
                    }
                });

                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        chkSerieTreino.setChecked(!chkSerieTreino.isChecked());
                    }
                });

                imgSerie.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        chkSerieTreino.setChecked(!chkSerieTreino.isChecked());
                    }
                });

                textSerie.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        chkSerieTreino.setChecked(!chkSerieTreino.isChecked());
                    }
                });

                holder.container_series.addView(v);
            }
        }
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