package sem4.javaAirport;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
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

@SpringBootApplication
public class MainApp extends Application {

    private static ConfigurableApplicationContext springContext;
    private QRController qrController;

    public static void main(String[] args) {
        Application.launch(MainApp.class, args);
    }

    @Override
    public void init() {
        springContext = SpringApplication.run(MainApp.class);
        qrController = springContext.getBean(QRController.class);
    }

    @Override
    public void start(Stage primaryStage) {
        TextField qrCodeInput = new TextField();
        qrCodeInput.setPromptText("Scan QR code here");
        qrCodeInput.setOnAction(event -> {
            String qrCodeData = qrCodeInput.getText();
            handleQRCodeScan(qrCodeData);
            qrCodeInput.clear();
        });

        Scene scene = new Scene(qrCodeInput, 400, 100);
        primaryStage.setScene(scene);
        primaryStage.setTitle("QR Code Scanner");
        primaryStage.show();
    }

    private void handleQRCodeScan(String qrCodeData) {
        try {
            BaggageStatusDTO statusUpdate = QRCodeDecoder.decodeQRCode(qrCodeData);
            qrController.scanQRCode(statusUpdate);
            System.out.println("Baggage status updated successfully");
        } catch (Exception e) {
            System.out.println("Error updating status: " + e.getMessage());
        }
    }

    @Override
    public void stop() {
        springContext.close();
    }
}
