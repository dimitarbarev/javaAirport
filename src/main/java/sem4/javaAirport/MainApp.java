package sem4.javaAirport;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import sem4.javaAirport.business.impl.BaggageService;
import sem4.javaAirport.business.impl.QRCodeDecoder;
import sem4.javaAirport.business.impl.QRGeneratorImplementation;
import sem4.javaAirport.configuration.AppConfig;
import sem4.javaAirport.controllers.QRController;
import sem4.javaAirport.domain.BaggageStatusDTO;

import sem4.javaAirport.persistence.entity.BaggageStatus;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

@SpringBootApplication
public class MainApp extends Application {
    private static ConfigurableApplicationContext springContext;
    private QRController qrController;
    //private VideoCapture capture;
    private ImageView imageView;
    private TextField scannerInput;
    private TextArea baggageInfoArea;
    private Button approveButton;
    private Button denyButton;
    private Button manualCheckButton;
    private BaggageStatusDTO currentStatusUpdate;

    public static void main(String[] args) {
       /* System.loadLibrary(Core.NATIVE_LIBRARY_NAME);*/
        Application.launch(MainApp.class, args);
    }

    @Override
    public void init() {
        springContext = SpringApplication.run(MainApp.class);
        qrController = springContext.getBean(QRController.class);
    }

    @Override
    public void start(Stage primaryStage) {
        imageView = new ImageView();
        scannerInput = new TextField();
        scannerInput.setPromptText("Scan QR code here");
        scannerInput.setOnAction(event -> {
            String qrCodeData = scannerInput.getText();
            System.out.println("Scanned QR Code Data: " + qrCodeData); // Added logging
            handleQRCodeScan(qrCodeData);
            scannerInput.clear();
        });

        baggageInfoArea = new TextArea();
        baggageInfoArea.setEditable(false);
        baggageInfoArea.setPromptText("Baggage information will be displayed here...");

        approveButton = new Button("Approve");
        approveButton.setOnAction(event -> handleApprove());

        denyButton = new Button("Deny");
        denyButton.setOnAction(event -> handleDeny());

        manualCheckButton = new Button("Manual Check");
        manualCheckButton.setOnAction(event -> handleManualCheck());
        manualCheckButton.setDisable(true);

        /*approveManualCheckButton = new Button("Approve Manual Check");
        approveManualCheckButton.setOnAction(event -> handleApproveManualCheck());
        approveManualCheckButton.setDisable(true);*/

        HBox buttonBox = new HBox(approveButton, denyButton, manualCheckButton /*,approveManualCheckButton*/);
        VBox root = new VBox(imageView, scannerInput, baggageInfoArea, buttonBox);
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("QR Code Scanner");
        primaryStage.show();

        //startCamera();
    }

    private void handleQRCodeScan(String qrCodeData) {
        try {
            // Log the raw scanned QR code data
            System.out.println("Scanned QR Code Data: " + qrCodeData);

            // Parse the custom formatted data
            String[] parts = qrCodeData.split(",");
            System.out.println("Parsed QR Code Data: " + parts.length + " parts");

            if (parts.length != 2) {
                throw new IllegalArgumentException("Invalid QR code format");
            }

            Long baggageId = Long.parseLong(parts[0]);
            String newStatus = parts[1];

            // Create BaggageStatusDTO from parsed data
            currentStatusUpdate = new BaggageStatusDTO();
            currentStatusUpdate.setBaggageId(baggageId);
            currentStatusUpdate.setNewStatus(BaggageStatus.valueOf(newStatus)); // Ensure BaggageStatus enum is used

            // Fetch baggage information and display it
            String baggageInfo = qrController.getBaggageInfo(baggageId); // Assuming a method to fetch baggage info
            baggageInfoArea.setText(baggageInfo);

            // Check if the baggage is suspicious
            boolean isSuspicious = qrController.isBaggageSuspicious(baggageId).getBody();
            if (isSuspicious) {
                baggageInfoArea.appendText("\nBaggage flagged as suspicious!");
                manualCheckButton.setDisable(false);
            } else {
                manualCheckButton.setDisable(true);
            }

            // Enable buttons
            approveButton.setDisable(false);
            denyButton.setDisable(false);

      /*      // Check if the baggage is in MANUAL_CHECK status
            if (BaggageStatus.MANUAL_CHECK.equals(currentStatusUpdate.getNewStatus())) {
                approveManualCheckButton.setDisable(false);
            } else {
                approveManualCheckButton.setDisable(true);
            }*/
        } catch (Exception e) {
            System.out.println("Error updating status: " + e.getMessage());
        }
    }

    private void handleApprove() {
        if (currentStatusUpdate != null) {
            try {
                qrController.scanQRCode(currentStatusUpdate);
                System.out.println("Baggage status updated successfully.");
                baggageInfoArea.appendText("\nStatus approved and updated successfully.");
            } catch (Exception e) {
                System.out.println("Error approving status: " + e.getMessage());
                baggageInfoArea.appendText("\nError approving status: " + e.getMessage());
            }
        }
        clearCurrentStatusUpdate();
    }

    private void handleDeny() {
        if (currentStatusUpdate != null) {
            System.out.println("Status change denied for baggage ID: " + currentStatusUpdate.getBaggageId());
            baggageInfoArea.appendText("\nStatus change denied.");
        }
        clearCurrentStatusUpdate();
    }

    private void handleManualCheck() {
        if (currentStatusUpdate != null) {
            try {
                qrController.triggerManualCheck(currentStatusUpdate.getBaggageId());
                System.out.println("Manual check triggered successfully.");
                baggageInfoArea.appendText("\nManual check triggered successfully.");
            } catch (Exception e) {
                System.out.println("Error triggering manual check: " + e.getMessage());
                baggageInfoArea.appendText("\nError triggering manual check: " + e.getMessage());
            }
        }
        clearCurrentStatusUpdate();
    }

    private void handleApproveManualCheck() {
        if (currentStatusUpdate != null) {
            try {
                qrController.approveManualCheck(currentStatusUpdate.getBaggageId());
                System.out.println("Baggage status updated to LOADED after manual check.");
                baggageInfoArea.appendText("\nBaggage status updated to LOADED after manual check.");
            } catch (Exception e) {
                System.out.println("Error approving manual check: " + e.getMessage());
                baggageInfoArea.appendText("\nError approving manual check: " + e.getMessage());
            }
        }
        clearCurrentStatusUpdate();
    }

    private void clearCurrentStatusUpdate() {
        currentStatusUpdate = null;
        approveButton.setDisable(true);
        denyButton.setDisable(true);
        manualCheckButton.setDisable(true);
        //approveManualCheckButton.setDisable(true);
    }

   /* private void startCamera() {
        capture = new VideoCapture(0);
        Thread thread = new Thread(() -> {
            Mat frame = new Mat();
            while (capture.read(frame)) {
                BufferedImage bufferedImage = matToBufferedImage(frame);
                Platform.runLater(() -> {
                    imageView.setImage(bufferedImageToImage(bufferedImage));
                    try {
                        String decodedQR = QRCodeDecoder.decodeQRCodeFromImage(bufferedImage);
                        if (decodedQR != null) {
                            System.out.println("Decoded QR Code: " + decodedQR); // Added logging
                            handleQRCodeScan(decodedQR);
                        }
                    } catch (com.google.zxing.NotFoundException e) {
                        System.out.println("QR code not found: " + e.getMessage());
                    } catch (Exception e) {
                        System.out.println("Error updating status: " + e.getMessage());
                    }
                });
                try {
                    Thread.sleep(30);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.setDaemon(true);
        thread.start();
    }

    private BufferedImage matToBufferedImage(Mat original) {
        int width = original.width();
        int height = original.height();
        int channels = original.channels();
        byte[] sourcePixels = new byte[width * height * channels];
        original.get(0, 0, sourcePixels);
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        final byte[] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        System.arraycopy(sourcePixels, 0, targetPixels, 0, sourcePixels.length);
        return image;
    }

    private Image bufferedImageToImage(BufferedImage bufferedImage) {
        return SwingFXUtils.toFXImage(bufferedImage, null);
    }*/

    @Override
    public void stop() {
        //capture.release();
        springContext.close();
    }
}