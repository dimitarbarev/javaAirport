package sem4.javaAirport.business;

import com.google.zxing.WriterException;

import java.awt.image.BufferedImage;
import java.io.IOException;

public interface IQRGenerator {
    BufferedImage generateQRCodeImage(String text, int width, int height) throws WriterException, IOException;
}
