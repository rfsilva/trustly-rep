package br.com.rodrigo.githubfiles.service;

import br.com.rodrigo.githubfiles.dto.GithubFilesSummaryDTO;

public interface GithubService  {

	public GithubFilesSummaryDTO getGithubSummary(String githubRepo);
}
