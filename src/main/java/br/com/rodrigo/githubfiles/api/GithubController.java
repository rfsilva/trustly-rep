package br.com.rodrigo.githubfiles.api;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.rodrigo.githubfiles.dto.GithubFilesSummaryDTO;
import br.com.rodrigo.githubfiles.dto.GithubRepositoryDTO;
import br.com.rodrigo.githubfiles.service.GithubService;

@RestController
@RequestMapping("/github")
public class GithubController {

	private GithubService githubService;
	
	@Autowired
	public GithubController(GithubService githubService) {
		this.githubService = githubService;
	}
	
	@PutMapping()
	public ResponseEntity<GithubFilesSummaryDTO> getSummary(@RequestBody @Valid GithubRepositoryDTO repo) {
		GithubFilesSummaryDTO result = githubService.getGithubSummary(repo.getUrl());
		if (result != null) {
			return ResponseEntity.ok(result);
		}
		return ResponseEntity.badRequest().body(GithubFilesSummaryDTO.builder().errorCode(1).build());
	}
}
