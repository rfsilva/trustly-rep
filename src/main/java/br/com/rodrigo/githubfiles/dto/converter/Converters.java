package br.com.rodrigo.githubfiles.dto.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import br.com.rodrigo.githubfiles.dto.GithubFileDTO;
import br.com.rodrigo.githubfiles.dto.GithubFilesSummaryDTO;
import br.com.rodrigo.githubfiles.service.impl.obj.FileSum;

public final class Converters {
	
	private Converters() {
	}

	public static GithubFilesSummaryDTO convert(Map<String, FileSum> input) {
		final GithubFilesSummaryDTO output = new GithubFilesSummaryDTO();
		List<GithubFileDTO> list = new ArrayList<>();

		input.entrySet().stream().forEach((entry) -> {
			list.add(GithubFileDTO.builder()
					.bytes(entry.getValue().getBytes())
					.extension(entry.getKey())
					.lines(entry.getValue().getLines())
					.numFiles(entry.getValue().getNumFiles())
					.build());
		});
		output.setFileDetailsList(list);
		return output;
	}
}
