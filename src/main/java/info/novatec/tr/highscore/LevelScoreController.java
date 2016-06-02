package info.novatec.tr.highscore;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LevelScoreController {
	
	private Logger _logger = LoggerFactory.getLogger(getClass()); 
	
	@Autowired
	private LevelScoreRepository levelScoreRepository;
		
	@RequestMapping(method={RequestMethod.POST}, path = {"{levelIdentifier}/score"}, consumes="application/json")
	public void logLevelScore(@RequestBody LevelScoreDTO newLevelScore, @PathVariable String levelIdentifier) {
		
		_logger.debug("got request to persist level score {} for level {}",newLevelScore,levelIdentifier);
		
		LevelScore levelScore = new LevelScore(newLevelScore, levelIdentifier);
		levelScoreRepository.save(levelScore);
		
		_logger.debug("successfully persisted level score {} for level {}",newLevelScore,levelIdentifier);
		
	}
	
	@RequestMapping(method={RequestMethod.GET}, path = {"{levelIdentifier}/scores/{count}"}, produces="application/json")
	@ResponseBody
	public List<LevelScoreDTO> logLevelScore(@PathVariable String levelIdentifier, @PathVariable int count) {
		
		_logger.debug("got request to load {} best scores for level {}", count, levelIdentifier);
		List<LevelScore> topNLevelScoresByLevelDefinition = levelScoreRepository.getTopNLevelScoresByLevelDefinition(levelIdentifier, new PageRequest(0, count));
		List<LevelScoreDTO> highScores = new ArrayList<LevelScoreDTO>();
		
		for(LevelScore levelScore:topNLevelScoresByLevelDefinition) {
			highScores.add(new LevelScoreDTO(levelScore.getPlayerName(), levelScore.getTime()));
		}
		
		_logger.debug("found {} elements", topNLevelScoresByLevelDefinition.size());
		
		return highScores;
		
	}	

}
