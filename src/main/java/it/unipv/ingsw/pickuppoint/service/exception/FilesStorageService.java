package it.unipv.ingsw.pickuppoint.service.exception;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FilesStorageService {

	private final Path root = Paths.get("uploads");

	public void init() {
		try {
			Files.createDirectory(root);
		} catch (IOException e) {
			System.out.println("folder already exists");
		}
	}

	public void save(MultipartFile file) {
		try {
			Files.copy(file.getInputStream(), this.root.resolve(file.getOriginalFilename()),
					StandardCopyOption.REPLACE_EXISTING);
		} catch (Exception e) {
			throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
		}
	}

	public Resource load(String filename) {
		try {
			Path file = root.resolve(filename);
			Resource resource = new UrlResource(file.toUri());

			if (resource.exists() || resource.isReadable()) {
				return resource;
			} else {
				throw new RuntimeException("Could not read the file!");
			}
		} catch (MalformedURLException e) {
			throw new RuntimeException("Error: " + e.getMessage());
		}
	}

	public void deleteAll() {
		FileSystemUtils.deleteRecursively(root.toFile());
	}
}