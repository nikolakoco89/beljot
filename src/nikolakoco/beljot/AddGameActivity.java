package nikolakoco.beljot;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
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
	TextView teamANameTxt;
	TextView teamBNameTxt;
	TextView teamAPointsTxt;
	TextView teamBPointsTxt;
	TextView teamAChaljoNameTxt;
	TextView teamBChaljoNameTxt;
	int gamePoints;
	boolean ignoreTextChanged;
	Team teamA;
	Team teamB;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_game);
		
		gamePoints = 162;
		ignoreTextChanged = true;
		
		addPointsBtn = (Button) findViewById(R.id.addPointsBtn);
		addPointsBtn.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				teamA.setBelotClaim(belotClaimAChkBox.isChecked());
				teamB.setBelotClaim(belotClaimBChkBox.isChecked());
				
				teamA.setPoints(pointsAEditText.getText().toString());
				teamB.setPoints(pointsBEditText.getText().toString());
				
				teamA.setClaims(claimsAEditText.getText().toString());
				teamB.setClaims(claimsBEditText.getText().toString());
				
				teamA.setFinalPoints(teamAPointsTxt.getText().toString());
				teamB.setFinalPoints(teamBPointsTxt.getText().toString());
				
				Game game = new Game(teamA, teamB, gamePoints);
				// TODO transfer of game information back to GameActivity
				Intent resultIntent = new Intent();
				resultIntent.putExtra("returnGameObject", game);
				AddGameActivity.this.setResult(RESULT_OK, resultIntent);
				finish();
			}
		});

		initializeTextViews();
		addListenerToCheckBox();
		addListenersToEditTexts();
		
		// TODO New game and viewing old game 
		boolean newGame = true;
		if(newGame) {
			createTeams(null, newGame);
		} else {
			// TODO Get information about the game to be viewed
			createTeams(null, newGame);
		}
		
	}

	private void createTeams(Game game, boolean newGame) {
		teamA = new Team();
		teamB = new Team();
		if(newGame) {
		}
		// TODO construct teams and fill views with information if it's an old game
		teamA.setBelotClaim(belotClaimAChkBox.isChecked());
		teamB.setBelotClaim(belotClaimBChkBox.isChecked());
		
		teamA.setChaljo(true);
		teamB.setChaljo(true);
		
	}

	private void initializeTextViews() {
		Bundle bundle = getIntent().getExtras();
		teamAPointsTxt = (TextView) findViewById(R.id.teamAPointsTxt);
		teamBPointsTxt = (TextView) findViewById(R.id.teamBPointsTxt);
		
		teamANameTxt = (TextView) findViewById(R.id.teamANameTxt);
		teamANameTxt.setText(bundle.getString("teamAName"));
		teamANameTxt.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(ignoreTextChanged) {
					ignoreTextChanged = false;
					teamANameTxt.setTextColor(Color.GREEN);
					teamBNameTxt.setTextColor(Color.GRAY);
					Toast.makeText(AddGameActivity.this, teamANameTxt.getText().toString()
														+ " have taken the card!",
														Toast.LENGTH_SHORT).show();
					teamA.setCardTaken(true);
					teamB.setCardTaken(false);
					ignoreTextChanged = true;
				}
			}
		});
		
		teamBNameTxt = (TextView) findViewById(R.id.teamBNameTxt);
		teamBNameTxt.setText(bundle.getString("teamBName"));
		teamBNameTxt.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(ignoreTextChanged) {
					ignoreTextChanged = false;
					teamBNameTxt.setTextColor(Color.GREEN);
					teamANameTxt.setTextColor(Color.GRAY);
					Toast.makeText(AddGameActivity.this, teamBNameTxt.getText().toString()
							+ " have taken the card!",
							Toast.LENGTH_SHORT).show();
					teamB.setCardTaken(true);
					teamA.setCardTaken(false);
					ignoreTextChanged = true;
				}
			}
		});
		
		teamAChaljoNameTxt = (TextView) findViewById(R.id.teamAChaljoNameTxt);
		teamAChaljoNameTxt.setText(bundle.getString("teamAName"));
		teamAChaljoNameTxt.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				pointsAEditText.setText("252");
				pointsBEditText.setText("0");
				teamA.setChaljo(true);
				teamB.setChaljo(false);
				calculatePoints();
			}
		});
		teamBChaljoNameTxt = (TextView) findViewById(R.id.teamBChaljoNameTxt);
		teamBChaljoNameTxt.setText(bundle.getString("teamBName"));
		teamBChaljoNameTxt.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				pointsBEditText.setText("252");
				pointsAEditText.setText("0");
				teamB.setChaljo(true);
				teamA.setChaljo(false);
				calculatePoints();
			}
		});
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
				RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)chaljoChkBox.getLayoutParams();
				if (chaljoChkBox.isChecked()) {
					layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL, 0);
					chaljoChkBox.setLayoutParams(layoutParams);
					
					teamAChaljoNameTxt.setVisibility(View.VISIBLE);
					teamBChaljoNameTxt.setVisibility(View.VISIBLE);
					
					pointsAEditText.setEnabled(false);
					pointsBEditText.setEnabled(false);
					
					gamePoints = 252;
				} else {
					layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
					chaljoChkBox.setLayoutParams(layoutParams);
					teamAChaljoNameTxt.setVisibility(View.GONE);
					teamBChaljoNameTxt.setVisibility(View.GONE);
					
					pointsAEditText.setEnabled(true);
					pointsAEditText.setText("");
					
					pointsBEditText.setEnabled(true);
					pointsBEditText.setText("");
					
					teamAPointsTxt.setText("0");
					teamAPointsTxt.setTextColor(Color.GRAY);
					
					teamBPointsTxt.setText("0");
					teamBPointsTxt.setTextColor(Color.GRAY);
					
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
