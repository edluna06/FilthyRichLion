package com.futiletech.filthyrichlion;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class categoriesActivity extends AppCompatActivity {

    SQLiteDatabase theDB;
    long currentRow;
    String question;
    String optionA;
    String optionB;
    String optionC;
    String optionD;
    String correctAnswer;
    int questionNumber;
    String courseName;

    Boolean lionWasClicked=false;
    Boolean fiftyWasClicked=false;

    int score=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);


    }

    public void play(View view) {


        if (theDB == null) {
            Toast.makeText(this, "Try again in a few seconds.", Toast.LENGTH_SHORT).show();
        } else {
            String[] columns = {"question_id", "question_course", "question_q", "question_optionA", "question_optionB", "question_optionC", "question_optionD", "question_correctAnswer"};
            String selection = "question_course = ?";
            String[] selArgs = new String[]
                    {view.getTag().toString()};

            Cursor c = theDB.query("questions", columns, selection, selArgs, null, null, "question_id", questionNumber+",1");
            if (c.moveToFirst()) {
                currentRow = c.getLong(c.getColumnIndexOrThrow("question_id"));

                courseName=c.getString(c.getColumnIndexOrThrow("question_course"));
                question=c.getString(c.getColumnIndexOrThrow("question_q"));
                optionA=c.getString(c.getColumnIndexOrThrow("question_optionA"));
                optionB=c.getString(c.getColumnIndexOrThrow("question_optionB"));
                optionC=c.getString(c.getColumnIndexOrThrow("question_optionC"));
                optionD=c.getString(c.getColumnIndexOrThrow("question_optionD"));
                correctAnswer=c.getString(c.getColumnIndexOrThrow("question_correctAnswer"));
                questionNumber=0;



            }

            c.close();
        }
        Intent intent = new Intent(categoriesActivity.this, questionsActivity.class);
        Bundle bundle=new Bundle();
        bundle.putString("b_questionCourse",courseName);
        bundle.putString("b_question",question);
        bundle.putString("b_optionA",optionA);
        bundle.putString("b_optionB",optionB);
        bundle.putString("b_optionC",optionC);
        bundle.putString("b_optionD",optionD);
        bundle.putString("b_correctAnswer",correctAnswer);
        bundle.putInt("b_questionNumber",questionNumber);
        bundle.putBoolean("b_lionWasClicked",lionWasClicked);
        bundle.putBoolean("b_fiftyWasClicked",fiftyWasClicked);
        bundle.putInt("b_score",score);
        intent.putExtras(bundle);
        startActivity(intent);


    }

    @Override
    public void onPause() {
        super.onPause();
        theDB.close();
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
}
