package nikolakoco.beljot;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class BeljotActivity extends Activity implements OnClickListener {
	/** Called when the activity is first created. */
	Button new_game_btn;
	Button resume_game_btn;
	Button history_btn;
	Button settings_btn;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		new_game_btn = (Button)this.findViewById(R.id.new_game_btn);
		new_game_btn.setOnClickListener(this);

		resume_game_btn = (Button)this.findViewById(R.id.resume_game_btn);
		resume_game_btn.setOnClickListener(this);

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
			startActivity(intent);
		} else if(v.equals(resume_game_btn)) {
			//  Set boolean resume_game for GameActivity
			Bundle bundle = new Bundle();
			bundle.putBoolean("resume_game", true);
			intent = new Intent(this, GameActivity.class);
			intent.putExtras(bundle);
			startActivity(intent);
		} else if(v.equals(history_btn)) {
			intent = new Intent(this, HistoryActivity.class);
			startActivity(intent);
		} else if(v.equals(settings_btn)) {
			intent = new Intent(this, SettingsActivity.class);
			startActivity(intent);
		}
	}
}