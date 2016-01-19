package com.fivestars.websites.onlinetest.entity;

public class SingleChoiceAnswerEntity {

	private Integer itemId;

	public SingleChoiceAnswerEntity(Integer itemId) {
		this.setItemId(itemId);
	}

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}
}
