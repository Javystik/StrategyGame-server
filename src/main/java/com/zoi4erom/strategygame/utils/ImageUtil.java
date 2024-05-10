package com.zoi4erom.strategygame.utils;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImageUtil {

	public static byte[] bufferedImageToBytes(BufferedImage image, String format)
	    throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(image, format, baos);
		return baos.toByteArray();
	}

	public static BufferedImage bytesToBufferedImage(byte[] bytes) throws IOException {
		ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
		return ImageIO.read(bais);
	}
}
