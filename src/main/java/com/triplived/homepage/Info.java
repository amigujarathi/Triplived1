package com.triplived.homepage;

import com.google.gson.annotations.Expose;

/**
 * 
 * @author santosh
 *
 */
public class Info {

	public static enum InfoLocation {
		TOP_LEFT, TOP_RIGHT, 
		BOTTOM_1,
		BOTTOM_2
	};

	public static enum ImageSource {
		APP, WEB
	};
	
	/**
	 * Will resolves something like this (img : ic_location , Open, 13) 10am - 11pm
	 */
	private static String TEMPLATE = "(img : %s , %s, %s) %s";

	/**
	 * Where to place
	 */
	private InfoLocation location;
	/**
	 * image source 
	 */
	@Expose(serialize = false, deserialize = false)
	private ImageSource imageSource;
	
	/**
	 * icon name or web image path
	 */
	@Expose(serialize = false, deserialize = false)
	private String image = "";
	
	/**
	 * size in dp
	 */
	@Expose(serialize = false, deserialize = false)
	private int size;
	
	/**
	 * Alternate text 
	 */
	@Expose(serialize = false, deserialize = false)
	private String altText = "";
	/**
	 * text 
	 * @return
	 */
	@Expose(serialize = false, deserialize = false)
	private String text;
	
	/**
	 * final description
	 * @return
	 */
	private String finalDescription;
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public InfoLocation getLocation() {
		return location;
	}

	public void setLocation(InfoLocation location) {
		this.location = location;
	}

	public ImageSource getImageSource() {
		return imageSource;
	}

	public void setImageSource(ImageSource imageSource) {
		this.imageSource = imageSource;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
	
	public String getAltText() {
		return altText;
	}
	
	public void setAltText(String altText) {
		this.altText = altText;
	}
	
	public String getFinalDescription() {
		finalDescription =  String.format(TEMPLATE, getImage(), getAltText(), getSize(), getText());
		return finalDescription;
	}
	
	/*public void setFinalDescription(String finalDescription) {
		this.finalDescription = String.format(TEMPLATE, getImage(), getAltText(), getSize(), getText());
	}*/

	/**
	 * clock icon with time:        (img : ic_location , Open, 13) 10am - 11pm
	 * distance icon with distance. (img : ic_location , Distance, 12) 2km from here
	 **/
	public String toString() {
		
		//"(img : %s , %s, %s) %s"
		return String.format(TEMPLATE, getImage(), getAltText(), getSize(), getText());
	}

	public void evaluateFinalDescription() {
		this.finalDescription = String.format(TEMPLATE, getImage(), getAltText(), getSize(), getText());
	}
	
}
