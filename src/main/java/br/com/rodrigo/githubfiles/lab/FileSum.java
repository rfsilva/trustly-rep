package br.com.rodrigo.githubfiles.lab;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class FileSum {

	private Long bytes;
	private Integer lines;
	
	public FileSum() {
		bytes = 0L;
		lines = 0;
	}
	
	public FileSum(Long bytes, Integer lines) {
		this.bytes = bytes;
		this.lines = lines;
	}
}
