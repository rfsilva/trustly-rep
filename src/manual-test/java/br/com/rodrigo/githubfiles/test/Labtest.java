package br.com.rodrigo.githubfiles.test;

import java.util.Map;
import java.util.Optional;

import br.com.rodrigo.githubfiles.service.impl.GithubRepoAnalyzerImpl;
import br.com.rodrigo.githubfiles.service.impl.obj.FileSum;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class Labtest {

	public static void main(String []args) {
		Optional<Map<String, FileSum>> result1 = new GithubRepoAnalyzerImpl().performAnalysis("https://github.com/rfsilva/trustly-rep/tree/develop");
		log.info(result1.get());
		Optional<Map<String, FileSum>> result2 = new GithubRepoAnalyzerImpl().performAnalysis("https://github.com/TheAlgorithms/Java");
		log.info(result2.get());
	}
}
