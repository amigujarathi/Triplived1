package com.triplived.homepage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.triplived.homepage.Info.ImageSource;
import com.triplived.homepage.Info.InfoLocation;

public class Main {

	public static void main(String[] args) throws Exception {

		/**
		 * Tips Layout
		 */
		
		Layout layoutTrips = new Layout();
		layoutTrips.setTitle("Tips");
		layoutTrips.setLayoutType(LayoutType.FULL_CAROUSEL);
		Card tipsCard = getTipsCard();
		Card tipsCard1 = getTipsCard1();
		layoutTrips.addCard(tipsCard);
		layoutTrips.addCard(tipsCard1);
		
		/**
		 * Attractions Layout
		 */
		Layout layoutAttraction = new Layout();
		layoutAttraction.setTitle("Near By Attraction");
		layoutAttraction.setLayoutType(LayoutType.FULL);
		Card attractionCard = getAttractionCard();
		layoutAttraction.addCard(attractionCard);

		/**
		 * Layout Restaurant
		 */
		Layout layoutRestaurants = new Layout();
		layoutRestaurants.setTitle("Restaurants");
		layoutRestaurants.setLayoutType(LayoutType.HALF_SCROLL);
		Card restaurantCard1 = getRestaurantCard1();
		Card restaurantCard2 = getRestaurantCard2();
		layoutRestaurants.addCard(restaurantCard1);
		layoutRestaurants.addCard(restaurantCard2);

		List<Layout> layouts = new ArrayList<>();
		layouts.add(layoutTrips);
		layouts.add(layoutAttraction);
		layouts.add(layoutRestaurants);

		 final GsonBuilder builder = new GsonBuilder();
		 builder.addSerializationExclusionStrategy(new ExclusionStrategy() {
			
			@Override
			public boolean shouldSkipField(FieldAttributes f) {
				// TODO Auto-generated method stub
				
				Expose anotation = f.getAnnotation(Expose.class);
				if(anotation != null && !anotation.deserialize()){
					return true;
				}
				return f.getAnnotation(Expose.class) != null;
			}
			
			@Override
			public boolean shouldSkipClass(Class<?> clazz) {
				return false;
			}
		});
		    
		System.out.println(builder.create().toJson(layouts));
	}

	/*
	 * private static Card cityGuide() throws IOException {
	 * 
	 * }
	 */

	private static Card getRestaurantCard1() throws IOException {

		Card restaurantCard = new Card();
		restaurantCard.setTitle("Dominos");
		restaurantCard.setImage(
				"https://lh6.googleusercontent.com/-CzY5p4g2klg/AAAAAAAAAAI/AAAAAAAAA58/shbGSVw6mvo/s0-c-k-no-ns/photo.jpg");
		restaurantCard.setId("dominos");
		restaurantCard.setType("Restaurant");

		Info restaurantInfo = new Info();
		restaurantInfo.setLocation(InfoLocation.BOTTOM_1);
		restaurantInfo.setImageSource(ImageSource.WEB);
		restaurantInfo.setAltText("Distance");
		restaurantInfo.setSize(20);
		restaurantInfo.setText("200m");
		restaurantInfo.setImage(Base64.encodeBase64String(FileUtils.readFileToByteArray(
				new File("C:/triplived/workspace/triplived-ui/static/triplived-web/img/monument.png"))));

		Info restaurantRating = new Info();
		restaurantRating.setLocation(InfoLocation.TOP_RIGHT);
		restaurantRating.setAltText("");
		restaurantRating.setSize(20);
		restaurantRating.setText("4.0");
		restaurantRating.evaluateFinalDescription();

		restaurantCard.addInfo(restaurantRating);
		restaurantCard.addInfo(restaurantInfo);
		return restaurantCard;
	}

	private static Card getRestaurantCard2() throws IOException {

		Card restaurantCard = new Card();
		restaurantCard.setTitle("Dominos");
		restaurantCard.setImage(
				"https://lh3.googleusercontent.com/-GGHvgo2I0FU/AAAAAAAAAAI/AAAAAAAAAO4/u4njwnhi0Rs/s0-c-k-no-ns/photo.jpg");
		restaurantCard.setId("dominos");
		restaurantCard.setType("Restaurant");

		Info restaurantInfo = new Info();
		restaurantInfo.setLocation(InfoLocation.BOTTOM_1);
		restaurantInfo.setImageSource(ImageSource.WEB);
		restaurantInfo.setAltText("Distance");
		restaurantInfo.setSize(20);
		restaurantInfo.setText("300m");
		restaurantInfo.setImage("base64:"+Base64.encodeBase64String(FileUtils.readFileToByteArray(
				new File("C:/triplived/workspace/triplived-ui/static/triplived-web/img/monument.png"))));
		restaurantInfo.evaluateFinalDescription();

		Info restaurantRating = new Info();
		restaurantRating.setLocation(InfoLocation.TOP_RIGHT);
		restaurantRating.setAltText("");
		restaurantRating.setSize(20);
		restaurantRating.setText("3.4");
		restaurantRating.evaluateFinalDescription();

		restaurantCard.addInfo(restaurantRating);
		restaurantCard.addInfo(restaurantInfo);
		return restaurantCard;
	}

	private static Card getAttractionCard() throws IOException {
		Card attractionCard = new Card();
		attractionCard.setTitle("TajMahal");
		attractionCard.setImage("https://affordabroad.files.wordpress.com/2014/03/20140311-india-budget-travel.jpg");
		attractionCard.setId("tajmahal");
		attractionCard.setType("Attraction");

		Info attractionInfo = new Info();
		attractionInfo.setLocation(InfoLocation.BOTTOM_1);
		attractionInfo.setImageSource(ImageSource.WEB);
		attractionInfo.setAltText("Distance");
		attractionInfo.setSize(20);
		attractionInfo.setText("2km from here");
		attractionInfo.setImage("base64:"+ Base64.encodeBase64String(FileUtils.readFileToByteArray(
				new File("C:/triplived/workspace/triplived-ui/static/triplived-web/img/monument.png"))));
		attractionInfo.evaluateFinalDescription();

		Info attractionRating = new Info();
		attractionRating.setLocation(InfoLocation.TOP_RIGHT);
		attractionRating.setAltText("");
		attractionRating.setSize(20);
		attractionRating.setText("4.0");
		attractionRating.evaluateFinalDescription();

		attractionCard.addInfo(attractionRating);
		attractionCard.addInfo(attractionInfo);
		return attractionCard;
	}

	private static Card getTipsCard() throws IOException {

		Card tipCard = new Card();
		tipCard.setTitle("");
		tipCard.setImage("https://affordabroad.files.wordpress.com/2014/03/20140311-india-budget-travel.jpg");
		tipCard.setId("tajmahal");
		tipCard.setType("Tip");

		Info info = new Info();
		info.setLocation(InfoLocation.BOTTOM_1);
		info.setImageSource(ImageSource.WEB);
		info.setImage("web:http://triplived.com/triplived/user/picture/48?&size=normal");
		info.setAltText("Saurabh");
		info.setSize(20);
		info.setText("\"Do visit Agra Fort, 20 minutes from here\"");

		info.evaluateFinalDescription();
		
		tipCard.addInfo(info);
		return tipCard;
	}
	
	private static Card getTipsCard1() throws IOException {

		Card tipCard = new Card();
		tipCard.setTitle("");
		tipCard.setImage("http://www.triplived.com/static/timeline/trip/440132331890/1455980626464-440132331890.jpg");
		tipCard.setId("tajmahal");
		tipCard.setType("Tip");

		Info info = new Info();
		info.setLocation(InfoLocation.BOTTOM_1);
		info.setImageSource(ImageSource.WEB);
		info.setImage("http://triplived.com/triplived/user/picture/23?&size=normal");
		info.setAltText("Santosh");
		info.setSize(20);
		info.setText("\"5 Indian Restaurant Nearby\"");
		info.evaluateFinalDescription();

		tipCard.addInfo(info);
		return tipCard;
	}
}
