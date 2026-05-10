package com.kelompok2.gomeseumsulsel;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class MuseumAdapter extends RecyclerView.Adapter<MuseumAdapter.ViewHolder> implements Filterable {

    private List<Museum> listMuseum;
    private List<Museum> listMuseumFull;

    public MuseumAdapter(List<Museum> listMuseum) {
        this.listMuseum = listMuseum;
        this.listMuseumFull = new ArrayList<>(listMuseum);
    }

    public void updateList(List<Museum> newList) {
        this.listMuseum = newList;
        this.listMuseumFull = new ArrayList<>(newList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_museum, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Museum museum = listMuseum.get(position);

        holder.tvNamaMuseum.setText(museum.getNama());
        holder.tvLokasiMuseum.setText(museum.getLokasi());

        Glide.with(holder.itemView.getContext())
                .load(museum.getGambar())
                .placeholder(R.drawable.logo)
                .error(R.drawable.logo)
                .centerCrop()
                .into(holder.imgMuseum);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(holder.itemView.getContext(), DetailMuseumActivity.class);
            intent.putExtra("EXTRA_NAMA", museum.getNama());
            intent.putExtra("EXTRA_LOKASI", museum.getLokasi());
            intent.putExtra("EXTRA_DESKRIPSI", museum.getDeskripsi());
            intent.putExtra("EXTRA_GAMBAR", museum.getGambar());
            intent.putExtra("EXTRA_RATING", museum.getRating());
            holder.itemView.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return listMuseum.size();
    }

    @Override
    public Filter getFilter() {
        return museumFilter;
    }

    private Filter museumFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Museum> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(listMuseumFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Museum item : listMuseumFull) {
                    if (item.getNama() != null && item.getNama().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            listMuseum.clear();
            if (results.values != null) {
                listMuseum.addAll((List<Museum>) results.values);
            }
            notifyDataSetChanged();
        }
    };

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgMuseum;
        TextView tvNamaMuseum, tvLokasiMuseum;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgMuseum = itemView.findViewById(R.id.imgMuseum);
            tvNamaMuseum = itemView.findViewById(R.id.tvNamaMuseum);
            tvLokasiMuseum = itemView.findViewById(R.id.tvLokasiMuseum);
        }
    }
}