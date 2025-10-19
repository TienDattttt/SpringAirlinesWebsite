package com.example.SwipeFlight.server.utils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileUploadService {

	public String fileUpload(MultipartFile file, String targetEntityName, BindingResult result) {
		String UPLOAD_FOLDER = "src/main/resources/static/",
				UPLOAD_SUB_FOLDER = "", fullPathStr = "", pathStr = "";

		if (targetEntityName == "City")
			UPLOAD_SUB_FOLDER = "/images/cities/";

		if (!file.isEmpty()) {

			String pattern = ".(jpg|jpeg|png|gif|JPG|JPEG|PNG|GIF)$";
			String extention = getFileExtension(file);

			if (!Pattern.matches(pattern, extention)) {
				result.rejectValue("imgUrl", "error.city",
						"The selected file type is not supported. Please upload a valid image file (JPG, JPEG, PNG, GIF).");
			}

			else {
				try {

					byte[] bytes = file.getBytes();

					pathStr = UPLOAD_SUB_FOLDER + file.getOriginalFilename();
					fullPathStr = UPLOAD_FOLDER + pathStr;
					Path path = Paths.get(fullPathStr);
					Files.write(path, bytes);

				} catch (Exception e) {
					result.rejectValue("imgUrl", "error.city", "File upload failed. Please try again.");
					pathStr = null;
				}
			}
		}
		return pathStr;
	}

	private String getFileExtension(MultipartFile file) {
		String name = file.getOriginalFilename();
		int lastIndexOf = name.lastIndexOf(".");
		if (lastIndexOf == -1) {
			return "";
		}
		return name.substring(lastIndexOf);
	}

}
