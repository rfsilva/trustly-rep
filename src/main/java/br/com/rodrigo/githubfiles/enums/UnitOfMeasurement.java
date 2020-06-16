package br.com.rodrigo.githubfiles.enums;

import java.util.Arrays;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum UnitOfMeasurement {
	BYTE("Byte", 1L), BYTES("Bytes", 1L), KB("KB", 1024L), MB("MB", 1024L * 1024L);

	private String value;
	private Long multiplier;
	
	public static UnitOfMeasurement of(String value) {
		return Arrays.stream(UnitOfMeasurement.values())
				.filter(tc -> value.equals(tc.getValue()))
				.findFirst()
				.orElseThrow(IllegalArgumentException :: new);
	}
}
