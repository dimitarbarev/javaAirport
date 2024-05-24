package sem4.javaAirport.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sem4.javaAirport.business.IBaggageService;
import sem4.javaAirport.business.IQRGenerator;
import com.google.zxing.WriterException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import sem4.javaAirport.business.impl.QRCodeDecoder;
import sem4.javaAirport.domain.BaggageStatusDTO;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class QRController {
    private final IQRGenerator qrGenerator;
    @Autowired
    private final IBaggageService baggageService;

    @GetMapping("/generateQRCode")
    public void generateQRCode(@RequestParam("text") String text, HttpServletResponse response) throws WriterException, IOException {
        BufferedImage qrCodeImage = qrGenerator.generateQRCodeImage(text, 350, 350);
        response.setContentType("image/png");
        ImageIO.write(qrCodeImage, "PNG", response.getOutputStream());
    }

    @PostMapping("/scanQRCode")
    public ResponseEntity<String> scanQRCode(@RequestBody String qrCodeData) {
        try {
            BaggageStatusDTO statusUpdate = QRCodeDecoder.decodeQRCode(qrCodeData);
            baggageService.updateBaggageStatus(statusUpdate.getBaggageId(), statusUpdate.getNewStatus());
            return ResponseEntity.ok("Baggage status updated successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error updating status: " + e.getMessage());
        }
    }
}


