package sem4.javaAirport.business.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import sem4.javaAirport.domain.BaggageStatusDTO;

public class QRCodeDecoder {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static BaggageStatusDTO decodeQRCode(String qrData) throws Exception {
        return objectMapper.readValue(qrData, BaggageStatusDTO.class);
    }
}

