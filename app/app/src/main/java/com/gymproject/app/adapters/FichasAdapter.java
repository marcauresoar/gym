package com.gymproject.app.adapters;

import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gymproject.app.R;
import com.gymproject.app.models.Ficha;

import java.util.List;

public class FichasAdapter extends RecyclerView.Adapter<FichasAdapter.MyViewHolder> {

    private Context mContext;
    private List<Ficha> fichaList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView nome, dias_semana;
        public ImageView overflow;

        public MyViewHolder(View view) {
            super(view);
            nome = (TextView) view.findViewById(R.id.nome);
            dias_semana = (TextView) view.findViewById(R.id.dias_semana);
            overflow = (ImageView) view.findViewById(R.id.overflow);
        }
    }


    public FichasAdapter(Context mContext, List<Ficha> fichaList) {
        this.mContext = mContext;
        this.fichaList = fichaList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ficha_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Ficha ficha = fichaList.get(position);
        holder.nome.setText(ficha.getNome());
        String dias_semana =ficha.getDias_semana();
        holder.dias_semana.setText(dias_semana.replace(",", ", "));

        holder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(holder.overflow);
            }
        });
    }

    /**
     * Showing popup menu when tapping on 3 dots
     */
    private void showPopupMenu(View view) {
        // inflate menu
        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_ficha_item, popup.getMenu());
        popup.setOnMenuItemClickListener(new FichaMenuItemClickListener());
        popup.show();
    }

    /**
     * Click listener for popup menu items
     */
    class FichaMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        public FichaMenuItemClickListener() {
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.action_editar:

                    return true;
                case R.id.action_deletar:

                    return true;
                default:
            }
            return false;
        }
    }

    @Override
    public int getItemCount() {
        return fichaList.size();
    }
}