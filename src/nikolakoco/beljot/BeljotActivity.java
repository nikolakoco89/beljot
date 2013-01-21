package nikolakoco.beljot;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class BeljotActivity extends Activity implements OnClickListener {
	/** Called when the activity is first created. */
	Button new_game_btn;
	Button resume_game_btn;
	Button history_btn;
	Button settings_btn;
	SharedPreferences prefs;
	private static final int SHOW_NEW_GAME = 1;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		Context context = getApplicationContext();
		prefs = PreferenceManager.getDefaultSharedPreferences(context);
		
		new_game_btn = (Button)this.findViewById(R.id.new_game_btn);
		new_game_btn.setOnClickListener(this);

		resume_game_btn = (Button)this.findViewById(R.id.resume_game_btn);
		resume_game_btn.setOnClickListener(this);
		if(prefs.getAll().size() == 0)
			resume_game_btn.setEnabled(false);
			
		history_btn = (Button)this.findViewById(R.id.history_btn);
		history_btn.setOnClickListener(this);

		settings_btn = (Button)this.findViewById(R.id.settings_btn);
		settings_btn.setOnClickListener(this);
	}

	public void onClick(View v) {
		Intent intent;
		if(v.equals(new_game_btn)) {
			//  Set boolean resume_game for GameActivity
			Bundle bundle = new Bundle();
			bundle.putBoolean("resume_game", false);
			intent = new Intent(this, GameActivity.class);
			intent.putExtras(bundle);
			startActivityForResult(intent, SHOW_NEW_GAME);
		} else if(v.equals(resume_game_btn)) {
			//  Set boolean resume_game for GameActivity
			Bundle bundle = new Bundle();
			bundle.putBoolean("resume_game", true);
			intent = new Intent(this, GameActivity.class);
			intent.putExtras(bundle);
			startActivityForResult(intent, SHOW_NEW_GAME);
		} else if(v.equals(history_btn)) {
			intent = new Intent(this, AddGameActivity.class);
			startActivity(intent);
		} else if(v.equals(settings_btn)) {
			intent = new Intent(this, SettingsActivity.class);
			startActivity(intent);
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode == SHOW_NEW_GAME) {
			if(prefs.getAll().size() == 0)
				resume_game_btn.setEnabled(false);
			else
				resume_game_btn.setEnabled(true);
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
}