package info.novatec.tr.highscore;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;


@Entity
public class LevelScore {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	private int score;
	private String playerName;
	private String levelIdentifier;
	
	public LevelScore() {
		// TODO Auto-generated constructor stub
	}
	
	public LevelScore(LevelScoreDTO newLevelScore, String levelIdentifier) {
		
		this.score = newLevelScore.getScore();
		this.playerName = newLevelScore.getPlayerName();
		this.levelIdentifier = levelIdentifier;
		
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
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

	public String getLevelIdentifier() {
		return levelIdentifier;
	}

	public void setLevelIdentifier(String levelIdentifier) {
		this.levelIdentifier = levelIdentifier;
	}

}
