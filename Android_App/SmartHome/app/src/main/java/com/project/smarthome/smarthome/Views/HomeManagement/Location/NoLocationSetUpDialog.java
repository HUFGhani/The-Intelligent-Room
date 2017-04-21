package com.project.smarthome.smarthome.Views.HomeManagement.Location;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.project.smarthome.smarthome.Model.Location.OpenLocationChooserChoiceListener;
import com.project.smarthome.smarthome.R;

public class NoLocationSetUpDialog extends DialogFragment {

    private OpenLocationChooserChoiceListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.no_location_set_up)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        listener.onUserSelectedOpenLocationChooser();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dismiss();
                    }
                });

        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OpenLocationChooserChoiceListener) {
            listener = (OpenLocationChooserChoiceListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OpenLocationChooserChoiceListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }
}
