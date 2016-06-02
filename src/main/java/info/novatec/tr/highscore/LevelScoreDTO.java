package info.novatec.tr.highscore;

public class LevelScoreDTO {
	
	private int score;
	private String playerName;
	
	public LevelScoreDTO() {
		// TODO Auto-generated constructor stub
	}
	
	public LevelScoreDTO(String playerName, int score) {
		this.score = score;
		this.playerName = playerName;		
	}
	
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public String getPlayerName() {
		return playerName;
	}
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
	
	@Override
	public String toString() {
		return "LevelScoreDTO [score=" + score + ", playerName=" + playerName + "]";
	}

}
