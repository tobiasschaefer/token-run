package info.novatec.tr.highscore;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;





public interface LevelScoreRepository extends CrudRepository<LevelScore, Long> {

	@Query("Select levelScore from LevelScore levelScore where levelScore.levelIdentifier = :levelIdentifier order by levelScore.time asc")
	public List<LevelScore> getTopNLevelScoresByLevelDefinition(@Param("levelIdentifier") String levelIdentifier,
			Pageable pageable);

}
