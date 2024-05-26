package sem4.javaAirport.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sem4.javaAirport.business.IBaggageService;
import sem4.javaAirport.business.IQRGenerator;
import com.google.zxing.WriterException;
import jakarta.servlet.http.HttpServletResponse;
import sem4.javaAirport.business.impl.QRCodeDecoder;
import sem4.javaAirport.domain.BaggageStatusDTO;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class QRController {
    private final IQRGenerator qrGenerator;
    private final IBaggageService baggageService;

    @GetMapping("/generateQRCode")
    public void generateQRCode(@RequestParam("text") String text, HttpServletResponse response) throws WriterException, IOException {
        BufferedImage qrCodeImage = qrGenerator.generateQRCodeImage(text, 350, 350);
        response.setContentType("image/png");
        ImageIO.write(qrCodeImage, "PNG", response.getOutputStream());
    }

    @PostMapping("/scanQRCode")
    public ResponseEntity<String> scanQRCodeEndpoint(@RequestBody BaggageStatusDTO statusUpdate) {
        try {
            System.out.println("Received status update request: " + statusUpdate);
            baggageService.updateBaggageStatus(statusUpdate.getBaggageId(), statusUpdate.getNewStatus());
            System.out.println("Baggage status updated successfully.");
            return ResponseEntity.ok("Baggage status updated successfully");
        } catch (Exception e) {
            System.out.println("Error updating status: " + e.getMessage());
            return ResponseEntity.badRequest().body("Error updating status: " + e.getMessage());
        }
    }

    public void scanQRCode(BaggageStatusDTO statusUpdate) {
        try {
            System.out.println("Processing status update: " + statusUpdate);
            baggageService.updateBaggageStatus(statusUpdate.getBaggageId(), statusUpdate.getNewStatus());
            System.out.println("Baggage status updated successfully.");
        } catch (Exception e) {
            System.out.println("Error updating status: " + e.getMessage());
        }
    }
    @PostMapping("/moveToNextStatus")
    public ResponseEntity<String> moveToNextStatus(@RequestParam Long baggageId) {
        try {
            baggageService.moveToNextStatus(baggageId);
            return ResponseEntity.ok("Baggage status moved to next successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error moving to next status: " + e.getMessage());
        }
    }

    @PostMapping("/triggerManualCheck")
    public ResponseEntity<String> triggerManualCheck(@RequestParam Long baggageId) {
        try {
            baggageService.triggerManualCheck(baggageId);
            return ResponseEntity.ok("Baggage status updated to MANUAL_CHECK");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error updating status: " + e.getMessage());
        }
    }

}


