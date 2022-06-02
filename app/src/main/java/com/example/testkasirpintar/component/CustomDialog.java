package com.example.testkasirpintar.component;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.testkasirpintar.R;

public class CustomDialog extends Dialog implements View.OnClickListener {

    public Activity c;
    public Dialog d;
    public Button okBtn, cancelBtn;
    public TextView header, isi, otpBtn;
    public EditText edtNama, edtKode, edtStok;

    public CustomDialog(Activity a) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog);

        header = (TextView) findViewById(R.id.txt_header);

        okBtn = (Button) findViewById(R.id.okBtn);
        okBtn.setOnClickListener(this);
        cancelBtn = (Button) findViewById(R.id.cancelBtn);
        cancelBtn.setOnClickListener(this);

        edtNama = (EditText) findViewById(R.id.edtNamaBrg);
        edtKode = (EditText) findViewById(R.id.edtKodeBrg);
        edtStok = (EditText) findViewById(R.id.edtStok);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.okBtn:
                dismiss();
                break;
            case R.id.cancelBtn:
                dismiss();
            default:
                break;
        }
        dismiss();
    }
}
