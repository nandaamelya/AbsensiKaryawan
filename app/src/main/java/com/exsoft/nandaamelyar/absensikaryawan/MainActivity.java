package com.exsoft.nandaamelyar.absensikaryawan;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.exsoft.nandaamelyar.absensikaryawan.domain.Karyawan;

import java.util.List;

public class MainActivity extends AppCompatActivity {





    Karyawan karyawan = new Karyawan();
    DBAdapter dbAdapter = null;

    EditText txtNama, txtJabatan;
    ListView listKaryawan;
    Button btnSimpan;
    Karyawan editKaryawan;

    private static final String OPTION[] = {"Edit", "Delete"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbAdapter = new DBAdapter(getApplicationContext());

        btnSimpan = (Button) findViewById(R.id.btnSimpan);
        txtNama = (EditText) findViewById(R.id.txtNama);
        txtJabatan = (EditText) findViewById(R.id.txtJabatan);
        listKaryawan = (ListView) findViewById(R.id.listKaryawan);

        listKaryawan.setOnItemClickListener(new ListItemClick());
        listKaryawan.setAdapter(new ListKaryawanAdapter(dbAdapter
                .getAllKaryawan()));
    }

    public class ListItemClick implements AdapterView
            .OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> adapter, View view,
                                int position, long id) {
            // TODO Auto-generated method stub
            final Karyawan karyawan = (Karyawan) listKaryawan
                    .getItemAtPosition(position);
            showOptionDialog(karyawan);
        }
    }

    public void showOptionDialog(Karyawan karyawan) {
        final Karyawan mKaryawan;
        mKaryawan = karyawan;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder
                .setTitle("Test")
                .setItems(OPTION, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int post) {
                        // TODO Auto-generated method stub
                        switch (post) {
                            case 0:
                                editKaryawan = mKaryawan;
                                txtNama.setText(mKaryawan.getNama());
                                txtJabatan.setText(mKaryawan.getJabatan());
                                btnSimpan.setText("Edit");
                                break;
                            case 1:
                                dbAdapter.delete(mKaryawan);
                                listKaryawan.setAdapter
                                        (new ListKaryawanAdapter(dbAdapter.getAllKaryawan()));
                                break;
                            default:
                                break;
                        }
                    }
                });
        final Dialog d = builder.create();
        d.show();
    }

    public void save(View v) {
        if(txtNama.getText().length() == 0 ||
                txtJabatan.getText().length() == 0) {
            txtNama.setError("Cannot Empty");
            txtJabatan.setError("Cannot Empty");
        } else {
            if(btnSimpan.getText().equals("Edit")) {
                editKaryawan.setNama(txtNama.getText().toString());
                editKaryawan.setJabatan(txtJabatan.getText().toString());
                dbAdapter.updateKaryawan(editKaryawan);
                btnSimpan.setText("Simpan");
            } else {
                karyawan.setNama(txtNama.getText().toString());
                karyawan.setJabatan(txtJabatan.getText().toString());
                dbAdapter.save(karyawan);
            }
            txtNama.setText("");
            txtJabatan.setText("");
        }
        listKaryawan.setAdapter(new ListKaryawanAdapter(dbAdapter
                .getAllKaryawan()));
    }

    public class ListKaryawanAdapter extends BaseAdapter {
        private List<Karyawan> listKaryawan;

        public ListKaryawanAdapter (List<Karyawan> listKaryawan) {
            this.listKaryawan = listKaryawan;
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return this.listKaryawan.size();
        }

        @Override
        public Karyawan getItem(int position) {
            // TODO Auto-generated method stub
            return this.listKaryawan.get(position);
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(int position, View convertView,
                            ViewGroup parent) {
            // TODO Auto-generated method stub
            if(convertView == null) {
                convertView = LayoutInflater
                        .from(getApplicationContext())
                        .inflate(R.layout.list_layout, parent, false);
            }
            final Karyawan karyawan = getItem(position);
            if(karyawan != null) {
                TextView labelNama = (TextView) convertView
                        .findViewById(R.id.labelNama);
                labelNama.setText(karyawan.getNama());
                TextView labelJabatan = (TextView) convertView
                        .findViewById(R.id.labelKelas);
                labelJabatan.setText(karyawan.getJabatan());
            }
            return convertView;
        }
    }
}
