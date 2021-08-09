package com.ergnologi.uts_gery.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ergnologi.uts_gery.R;
import com.ergnologi.uts_gery.models.MahasiswaModels;
import com.ergnologi.uts_gery.utils.MahasiswaHelper;

public class DeleteActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String EXTRA_NOTE = "extra_note";
    public static final String EXTRA_POSITION = "extra_position";
    public static final int REQUEST_DELETE = 300;
    public static final int RESULT_DELETE = 301;
    private MahasiswaModels mahasiswaModels;
    private int position;
    private MahasiswaHelper mahasiswaHelper;
    private TextView txtNim;
    private Button btnHapus, btnBatal;
    private String nim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);
        //Hide Status Bar
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();
        //ViewBinding
        btnBatal = findViewById(R.id.btnBatal);
        btnHapus = findViewById(R.id.btnHapus);
        txtNim = findViewById(R.id.txtNim);
        btnHapus.setOnClickListener(this);
        btnBatal.setOnClickListener(this);
        //Helper Instance
        mahasiswaHelper = MahasiswaHelper.getInstance(getApplicationContext());
        mahasiswaModels = getIntent().getParcelableExtra(EXTRA_NOTE);
        if (mahasiswaModels != null) {
            position = getIntent().getIntExtra(EXTRA_POSITION, 0);
            nim = mahasiswaModels.getNim();
            txtNim.setText(nim);
        }
    }

    private void hapusData() {
        long result = mahasiswaHelper.deleteData(mahasiswaModels.getNim());
        if (result > 0) {
            Intent intent = new Intent();
            intent.putExtra(EXTRA_POSITION, position);
            setResult(RESULT_DELETE, intent);
            finish();
        } else
            Toast.makeText(getApplicationContext(), "Gagal menghapus data!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {
        if (view == btnBatal) {
            finish();
        } else if (view == btnHapus) {
            hapusData();
        }
    }
}
