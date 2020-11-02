package com.bbl.interview.api.models;

import java.util.List;

public class ValueAPI extends RespondResult{
    private List<Integer> value;

	public List<Integer> getValue() {
		return value;
	}

	public void setValue(List<Integer> value) {
		this.value = value;
	}

}
