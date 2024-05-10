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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ImageServiceImpl implements ImageService {

	@Value("${file.upload-dir}")
	private String uploadDir;

	@Override
	public String loadImageBase64(String path) {
		createUploadDirIfNeeded();
		try {
			String fullPath = uploadDir + File.separator + path;
			File file = new File(fullPath);

			if (file.exists() && file.isFile()) {
				BufferedImage image = ImageIO.read(file);
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				ImageIO.write(image, "png", baos);
				byte[] imageBytes = baos.toByteArray();
				return Base64.getEncoder().encodeToString(imageBytes);
			} else {
				throw new IOException("Файл не існує або не є файлом: " + fullPath);
			}
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public String saveImageBase64(String base64Image, String oldPath,
	    DefaultImagePatch defaultImagePatch) {
		createUploadDirIfNeeded();
		if (oldPath != null && defaultImagePatch != null){
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

			return fileName;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	private void deleteImage(String imageUrl) {
		File file = new File(imageUrl);
		if (file.exists() && file.isFile()) {
			file.delete();
		}
	}

	private void createUploadDirIfNeeded() {
		Path directoryPath = Paths.get(uploadDir);
		if (!Files.exists(directoryPath)) {
			try {
				Files.createDirectories(directoryPath);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Getter
	@AllArgsConstructor
	public enum DefaultImagePatch {
		USER_AVATAR("base/user-avatar.png"),
		CLAN_AVATAR("base/clan-avatar.png");
		private final String path;
	}
}
