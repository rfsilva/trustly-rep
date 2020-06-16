package br.com.rodrigo.githubfiles.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import br.com.rodrigo.githubfiles.controller.dto.GithubFileDTO;
import br.com.rodrigo.githubfiles.service.GithubRepoService;

@Service
public class GithubRepoServiceImpl implements GithubRepoService {

	public List<GithubFileDTO> getFiles(String repoName) {
		return new ArrayList<>();
	}
}
