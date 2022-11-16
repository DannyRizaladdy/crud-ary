package com.pmo.crud_aryafirmansyah;

import static android.text.TextUtils.isEmpty;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class upd_mahasiswa extends AppCompatActivity {

    private EditText new_nim, new_nama, new_jurusan;
    private Button update;
    private DatabaseReference databaseReference;
    private String cekNIM, cekNAMA, cekJURUSAN;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upd_mahasiswa);

        getSupportActionBar().setTitle("Update Data");
        new_nim = findViewById(R.id.new_nim);
        new_nama = findViewById(R.id.new_nama);
        new_jurusan = findViewById(R.id.new_jurusan);
        update = findViewById(R.id.update);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        getData();

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Mendapatkan Data Mahasiswa yang akan dicek
                cekNIM = new_nim.getText().toString();
                cekNAMA = new_nama.getText().toString();
                cekJURUSAN = new_jurusan.getText().toString();


                if (isEmpty(cekNIM) || isEmpty(cekNAMA) || isEmpty(cekJURUSAN)){
                    Toast.makeText(upd_mahasiswa.this, "Data Harus Diisi", Toast.LENGTH_SHORT).show();
                }else {
                    data_mahasiswa setMahasiswa = new data_mahasiswa();
                    setMahasiswa.setNim(new_nim.getText().toString());
                    setMahasiswa.setNama(new_nama.getText().toString());
                    setMahasiswa.setJurusan(new_jurusan.getText().toString());
                    updateMahasiswa(setMahasiswa);
                }
            }
        });
    }

    private void updateMahasiswa(data_mahasiswa setMahasiswa) {
        String getKEY = getIntent().getExtras().getString("dataPrimaryKey");
        databaseReference.child("Admin")
                .child("Mahasiswa")
                .child(getKEY)
                .setValue(setMahasiswa)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        new_nim.setText("");
                        new_nama.setText("");
                        new_jurusan.setText("");
                        Toast.makeText(upd_mahasiswa.this,"Data Berhasil Diubah",Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });

    }


    private void getData() {

        final String getNIM = getIntent().getExtras().getString("dataNIM");
        final String getNAMA = getIntent().getExtras().getString("dataNAMA");
        final String getJURUSAN = getIntent().getExtras().getString("dataJURUSAN");
        new_nim.setText(getNIM);
        new_nama.setText(getNAMA);
        new_jurusan.setText(getJURUSAN);
    }


}