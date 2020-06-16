package br.com.rodrigo.githubfiles.service;

import java.util.List;

import br.com.rodrigo.githubfiles.controller.dto.GithubFileDTO;

public interface GithubRepoService {

	public List<GithubFileDTO> getFiles(String repoName);
}
