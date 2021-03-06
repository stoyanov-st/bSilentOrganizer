package bg.uni_ruse.stoyanov.bsilentorganizer.event;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Locale;

import bg.uni_ruse.stoyanov.bsilentorganizer.R;

/**
 * Created by stoyanovst on 9/4/17.
 */

public class NewEventDialogFragment extends DialogFragment
                                    implements DialogInterface.OnClickListener{

    private String TAG = NewEventDialogFragment.class.getCanonicalName();
    private AlertDialog dialog;

    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(getContext(), R.style.DialogStyle));
        final LayoutInflater inflater = getActivity().getLayoutInflater();

        builder.setView(inflater.inflate(R.layout.layout_new_event, null))
                .setTitle(R.string.new_event)
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

        switch (getTargetRequestCode()) {
            case 1:
                builder.setPositiveButton(R.string.add_event, this);
                break;
            case 2:
                builder.setPositiveButton(R.string.edit_event, this);
                break;
        }

        dialog = builder.show();

        TimePicker timePicker = dialog.findViewById(R.id.eventTimePicker);
        timePicker.setIs24HourView(true);

        if (getTargetRequestCode() == 2) {
            Bundle bundle = getArguments();
            EditText dialogEventTitle = dialog.findViewById(R.id.dialog_event_title);
            dialogEventTitle.setText(bundle.getString("eventTitle"));
            CheckBox importantCheckBox = dialog.findViewById(R.id.importantCheckBox);
            importantCheckBox.setChecked(bundle.getBoolean("eventImportant"));
        }

        Log.d(TAG, "NewEventDialogInflated");
        return dialog;
    }

    @Deprecated
    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        EditText dialogEventTitle = dialog.findViewById(R.id.dialog_event_title);
        CheckBox importantCheckBox = dialog.findViewById(R.id.importantCheckBox);
        TimePicker eventTimePicker = dialog.findViewById(R.id.eventTimePicker);

        Intent intent = new Intent();
        String eventTitle = dialogEventTitle.getText().toString();
        boolean isImportant = importantCheckBox.isChecked();

        String timestamp = eventTimePicker.getCurrentHour() + ":" + eventTimePicker.getCurrentMinute();

        intent.putExtra("eventTitle", eventTitle);
        intent.putExtra("eventImportant", isImportant);
        intent.putExtra("eventTimestamp", timestamp);

        getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);

        Log.d(TAG, "NewEventDialogClosed");
    }
}
