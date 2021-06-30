package it.unipv.ingsw.pickuppoint.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import it.unipv.ingsw.pickuppoint.service.exception.EmptyFileException;
import it.unipv.ingsw.pickuppoint.service.exception.FileFormatException;

@Service
public class FilesStorageService {

	private final Path root = Paths.get("uploads");

	/**
	 * Crea directory uploads
	 */
	public void init() {
		try {
			Files.createDirectory(root);
		} catch (IOException e) {
		}
	}

	/**
	 * Salva il file nella directory uploads
	 * 
	 * @param file
	 * @throws EmptyFileException
	 * @throws FileFormatException
	 */
	public void save(MultipartFile file) throws EmptyFileException, FileFormatException {
		String fileName = file.getOriginalFilename();
		String extension = fileName.substring(fileName.lastIndexOf(".") + 1);

		try {
			Files.copy(file.getInputStream(), this.root.resolve(file.getOriginalFilename()),
					StandardCopyOption.REPLACE_EXISTING);
		} catch (Exception e) {
			throw new EmptyFileException("Empty file, please upload a json file ");
		}

		if (!extension.equals("json")) {
			throw new FileFormatException("Wrong file format, please upload a json file");
		}
	}
}