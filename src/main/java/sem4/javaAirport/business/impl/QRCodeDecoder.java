package sem4.javaAirport.business.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;
import sem4.javaAirport.domain.BaggageStatusDTO;

import java.awt.image.BufferedImage;

public class QRCodeDecoder {

    public static String decodeQRCodeFromImage(BufferedImage bufferedImage) throws Exception {
        LuminanceSource source = new BufferedImageLuminanceSource(bufferedImage);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
        Result result = new MultiFormatReader().decode(bitmap);
        return result.getText();
    }
// private static final ObjectMapper objectMapper = new ObjectMapper();
/*    public static BaggageStatusDTO decodeQRCode(BufferedImage bufferedImage) throws Exception {
        LuminanceSource source = new BufferedImageLuminanceSource(bufferedImage);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
        Result result = new MultiFormatReader().decode(bitmap);
        return objectMapper.readValue(result.getText(), BaggageStatusDTO.class);
    }*/
    /*public static BaggageStatusDTO decodeQRCode(BufferedImage bufferedImage) throws Exception {
        LuminanceSource source = new BufferedImageLuminanceSource(bufferedImage);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
        Result result = new MultiFormatReader().decode(bitmap);
        BaggageStatusDTO statusDTO = objectMapper.readValue(result.getText(), BaggageStatusDTO.class);
        System.out.println("Decoded QR Code: " + statusDTO);
        return statusDTO;
    }*/


}

