package it.unipv.ingsw.pickuppoint.utility;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class JsonReader {

	/**
	 * Legge uno stream di caratteri, li unisce in uno stringa
	 * 
	 * @param rd
	 * @return json in formato stringa
	 * @throws IOException
	 */
	private static String readAll(Reader rd) throws IOException {
		StringBuilder sb = new StringBuilder();
		int cp;
		while ((cp = rd.read()) != -1) {
			sb.append((char) cp);
		}
		return sb.toString();
	}
	
	/**
	 * Legge un file e restituisce un oggetto Json
	 * 
	 * @param file
	 * @return JSONObject
	 * @throws IOException
	 * @throws JSONException
	 */
	public static JSONObject readJson(MultipartFile file) throws IOException, JSONException {
		InputStream inputStream = file.getInputStream();
		try {
			BufferedReader rd = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
			String jsonText = readAll(rd);
			JSONObject json = new JSONObject(jsonText);
			return json;
		} finally {
			inputStream.close();
		}
	}
}