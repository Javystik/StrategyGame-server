package com.zoi4erom.strategygame.service.contract;

import com.zoi4erom.strategygame.service.impl.ImageServiceImpl.DefaultImagePatch;

public interface ImageService {
	String loadImageBase64(String path);
	String saveImageBase64(String base64Image, String oldPath, DefaultImagePatch defaultImagePatch);
}
