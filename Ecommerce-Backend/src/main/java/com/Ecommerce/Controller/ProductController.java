package com.Ecommerce.Controller;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.Ecommerce.Entity.ProductEntity;
import com.Ecommerce.Repository.Product;
import com.Ecommerce.Service.ImageService;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;



@RestController
@RequestMapping("api")
@CrossOrigin(origins = "http://localhost:3000")
public class ProductController {
	@Autowired
	private  Product repository;
	
	@Autowired 
	private ImageService imageservice;
	
	@GetMapping("/pagination")
	public Page<ProductEntity> getData(Pageable pageable) {
		return  repository.findAll(pageable);
	}
	
	
	
       @GetMapping("/product")
public List<ProductEntity> getAllPhones() {	  
    	   System.out.println("hi");
       return repository.findAll();
    }
	
    

	@GetMapping("/{imageName}")
	public ResponseEntity<byte[]> getImage(@PathVariable String imageName) throws IOException {
	    // Load image from the classpath
	    Resource resource = new ClassPathResource("static/images/" + imageName);

	    // Check if the image exists
	    if (!resource.exists()) {
	        return ResponseEntity.notFound().build();
	    }

	    // Determine content type based on file extension
	    MediaType mediaType;
	    if (imageName.toLowerCase().endsWith(".png")) {
	        mediaType = MediaType.IMAGE_PNG;
	    } else if (imageName.toLowerCase().endsWith(".jpg") || imageName.toLowerCase().endsWith(".jpeg")) {
	        mediaType = MediaType.IMAGE_JPEG;
	    } else {
	        // Unsupported image format
	        return ResponseEntity.status(415).build();
	    }

	    // Convert the image to a byte array
	    byte[] imageBytes = resource.getInputStream().readAllBytes();

	    // Create a ResponseEntity with the image bytes and appropriate content type
	    return ResponseEntity.ok().contentType(mediaType).body(imageBytes);
	}

	
	
	
	@PostMapping("/saveProduct")
  public ResponseEntity<String> saveData(            @RequestPart("product") ProductEntity product,
          @RequestPart(value = "images", required = false) MultipartFile images) throws IOException{
	  System.out.println(images);
		 if (images != null && !images.isEmpty()) {
			 try {
				String name=imageservice.saveImage(images);
				product.setImages(name);
			} catch (Exception e) {
				
				e.printStackTrace();
			}
	            System.out.println("data");
	        }
		System.out.println("jhi");
		repository.save(product);
	  return ResponseEntity.ok("Data saved successfully");
  }
	@DeleteMapping("/deleteProduct/{id}")
	public ResponseEntity<String> deleteProduct(@PathVariable Integer id) {
	    try {
	        repository.deleteById(id);
	        return ResponseEntity.ok("Product deleted successfully");
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting product");
	    }
	}
	
	@PutMapping("/updateProduct/{id}")
	public ResponseEntity<String> updateProduct(@PathVariable Integer id, 
	                                            @RequestPart("product") ProductEntity product,
	                                            @RequestPart("images") MultipartFile images) throws IOException {
	    Optional<ProductEntity> existingProductOptional = repository.findById(id);

	    if (existingProductOptional.isPresent()) {
	        ProductEntity existingProduct = existingProductOptional.get();

	       
	        if (images != null && !images.isEmpty()) {
	        	String oldname=existingProduct.getImages();
	        	System.out.println(oldname);
	        	imageservice.deleteImage(oldname);
	            String imageName = imageservice.saveImage(images);
	            existingProduct.setImages(imageName);
	        }

	        // Update other fields
	        existingProduct.setName(product.getName());
	        existingProduct.setPrice(product.getPrice());
	        existingProduct.setBrand(product.getBrand());
	        existingProduct.setCategory(product.getCategory());
	        existingProduct.setDescrption(product.getDescription());
	        existingProduct.setQuantity(product.getQuantity());
    repository.save(existingProduct);
return ResponseEntity.ok("Update record is successful");
	    } else {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
	    }
	}
}
