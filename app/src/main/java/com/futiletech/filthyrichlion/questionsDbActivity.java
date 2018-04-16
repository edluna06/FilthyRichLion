package com.futiletech.filthyrichlion;



import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class questionsDbActivity extends AppCompatActivity {
    SQLiteDatabase theDB;
    long currentRow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions_db);


        final Spinner spinner = (Spinner) findViewById(R.id.sp_courses);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.CoursesChoices, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
                String text = spinner.getSelectedItem().toString();
                TextView tv_name_course=findViewById(R.id.textView8);

                if(text.equals("COMP511"))
                    tv_name_course.setText("Algorithms");
                else if(text.equals("CMPSC475"))
                    tv_name_course.setText("App Programming");

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

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
            values.put("question_course",
                    ((Spinner) findViewById(R.id.sp_courses)).getSelectedItem().toString());
            values.put("question_q",
                    ((EditText) findViewById(R.id.txt_ques)).getText().toString());
            values.put("question_optionA",
                    ((EditText) findViewById(R.id.txt_optA)).getText().toString());
            values.put("question_optionB",
                    ((EditText) findViewById(R.id.txt_optB)).getText().toString());
            values.put("question_optionC",
                    ((EditText) findViewById(R.id.txt_optC)).getText().toString());
            values.put("question_optionD",
                    ((EditText) findViewById(R.id.txt_optD)).getText().toString());
            values.put("question_correctAnswer",
                    ((EditText) findViewById(R.id.crct_ans)).getText().toString());


            long newRowId = theDB.insert("questions", null, values);

            Toast.makeText(this, "Record Added", Toast.LENGTH_SHORT).show();



        }
    }

    public void btnSearchClick(View view) {

        if (theDB == null) {
            Toast.makeText(this, "Try again in a few seconds.", Toast.LENGTH_SHORT).show();
        }
        else {
            String[] columns = {"question_id", "question_course","question_q", "question_optionA","question_optionB","question_optionC","question_optionD","question_correctAnswer"};
            String selection = "question_id = ?";
            String[] selArgs = new String[]
                    {((TextView) findViewById(R.id.txt_id)).getText().toString()};

            Cursor c = theDB.query("questions", columns, selection, selArgs, null, null, null);
            if (c.moveToFirst()) {
                currentRow = c.getLong(c.getColumnIndexOrThrow("question_id"));

                ((EditText) findViewById(R.id.txtEditCourse)).setText(
                        c.getString(c.getColumnIndexOrThrow("question_course")));
                ((EditText) findViewById(R.id.txtEditQues)).setText(
                        c.getString(c.getColumnIndexOrThrow("question_q")));
                ((EditText) findViewById(R.id.txtEditOptA)).setText(
                        c.getString(c.getColumnIndexOrThrow("question_optionA")));
                ((EditText) findViewById(R.id.txtEditOptB)).setText(
                        c.getString(c.getColumnIndexOrThrow("question_optionB")));
                ((EditText) findViewById(R.id.txtEditOptC)).setText(
                        c.getString(c.getColumnIndexOrThrow("question_optionC")));
                ((EditText) findViewById(R.id.txtEditOptD)).setText(
                        c.getString(c.getColumnIndexOrThrow("question_optionD")));
                ((EditText) findViewById(R.id.txtEditCrcAns)).setText(
                        c.getString(c.getColumnIndexOrThrow("question_correctAnswer")));

                changeEditFieldVisibility(View.VISIBLE);
            }
            else {
                //changeEditFieldVisibility(View.GONE);
            }
            c.close();
        }
    }



    public void changeEditFieldVisibility(int visibility) {
        findViewById(R.id.txtEditCourse).setVisibility(visibility);
        findViewById(R.id.txtEditQues).setVisibility(visibility);
        findViewById(R.id.txtEditOptA).setVisibility(visibility);
        findViewById(R.id.txtEditOptB).setVisibility(visibility);
        findViewById(R.id.txtEditOptC).setVisibility(visibility);
        findViewById(R.id.txtEditOptD).setVisibility(visibility);
        findViewById(R.id.txtEditCrcAns).setVisibility(visibility);
        findViewById(R.id.btnDelete).setVisibility(visibility);
        findViewById(R.id.btnUpdate).setVisibility(visibility);
  }

    public void btnUpdateClick(View view) {
        if (theDB == null) {
            Toast.makeText(this, "Try again in a few seconds.", Toast.LENGTH_SHORT).show();
        }
        else {
            ContentValues values = new ContentValues();
            values.put("question_course",
                    ((EditText) findViewById(R.id.txtEditCourse)).getText().toString());
            values.put("question_q",
                    ((EditText) findViewById(R.id.txtEditQues)).getText().toString());
            values.put("question_optionA",
                    ((EditText) findViewById(R.id.txtEditOptA)).getText().toString());
            values.put("question_optionB",
                    ((EditText) findViewById(R.id.txtEditOptB)).getText().toString());
            values.put("question_optionC",
                    ((EditText) findViewById(R.id.txtEditOptC)).getText().toString());
            values.put("question_optionD",
                    ((EditText) findViewById(R.id.txtEditOptD)).getText().toString());
            values.put("question_correctAnswer",
                    ((EditText) findViewById(R.id.txtEditCrcAns)).getText().toString());

            String selection = "question_id = " + currentRow;

            theDB.update("questions",values,selection,null);
            Toast.makeText(this, "Record Updated", Toast.LENGTH_SHORT).show();
        }
    }

    public void btnDeleteClick(View view) {
        if (theDB == null) {
            Toast.makeText(this, "Try again in a few seconds.", Toast.LENGTH_SHORT).show();
        }
        else {
            String selection = "question_id = " + currentRow;

            theDB.delete("questions",selection,null);
            Toast.makeText(this, "Record Deleted", Toast.LENGTH_SHORT).show();
        }
    }


    public void btnRefreshClick(View view) {
        StringBuffer sb = new StringBuffer();
        String[] columns = {"question_id", "question_course","question_q", "question_optionA","question_optionB","question_optionC","question_optionD","question_correctAnswer"};

        Cursor c = theDB.query("questions", columns, null, null, null, null, "question_id");

        while (c.moveToNext()) {
            sb.append("id: " + c.getLong(c.getColumnIndexOrThrow("question_id")) + "\n");
            sb.append(c.getString(c.getColumnIndexOrThrow("question_course")));
            sb.append("\n");
            sb.append("Question:"+c.getString(c.getColumnIndexOrThrow("question_q")));
            sb.append("\n");
            sb.append("option A:"+c.getString(c.getColumnIndexOrThrow("question_optionA")));
            sb.append("\n");
            sb.append("option B:"+c.getString(c.getColumnIndexOrThrow("question_optionB")));
            sb.append("\n");
            sb.append("option C:"+c.getString(c.getColumnIndexOrThrow("question_optionC")));
            sb.append("\n");
            sb.append("option D:"+c.getString(c.getColumnIndexOrThrow("question_optionD")));
            sb.append("\n");
            sb.append("Correct Answer:"+c.getString(c.getColumnIndexOrThrow("question_correctAnswer")));

            sb.append("\n---------------------------------------------------------------\n");
        }
        ((TextView) findViewById(R.id.txt_results)).setText(sb);
        c.close();
    }





    @Override
    public void onPause() {
        super.onPause();
        theDB.close();
    }





}
