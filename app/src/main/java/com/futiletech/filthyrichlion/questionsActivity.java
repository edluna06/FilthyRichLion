package com.futiletech.filthyrichlion;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class questionsActivity extends AppCompatActivity {

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
    int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);


        Intent intentExtras=getIntent();
        Bundle extrasBundle= intentExtras.getExtras();
        if (!extrasBundle.isEmpty()){
             courseName=extrasBundle.getString("b_questionCourse");
             question=extrasBundle.getString("b_question");
             optionA=extrasBundle.getString("b_optionA");
             optionB=extrasBundle.getString("b_optionB");
             optionC=extrasBundle.getString("b_optionC");
             optionD=extrasBundle.getString("b_optionD");
             correctAnswer=extrasBundle.getString("b_correctAnswer");
             questionNumber=extrasBundle.getInt("b_questionNumber");

            lionWasClicked=extrasBundle.getBoolean("b_lionWasClicked");
            fiftyWasClicked=extrasBundle.getBoolean("b_fiftyWasClicked");

            score=extrasBundle.getInt("b_score");

            TextView tv_question=findViewById(R.id.tv_question_description);
            tv_question.setText(question);

            Button btn_optionA=findViewById(R.id.btn_optionA);
            btn_optionA.setText("A. "+ optionA);

            Button btn_optionB=findViewById(R.id.btn_optionB);
            btn_optionB.setText("B. "+ optionB);

            Button btn_optionC=findViewById(R.id.btn_optionC);
            btn_optionC.setText("C. "+ optionC);

            Button btn_optionD=findViewById(R.id.btn_optionD);
            btn_optionD.setText("D. "+ optionD);

            questionNumber=questionNumber+1;
            //Toast.makeText(this, questionNumber, Toast.LENGTH_SHORT).show();

            TextView tv_questionNumber=findViewById(R.id.textView9);
            tv_questionNumber.setText("Question "+questionNumber);


            if(lionWasClicked){
                ImageView lion=findViewById(R.id.iv_lion);
                lion.setVisibility(View.INVISIBLE);

            }

            if(fiftyWasClicked){
                ImageView fifty=findViewById(R.id.iv_fifty);
                fifty.setVisibility(View.INVISIBLE);
            }

            if(fiftyWasClicked && lionWasClicked){
                TextView warning=findViewById(R.id.tv_warning);
                warning.setVisibility(View.VISIBLE);

                ImageView iv_warning=findViewById(R.id.imageView);
                iv_warning.setVisibility(View.VISIBLE);


            }

        }


    }


    public void answer(View view){

        String ans=view.getTag().toString();
        if(ans.equalsIgnoreCase(correctAnswer))
            score=score+100;



       Toast.makeText(this, "Ans is "+ans+" corAns is "+correctAnswer+" score "+score, Toast.LENGTH_SHORT).show();
     if (theDB == null) {
            Toast.makeText(this, "Try again in a few seconds.", Toast.LENGTH_SHORT).show();
        } else {
            String[] columns = {"question_id", "question_course", "question_q", "question_optionA", "question_optionB", "question_optionC", "question_optionD", "question_correctAnswer"};
            String selection = "question_course = ?";
            String[] selArgs = new String[]
                    {courseName};

            Cursor c = theDB.query("questions", columns, selection, selArgs, null, null, "question_id", questionNumber+",1");
            if (c.moveToFirst()) {
                currentRow = c.getLong(c.getColumnIndexOrThrow("question_id"));


                question=c.getString(c.getColumnIndexOrThrow("question_q"));
                optionA=c.getString(c.getColumnIndexOrThrow("question_optionA"));
                optionB=c.getString(c.getColumnIndexOrThrow("question_optionB"));
                optionC=c.getString(c.getColumnIndexOrThrow("question_optionC"));
                optionD=c.getString(c.getColumnIndexOrThrow("question_optionD"));
                correctAnswer=c.getString(c.getColumnIndexOrThrow("question_correctAnswer"));



            }

            c.close();
        }





        Intent intent = new Intent(questionsActivity.this, questionsActivity.class);
        Intent intent2 = new Intent(questionsActivity.this, scoreActivity.class);

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
        intent2.putExtras(bundle);
        //Limit on the number of questions asked
        if(questionNumber<=2)
            startActivity(intent);
        else
            startActivity(intent2);
        finish();

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

    public void lionAnswer(View view) {

        Toast.makeText(this, "The answer is "+correctAnswer, Toast.LENGTH_SHORT).show();
        lionWasClicked=true;
    }

    public void fiftyAnswer(View view) {

        Toast.makeText(this, "Just deleted 2 wrong options", Toast.LENGTH_SHORT).show();
        fiftyWasClicked=true;
        Button optionA=findViewById(R.id.btn_optionA);
        Button optionB=findViewById(R.id.btn_optionB);
        Button optionC=findViewById(R.id.btn_optionC);
        Button optionD=findViewById(R.id.btn_optionD);

        if(correctAnswer=="A"){
            optionB.setVisibility(View.INVISIBLE);
            optionC.setVisibility(View.INVISIBLE);
        }else if(correctAnswer=="B"){
            optionA.setVisibility(View.INVISIBLE);
            optionC.setVisibility(View.INVISIBLE);
        }else if(correctAnswer=="C"){
            optionB.setVisibility(View.INVISIBLE);
            optionD.setVisibility(View.INVISIBLE);
        }else{
            optionB.setVisibility(View.INVISIBLE);
            optionC.setVisibility(View.INVISIBLE);
        }



    }



}
