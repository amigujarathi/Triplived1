package com.triplived.dao.experience;

import java.util.List;

import com.triplived.entity.WebExperiencesDb;
import com.triplived.entity.WebExperiencesMediaDb;

public interface ExperienceDAO {

	void updateWebExperience(WebExperiencesDb obj);

	WebExperiencesDb getWebExperienceById(String id);

	List<WebExperiencesDb> getWebExperiencesByTripId(Long id);

	void updateWebExperienceMedia(WebExperiencesMediaDb obj);

	WebExperiencesMediaDb getWebExperiencesMediaById(String id);
}
