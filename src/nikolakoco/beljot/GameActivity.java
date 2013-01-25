package nikolakoco.beljot;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class GameActivity extends Activity {

	TextView team1_name_txt;
	TextView team2_name_txt;
	TextView validationWarningText;
	TextView fullPointsTeamATxt;
	TextView fullPointsTeamBTxt;
	EditText firstTeamEditText;
	EditText secondTeamEditText;
	Button add_new_game_btn;
	ListView pointsList;
	ArrayList<HashMap<String, String>> pointsArrayList;
	HashMap<String, String> pointsMap;
	ArrayList<Game> gamesArrayList;
	private SimpleAdapter pointsAdapter;
	static final private int NAMES_ENTRY_DIALOG = 1;
	static final private int ON_BACK_KEY_DIALOG = 2;
	static final private int SHOW_ADD_NEW_GAME = 3;
	static final private int RESULT_OLD_GAME = 4;
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

		fullPointsTeamATxt = (TextView) findViewById(R.id.fullPointsTeamATxt);
		fullPointsTeamBTxt = (TextView) findViewById(R.id.fullPointsTeamBTxt);
		
		pointsList = (ListView)findViewById(R.id.pointsList);
		
		gamesArrayList = new ArrayList<Game>();
		pointsArrayList = new ArrayList<HashMap<String, String>>();
		pointsMap = new HashMap<String, String>();
		pointsAdapter = new SimpleAdapter(this, pointsArrayList, R.layout.row,
											new String[] {"teamAPoints", "teamBPoints"},
											new int[] {R.id.teamACell, R.id.teamBCell});
		pointsList.setAdapter(pointsAdapter);
		pointsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int index,
					long arg3) {
				Game game = gamesArrayList.get(index);
				
				Bundle addGameBundle = new Bundle();
				addGameBundle.putString("teamAName", team1_name_txt.getText().toString());
				addGameBundle.putString("teamBName", team2_name_txt.getText().toString());
				addGameBundle.putString("gameNumber", Integer.toString(index + 1));
				addGameBundle.putBoolean("newGame", false);
				Intent intent;
				intent = new Intent(GameActivity.this, AddGameActivity.class);
				intent.putExtras(addGameBundle);
				intent.putExtra("viewExistingGameObject", game);
				startActivityForResult(intent, SHOW_ADD_NEW_GAME);
			}
		});
		
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
				Bundle addGameBundle = new Bundle();
				addGameBundle.putString("teamAName", team1_name_txt.getText().toString());
				addGameBundle.putString("teamBName", team2_name_txt.getText().toString());
				addGameBundle.putString("gameNumber", Integer.toString(gamesArrayList.size() + 1));
				addGameBundle.putBoolean("newGame", true);
				Intent intent;
				intent = new Intent(GameActivity.this, AddGameActivity.class);
				intent.putExtras(addGameBundle);
				startActivityForResult(intent, SHOW_ADD_NEW_GAME);
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
								team1_name_txt.setText(firstTeamEditText.getText().toString().toUpperCase());
								team2_name_txt.setText(secondTeamEditText.getText().toString().toUpperCase());
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
	
	public void addRow(Game game, boolean edit, int index) {
		// Add the points to the pointsMap and add the map to the pointsArrayList 
		// so that the pointAdapter updates the pointsList
		pointsMap = new HashMap<String, String>();
		pointsMap.put("teamAPoints", game.getTeamA().getFinalPoints());
		pointsMap.put("teamBPoints", game.getTeamB().getFinalPoints());
	
		// Add the row in both of the lists depending if it's a new or an old one
		if (edit) {
			gamesArrayList.set(index, game);
			pointsArrayList.set(index, pointsMap);
		} else {
			gamesArrayList.add(game);
			pointsArrayList.add(pointsMap);
		}
		pointsAdapter.notifyDataSetChanged();
		
		// Calculate full points
		int fullPointsA = 0; 
		int fullPointsB = 0; 
		for(int i = 0; i < gamesArrayList.size(); i++) {
			fullPointsA += Integer.parseInt(gamesArrayList.get(i).getTeamA().getFinalPoints()); 
			fullPointsB += Integer.parseInt(gamesArrayList.get(i).getTeamB().getFinalPoints()); 
		}
		
		// Setting the points 
		fullPointsTeamATxt.setText(Integer.toString(fullPointsA));
		fullPointsTeamBTxt.setText(Integer.toString(fullPointsB));
		if (fullPointsA > fullPointsB) {
			fullPointsTeamATxt.setTextColor(Color.GREEN);
			fullPointsTeamBTxt.setTextColor(Color.RED);
		} else if (fullPointsA < fullPointsB) {
			fullPointsTeamATxt.setTextColor(Color.RED);
			fullPointsTeamBTxt.setTextColor(Color.GREEN);
		} else {
			// PARTIJATA VISI, RESENIE DA SE NAJDI
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == SHOW_ADD_NEW_GAME) {
			if(resultCode == Activity.RESULT_OK) {
				// Get the game object from AddGameActivity and add it to a new row
				Game game = data.getParcelableExtra("returnGameObject");
				addRow(game, false, 0);  // New row, the index member is default 0
			} else if (resultCode == RESULT_OLD_GAME) {
				// Get the game object from AddGameActivity and add it to a new row
				Game game = data.getParcelableExtra("returnGameObject");
				int index = Integer.parseInt(data.getStringExtra("gameNumber"));
				--index;
				addRow(game, true, index);  // Old row
			}
		}
	}
}
