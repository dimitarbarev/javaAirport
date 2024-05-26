package sem4.javaAirport;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;
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
import org.opencv.core.Core;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.lang.reflect.Field;

@SpringBootApplication
public class MainApp extends Application {
    private static ConfigurableApplicationContext springContext;
    private QRController qrController;
    private VideoCapture capture;
    private ImageView imageView;

    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
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
        VBox root = new VBox();
        root.getChildren().add(imageView);

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("QR Code Scanner");
        primaryStage.show();

        startCamera();
    }

    private void startCamera() {
        capture = new VideoCapture(0);
        Thread thread = new Thread(() -> {
            Mat frame = new Mat();
            while (capture.read(frame)) {
                BufferedImage bufferedImage = matToBufferedImage(frame);
                Platform.runLater(() -> {
                    imageView.setImage(bufferedImageToImage(bufferedImage));
                    try {
                        BaggageStatusDTO statusUpdate = QRCodeDecoder.decodeQRCode(bufferedImage);
                        if (statusUpdate != null) {
                            qrController.scanQRCode(statusUpdate);
                            System.out.println("Baggage status updated successfully");
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
    }

    @Override
    public void stop() {
        capture.release();
        springContext.close();
    }
}