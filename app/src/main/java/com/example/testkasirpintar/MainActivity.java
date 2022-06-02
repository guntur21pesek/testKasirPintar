package com.example.testkasirpintar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.testkasirpintar.database.DBBarang;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
private Button btnBarang, btnTransaksi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DBBarang dbBarang = new DBBarang(getBaseContext());

        btnBarang = findViewById(R.id.btnBarang);
        btnBarang.setOnClickListener(this);
        btnTransaksi = findViewById(R.id.btnTransaksi);
        btnTransaksi.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnBarang){
            Intent intent= new Intent(MainActivity.this, BarangDetail.class);
            startActivity(intent);
        } else if(view.getId() == R.id.btnTransaksi){
            Intent intent = new Intent(MainActivity.this, TransaksiMenu.class);
            startActivity(intent);
        }
    }
}