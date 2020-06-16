package br.com.rodrigo.githubfiles.test;

import java.util.Map;

import br.com.rodrigo.githubfiles.service.impl.GithubRepoAnalyzerImpl;
import br.com.rodrigo.githubfiles.service.impl.obj.FileSum;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class Labtest {

	public static void main(String []args) {
		Map<String, FileSum> result1 = new GithubRepoAnalyzerImpl().performAnalysis("https://github.com/rfsilva/trustly-rep/tree/develop");
		log.info(result1);
		Map<String, FileSum> result2 = new GithubRepoAnalyzerImpl().performAnalysis("https://github.com/TheAlgorithms/Java");
		log.info(result2);
	}
}
