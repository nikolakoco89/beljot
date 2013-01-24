package nikolakoco.beljot;

import android.os.Parcel;
import android.os.Parcelable;

public class Team implements Parcelable{
	
	private boolean chaljo;
	private boolean belotClaim;
	private boolean cardTaken;
	private String points;
	private String claims;
	private String finalPoints;
	
	public Team() {
		this.chaljo = false;
		this.belotClaim = false;
		this.cardTaken = false;
		this.points = "0";
		this.claims = "0";
		this.finalPoints = "0";
	}

	public Team(boolean chaljo, boolean belotClaim, boolean cardTaken, String points, String claims, String finalPoints) {
		this.claims = claims;
		this.points = points;
		this.finalPoints = finalPoints;
		this.belotClaim = belotClaim;
		this.cardTaken = cardTaken;
		this.chaljo = chaljo;
	}
	
	public String getClaims() {
		return claims;
	}
	
	public String getFinalPoints() {
		return finalPoints;
	}
	
	public String getPoints() {
		return points;
	}
	
	public void setClaims(String claims) {
		this.claims = claims;
	}
	
	public void setPoints(String points) {
		this.points = points;
	}
	
	public void setFinalPoints(String finalPoints) {
		this.finalPoints = finalPoints;
	}
	
	public void setBelotClaim(boolean belotClaim) {
		this.belotClaim = belotClaim;
	}
	
	public void setCardTaken(boolean cardTaken) {
		this.cardTaken = cardTaken;
	}
	
	public void setChaljo(boolean chaljo) {
		this.chaljo = chaljo;
	}
	
	public boolean isBelotClaimed() {
		return belotClaim;
	}
	
	public boolean isCardTaken() {
		return cardTaken;
	}
	
	public boolean isChaljo() {
		return chaljo;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeByte((byte) (chaljo ? 1 : 0));
		dest.writeByte((byte) (belotClaim ? 1 : 0));
		dest.writeByte((byte) (cardTaken ? 1 : 0));
		dest.writeString(points);
		dest.writeString(claims);
		dest.writeString(finalPoints);
	}
	
	public static final Parcelable.Creator<Team> CREATOR = new Parcelable.Creator<Team>() {

		@Override
		public Team createFromParcel(Parcel source) {
			return new Team(source);
		}

		@Override
		public Team[] newArray(int size) {
			return new Team[size];
		}
	};
	
	private Team(Parcel in) {
		this.chaljo = in.readByte() == 1;
		this.belotClaim = in.readByte() == 1;
		this.cardTaken = in.readByte() == 1;
		this.points = in.readString();
		this.claims = in.readString();
		this.finalPoints = in.readString();
	}
}
