package com.fivestars.websites.onlinetest.entity;

import java.util.Set;

public class MultipleChoiceAnswerEntity {

	private Set<Integer> itemIds;

	public MultipleChoiceAnswerEntity(Set<Integer> itemIds) {
		this.itemIds = itemIds;
	}

	public Set<Integer> getItemIds() {
		return itemIds;
	}

	public void setItemIds(Set<Integer> itemIds) {
		this.itemIds = itemIds;
	}
}
