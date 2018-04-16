package com.futiletech.filthyrichlion;

import android.app.DialogFragment;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.facebook.share.model.ShareLinkContent;
import com.squareup.picasso.Picasso;

import java.net.MalformedURLException;
import java.net.URL;


public class welcomeActivity extends AppCompatActivity implements LionDialog.LionDialogListener,View.OnClickListener{




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        findViewById(R.id.button_info).setOnClickListener(this);




        //Intent intent = getIntent();

        Intent intentExtras=getIntent();
        Bundle extrasBundle= intentExtras.getExtras();


        if (!extrasBundle.isEmpty()) {
            //String user_name = intent.getStringExtra(MainActivity.EXTRA_USERNAME);
            String user_name = extrasBundle.getString("b_user_name");

            TextView description = findViewById(R.id.tv_user_name);
            description.setText(user_name);



            //String image_url = intent.getStringExtra(MainActivity.EXTRA_IMAGE_URL);
            String image_url = extrasBundle.getString("b_url_image");
            ImageView picture = findViewById(R.id.iv_user);
            try {
                URL profile_picture = new URL(image_url);
                Picasso.with(this).load(profile_picture.toString()).into(picture);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            //wiring
            ImageButton play_button = findViewById(R.id.button_play);

            play_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent_play = new Intent(welcomeActivity.this, categoriesActivity.class);
                    startActivity(intent_play);
                }
            });


        }

    }

    public void onClick(View v){
        Intent intent=new Intent(welcomeActivity.this,howToPlayActivity.class);
        startActivity(intent);

    }

     //FOR THE ALERT DIALOG

    //the button has on click property
    public void confirmExit(View view){
        DialogFragment dialogFragment = new LionDialog();
        dialogFragment.show(getFragmentManager(), "exitDialog");


    }

    //CALLBACK
    @Override
    public void OnYesClick() {

        Toast.makeText(this,"Bye",Toast.LENGTH_SHORT).show();
        LoginManager.getInstance().logOut();
        Intent intent3=new Intent(welcomeActivity.this,MainActivity.class);
        startActivity(intent3);
        finish();

    }


    public void goToManageDatabase(View view){
        Intent intent=new Intent(welcomeActivity.this,manageDbActivity.class);
        startActivity(intent);
    }




    }

