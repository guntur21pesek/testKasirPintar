package com.example.testkasirpintar;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

import com.example.testkasirpintar.database.DBBarang;
import com.example.testkasirpintar.model.Barang;

public class TransaksiMenu extends AppCompatActivity implements View.OnClickListener {

    private Button btnBack, btnCheckout;
    Barang barangModel;
    private ImageView btnOrder;

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
        setContentView(R.layout.transaksi_menu);

        btnBack = findViewById(R.id.backBtn);
        btnBack.setOnClickListener(this);
        btnCheckout = findViewById(R.id.btnCheckout);
        btnCheckout.setOnClickListener(this);
        btnOrder = findViewById(R.id.imgCart);
        btnOrder.setOnClickListener(this);

        dbBarang = new DBBarang(TransaksiMenu.this);

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
                TransaksiBarang adapter= new TransaksiBarang();
                listView.setAdapter(adapter);
            }
        }
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.backBtn){
            onBackPressed();
        }else if (view.getId() == R.id.btnCheckout){
            if (barangModel != null){
                Intent intent= new Intent(TransaksiMenu.this, Checkout.class);
                intent.putExtra("barangModel", barangModel);
                startActivity(intent);
            }
        }else if(view.getId() == R.id.imgCart){
            if (barangModel != null){
                Intent intent = new Intent(TransaksiMenu.this, OrderMenu.class);
                intent.putExtra("barangModel", barangModel);
                startActivity(intent);
            }
        }
    }

    private class TransaksiBarang extends BaseAdapter {
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
            view = LayoutInflater.from(TransaksiMenu.this)
                    .inflate(R.layout.list_transaksis, parent, false);
            nameBarang = view.findViewById(R.id.text_nama_barang);
            codeBarang = view.findViewById(R.id.text_id);
            imgDelete = view.findViewById(R.id.imgAddCart);
            nameBarang.setText(namaBarang[position]);
            codeBarang.setText(idBarang[position]);
            imgDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    barangModel = new Barang();
                    barangModel.setIdBarang(idBarang[position]);
                    barangModel.setNamaBarang(namaBarang[position]);
                    barangModel.setKodeBarang(kodeBarang[position]);
                    barangModel.setStok(stok[position]);
                }
            });
            return view;
        }
    }
}
