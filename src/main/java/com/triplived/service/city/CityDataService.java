package com.triplived.service.city;

public interface CityDataService {

	void updateCityDescription(String userName,String cityCode, String cityName,
			String cityDescrption);

	void updateCityImagePath(String cityId, String path, String updatedBy);

}
