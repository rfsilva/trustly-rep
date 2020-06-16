package br.com.rodrigo.githubfiles.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.rodrigo.githubfiles.controller.dto.GithubFileDTO;
import br.com.rodrigo.githubfiles.controller.dto.GithubFilesSummaryDTO;
import br.com.rodrigo.githubfiles.service.GithubFileDetailsService;
import br.com.rodrigo.githubfiles.service.GithubRepoService;
import br.com.rodrigo.githubfiles.service.GithubService;
import br.com.rodrigo.githubfiles.util.FileUtils;

@Service
public class GithubServiceImpl implements GithubService {

	private GithubFileDetailsService fileDetailsService;
	
	private GithubRepoService githubRepoService;
	
	@Autowired
	public GithubServiceImpl(GithubFileDetailsService fileDetailsService,
			GithubRepoService githubRepoService) {
		this.fileDetailsService = fileDetailsService;
		this.githubRepoService = githubRepoService;
	}
	
	public GithubFilesSummaryDTO getFilesSummary(String githubRepo) {
		Map<String, GithubFileDTO> map = new HashMap<>();
		
		for (GithubFileDTO file : githubRepoService.getFiles(githubRepo)) {
			String extension = FileUtils.getExtension(file.getName());
			Optional<GithubFileDTO> opt = fileDetailsService.getDetails(file);
			if (opt.isPresent()) {
				
				GithubFileDTO newDetails = opt.get();
				GithubFileDTO details = map.get(extension);
				if (details == null) {
					details = new GithubFileDTO();
				}
				map.put(extension, GithubFileDTO.builder()
						.extension(newDetails.getExtension())
						.bytes(details.getBytes() + newDetails.getBytes())
						.lines(details.getLines() + newDetails.getLines())
						.build());
			}
		}
		List<GithubFileDTO> list = new ArrayList<>();
		for (String ext : map.keySet()) {
			list.add(map.get(ext));
		}
		return GithubFilesSummaryDTO.builder()
				.fileDetailsList(list)
				.build();
	}
}
