package com.pmo.crud_aryafirmansyah;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pmo.crud_aryafirmansyah.databinding.FragmentHomeBinding;
import com.pmo.crud_aryafirmansyah.ui.home.HomeViewModel;

public class mahasiswa extends Fragment {
    EditText nim, nama, jurusan;
    Button simpan,show_data;
    ProgressBar progress;
    String getNIM, getNama, getJurusan;

    DatabaseReference getReference;
    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root =  inflater.inflate(R.layout.activity_mahasiswa, container, false);
        //Inisialisas ID (EditText)
        nim = root.findViewById(R.id.nim);
        nama = root.findViewById(R.id.nama);
        jurusan = root.findViewById(R.id.jurusan);

        //Inisisalisasi ID (Button)
        simpan = root.findViewById(R.id.simpan);
        show_data = root.findViewById(R.id.show_data);

        //Inisialisasi ID (Progress Bar)
        progress=root.findViewById(R.id.progress);
        progress.setVisibility(View.GONE);

        //Mendapatkan Instance dari Database
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        getReference = database.getReference();

        //Membuat fungsi tombol simpan
        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Menyimpan Data yang Diinputkan user kedalam variabel
                getNIM = nim.getText().toString();
                getNama = nama.getText().toString();
                getJurusan = jurusan.getText().toString();

                checkUser();
                progress.setVisibility(view.VISIBLE);
            }
        });

        show_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),list_mahasiswa.class);
                startActivity(intent);
            }
        });



        return root;
    }

    private void checkUser() {
    //mengecek apakah ada data yang kosong
    if (getNIM==null && getNama==null && getJurusan==null){
        //Jika ada, maka akan muncul pesan
        Toast.makeText(getActivity(), "Wajib Mengisi Data", Toast.LENGTH_SHORT).show();
        }else{

        getReference.child("Admin").child("Mahasiswa").push()
                .setValue(new data_mahasiswa(getNIM,getNama,getJurusan))
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        nama.setText("");
                        nim.setText("");
                        jurusan.setText("");
                        Toast.makeText(getActivity(), "Data Berhasil Tersimpan", Toast.LENGTH_SHORT).show();
                        progress.setVisibility(View.GONE);
                    }
                });
    }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
/*    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mahasiswa);
    }
}*/