package com.example.testkasirpintar;

import android.content.Intent;
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

import com.example.testkasirpintar.model.Barang;

public class OrderMenu extends AppCompatActivity implements View.OnClickListener {
    private Button backBtn;
    Barang barangModel;
    ListView listView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_menu);

        backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(this);

        barangModel = (Barang) getIntent().getSerializableExtra("barangModel");
        listView = findViewById(R.id.list);

        displayData();
    }

    private void displayData() {
        if (barangModel != null){
            listView.setVisibility(View.VISIBLE);
            OrderBarang adapter = new OrderBarang();
            listView.setAdapter(adapter);
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.backBtn){
            onBackPressed();
        }
    }

    private class OrderBarang extends BaseAdapter {
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
            view = LayoutInflater.from(OrderMenu.this)
                    .inflate(R.layout.list_order, parent, false);
            nameBarang = view.findViewById(R.id.text_nama_barang);
            codeBarang = view.findViewById(R.id.text_id);
            imgDelete = view.findViewById(R.id.imgDelete);
            imgEdit = findViewById(R.id.imgCart);
            nameBarang.setText(barangModel.getNamaBarang());
            codeBarang.setText(barangModel.getIdBarang());

            imgEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (barangModel != null){
                        Intent intent = new Intent(OrderMenu.this, Checkout.class);
                        intent.putExtra("barangModel", barangModel);
                        startActivity(intent);
                    }
                }
            });

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
