package com.zoi4erom.strategygame.dto.search;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Range<T> {

	private T from;
	private T to;
}
