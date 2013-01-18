package nikolakoco.beljot;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddGameActivity extends Activity implements TextWatcher {

	Button addPointsBtn;
	EditText pointsAEditText;
	EditText pointsBEditText;
	EditText claimsAEditText;
	EditText claimsBEditText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_game);

		addPointsBtn = (Button) findViewById(R.id.addPointsBtn);
		addPointsBtn.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				finish();
			}
		});

		pointsAEditText = (EditText) findViewById(R.id.pointsAEditText);
		pointsAEditText.addTextChangedListener(this);

		pointsBEditText = (EditText) findViewById(R.id.pointsBEditText);
		pointsBEditText.addTextChangedListener(this);

		claimsAEditText = (EditText) findViewById(R.id.claimsAEditText);
		claimsAEditText.addTextChangedListener(this);

		claimsBEditText = (EditText) findViewById(R.id.claimsBEditText);
		claimsBEditText.addTextChangedListener(this);

	}

	public void afterTextChanged(Editable s) {
		// TO DO MAKE CALCULATING
		int ptsA, ptsB, clmsA, clmsB;
		Toast.makeText(AddGameActivity.this, "INSIDE", Toast.LENGTH_SHORT)
		.show();
		if (pointsAEditText.getText().toString().length() < 1) {
			Toast.makeText(AddGameActivity.this, "EMPTY", Toast.LENGTH_SHORT)
					.show();
		}
		
		if (claimsAEditText.getText().toString().length() > 0) {
			
		}
	}
	public boolean onKey(View v, int keyCode, KeyEvent event) {
		Toast.makeText(AddGameActivity.this, "INSIDE", Toast.LENGTH_SHORT)
		.show();
		if (pointsAEditText.getText().toString().length() < 1) {
			Toast.makeText(AddGameActivity.this, "EMPTY", Toast.LENGTH_SHORT)
					.show();
		}

		return false;
	}

	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		// TODO Auto-generated method stub
		
	}

	public void onTextChanged(CharSequence s, int start, int before, int count) {
		// TODO Auto-generated method stub
		
	}
}
