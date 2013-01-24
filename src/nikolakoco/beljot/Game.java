package nikolakoco.beljot;

import android.os.Parcel;
import android.os.Parcelable;

public class Game implements Parcelable{
	private Team teamA;
	private Team teamB;
	private int gamePoints;
	
	public Game() {
		this.gamePoints = 0;
	}
	
	public Game(Team teamA, Team teamB, int gamePoints) {
		this.teamA = teamA;
		this.teamB = teamB;
		this.gamePoints = gamePoints;
	}
	
	public Team getTeamA() {
		return teamA;
	}
	
	public Team getTeamB() {
		return teamB;
	}
	
	public int getGamePoints() {
		return gamePoints;
	}
	
	public void setTeamA(Team teamA) {
		this.teamA = teamA;
	}
	
	public void setTeamB(Team teamB) {
		this.teamB = teamB;
	}
	
	public void setGamePoints(int gamePoints) {
		this.gamePoints = gamePoints;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeParcelable(teamA, flags);
		dest.writeParcelable(teamB, flags);
		dest.writeInt(gamePoints);
	}
	
	public static final Parcelable.Creator<Game> CREATOR = new Parcelable.Creator<Game>() {

		@Override
		public Game createFromParcel(Parcel source) {
			return new Game(source);
		}

		@Override
		public Game[] newArray(int size) {
			return new Game[size];
		}
	};
	
	private Game(Parcel in) {
		this.teamA = in.readParcelable(Game.class.getClassLoader());
		this.teamB = in.readParcelable(Game.class.getClassLoader());
		this.gamePoints = in.readInt();		
	}
}

