package sem4.javaAirport.business.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;
import sem4.javaAirport.domain.BaggageStatusDTO;

import java.awt.image.BufferedImage;

public class QRCodeDecoder {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String decodeQRCodeFromImage(BufferedImage bufferedImage) {
        try {
            BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(bufferedImage)));
            Result result = new QRCodeReader().decode(binaryBitmap);
            return result.getText();
        } catch (ReaderException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static BaggageStatusDTO decodeQRCode(BufferedImage bufferedImage) throws Exception {
        LuminanceSource source = new BufferedImageLuminanceSource(bufferedImage);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
        Result result = new MultiFormatReader().decode(bitmap);
        return objectMapper.readValue(result.getText(), BaggageStatusDTO.class);
    }

}

