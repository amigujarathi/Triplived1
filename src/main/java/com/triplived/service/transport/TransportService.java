package com.triplived.service.transport;

import com.domain.triplived.transport.dto.FinalTransportStatus;

public interface TransportService {

	String getTransport(String originCityId, String destCityId, String sourceEntityId, String destEntityId, String deviceId, 
			Long timeDiff, String tripId)
			throws Exception;

	FinalTransportStatus getFinalTransportDetail(String transportType);

}
