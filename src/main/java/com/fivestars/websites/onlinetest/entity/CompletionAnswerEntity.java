package com.fivestars.websites.onlinetest.entity;

import java.util.List;

public class CompletionAnswerEntity {

	private List<String> fills;

	public CompletionAnswerEntity(List<String> fills) {
		this.fills = fills;
	}

	public List<String> getFills() {
		return fills;
	}

	public void setFills(List<String> fills) {
		this.fills = fills;
	}
}
