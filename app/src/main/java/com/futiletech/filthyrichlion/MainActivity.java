package com.futiletech.filthyrichlion;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;


public class MainActivity extends AppCompatActivity {

    //public static final String EXTRA_IMAGE_URL = "com.futiletech.filthyrichlion.EXTRA_IMAGE_URL";
    //public static final String EXTRA_USERNAME = "com.futiletech.filthyrichlion.EXTRA_USERNAME";
//
    CallbackManager callbackManager;
    ProgressDialog mDialog;

    String url_image;
    String user_name;

    private Session session;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode,resultCode,data);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //TextView question=findViewById(R.id.tv_ask);
        //ImageButton button_continue=findViewById(R.id.button_continue);

        callbackManager=CallbackManager.Factory.create();

        LoginButton loginButton=findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList("public_profile","email","user_birthday","user_friends"));

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                mDialog= new ProgressDialog(MainActivity.this);
                mDialog.setMessage("Retrieving data...");
                mDialog.show();

                Toast.makeText(MainActivity.this,"Loading...",Toast.LENGTH_SHORT).show();

                 GraphRequest request=GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        mDialog.dismiss();
                        Log.d("response",response.toString());
                        getData(object);
                        Intent intent = new Intent(MainActivity.this, welcomeActivity.class);

                        Bundle bundle=new Bundle();
                        bundle.putString("b_user_name",user_name);
                        bundle.putString("b_url_image",url_image);
                        intent.putExtras(bundle);
                        //intent.putExtra(EXTRA_USERNAME, user_name);
                        //intent.putExtra(EXTRA_IMAGE_URL, url_image);


                        startActivity(intent);
                        finish();
                    }
                });

                //Request Graph API
                Bundle parameters= new Bundle();
                parameters.putString("fields","id,first_name,last_name,email,birthday,friends");
                request.setParameters(parameters);
                request.executeAsync();
        }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }

        });

        //if already login
        /*if(AccessToken.getCurrentAccessToken()!=null){

            //Just set User id
            //txtEmail.setText(AccessToken.getCurrentAccessToken().getUserId());
           Intent intent = new Intent(MainActivity.this, welcomeActivity.class);
            //user_name=AccessToken.getCurrentAccessToken().getUserId();

            intent.putExtra(EXTRA_USERNAME, EXTRA_USERNAME);
            intent.putExtra(EXTRA_IMAGE_URL, EXTRA_IMAGE_URL);

            startActivity(intent);
            finish();

        }*/

        Profile profile = Profile.getCurrentProfile();
        if (profile != null) {
            //Log.v(TAG, "Logged, user name=" + profile.getFirstName() + " " + profile.getLastName());
            Intent intent = new Intent(MainActivity.this, welcomeActivity.class);
            //user_name=AccessToken.getCurrentAccessToken().getUserId();
            user_name=profile.getFirstName()+" "+profile.getLastName();
            url_image="https://graph.facebook.com/"+profile.getId()+"/picture?width=70&height=70";
            //intent.putExtra(EXTRA_USERNAME, user_name);
            //intent.putExtra(EXTRA_IMAGE_URL, url_image);
            Bundle bundle=new Bundle();
            bundle.putString("b_user_name",user_name);
            bundle.putString("b_url_image",url_image);
            intent.putExtras(bundle);
            startActivity(intent);
            finish();

        }

        //just for printing the hash key to put it on the facebook login sdk
        //printKeyHash();
    }

    private void getData(JSONObject object) {
        try{
           URL profile_picture=new URL("https://graph.facebook.com/"+object.getString("id")+"/picture?width=70&height=70");
            //Picasso.with(this).load(profile_picture.toString()).into(imgAvatar);

            url_image="https://graph.facebook.com/"+object.getString("id")+"/picture?width=70&height=70";
            user_name=object.getString("first_name")+" "+object.getString("last_name");

    } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

  private void printKeyHash(){
        try{
            PackageInfo info= getPackageManager().getPackageInfo("com.futiletech.filthyrichlion", PackageManager.GET_SIGNATURES);
            for(Signature signature:info.signatures){

                MessageDigest md=MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash", Base64.encodeToString(md.digest(),Base64.DEFAULT));

            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }


    }



}
