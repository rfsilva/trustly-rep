package br.com.rodrigo.githubfiles.service.impl.obj;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class FileSum {

	private Long bytes;
	private Integer lines;
	
	public FileSum(Long bytes, Integer lines) {
		this.bytes = bytes;
		this.lines = lines;
	}
}
