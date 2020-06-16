package br.com.rodrigo.githubfiles.service;

import java.util.Optional;

import br.com.rodrigo.githubfiles.controller.dto.GithubFileDTO;

public interface GithubFileDetailsService {

	public Optional<GithubFileDTO> getDetails(GithubFileDTO file);
}
