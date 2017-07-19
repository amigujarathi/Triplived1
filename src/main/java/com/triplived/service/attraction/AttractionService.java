package com.triplived.service.attraction;

import com.connectme.domain.triplived.dto.AttractionDataUploadDTO;
import com.connectme.domain.triplived.dto.NewAttractionDataAddDTO;

public interface AttractionService {
	
	public void addAttractionImage(AttractionDataUploadDTO obj);

	public int inActivateImage(String name, String attractionId);
	
	public int inActivateAttraction(String attractionId, String userName);
	
	public void addNewAttraction(NewAttractionDataAddDTO Obj);

}
