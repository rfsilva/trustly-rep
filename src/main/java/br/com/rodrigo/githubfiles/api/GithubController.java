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
import br.com.rodrigo.githubfiles.service.GithubRepoAnalyzerService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/github")
public class GithubController {

	private GithubRepoAnalyzerService githubService;
	
	@Autowired
	public GithubController(GithubRepoAnalyzerService githubService) {
		this.githubService = githubService;
	}
	
    @ApiOperation(value = "Get lines and bytes summary of a Github repository")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The summary has been successfully processed.", response = GithubFilesSummaryDTO.class),
            @ApiResponse(code = 400, message = "Github URL or site content is invalid", response = GithubFilesSummaryDTO.class),
            @ApiResponse(code = 500, message = "Internal failure.", response = GithubFilesSummaryDTO.class) })
	@PutMapping()
	public ResponseEntity<GithubFilesSummaryDTO> getSummary(@RequestBody @Valid GithubRepositoryDTO repo) {
		GithubFilesSummaryDTO result = githubService.performAnalysis(repo.getUrl());
		if (result != null) {
			return ResponseEntity.ok(result);
		}
		return ResponseEntity.badRequest().body(GithubFilesSummaryDTO.builder().errorCode(1).build());
	}
}
