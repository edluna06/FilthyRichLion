package com.futiletech.filthyrichlion;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareButton;
import com.facebook.share.widget.ShareDialog;


public class scoreActivity extends AppCompatActivity {

    private ShareDialog shareDialog;
    // share button
    private ShareButton shareButton;
    //image
    private Bitmap image;
    //counter
    private int counter = 0;

    int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        ShareButton fbShareButton = (ShareButton) findViewById(R.id.share_btn);
        shareDialog = new ShareDialog(this);

        /*
        Drawable vectorDrawable = ResourcesCompat.getDrawable(this.getResources(), R.drawable.lion_logo_, null);
        Bitmap image = ((BitmapDrawable) vectorDrawable).getBitmap();

        SharePhoto photo = new SharePhoto.Builder()
                .setBitmap(image)
        .build();
        SharePhotoContent content = new SharePhotoContent.Builder()
                .addPhoto(photo)
        .build();*/
        /* to share a link*/
        ShareLinkContent content = new ShareLinkContent.Builder()
                .setContentUrl(Uri.parse("https://www.dropbox.com/s/xzkf0fz28tgewz8/psu.jpg?dl=0"))
                .build();


        //look at how to screenshot in bookmarks quora

        fbShareButton.setShareContent(content);




        Intent intentExtras = getIntent();
        Bundle extrasBundle = intentExtras.getExtras();
        if (!extrasBundle.isEmpty()) {

     score = extrasBundle.getInt("b_score");


            TextView tv_score = findViewById(R.id.tv_score);
            if (score == 0)
                tv_score.setText("Your score is: 0");
            else
                tv_score.setText("Your score is: " + score + ".000");


        }
    }

    public void exit(View view) {

        Intent intent = new Intent(scoreActivity.this, welcomeActivity.class);
        startActivity(intent);
        finish();
    }

    public void replay(View view) {
        Intent intent = new Intent(scoreActivity.this, categoriesActivity.class);
        startActivity(intent);
        finish();

    }




}
