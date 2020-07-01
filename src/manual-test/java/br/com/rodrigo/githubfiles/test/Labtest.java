package br.com.rodrigo.githubfiles.test;

import br.com.rodrigo.githubfiles.dto.GithubFilesSummaryDTO;
import br.com.rodrigo.githubfiles.service.GithubContentManager;
import br.com.rodrigo.githubfiles.service.GithubRepoAnalyzerService;
import br.com.rodrigo.githubfiles.service.impl.GithubContentManagerImpl;
import br.com.rodrigo.githubfiles.service.impl.GithubRepoAnalyzerServiceImpl;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class Labtest {

	public static void main(String []args) {
		GithubContentManager contentManager = new GithubContentManagerImpl();
		GithubRepoAnalyzerService repoAnalyzer = new GithubRepoAnalyzerServiceImpl(contentManager);
		
		GithubFilesSummaryDTO result1 = repoAnalyzer.performAnalysis("https://github.com/rfsilva/trustly-rep/tree/develop");
		log.info(result1);
		GithubFilesSummaryDTO result2 = repoAnalyzer.performAnalysis("https://github.com/TheAlgorithms/Java");
		log.info(result2);
		GithubFilesSummaryDTO result3 = repoAnalyzer.performAnalysis("https://stackoverflow.com/questions/29250052/how-to-validate-2-field-with-or-condition");
		log.info(result3);
	}
}
