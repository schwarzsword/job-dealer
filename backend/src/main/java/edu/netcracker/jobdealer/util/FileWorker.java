package edu.netcracker.jobdealer.util;

import org.postgresql.shaded.com.ongres.scram.common.bouncycastle.base64.Base64;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class FileWorker {
    public static String extractBytes(String imageName) throws IOException {
        File imgPath = new File(imageName);
        BufferedImage bufferedImage = ImageIO.read(imgPath);
        WritableRaster raster = bufferedImage.getRaster();
        DataBufferByte data = (DataBufferByte) raster.getDataBuffer();
       return new String(Base64.encode(data.getData()), StandardCharsets.UTF_8);
    }
}
