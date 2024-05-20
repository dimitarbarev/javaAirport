package com.example.javaairport.controllers;

import com.example.javaairport.business.QRGenerator;
import com.google.zxing.WriterException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

@RestController
public class QRController {
    private final QRGenerator qrGenerator;

    @Autowired
    public QRController(QRGenerator qrGenerator) {
        this.qrGenerator = qrGenerator;
    }

    @GetMapping("/generateQRCode")
    public void generateQRCode(@RequestParam("text") String text, HttpServletResponse response) throws WriterException, IOException {
        BufferedImage qrCodeImage = qrGenerator.generateQRCodeImage(text, 350, 350);
        response.setContentType("image/png");
        ImageIO.write(qrCodeImage, "PNG", response.getOutputStream());
    }}
