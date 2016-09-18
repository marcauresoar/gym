package com.gymproject.app.adapters;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.gymproject.app.R;
import com.gymproject.app.classes.ItemData;
import com.gymproject.app.dao.ExercicioDao;
import com.gymproject.app.dao.SerieDao;
import com.gymproject.app.dao.UpdateSerieDao;
import com.gymproject.app.models.Exercicio;
import com.gymproject.app.models.Serie;
import com.gymproject.app.models.UpdateSerie;
import com.gymproject.app.sync.SyncService;
import com.gymproject.app.sync.event.SyncEvent;
import com.gymproject.app.sync.event.SyncStatus;
import com.gymproject.app.sync.event.SyncType;
import com.gymproject.app.utils.HashUtils;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;

public class ExercicioAdapter extends RecyclerView.Adapter<ExercicioAdapter.ExercicioViewHolder> {

    private Context mContext;
    private List<Exercicio> items;
    private int selectedItem;

    public class ExercicioViewHolder extends RecyclerView.ViewHolder {
        public TextView nome, grupo_muscular;
        public ImageView imgExercicio;
        public LinearLayout container_series;
        public Button btnAddSerie;

        public ExercicioViewHolder(final View view) {
            super(view);
            nome = (TextView) view.findViewById(R.id.nome);
            grupo_muscular = (TextView) view.findViewById(R.id.grupo_muscular);
            imgExercicio = (ImageView) view.findViewById(R.id.imgExercicio);
            container_series = (LinearLayout) view.findViewById(R.id.container_series);
            btnAddSerie = (Button) view.findViewById(R.id.btnAddSerie);
        }
    }

    public ExercicioAdapter(Context context, List<Exercicio> items) {
        if (items == null) {
            throw new IllegalArgumentException("modelData must not be null");
        }
        this.mContext = context;
        this.items = items;
        this.selectedItem = -1;
    }

    public void putData(List<Exercicio> newData){
        this.items.clear();
        if(newData!=null && newData.size()>0){
            for(Exercicio item : newData){
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

    public Exercicio getItem(int position) {
        return items.get(position);
    }

    @Override
    public ExercicioViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_exercicio, parent, false);
        return new ExercicioViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ExercicioViewHolder holder, int position) {
        final Exercicio model = items.get(position);
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

        List<Serie> series = SerieDao.getAll(Realm.getDefaultInstance(), model.getId());
        if(series.size() > 0 ){
            holder.container_series.removeAllViews();

            for(final Serie serie : series){
                final View v = vi.inflate(R.layout.card_serie, null);

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

                ImageView btnExcluirSerie =  (ImageView) v.findViewById(R.id.btnExcluirSerie);
                btnExcluirSerie.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new AlertDialog.Builder(mContext)
                                .setTitle("Atenção")
                                .setMessage("Você realmente deseja excluir esse registro?")
                                .setIcon(R.drawable.ic_warning)
                                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {

                                        SerieDao.delete(serie);

                                        holder.container_series.removeView(v);

                                        SyncEvent.send(SyncType.EXERCICIOS, SyncStatus.COMPLETED);
                                        SyncService.request(SyncType.EXERCICIOS);

                                    }})
                                .setNegativeButton("Não", null).show();
                    }
                });

                holder.container_series.addView(v);
            }
        }

        holder.btnAddSerie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View dialogView = inflater.inflate(R.layout.dialog_add_serie, null);

                final Spinner spnTipo = (Spinner) dialogView.findViewById(R.id.spnTipo);

                final EditText edtTempo = (EditText) dialogView.findViewById(R.id.edtTempo);
                final EditText edtRepeticoes = (EditText) dialogView.findViewById(R.id.edtRepeticoes);
                final EditText edtPeso = (EditText) dialogView.findViewById(R.id.edtPeso);

                ArrayList<ItemData> mItens = new ArrayList<>();
                mItens.add(new ItemData("Tempo",R.drawable.ic_tempo));
                mItens.add(new ItemData("Peso",R.drawable.ic_peso));
                final CustomSpinnerAdapter adapter = new CustomSpinnerAdapter(mContext,
                        R.layout.row_grupo_muscular, R.id.text, mItens);
                spnTipo.setAdapter(adapter);
                spnTipo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        ItemData item = adapter.getItem(i);
                        if(item.getText().equals("Peso")){
                            edtTempo.setVisibility(View.GONE);
                            edtRepeticoes.setVisibility(View.VISIBLE);
                            edtPeso.setVisibility(View.VISIBLE);
                        } else if(item.getText().equals("Tempo")){
                            edtTempo.setVisibility(View.VISIBLE);
                            edtRepeticoes.setVisibility(View.GONE);
                            edtPeso.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }

                });

                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mContext)
                        .setPositiveButton("Adicionar", null)
                        .setNegativeButton("Cancelar", null);


                dialogBuilder.setView(dialogView);
                final AlertDialog alertDialog = dialogBuilder.create();
                alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {

                    @Override
                    public void onShow(DialogInterface dialog) {

                        Button b = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                        b.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View view) {
                                ItemData itemSelected = adapter.getItem(spnTipo.getSelectedItemPosition());
                                String tipo = itemSelected.getText().toLowerCase();
                                String tempo = edtTempo.getText().toString().trim();
                                String repeticoes = edtRepeticoes.getText().toString().trim();
                                String peso = edtPeso.getText().toString().trim();

                                if(tipo.equals("tempo") && tempo.isEmpty()){
                                    Toast.makeText(mContext, "Por favor, preencha o campo Tempo!", Toast.LENGTH_LONG).show();
                                } else if(tipo.equals("peso") && repeticoes.isEmpty()){
                                    Toast.makeText(mContext, "Por favor, preencha o campo Repetições!", Toast.LENGTH_LONG).show();
                                } else if(tipo.equals("peso") && peso.isEmpty()){
                                    Toast.makeText(mContext, "Por favor, preencha o campo Peso!", Toast.LENGTH_LONG).show();
                                } else {

                                    Serie serie = new Serie();
                                    serie.setId(HashUtils.generateId());
                                    serie.setTipo(tipo);
                                    if(tipo.equals("tempo")) {
                                        serie.setTempo(Integer.valueOf(tempo));
                                    } else if(tipo.equals("peso")) {
                                        serie.setRepeticoes(Integer.valueOf(repeticoes));
                                        serie.setPeso(Integer.valueOf(peso));
                                    }
                                    System.out.println("ID: " + model.getId());
                                    System.out.println("NOME: " + ExercicioDao.getById(Realm.getDefaultInstance(), model.getId()).getNome());
                                    serie.setExercicio(ExercicioDao.getById(Realm.getDefaultInstance(), model.getId()));
                                    SerieDao.save(serie, "insert");

                                    // dispara sync e fecha activity
                                    SyncEvent.send(SyncType.EXERCICIOS, SyncStatus.COMPLETED);
                                    SyncService.request(SyncType.EXERCICIOS);
                                    alertDialog.dismiss();
                                }
                            }
                        });
                    }
                });
                alertDialog.show();
            }
        });
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