package com.canknow.cbp.utils;

import lombok.experimental.UtilityClass;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@UtilityClass
public class ImageUtils {

    public static byte[] imageToBytes(BufferedImage bImage) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ImageIO.write(bImage, "png", out);
        return out.toByteArray();
    }
}
