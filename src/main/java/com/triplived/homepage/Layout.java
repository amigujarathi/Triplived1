package com.triplived.homepage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author santosh
 *
 */
public class Layout {

	/**
	 * Title
	 */
	private String title;
	
	/**
	 * layoutType
	 */
	private LayoutType layoutType;
	
	/**
	 * 
	 */
	private List<Card> cards = new ArrayList<Card>();
	
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();
	
	public List<Card> getCards() {
		return cards;
	}
	
	public void setCards(List<Card> cards) {
		this.cards = cards;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public LayoutType getLayoutType() {
		return layoutType;
	}
	
	public void setLayoutType(LayoutType layoutType) {
		this.layoutType = layoutType;
	}
	
	public Map<String, Object> getAdditionalProperties() {
		return additionalProperties;
	}
	public void setAdditionalProperties(Map<String, Object> additionalProperties) {
		this.additionalProperties = additionalProperties;
	}

	public void addCard(Card attractionCard) {
		this.cards.add(attractionCard);
	}

	@Override
	public String toString() {
		return "Layout [title=" + title + ", layoutType=" + layoutType + ", cards=" + cards + ", additionalProperties="
				+ additionalProperties + "]";
	}
	
	
}
