package com.connectme.domain.triplived;

public enum AppPage {
	
 	SPLASH_SCREEN ("splash_screen"),
    HOME_PAGE     ("home_page"),
    EXPLORE_PAGE  ("explore_page"),
    SEARCH_PAGE   ("search_page"),
    VIEW_PAGE     ("entity_view_page"),
    TIMELINE_PAGE ("timeline_page"),
    PROFILE_PAGE  ("profile_page"),
    FEEDBACK_PAGE ("feedback_page"),
    TERM_SERVICE  ("service_page"),
    IMAGE_GALLERY ("gallery_page"),
    RECORDING_START("recording_start_page"),
    NOTIFICATION  ("notification"),
    UNKNOWN       ("unknown_page");


    private String value;

    AppPage(String value) {
        this.value = value;
    }
}

