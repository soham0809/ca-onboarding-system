package com.iac.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

@Service
public class QRCodeService {

    public String generateQRCodeBase64(String url) {
        try {
            System.out.println("Generating QR code for URL: " + url);
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(url, BarcodeFormat.QR_CODE, 200, 200);
            System.out.println("QR code matrix generated successfully");

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);
            System.out.println("QR code written to stream successfully");

            String base64Image = Base64.getEncoder().encodeToString(outputStream.toByteArray());
            System.out.println("QR code converted to base64 successfully");
            System.out.println("Base64 length: " + (base64Image != null ? base64Image.length() : 0));
            return base64Image;

        } catch (WriterException | IOException e) {
            System.out.println("Error generating QR code: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}