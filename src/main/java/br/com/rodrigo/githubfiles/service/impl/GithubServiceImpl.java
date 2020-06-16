package br.com.rodrigo.githubfiles.service.impl;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.rodrigo.githubfiles.dto.GithubFilesSummaryDTO;
import br.com.rodrigo.githubfiles.dto.converter.Converters;
import br.com.rodrigo.githubfiles.service.GithubRepoAnalyzer;
import br.com.rodrigo.githubfiles.service.GithubService;
import br.com.rodrigo.githubfiles.service.impl.obj.FileSum;

@Service
public class GithubServiceImpl implements GithubService {

	private final GithubRepoAnalyzer githubRepoAnalyzer;
	
	@Autowired
	public GithubServiceImpl(GithubRepoAnalyzer githubRepoAnalyzer) {
		this.githubRepoAnalyzer = githubRepoAnalyzer;
	}
	
	public GithubFilesSummaryDTO getGithubSummary(String githubRepo) {
		Optional<Map<String, FileSum>> resultMap = githubRepoAnalyzer.performAnalysis(githubRepo);
		if (resultMap.isPresent()) {
			return Converters.convert(resultMap.get());	
		}
		return null;
	}
}
