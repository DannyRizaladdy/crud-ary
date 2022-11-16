package com.pmo.crud_aryafirmansyah;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Locale;

public class list_mahasiswa extends AppCompatActivity implements ReyclerViewAdapter.dataListener {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private DatabaseReference reference;
    private ArrayList<data_mahasiswa>  data_mahasiswa;

    private FloatingActionButton fab;

    private EditText search;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_mahasiswa);

        recyclerView = findViewById(R.id.data_list);
        fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(list_mahasiswa.this, mahasiswa.class);
                startActivity(intent);
            }
        });

        search = findViewById(R.id.search);

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) { filter(editable.toString());
            }


        });
        MyRecyclerView();
        GetData();
    }


    private void MyRecyclerView() {
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);


        DividerItemDecoration itemDecoration = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.line));
        recyclerView.addItemDecoration(itemDecoration);
    }

    private void GetData(){
        Toast.makeText(getApplicationContext(), "Mohon Tunggu Sebentar", Toast.LENGTH_SHORT).show();

        reference = FirebaseDatabase.getInstance().getReference();
        reference.child("Admin").child("Mahasiswa")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        data_mahasiswa = new ArrayList<>();

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                            data_mahasiswa mahasiswa = snapshot.getValue(data_mahasiswa.class);

                            //Mengambil primary key untuk Update dan Delete
                            mahasiswa.setKey(snapshot.getKey());
                            data_mahasiswa.add(mahasiswa);
                        }
                        adapter = new ReyclerViewAdapter(data_mahasiswa, list_mahasiswa.this);

                        recyclerView.setAdapter(adapter);

                        Toast.makeText(getApplicationContext(), "Data Berhasil Disimpan", Toast.LENGTH_SHORT).show();

                    }



                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                        Toast.makeText(getApplicationContext(), "Data Gagal Dimuat", Toast.LENGTH_SHORT).show();
                        Log.e("MyListActivity", databaseError.getDetails()+" "+databaseError.getMessage());


                    }
                });
    }

    @Override
    public void onDeleteData(com.pmo.crud_aryafirmansyah.data_mahasiswa data, int position) {
        if (reference !=null){
            reference.child("Admin")
                    .child("Mahasiswa")
                    .child(data.getKey())
                    .removeValue()
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(list_mahasiswa.this,"Data Berhasil Dihapus", Toast.LENGTH_SHORT).show();
                        }
                    });
        }

    }

    private void filter(String text) {
        ArrayList<data_mahasiswa> filteredlist = new ArrayList<>();

        for (data_mahasiswa item : data_mahasiswa ){
            if (item.getNama().toLowerCase().contains(text.toLowerCase())){
                filteredlist.add(item);
            }
        }
    }
}