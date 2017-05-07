package br.gov.ce.sda.androidsda.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.gov.ce.sda.androidsda.R;
import br.gov.ce.sda.androidsda.model.Filme;

/**
 * Adapter for the list of Movies on Recycle view.
 * Adapter para a lista de filmes na Recycle view
 */

public class FilmesAdapter extends RecyclerView.Adapter<FilmesAdapter.FilmeViewHolder> {
    private List<Filme> filmes;
    private ItemClickListener clickListener;
    private int rowLayout;

    public FilmesAdapter(List<Filme> filmes, int rowLayout) {
        this.filmes = filmes;
        this.rowLayout = rowLayout;
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    @Override
    public FilmeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new FilmeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FilmeViewHolder holder, int position) {
        holder.titulo.setText(filmes.get(position).getTitulo());
    }

    @Override
    public int getItemCount() {
        return filmes.size();
    }

    public class FilmeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView titulo;

        public FilmeViewHolder(View itemView) {
            super(itemView);
            titulo = (TextView) itemView.findViewById(R.id.tvTitulo);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (clickListener != null) clickListener.onClick(v, getAdapterPosition());
        }
    }
}
