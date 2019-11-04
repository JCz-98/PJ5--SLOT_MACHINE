package com.example.pj5;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.SeekBar; // for changing the tip percentage
import android.widget.SeekBar.OnSeekBarChangeListener; // SeekBar listener
import android.widget.TextView;

import androidx.appcompat.app.AppCompatDialogFragment;

import java.text.NumberFormat;

public class addCreditDialog extends AppCompatDialogFragment
{
    private static final NumberFormat intFormat = NumberFormat.getIntegerInstance();

    private TextView new_credit;
    private SeekBar credAdd;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.add_credit, null);

        builder.setView(view)
                .setTitle("Añadir Creditos")
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {

                    }
                })
                .setPositiveButton("ADD", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        int credits = Integer.parseInt(new_credit.getText().toString());
                        MainActivity.INIT_SCORE += credits;
                        MainActivity.getScoreView().setText(String.valueOf(MainActivity.INIT_SCORE));

                        if(credits > 0)
                        {
                            MainActivity.getPlayButton().setEnabled(true);
                        }


                    }
                });

        credAdd = view.findViewById(R.id.percentSeekBar);
        credAdd.setOnSeekBarChangeListener(seekBarListener);

        new_credit = view.findViewById(R.id.valC);

        return builder.create();
    }

    // listener object for the SeekBar's progress changed events
    private final OnSeekBarChangeListener seekBarListener =
            new OnSeekBarChangeListener()
            {

                // update percent, then call calculate
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
                {
                    new_credit.setText(intFormat.format(progress));
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) { }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) { }
            };
}
