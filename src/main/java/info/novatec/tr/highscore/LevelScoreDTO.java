package info.novatec.tr.highscore;

public class LevelScoreDTO {

	private long time;
	private String playerName;





	public LevelScoreDTO() {
		// TODO Auto-generated constructor stub
	}





	public LevelScoreDTO(String playerName, long time) {
		this.time = time;
		this.playerName = playerName;
	}





	public long getTime() {
		return time;
	}





	public void setTime(long score) {
		time = score;
	}





	public String getPlayerName() {
		return playerName;
	}





	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}





	@Override
	public String toString() {
		return "LevelScoreDTO [score=" + time + ", playerName=" + playerName + "]";
	}

}
