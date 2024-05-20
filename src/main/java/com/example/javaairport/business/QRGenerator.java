package com.example.javaairport.business;

import com.google.zxing.WriterException;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.io.IOException;

public interface QRGenerator {
    BufferedImage generateQRCodeImage(String text, int width, int height) throws WriterException, IOException;
}
