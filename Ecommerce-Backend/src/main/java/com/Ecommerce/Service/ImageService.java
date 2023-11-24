package com.Ecommerce.Service;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;


import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.util.StringUtils;


import java.io.IOException;
import java.net.MalformedURLException;


@Service
public class ImageService {

    private static final String UPLOAD_DIR = "src/main/resources/static/images";

    public String saveImage(MultipartFile file) throws IOException {
        // Normalize the file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        // Set the path where the file will be stored
        Path uploadPath = Paths.get(UPLOAD_DIR);

        // Create the upload directory and parent directories if they don't exist
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Resolve the complete file path
        Path filePath = uploadPath.resolve(fileName);

        // Save the file to the specified directory
        Files.copy(file.getInputStream(), filePath);

        // You can return the file path or other information if needed
        return fileName;
    }

  public void deleteImage(String name) {
	  try {
	      String imagePath = "src/main/resources/static/images/" + name;

	        try {
	            Path pathToDelete = Paths.get(imagePath);
	            Files.deleteIfExists(pathToDelete);
	            System.out.println("Image deleted successfully.");
	        } catch (NoSuchFileException e) {
	            System.err.println("The image does not exist: " + e.getMessage());
	        } catch (DirectoryNotEmptyException e) {
	            System.err.println("The specified path is a directory: " + e.getMessage());
	        } catch (IOException e) {
	            System.err.println("An I/O error occurred: " + e.getMessage());
	        }
	    }catch(Exception e){
		  e.printStackTrace();
	  }
  }

}



