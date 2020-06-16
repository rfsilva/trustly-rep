package br.com.rodrigo.githubfiles.controller.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

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

	@JsonIgnore
	private String name;
	
	private String extension;
	private Integer lines;
	private Long bytes;
}
