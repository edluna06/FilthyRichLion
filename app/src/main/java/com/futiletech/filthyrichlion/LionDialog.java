package com.futiletech.filthyrichlion;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 * Created by Edu on 2/22/18.
 */

public class LionDialog extends DialogFragment{

    //interface defined by us
    public interface LionDialogListener{
        //methods
         void OnYesClick();
    }
    //setter method
    public void setListener(LionDialogListener listener ){

        this.listener=listener;
    }
    //reference to a listener callback provide to an Activity
    LionDialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Build the dialog and set up the button click handlers
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("Exit the game?")
                .setMessage("You'll be logout!!!")

                .setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //the reference and the method of the interface
                        listener.OnYesClick();
                    } })


                .setNegativeButton("Cancel", new
                        DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //we do nothing here, because it's the cancel button
                            }
                        });
        return builder.create();
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        //
        try {
            listener = (LionDialogListener) activity;
        }catch (ClassCastException e){
            throw new ClassCastException(activity.toString()+"Must implement Lion" +
                    "Dialog Listener interface");
        }

    }//end onAttach

}
