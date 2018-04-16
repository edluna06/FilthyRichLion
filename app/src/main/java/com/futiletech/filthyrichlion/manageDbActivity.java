package com.futiletech.filthyrichlion;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class manageDbActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_db);


    }


    public void gotoCourseQuestionsDatabase(View view) {
       Intent intent=new Intent(manageDbActivity.this,questionsDbActivity.class);
       startActivity(intent);
    }

    public void gotoCoursesDatabase(View view) {
        Intent intent=new Intent(manageDbActivity.this,coursesDbActivity.class);
        startActivity(intent);
    }
}
