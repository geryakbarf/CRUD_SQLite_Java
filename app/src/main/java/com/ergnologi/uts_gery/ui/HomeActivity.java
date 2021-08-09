package com.ergnologi.uts_gery.ui;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ergnologi.uts_gery.R;
import com.ergnologi.uts_gery.adapter.MahasiswaAdapter;
import com.ergnologi.uts_gery.models.MahasiswaModels;
import com.ergnologi.uts_gery.utils.LoadDataCallback;
import com.ergnologi.uts_gery.utils.MahasiswaHelper;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener, LoadDataCallback {

    private static final String EXTRA_STATE = "EXTRA_STATE";
    private ConstraintLayout btnAdd;
    private RecyclerView rvMahasiswa;
    private MahasiswaAdapter mahasiswaAdapter;
    private MahasiswaHelper mahasiswaHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //Hide StatusBar
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();
        //View Binding
        btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);
        //RecyclerView Binding
        rvMahasiswa = findViewById(R.id.rvMahasiswa);
        rvMahasiswa.setLayoutManager(new LinearLayoutManager(this));
        rvMahasiswa.setHasFixedSize(true);
        //Helper Instance
        mahasiswaHelper = MahasiswaHelper.getInstance(getApplicationContext());
        mahasiswaHelper.open();
        //Adapter Binding
        mahasiswaAdapter = new MahasiswaAdapter(this);
        rvMahasiswa.setAdapter(mahasiswaAdapter);

        if (savedInstanceState == null) {
            new LoadDataAsync(mahasiswaHelper, this).execute();
        } else {
            ArrayList<MahasiswaModels> list = savedInstanceState.getParcelableArrayList(EXTRA_STATE);
            if (list != null) {
                mahasiswaAdapter.setList(list);
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(EXTRA_STATE, mahasiswaAdapter.getList());
    }


    @Override
    public void onClick(View view) {
        Intent intent = new Intent(getApplicationContext(), AddActivity.class);
        startActivityForResult(intent, AddActivity.REQUEST_ADD);
    }

    @Override
    public void preExecute() {
        //Do Nothing
    }

    @Override
    public void postExecute(ArrayList<MahasiswaModels> data) {
        mahasiswaAdapter.setList(data);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (requestCode == AddActivity.REQUEST_ADD) {
                if (resultCode == AddActivity.RESULT_ADD) {
                    MahasiswaModels mhs = data.getParcelableExtra(AddActivity.EXTRA_NOTE);
                    mahasiswaAdapter.addItem(mhs);
                    rvMahasiswa.smoothScrollToPosition(mahasiswaAdapter.getItemCount() - 1);
                    Toast.makeText(getApplicationContext(), "Berhasil menambahkan data", Toast.LENGTH_SHORT).show();
                }
            } else if (requestCode == AddActivity.REQUEST_UPDATE) {
                if (resultCode == AddActivity.RESULT_UPDATE) {
                    MahasiswaModels mhs = data.getParcelableExtra(AddActivity.EXTRA_NOTE);
                    int position = data.getIntExtra(AddActivity.EXTRA_POSITION, 0);
                    mahasiswaAdapter.updateItem(position, mhs);
                    rvMahasiswa.smoothScrollToPosition(position);
                    Toast.makeText(getApplicationContext(), "Berhasil mengubah data", Toast.LENGTH_SHORT).show();
                }
            } else if (requestCode == DeleteActivity.REQUEST_DELETE) {
                if (resultCode == DeleteActivity.RESULT_DELETE) {
                    int position = data.getIntExtra(DeleteActivity.EXTRA_POSITION, 0);
                    mahasiswaAdapter.removeItem(position);
                    Toast.makeText(getApplicationContext(), "Berhasil menghapus data", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mahasiswaHelper.close();
    }

    private static class LoadDataAsync extends AsyncTask<Void, Void, ArrayList<MahasiswaModels>> {

        private final WeakReference<MahasiswaHelper> weakNoteHelper;
        private final WeakReference<LoadDataCallback> weakCallback;

        private LoadDataAsync(MahasiswaHelper noteHelper, LoadDataCallback callback) {
            weakNoteHelper = new WeakReference<>(noteHelper);
            weakCallback = new WeakReference<>(callback);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            weakCallback.get().preExecute();
        }

        @Override
        protected ArrayList<MahasiswaModels> doInBackground(Void... voids) {
            return weakNoteHelper.get().getAllData();
        }

        @Override
        protected void onPostExecute(ArrayList<MahasiswaModels> data) {
            super.onPostExecute(data);
            weakCallback.get().postExecute(data);
        }
    }
}
