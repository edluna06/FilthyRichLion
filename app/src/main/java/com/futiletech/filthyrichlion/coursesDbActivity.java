package com.futiletech.filthyrichlion;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class coursesDbActivity extends AppCompatActivity {
    SQLiteDatabase theDB;
    long currentRow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses_db);





    }

    @Override
    protected void onResume() {
        super.onResume();
        // Get a writable database
        LionDB.getInstance(this).getWritableDatabase(new LionDB.OnDBReadyListener() {
            @Override
            public void onDBReady(SQLiteDatabase db) {
                theDB = db;
            }
        });

    }



    public void btnAddClick(View view) {
        if (theDB == null) {
            Toast.makeText(this, "Try again in a few seconds.", Toast.LENGTH_SHORT).show();
        }
        else {
            ContentValues values = new ContentValues();
            values.put("course_code",
                    ((TextView) findViewById(R.id.txtNewSetup)).getText().toString());
            values.put("course_name",
                    ((TextView) findViewById(R.id.txtNewPunchline)).getText().toString());

            long newRowId = theDB.insert("courses", null, values);

            Toast.makeText(this, "Record Added", Toast.LENGTH_SHORT).show();

        }
    }

    public void btnSearchClick(View view) {
        if (theDB == null) {
            Toast.makeText(this, "Try again in a few seconds.", Toast.LENGTH_SHORT).show();
        }
        else {
            String[] columns = {"course_id", "course_code", "course_name"};
            String selection = "course_id = ?";
            String[] selArgs = new String[]
                    {((TextView) findViewById(R.id.txtSearchID)).getText().toString()};

            Cursor c = theDB.query("courses", columns, selection, selArgs, null, null, null);
            if (c.moveToFirst()) {
                currentRow = c.getLong(c.getColumnIndexOrThrow("course_id"));
                ((TextView) findViewById(R.id.txtEditSetup)).setText(
                        c.getString(c.getColumnIndexOrThrow("course_code")));
                ((TextView) findViewById(R.id.txtEditPunchline)).setText(
                        c.getString(c.getColumnIndexOrThrow("course_name")));

                changeEditFieldVisibility(View.VISIBLE);
            }
            else {
                changeEditFieldVisibility(View.GONE);
            }
            c.close();
        }
    }



    private void changeEditFieldVisibility(int visibility) {
        findViewById(R.id.txtEditSetup).setVisibility(visibility);
        findViewById(R.id.txtEditPunchline).setVisibility(visibility);
        findViewById(R.id.btnUpdate).setVisibility(visibility);
        findViewById(R.id.btnDelete).setVisibility(visibility);
    }

    public void btnUpdateClick(View view) {
        if (theDB == null) {
            Toast.makeText(this, "Try again in a few seconds.", Toast.LENGTH_SHORT).show();
        }
        else {
            ContentValues values = new ContentValues();
            values.put("course_code",
                    ((TextView) findViewById(R.id.txtEditSetup)).getText().toString());
            values.put("course_name",
                    ((TextView) findViewById(R.id.txtEditPunchline)).getText().toString());

            String selection = "course_id = " + currentRow;

            theDB.update("courses",values,selection,null);
        }
    }

    public void btnDeleteClick(View view) {
        if (theDB == null) {
            Toast.makeText(this, "Try again in a few seconds.", Toast.LENGTH_SHORT).show();
        }
        else {
            String selection = "course_id = " + currentRow;

            theDB.delete("courses",selection,null);
        }
    }


    public void btnRefreshClick(View view) {
        StringBuffer sb = new StringBuffer();
        String[] columns = {"course_id", "course_code", "course_name"};

        Cursor c = theDB.query("courses", columns, null, null, null, null, "course_id");

        while (c.moveToNext()) {
            sb.append("id: " + c.getLong(c.getColumnIndexOrThrow("course_id")) + "\n");
            sb.append(c.getString(c.getColumnIndexOrThrow("course_code")));
            sb.append("\n");
            sb.append(c.getString(c.getColumnIndexOrThrow("course_name")));
            sb.append("\n---------------------------------------------------------------\n");
        }
        ((TextView) findViewById(R.id.lblResults)).setText(sb);
        c.close();
    }

    @Override
    public void onPause() {
        super.onPause();
        theDB.close();
    }
}
