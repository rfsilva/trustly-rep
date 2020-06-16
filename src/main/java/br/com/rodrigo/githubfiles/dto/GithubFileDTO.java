package br.com.rodrigo.githubfiles.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GithubFileDTO {
	private String extension;
	private Integer lines;
	private Integer numFiles;
	private Long bytes;
}
