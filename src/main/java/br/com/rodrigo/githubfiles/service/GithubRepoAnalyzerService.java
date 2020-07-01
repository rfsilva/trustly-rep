package br.com.rodrigo.githubfiles.service;

import br.com.rodrigo.githubfiles.dto.GithubFilesSummaryDTO;

public interface GithubRepoAnalyzerService  {

	public GithubFilesSummaryDTO performAnalysis(String githubRepo);
}
