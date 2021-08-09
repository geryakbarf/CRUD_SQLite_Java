package com.ergnologi.uts_gery.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ergnologi.uts_gery.R;
import com.ergnologi.uts_gery.models.MahasiswaModels;
import com.ergnologi.uts_gery.utils.MahasiswaHelper;

public class AddActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String EXTRA_NOTE = "extra_note";
    public static final String EXTRA_POSITION = "extra_position";
    public static final int REQUEST_ADD = 100;
    public static final int RESULT_ADD = 101;
    public static final int REQUEST_UPDATE = 200;
    public static final int RESULT_UPDATE = 201;
    private EditText etNim, etNama, etKelas, etProdi;
    private TextView txtTitle;
    private Button btnSimpan;
    private ImageView btnBack;
    private boolean isEdit = false;
    private MahasiswaModels mahasiswaModels;
    private int position;
    private MahasiswaHelper mahasiswaHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        //Hide StatusBar
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();
        //View Binding
        txtTitle = findViewById(R.id.txtTitle);
        etNim = findViewById(R.id.etNim);
        etNama = findViewById(R.id.etNama);
        etKelas = findViewById(R.id.etKelas);
        etProdi = findViewById(R.id.etProdi);
        btnSimpan = findViewById(R.id.btnSimpan);
        btnBack = findViewById(R.id.btnBack);
        btnSimpan.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        //Helper Instance
        mahasiswaHelper = MahasiswaHelper.getInstance(getApplicationContext());
        mahasiswaModels = getIntent().getParcelableExtra(EXTRA_NOTE);
        if (mahasiswaModels != null) {
            position = getIntent().getIntExtra(EXTRA_POSITION, 0);
            isEdit = true;
        } else
            mahasiswaModels = new MahasiswaModels();
        //Pengecekan apakah sedang Add Atau Edit
        if (isEdit) {
            txtTitle.setText("Ubah Data");
            btnSimpan.setText("Ubah Data");
            if (mahasiswaModels != null) {
                etNim.setText(mahasiswaModels.getNim());
                etNama.setText(mahasiswaModels.getNama());
                etKelas.setText(mahasiswaModels.getKelas());
                etProdi.setText(mahasiswaModels.getProdi());
                etNim.setFocusable(false);
                etNim.setClickable(false);
            }
        }
    }

    private void saveData(String nim, String nama, String kelas, String prodi) {
        //Pengecekan apakah data input kosong
        if (TextUtils.isEmpty(nim) || TextUtils.isEmpty(nama) || TextUtils.isEmpty(kelas) || TextUtils.isEmpty(prodi)) {
            Toast.makeText(getApplicationContext(), "Data input tidak boleh kosong!", Toast.LENGTH_SHORT).show();
            return;
        }

        mahasiswaModels.setNim(nim);
        mahasiswaModels.setNama(nama);
        mahasiswaModels.setKelas(kelas);
        mahasiswaModels.setProdi(prodi);

        Intent intent = new Intent();
        intent.putExtra(EXTRA_NOTE, mahasiswaModels);
        intent.putExtra(EXTRA_POSITION, position);
        if (isEdit) {
            long result = mahasiswaHelper.updateData(mahasiswaModels);
            if (result > 0) {
                setResult(RESULT_UPDATE, intent);
                finish();
            } else
                Toast.makeText(getApplicationContext(), "Gagal mengubah data :(", Toast.LENGTH_SHORT).show();
        } else {
            boolean isExist = mahasiswaHelper.checkNim(mahasiswaModels.getNim());
            if (!isExist) {
                long result = mahasiswaHelper.inserData(mahasiswaModels);
                if (result > 0) {
                    setResult(RESULT_ADD, intent);
                    finish();
                } else
                    Toast.makeText(getApplicationContext(), "Gagal menambah data!", Toast.LENGTH_SHORT).show();
            } else
                Toast.makeText(getApplicationContext(), "Data nim " + mahasiswaModels.getNim() + " sudah ada!", Toast.LENGTH_SHORT).show();

        }
    }


    @Override
    public void onClick(View view) {
        if (view == btnSimpan) {
            saveData(etNim.getText().toString().trim(), etNama.getText().toString().trim(), etKelas.getText().toString().trim(), etProdi.getText().toString().trim());
        } else if (view == btnBack)
            finish();
    }
}
