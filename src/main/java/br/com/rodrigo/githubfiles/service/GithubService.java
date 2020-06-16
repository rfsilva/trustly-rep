package br.com.rodrigo.githubfiles.service;

import br.com.rodrigo.githubfiles.controller.dto.GithubFilesSummaryDTO;

public interface GithubService  {

	public GithubFilesSummaryDTO getFilesSummary(String githubRepo);
}
