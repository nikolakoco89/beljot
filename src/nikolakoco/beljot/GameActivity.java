package nikolakoco.beljot;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class GameActivity extends Activity {

	TextView team1_name_txt;
	TextView team2_name_txt;
	TextView validationWarningText;
	TextView cell_item;
	TextView cell_item2;
	EditText firstTeamEditText;
	EditText secondTeamEditText;
	TableLayout table_scores;
	Button add_new_game_btn;
	static final private int NAMES_ENTRY_DIALOG = 1;
	static final private int ON_BACK_KEY_DIALOG = 2;
	public static final String FIRST_TEAM_NAME = "FIRST_TEAM_NAME";
	public static final String SECOND_TEAM_NAME = "SECOND_TEAM_NAME";
	SharedPreferences prefs;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game);

		team1_name_txt = (TextView)findViewById(R.id.team1_name_txt);
		team2_name_txt = (TextView)findViewById(R.id.team2_name_txt);

		table_scores = (TableLayout)findViewById(R.id.table_scores);
		final TableRow table_row = new TableRow(this);
		
		table_row.setClickable(true);
		cell_item = new TextView(this);
		cell_item2 = new TextView(this);
		
		cell_item.setText("OD_KOD");
		cell_item.setGravity(Gravity.LEFT);

		cell_item2.setText("OD_KOD");
		cell_item2.setGravity(Gravity.RIGHT);
		
		table_row.addView(cell_item, 0);
		table_row.addView(cell_item2, 1);
		table_row.setId(0);
		cell_item.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				Toast.makeText(GameActivity.this, "SUCCESS", Toast.LENGTH_SHORT).show();
			}
		});
		table_scores.addView(table_row);
		
		Bundle bundle = getIntent().getExtras();
		boolean resume_game = bundle.getBoolean("resume_game");
		Context context = getApplicationContext();
		prefs = PreferenceManager.getDefaultSharedPreferences(context);
		if(resume_game) {
			updateGameFromPreferences();
		}
		else {
			showDialog(NAMES_ENTRY_DIALOG);
		}
		
		add_new_game_btn = (Button)findViewById(R.id.add_new_game_btn);
		add_new_game_btn.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				Toast.makeText(GameActivity.this, "BUTTON CLICKED", Toast.LENGTH_SHORT).show();
				Intent intent;
				intent = new Intent(GameActivity.this, AddGameActivity.class);
				startActivity(intent);
			}
		});
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch(id) {
		case(NAMES_ENTRY_DIALOG):
			LayoutInflater factory = LayoutInflater.from(this);
			final View textEntryView = factory.inflate(R.layout.name_entry_dialog, null);
			firstTeamEditText = (EditText)textEntryView.findViewById(R.id.firstTeamEditText);
			secondTeamEditText = (EditText)textEntryView.findViewById(R.id.secondTeamEditText);
			validationWarningText = (TextView)textEntryView.findViewById(R.id.validationWarningText);
			final AlertDialog names_entry_dialog = new AlertDialog.Builder(this)
						.setMessage(R.string.name_entry_dialog_string)
						.setView(textEntryView)
						.setPositiveButton(R.string.start_button_string, new DialogInterface.OnClickListener() {
							
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
								
							}
						})
						.setNegativeButton(R.string.cancel_button_string, new DialogInterface.OnClickListener() {
							
							public void onClick(DialogInterface dialog, int which) {
								finish();
							}
						})
						.create();
			names_entry_dialog.setOnShowListener(new DialogInterface.OnShowListener() {
				
				public void onShow(DialogInterface dialog) {
					Button b = names_entry_dialog.getButton(AlertDialog.BUTTON_POSITIVE);
					b.setOnClickListener(new View.OnClickListener() {
						
						public void onClick(View v) {
							if((firstTeamEditText.getText().length() == 0) || (secondTeamEditText.getText().length() == 0)) {
								validationWarningText.setVisibility(View.VISIBLE);
							} else {
								validationWarningText.setVisibility(View.INVISIBLE);
								team1_name_txt.setText(firstTeamEditText.getText());
								team2_name_txt.setText(secondTeamEditText.getText());
								names_entry_dialog.dismiss();
							} 
						}
					});
				}
			});
			
			return names_entry_dialog;
		
		case(ON_BACK_KEY_DIALOG):
			AlertDialog.Builder onBackKeyDialog = new AlertDialog.Builder(this);
			onBackKeyDialog.setTitle(R.string.information_string);
			onBackKeyDialog.setMessage(R.string.on_back_key_dialog_message_string);
			onBackKeyDialog.setPositiveButton(R.string.end_game_button_string, new DialogInterface.OnClickListener() {
				
				public void onClick(DialogInterface dialog, int which) {
					// TODO Save to database
					Editor editor = prefs.edit();
					editor.remove(FIRST_TEAM_NAME);
					editor.remove(SECOND_TEAM_NAME);
					editor.commit();
					finish();
				}
			});
			onBackKeyDialog.setNeutralButton(R.string.save_to_resume_button_string, new DialogInterface.OnClickListener() {
				
				public void onClick(DialogInterface dialog, int which) {
					// TODO Save to shared preferences
					Editor editor = prefs.edit();
					editor.putString(FIRST_TEAM_NAME, team1_name_txt.getText().toString());
					editor.putString(SECOND_TEAM_NAME, team2_name_txt.getText().toString());
					editor.commit();
					if(prefs.contains(FIRST_TEAM_NAME) && prefs.contains(SECOND_TEAM_NAME))
						finish();
				}
			});
			onBackKeyDialog.setNegativeButton(R.string.cancel_button_string, new DialogInterface.OnClickListener() {
				
				public void onClick(DialogInterface dialog, int which) {
					// Do nothing
				}
			});
			return onBackKeyDialog.create();
		}
		return null;
	}

	@Override
	public void onBackPressed() {
		showDialog(ON_BACK_KEY_DIALOG);
//		super.onBackPressed();
	}
	
	public void updateGameFromPreferences() {
		team1_name_txt.setText(prefs.getString(FIRST_TEAM_NAME, "TEAM 1"));
		team2_name_txt.setText(prefs.getString(SECOND_TEAM_NAME, "TEAM 2"));
	}
	
	public void addNewRow() {
		final TableRow table_row = new TableRow(this);
		table_row.setClickable(true);
		cell_item.setText("TEST"); // TODO ADDING POINTS
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
	}
}
