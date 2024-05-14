package com.zoi4erom.strategygame.service.impl;

import com.zoi4erom.strategygame.service.contract.ImageService;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.UUID;
import javax.imageio.ImageIO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Implementation of the interface responsible for loading and saving images. This implementation
 * supports loading images from the file system, converting them to Base64 format, saving
 * Base64-encoded images to the file system, and deleting images from the file system.
 */
@Component
@Slf4j
public class ImageServiceImpl implements ImageService {

	@Value("${file.upload-dir}")
	private String uploadDir; //The directory path where images will be uploaded.

	/**
	 * Loads an image from the specified path on the file system and returns it as a
	 * Base64-encoded string.
	 *
	 * @param path The path of the image file to load
	 * @return The Base64-encoded string representation of the image
	 */
	@Override
	public String loadImageBase64(String path) {
		createUploadDirIfNeeded();
		try {
			String fullPath = uploadDir + File.separator + path;
			File file = new File(fullPath);

			if (file.exists() && file.isFile()) {
				log.info("Loading image from path: '{}'", fullPath);
				BufferedImage image = ImageIO.read(file);
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				ImageIO.write(image, "png", baos);
				byte[] imageBytes = baos.toByteArray();
				return Base64.getEncoder().encodeToString(imageBytes);
			} else {
				throw new IOException("Файл не існує або не є файлом: " + fullPath);
			}
		} catch (IOException e) {
			log.error("Error loading image");
			return null;
		}
	}

	/**
	 * Saves a Base64-encoded image to the file system and returns the path of the saved image.
	 *
	 * @param base64Image       The Base64-encoded image string to save
	 * @param oldPath           The path of the old image (if applicable)
	 * @param defaultImagePatch The default image patch (if applicable)
	 * @return The path of the saved image
	 */
	@Override
	public String saveImageBase64(String base64Image, String oldPath,
	    DefaultImagePatch defaultImagePatch) {
		createUploadDirIfNeeded();
		if (oldPath != null && defaultImagePatch != null) {
			if (!oldPath.equals(defaultImagePatch.getPath())) {
				var imageUrl = uploadDir + File.separator + oldPath;
				deleteImage(imageUrl);
			}
		}
		try {
			byte[] imageBytes = Base64.getDecoder().decode(base64Image);
			String fileName = UUID.randomUUID() + "_name.png";

			String fullPath = uploadDir + File.separator + fileName;
			File outputFile = new File(fullPath);

			ByteArrayInputStream bais = new ByteArrayInputStream(imageBytes);
			BufferedImage image = ImageIO.read(bais);
			ImageIO.write(image, "png", outputFile);

			log.info("Image saved to path: '{}'", fullPath);
			return fileName;
		} catch (IOException e) {
			log.error("Error saving image");
			return null;
		}
	}

	/**
	 * Deletes an image from the file system.
	 *
	 * @param imageUrl The URL of the image to delete
	 */
	private void deleteImage(String imageUrl) {
		File file = new File(imageUrl);
		if (file.exists() && file.isFile()) {
			file.delete();
			log.info("Image deleted from path: '{}'", imageUrl);
		}
	}

	/**
	 * Creates the upload directory if it does not exist.
	 */
	private void createUploadDirIfNeeded() {
		Path directoryPath = Paths.get(uploadDir);
		if (!Files.exists(directoryPath)) {
			try {
				Files.createDirectories(directoryPath);
				log.info("Upload directory created at path: '{}'", uploadDir);
			} catch (IOException e) {
				e.printStackTrace();
				log.error("Error creating upload directory", e);
			}
		}
	}

	/**
	 * Enumeration of default image paths.
	 */
	@Getter
	@AllArgsConstructor
	public enum DefaultImagePatch {
		USER_AVATAR("base/user-avatar.png"),
		CLAN_AVATAR("base/clan-avatar.png"),
		ARTICLE_AVATAR("");
		private final String path;
	}
}
