package com.ergnologi.uts_gery.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ergnologi.uts_gery.R;
import com.ergnologi.uts_gery.models.MahasiswaModels;
import com.ergnologi.uts_gery.ui.AddActivity;
import com.ergnologi.uts_gery.ui.DeleteActivity;
import com.ergnologi.uts_gery.utils.CustomOnItemClickListener;

import java.util.ArrayList;

public class MahasiswaAdapter extends RecyclerView.Adapter<MahasiswaAdapter.ViewHolder> {
    private ArrayList<MahasiswaModels> list = new ArrayList<>();
    private Activity activity;

    public MahasiswaAdapter(Activity activity) {
        this.activity = activity;
    }

    public ArrayList<MahasiswaModels> getList() {
        return list;
    }

    public void setList(ArrayList<MahasiswaModels> list) {
        if (list.size() > 0) {
            this.list.clear();
        }
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public void addItem(MahasiswaModels mahasiswaModels) {
        this.list.add(mahasiswaModels);
        notifyItemInserted(list.size() - 1);
    }

    public void updateItem(int position, MahasiswaModels mahasiswaModels) {
        this.list.set(position, mahasiswaModels);
        notifyItemChanged(position, mahasiswaModels);
    }

    public void removeItem(int position) {
        this.list.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, list.size());
    }

    @NonNull
    @Override
    public MahasiswaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_mahasiswa, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MahasiswaAdapter.ViewHolder holder, int position) {
        holder.txtNim.setText(list.get(position).getNim());
        holder.txtNama.setText(list.get(position).getNama());
        holder.txtKelas.setText(list.get(position).getKelas());
        holder.txtProdi.setText(list.get(position).getProdi());
        holder.btnUbah.setOnClickListener(new CustomOnItemClickListener(position, new CustomOnItemClickListener.OnItemClickCallback() {
            @Override
            public void onItemClicked(View view, int position) {
                Intent intent = new Intent(activity, AddActivity.class);
                intent.putExtra(AddActivity.EXTRA_POSITION, position);
                intent.putExtra(AddActivity.EXTRA_NOTE, list.get(position));
                activity.startActivityForResult(intent, AddActivity.REQUEST_UPDATE);
            }
        }));
        holder.btnHapus.setOnClickListener(new CustomOnItemClickListener(position, new CustomOnItemClickListener.OnItemClickCallback() {
            @Override
            public void onItemClicked(View view, int position) {
                Intent intent = new Intent(activity, DeleteActivity.class);
                intent.putExtra(DeleteActivity.EXTRA_POSITION, position);
                intent.putExtra(DeleteActivity.EXTRA_NOTE, list.get(position));
                activity.startActivityForResult(intent, DeleteActivity.REQUEST_DELETE);
            }
        }));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtNim, txtNama, txtKelas, txtProdi;
        Button btnUbah, btnHapus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNim = itemView.findViewById(R.id.txtNim);
            txtNama = itemView.findViewById(R.id.txtNama);
            txtKelas = itemView.findViewById(R.id.txtKelas);
            txtProdi = itemView.findViewById(R.id.txtProdi);
            btnUbah = itemView.findViewById(R.id.btnUbah);
            btnHapus = itemView.findViewById(R.id.btnHapus);
        }
    }
}
