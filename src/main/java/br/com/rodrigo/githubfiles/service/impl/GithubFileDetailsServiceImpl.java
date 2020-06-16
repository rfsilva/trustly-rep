package br.com.rodrigo.githubfiles.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.rodrigo.githubfiles.controller.dto.GithubFileDTO;
import br.com.rodrigo.githubfiles.service.GithubFileDetailsService;

@Service
public class GithubFileDetailsServiceImpl implements GithubFileDetailsService {


	public Optional<GithubFileDTO> getDetails(GithubFileDTO file) {
		return Optional.of(GithubFileDTO.builder()
				.lines(0)
				.bytes(0L)
				.build());
	}
}
