package it.unipv.ingsw.pickuppoint.controller;

import java.io.IOException;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonFormat;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import it.unipv.ingsw.pickuppoint.model.User;
import it.unipv.ingsw.pickuppoint.service.AdminService;
import it.unipv.ingsw.pickuppoint.service.FilesStorageService;
import it.unipv.ingsw.pickuppoint.service.LockerService;
import it.unipv.ingsw.pickuppoint.service.UserService;
import it.unipv.ingsw.pickuppoint.service.exception.EmptyFileException;
import it.unipv.ingsw.pickuppoint.service.exception.FileFormatException;
import it.unipv.ingsw.pickuppoint.service.exception.JsonFormatException;

@Controller
public class AdministratorController {

	@Autowired
	private UserService userService;
	@Autowired
	private FilesStorageService storageService;
	@Autowired
	private LockerService lockerService;
	@Autowired
	private AdminService adminService;

	/**
	 * Invocato quando l'admin effettua una richiesta GET a /userList da profile
	 * 
	 * @param model contenitore di attributi dove vengono inseriti gli utenti dal
	 *              database
	 * @return la pagina userList
	 */
	@GetMapping("/UserList")
	public String viewUserList(Model model) {
		userService.findAllUsers(model);
		return "userList";
	}

	/**
	 * Invocato quando l'admin effettua una richiesta GET a /editUser da userList.
	 * 
	 * @param id    dell'utente da modificare
	 * @param model è il contenitore di attributi che viene inoltrato al client per
	 *              inserire i dati del nuovo utente
	 * @return la pagina del form di modifica utente
	 */
	@GetMapping(value = "/editUser/{id}")
	public String viewEditUserForm(@PathVariable(name = "id") Long id, Model model) {
		model.addAttribute(userService.getUser(id));
		return "/editUser";
	}

	/**
	 * Invocato quando l'admin effettua una richiesta POST a /save dalla pagina
	 * editUser.
	 * 
	 * @param id            dell'utente modificato
	 * @param user          utente con le nuove modifiche
	 * @param bindingResult per la verifica di errori di compilazione del form
	 * @param model         si riferisce all'istanza di user, serve in caso di
	 *                      errori nella compilazione.
	 * @return la pagina stessa editUser in caso di problemi, o userList in caso di
	 *         successo.
	 */
	@PostMapping(value = "/save")
	public String saveUser(@Valid @ModelAttribute User user, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("user", user);
			return "editUser";
		}
		userService.saveUser(user);
		return "redirect:" + "/UserList";
	}

	/**
	 * Invocato quando l'admin effettua una richiesta GET a /delete/id. Elimina il
	 * corriere dato l'id
	 * 
	 * @param id del corriere dal eliminare
	 * @return viewUserList
	 */
	@GetMapping("/delete/{id}")
	public String deleteUser(@PathVariable(name = "id") Long id) {
		adminService.deleteCourier(id);
		userService.delete(id);
		return "redirect:" + "/UserList";
	}

	/**
	 * Invocato quando l'admin effettua una richiesta POST a /uploadFile da profile.
	 * 
	 * @param file  caricato dall'admin
	 * @param model
	 * @return /profile
	 * @throws EmptyFileException     verifica se il file è vuoto
	 * @throws FileFormatException    verifica il formato del file
	 * @throws JsonFormat    verifica il formato del json
	 * @throws JSONException gestisce eccezione json
	 * @throws IOException   gestisce eccezione i/o
	 */
	@PostMapping(value = "/uploadFile")
	public String uploadFile(@RequestParam("file") MultipartFile file, Model model)
			throws EmptyFileException, FileFormatException, JsonFormatException, JSONException, IOException {
		storageService.init();
		try {
			storageService.save(file);
		} catch (EmptyFileException emptyFile) {
			model.addAttribute("emptyfile", emptyFile.getMessage());
			return "/profile";
		} catch (FileFormatException fileFormat) {
			model.addAttribute("errorfile", fileFormat.getMessage());
			return "/profile";
		}
		try {
			adminService.addOrders(file);
		} catch (JsonFormatException jsonFormat) {
			model.addAttribute("errorfile", jsonFormat.getMessage());
			return "/profile";
		}

		return "redirect:" + "/profile";
	}

	/**
	 * Invocato quando l'admin effetua una richiesta GET a /locker da profile.
	 * 
	 * @param model
	 * @return pagina lockers
	 */
	@GetMapping("/lockers")
	public String viewLocker(Model model) {
		model.addAttribute("lockers", lockerService.getAllLocker());
		return "lockers";
	}

	/**
	 * Invocato quando l'admin effetua una richiesta POST a /lockers nel momento in
	 * cui viene selezionato il locker da visualizzare
	 * 
	 * @param model
	 * @param lockerId
	 * @return pagina lockers
	 */
	@PostMapping(value = "/lockers")
	public String selectedLocker(Model model, @RequestParam(value = "lockerId") Long lockerId) {
		model.addAttribute("lk", lockerService.getLockerById(lockerId));
		model.addAttribute("lockers", lockerService.getAllLocker());
		model.addAttribute("slots", lockerService.getLockerSlot(lockerId));
		return "lockers";
	}
}
