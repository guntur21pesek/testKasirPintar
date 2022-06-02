package com.example.testkasirpintar;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class TransactionSucceed extends AppCompatActivity implements View.OnClickListener {

    private Button btnNewTransaksi, btnHome;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transaction_sukses);

        btnNewTransaksi = findViewById(R.id.btnNewTransaction);
        btnNewTransaksi.setOnClickListener(this);
        btnHome = findViewById(R.id.btnHome);
        btnHome.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnNewTransaction){
            Intent intent = new Intent(TransactionSucceed.this, TransaksiMenu.class);
            startActivity(intent);
        }else if (view.getId() == R.id.btnHome){
            Intent intent = new Intent(TransactionSucceed.this, MainActivity.class);
            startActivity(intent);
        }
    }
}
