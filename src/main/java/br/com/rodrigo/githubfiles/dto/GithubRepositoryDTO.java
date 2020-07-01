package br.com.rodrigo.githubfiles.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GithubRepositoryDTO {
	
	@NotNull
	@Size(min = 10)
	@ApiModelProperty(required = true, notes = "URL of a valid repository in Github (i.e. 'https://github.com/rfsilva/trustly-rep')")
	private String url;
}
