package com.exsoft.nandaamelyar.absensikaryawan;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.exsoft.nandaamelyar.absensikaryawan.domain.Karyawan;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kresek on 09/11/15.
 */
public class DBAdapter extends SQLiteOpenHelper {
    private static final String DB_NAME = "Karyawan";
    private static final String TABLE_NAME = "m_karyawan";
    private static final String COL_ID = "id";
    private static final String COL_NAME = "nama";
    private static final String COL_JABATAN= "jabatan";
    private static final String DROP_TABLE = "DROP TABLE IF EXISTS "
            + TABLE_NAME + ";";
    private SQLiteDatabase sqliteDatabase = null;

    public DBAdapter(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createTable(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,
                          int newVersion) {
        db.execSQL(DROP_TABLE);
    }

    public void openDB() {
        if (sqliteDatabase == null) {
            sqliteDatabase = getWritableDatabase();
        }
    }

    public void closeDB() {
        if (sqliteDatabase != null) {
            if (sqliteDatabase.isOpen()) {
                sqliteDatabase.close();
            }
        }
    }

    public void createTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + "(" + COL_ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
                + COL_NAME + " TEXT," + COL_JABATAN+ " TEXT);");
    }

    public void updateKaryawan(Karyawan karyawan) {
        sqliteDatabase = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_NAME, karyawan.getNama());
        cv.put(COL_JABATAN, karyawan.getJabatan());
        String whereClause = COL_ID + "==?";
        String whereArgs[] = new String[] { karyawan.getId() };
        sqliteDatabase.update(TABLE_NAME, cv, whereClause, whereArgs);
        sqliteDatabase.close();
    }

    public void save(Karyawan karyawan) {
        sqliteDatabase = getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_NAME, karyawan.getNama());
        contentValues.put(COL_JABATAN,karyawan.getJabatan());

        sqliteDatabase.insertWithOnConflict(TABLE_NAME, null,
                contentValues, SQLiteDatabase.CONFLICT_IGNORE);

        sqliteDatabase.close();
    }

    public void delete(Karyawan karyawan) {
        sqliteDatabase = getWritableDatabase();
        String whereClause = COL_ID + "==?";
        String[] whereArgs = new String[] { String.valueOf(karyawan.getId()) };
        sqliteDatabase.delete(TABLE_NAME, whereClause, whereArgs);
        sqliteDatabase.close();
    }

    public void deleteAll() {
        sqliteDatabase = getWritableDatabase();
        sqliteDatabase.delete(TABLE_NAME, null, null);
        sqliteDatabase.close();
    }

    public List<Karyawan> getAllKaryawan() {
        sqliteDatabase = getWritableDatabase();

        Cursor cursor = this.sqliteDatabase.query(TABLE_NAME, new String[] {
                COL_ID, COL_NAME, COL_JABATAN }, null, null, null, null, null);
        List<Karyawan> karyawans = new ArrayList<Karyawan>();

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                Karyawan karyawan = new Karyawan();
                karyawan.setId(cursor.getString(cursor.getColumnIndex(COL_ID)));
                karyawan.setNama(cursor.getString(cursor
                        .getColumnIndex(COL_NAME)));
                karyawan.setJabatan(cursor.getString(cursor
                        .getColumnIndex(COL_JABATAN)));
                karyawans.add(karyawan);
            }
            sqliteDatabase.close();
            return karyawans;
        } else {
            sqliteDatabase.close();
            return new ArrayList<Karyawan>();
        }
    }
}
