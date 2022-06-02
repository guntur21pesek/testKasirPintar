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

public class Checkout extends AppCompatActivity implements View.OnClickListener {

    private Button btnBack, btnSave, btnCancel, btnSumit;
    private Barang barangModel;

    ListView listView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkout_menu);

        btnBack = findViewById(R.id.backBtn);
        btnBack.setOnClickListener(this);
        btnCancel = findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(this);
        btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(this);
        btnSumit = findViewById(R.id.btnSubmit);
        btnSumit.setOnClickListener(this);

        barangModel = (Barang) getIntent().getSerializableExtra("barangModel");
        listView = findViewById(R.id.list);

        displayData();
    }

    private void displayData() {
        if (barangModel != null){
            listView.setVisibility(View.VISIBLE);
            CheckoutBarang adapter = new CheckoutBarang();
            listView.setAdapter(adapter);
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.backBtn || view.getId() == R.id.btnCancel){
            onBackPressed();
        } else if(view.getId() == R.id.btnSave){
            if (barangModel != null){
                Intent intent = new Intent(Checkout.this, OrderMenu.class);
                intent.putExtra("barangModel", barangModel);
                startActivity(intent);
            }
        }else if(view.getId() == R.id.btnSubmit){
            Intent intent = new Intent(Checkout.this, TransactionSucceed.class);
            startActivity(intent);
        }
    }

    private class CheckoutBarang extends BaseAdapter {
        @Override
        public int getCount() {
            return barangModel.getNamaBarang().length();
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
            view = LayoutInflater.from(Checkout.this)
                    .inflate(R.layout.list_order, parent, false);
            nameBarang = view.findViewById(R.id.text_nama_barang);
            codeBarang = view.findViewById(R.id.text_id);
            imgDelete = view.findViewById(R.id.imgDelete);
            nameBarang.setText(barangModel.getNamaBarang());
            codeBarang.setText(barangModel.getIdBarang());

            imgDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listView.removeViewAt(position);
                }
            });

            return view;
        }
    }
}
