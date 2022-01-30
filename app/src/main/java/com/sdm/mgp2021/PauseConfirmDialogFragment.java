package com.sdm.mgp2021;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import android.app.DialogFragment;

public class PauseConfirmDialogFragment extends DialogFragment
{
    public static boolean isShown = false;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        isShown = true;
        // Use the Builder Class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Confirm Pause/Unpause?")
            .setPositiveButton("Yes", new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    GameSystem.Instance.SetIsPaused(!GameSystem.Instance.GetIsPaused());
                    isShown = false;
                }
            })
            .setNegativeButton("No", new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    // User cancels the pause
                    isShown = false;
                }
            });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}
