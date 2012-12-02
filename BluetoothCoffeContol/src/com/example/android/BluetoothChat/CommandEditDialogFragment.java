package com.example.android.BluetoothChat;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView.FindListener;
import android.widget.Button;
import android.widget.EditText;


public class CommandEditDialogFragment extends DialogFragment {
	private Button restoreButton,
			clearButton;
	private EditText commandText;
	
	private Button commandButton;
	
	public CommandEditDialogFragment(View button) {
		super();
		this.commandButton = (Button) button;
	}
	
	public CommandEditDialogFragment() {
		super();
		
	}
	
	
    /* The activity that creates an instance of this dialog fragment must
     * implement this interface in order to receive event callbacks.
     * Each method passes the DialogFragment in case the host needs to query it. */
    public interface CommandEditDialogListener {
        public void onDialogPositiveClick(DialogFragment dialog);
        public void onDialogNegativeClick(DialogFragment dialog);
		public CharSequence getCommandByButtonId(int id);
    }
    
    // Use this instance of the interface to deliver action events
    CommandEditDialogListener mListener;
    
    // Override the Fragment.onAttach() method to instantiate the CommandEditDialogListener
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the CommandEditDialogListener so we can send events to the host
            mListener = (CommandEditDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement CommandEditDialogListener");
        }
    }
    
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Build the dialog and set up the button click handlers
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        
        String dialogTitle = getString(R.string.command_edit_dialog_title) 
        		+ "  " 
        		+  ((Button) commandButton).getText().toString();
        
        builder.setMessage(dialogTitle)        
               .setPositiveButton(R.string.command_edit_dialog_yes, new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                       // Send the positive button event back to the host activity
                       mListener.onDialogPositiveClick(CommandEditDialogFragment.this);
                   }
               })
               .setNegativeButton(R.string.command_edit_dialog_no, new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                       // Send the negative button event back to the host activity
                       mListener.onDialogPositiveClick(CommandEditDialogFragment.this);
                   }
               });
        
      
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        View view = inflater.inflate(R.layout.edit_command_fields, null);
        initEditDialog(view);
        
        builder.setView(view);
        
        
        return builder.create();
    }

	private void initEditDialog(View view) {
		
		commandText = (EditText ) view.findViewById(R.id.command_edit_dialog_command_text);
		restoreButton = (Button) view.findViewById(R.id.command_edit_dialog_button_restore);
		clearButton = (Button) view.findViewById(R.id.command_edit_dialog_button_clear);
		
		final CharSequence currentCommand = mListener.getCommandByButtonId(commandButton.getId());
		
		clearButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {				
				commandText.setText("");
			}
		});
		
		restoreButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {				
				commandText.setText(currentCommand);
				commandText.setSelection(currentCommand.length());
			}
		});	
		
		
		// set current command in command text field
		commandText.setText(currentCommand);
		
		
	}
	
	
	public EditText getCommandTextField() {
		return commandText;
	}

	public Button getCommandButton() {
		return commandButton;
	}
    
}