package com.example.testkasirpintar;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testkasirpintar.component.CustomDialog;
import com.example.testkasirpintar.database.DBBarang;

public class BarangDetail extends AppCompatActivity implements View.OnClickListener {

    private Button btnTambah, btnBack;
    DBBarang dbBarang;
    SQLiteDatabase sqDb;
    ListView listView;
    String[] namaBarang;
    String[] kodeBarang;
    int[] idBarang;
    int[] stok;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_barang);

        btnTambah = findViewById(R.id.btnTambah);
        btnTambah.setOnClickListener(this);

        btnBack = findViewById(R.id.backBtn);
        btnBack.setOnClickListener(this);
        dbBarang = new DBBarang(BarangDetail.this);

        listView = findViewById(R.id.list);
        displayData();
    }

    private void displayData() {
        sqDb = dbBarang.getReadableDatabase();
        Cursor cursor = sqDb.rawQuery("select *from Barang", null);
        if (cursor.getCount()>0){
            listView.setVisibility(View.VISIBLE);
            idBarang= new int[cursor.getCount()];
            stok= new int[cursor.getCount()];
            namaBarang= new String[cursor.getCount()];
            kodeBarang= new String[cursor.getCount()];
            int i=0;
            while(cursor.moveToNext()){
                idBarang[i]=cursor.getInt(0);
                namaBarang[i]=cursor.getString(1);
                kodeBarang[i]=cursor.getString(2);
                stok[i]=cursor.getInt(3);
                i++;
                Barang adapter= new Barang();
                listView.setAdapter(adapter);
            }
        }
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.backBtn){
            onBackPressed();
        } else if( view.getId() == R.id.btnTambah){
//            Intent intent = new Intent(BarangDetail.this, TambahBarang.class);
//            startActivity(intent);
            final CustomDialog cd = new CustomDialog(this);
            cd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            cd.show();
            cd.cancelBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
                    cd.dismiss();
                }
            });
            cd.okBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dbBarang.insert(
                            cd.edtNama.getText().toString(),
                            cd.edtKode.getText().toString(),
                            cd.edtStok.getText().toString());
                    displayData();
                    cd.dismiss();
                }

            });

        }
    }

    private class Barang extends BaseAdapter {
        @Override
        public int getCount() {
            return namaBarang.length;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            TextView nameBarang, codeBarang, stock;
            ImageView imgEdit, imgDelete;
            view = LayoutInflater.from(BarangDetail.this)
                    .inflate(R.layout.list_barang, parent, false);
            nameBarang = view.findViewById(R.id.text_nama_barang);
            codeBarang = view.findViewById(R.id.text_id);
            stock = view.findViewById(R.id.text_stok);
            imgEdit = view.findViewById(R.id.imgEdit);
            imgDelete = view.findViewById(R.id.imgDelete);

            nameBarang.setText(namaBarang[position]);
            codeBarang.setText(idBarang[position]);
            stock.setText(stok[position]);

            imgEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CustomDialog cd = new CustomDialog(BarangDetail.this);
                    cd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    cd.show();
                    cd.header.setText("Edit Barang");
                    cd.cancelBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            onBackPressed();
                            cd.dismiss();
                        }
                    });
                    cd.okBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dbBarang.update(
                                    cd.edtNama.getText().toString(),
                                    cd.edtKode.getText().toString(),
                                    cd.edtStok.getText().toString());
                            displayData();
                            cd.dismiss();
                        }

                    });

                }
            });

            imgDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sqDb = dbBarang.getReadableDatabase();
                    long recd=sqDb.delete("Barang", "idBarang"+idBarang[position], null);
                    if (recd!=-1){
                        Toast.makeText(BarangDetail.this, "Berhasil Hapus Barang", Toast.LENGTH_SHORT).show();
                        displayData();
                    }
                }
            });
            return view;
        }
    }
}
