package com.pmo.crud_aryafirmansyah;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.pmo.crud_aryafirmansyah.databinding.ActivityUpdMahasiswaBinding;

import java.util.ArrayList;

public class ReyclerViewAdapter extends RecyclerView.Adapter<ReyclerViewAdapter.ViewHolder> {

    private ArrayList<data_mahasiswa> listMahasiswa;
    private Context context;

    public void filterList(ArrayList<data_mahasiswa> filteredList) {
        listMahasiswa = new ArrayList<>();
        listMahasiswa.addAll(filteredList);
        notifyDataSetChanged();
    }

    public interface dataListener{
        void onDeleteData(data_mahasiswa data ,int position);
    }

    dataListener listener;

    public ReyclerViewAdapter(ArrayList<data_mahasiswa> listMahasiswa, Context context) {
        this.listMahasiswa = listMahasiswa;
        this.context = context;
        listener = (list_mahasiswa)context;
    }

    @NonNull
    @Override
    public ReyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View V = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_mhs,parent, false);
        return new ViewHolder(V);
    }

    @Override
    public void onBindViewHolder(@NonNull ReyclerViewAdapter.ViewHolder holder, int position) {
        final String nim = listMahasiswa.get(position).getNim();
        final String nama = listMahasiswa.get(position).getNama();
        final String jurusan = listMahasiswa.get(position).getJurusan();

        holder.nim.setText("NIM: "+nim);
        holder.nama.setText("NAMA: "+nama);
        holder.jurusan.setText("JURUSAN: "+jurusan);

        holder.list_item.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view)
            {
                final String[] action = {"Update", "Delete"};
                AlertDialog.Builder alert = new AlertDialog.Builder(view.getContext());
                alert.setItems(action, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i){
                            case 0:

                                Bundle bundle = new Bundle();
                                bundle.putString("dataNIM",listMahasiswa.get(holder.getAdapterPosition()).getNim());
                                bundle.putString("dataNAMA",listMahasiswa.get(holder.getAdapterPosition()).getNama());
                                bundle.putString("dataJURUSAN",listMahasiswa.get(holder.getAdapterPosition()).getJurusan());
                                bundle.putString("dataPrimaryKey",listMahasiswa.get(holder.getAdapterPosition()).getKey());
                                Intent intent = new Intent(view.getContext(),upd_mahasiswa.class);
                                intent.putExtras(bundle);
                                context.startActivity(intent);
                                break;

                            case 1:
                            listener.onDeleteData(listMahasiswa.get(holder.getAdapterPosition()), holder.getAdapterPosition());
                            break;
                        }
                    }
                });
                alert.create();
                alert.show();
                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return listMahasiswa.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView nim,nama,jurusan;
        private CardView list_item;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nim = itemView.findViewById(R.id.nim);
            nama = itemView.findViewById(R.id.nama);
            jurusan = itemView.findViewById(R.id.jurusan);
            list_item = itemView.findViewById(R.id.list_item);
        }
    }
}
