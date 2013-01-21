package nikolakoco.beljot;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class AddGameActivity extends Activity {

	Button addPointsBtn;
	EditText pointsAEditText;
	EditText pointsBEditText;
	EditText claimsAEditText;
	EditText claimsBEditText;
	RadioGroup belotRadioGroup;
	CheckBox belotClaimAChkBox;
	CheckBox belotClaimBChkBox;
	CheckBox chaljoChkBox;
	TextView teamAPointsTxt;
	TextView teamBPointsTxt;
	int gamePoints;
	boolean ignoreTextChanged;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_game);
		
		gamePoints = 162;
		ignoreTextChanged = true;
		
		addPointsBtn = (Button) findViewById(R.id.addPointsBtn);
		addPointsBtn.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				finish();
			}
		});

		teamAPointsTxt = (TextView) findViewById(R.id.teamAPointsTxt);
		teamBPointsTxt = (TextView) findViewById(R.id.teamBPointsTxt);
		addListenerToCheckBox();
		addListenersToEditTexts();
		
	}

	private void addListenersToEditTexts() {
		pointsAEditText = (EditText) findViewById(R.id.pointsAEditText);
		pointsAEditText.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if(ignoreTextChanged) {
					if (pointsAEditText.getText().toString().length() > 0) {
						ignoreTextChanged = false;
						pointsBEditText.setText(Integer.toString(gamePoints - Integer.parseInt(pointsAEditText.getText().toString())));
						ignoreTextChanged = true;
						calculatePoints();
					}
				}
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
			}
		});

		pointsBEditText = (EditText) findViewById(R.id.pointsBEditText);
		pointsBEditText.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if(ignoreTextChanged) {
					if (pointsBEditText.getText().toString().length() > 0) {
						ignoreTextChanged = false;
						pointsAEditText.setText(Integer.toString(gamePoints - Integer.parseInt(pointsBEditText.getText().toString())));
						ignoreTextChanged = true;
						calculatePoints();
					}
				}
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
			}
		});
		
		claimsAEditText = (EditText) findViewById(R.id.claimsAEditText);
		claimsAEditText.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if(ignoreTextChanged) {
						ignoreTextChanged = false;
						claimsBEditText.setText("0");
						ignoreTextChanged = true;
						calculatePoints();
				}
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
			}
		});
		
		claimsBEditText = (EditText) findViewById(R.id.claimsBEditText);
		claimsBEditText.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if(ignoreTextChanged) {
					ignoreTextChanged = false;
					claimsAEditText.setText("0");
					ignoreTextChanged = true;
					calculatePoints();
				}
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
			}
		});
	}

	private void addListenerToCheckBox() {
		belotClaimAChkBox = (CheckBox) findViewById(R.id.belotClaimAChkBox);
		belotClaimAChkBox.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(belotClaimBChkBox.isChecked()) {
					belotClaimBChkBox.setChecked(false);
				}
				calculatePoints();
			}
		});
		
		belotClaimBChkBox = (CheckBox) findViewById(R.id.belotClaimBChkBox);
		belotClaimBChkBox.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(belotClaimAChkBox.isChecked()) {
					belotClaimAChkBox.setChecked(false);
				}
				calculatePoints();
			}
		});
		
		chaljoChkBox = (CheckBox) findViewById(R.id.chaljoChkBox);
		chaljoChkBox.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (chaljoChkBox.isChecked()) {
					gamePoints = 252;
				} else {
					gamePoints = 162;
				}
				calculatePoints();
			}
		});
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
	
	private void calculatePoints() {
		int finalPtsA, finalPtsB, ptsA, ptsB, clmsA, clmsB;
		finalPtsA = finalPtsB = ptsA = ptsB = clmsA = clmsB = 0;
		if (pointsAEditText.getText().toString().length() == 0 && 
				pointsBEditText.getText().toString().length() == 0) {
			Toast.makeText(AddGameActivity.this, "TEXTS EMPTY", Toast.LENGTH_SHORT)
					.show();
			return;
		}
		
		if (claimsAEditText.getText().toString().length() > 1) {
			clmsA = Integer.parseInt(claimsAEditText.getText().toString());
		} else if (claimsBEditText.getText().toString().length() > 1) {
			clmsB = Integer.parseInt(claimsBEditText.getText().toString());
		}
		
		if(belotClaimAChkBox.isChecked()) {
			clmsA += 20;
		} else if(belotClaimBChkBox.isChecked()) {
			clmsB += 20;
		} 
		
		ptsA = Integer.parseInt(pointsAEditText.getText().toString());
		ptsB = Integer.parseInt(pointsBEditText.getText().toString());
		
		finalPtsA = ptsA + clmsA;
		finalPtsB = ptsB + clmsB;
		
		// Setting the points 
		teamAPointsTxt.setText(Integer.toString(finalPtsA));
		teamBPointsTxt.setText(Integer.toString(finalPtsB));
		if (finalPtsA > finalPtsB) {
			teamAPointsTxt.setTextColor(Color.GREEN);
			teamBPointsTxt.setTextColor(Color.RED);
		} else if (finalPtsB > finalPtsA) {
			teamAPointsTxt.setTextColor(Color.RED);
			teamBPointsTxt.setTextColor(Color.GREEN);
		} else {
			// PARTIJATA VISI, RESENIE DA SE NAJDI
		}
	}
}
