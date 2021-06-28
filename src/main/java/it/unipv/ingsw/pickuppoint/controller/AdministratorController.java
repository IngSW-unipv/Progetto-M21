package it.unipv.ingsw.pickuppoint.controller;

import java.io.IOException;

import javax.validation.Valid;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import it.unipv.ingsw.pickuppoint.model.User;
import it.unipv.ingsw.pickuppoint.service.HubService;
import it.unipv.ingsw.pickuppoint.service.LockerService;
import it.unipv.ingsw.pickuppoint.service.UserService;
import it.unipv.ingsw.pickuppoint.service.exception.EmptyFile;
import it.unipv.ingsw.pickuppoint.service.exception.FileFormat;
import it.unipv.ingsw.pickuppoint.service.exception.FilesStorageService;
import it.unipv.ingsw.pickuppoint.service.exception.JsonFormat;

@Controller
public class AdministratorController {

	@Autowired
	UserService userService;
	@Autowired
	FilesStorageService storageService;
	@Autowired
	HubService hubService;
	@Autowired
	LockerService lockerService;

	/**
	 * Questo metodo viene invocato quando il client invia una richiesta GET a
	 * /userList dalla pagina profile.
	 * 
	 * @param model contenitore di attributi dove vengono inseriti gli utenti dal
	 *              database
	 * @return la pagina userList
	 */

	@RequestMapping("/UserList")
	public String viewHomePage(Model model) {
		userService.findAllUsers(model);
		return "userList";
	}

	/**
	 * Questo metodo viene invocato quando il client invia una richiesta GET a
	 * /editUser dalla pagina userList.
	 * 
	 * @param id    è l'id dell'utente da modificare
	 * @param model è il contenitore di attributi che viene inoltrato al client per
	 *              inserire i dati del nuovo utente
	 * @return la pagina del form di modifica utente
	 */
	@RequestMapping(value = "/editUser/{id}")
	public String showEditUserForm(@PathVariable(name = "id") Long id, Model model) {
		model.addAttribute(userService.getUser(id));
		return "/editUser";
	}

	/**
	 * Questo metodo viene invocato quando il client invia una richiesta POST a
	 * /save dalla pagina editUser.
	 * 
	 * @param id            è l'id dell'utente modificato
	 * @param user          è l'utente con le nuove modifiche
	 * @param bindingResult per la verifica di errori di compilazione del form
	 * @param model         si riferisce all'istanza di user, serve in caso di
	 *                      errori nella compilazione.
	 * @return la pagina stessa editUser in caso di problemi, o userList in caso di
	 *         successo.
	 */

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveUser(@Valid @ModelAttribute User user, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("user", user);
			return "editUser";
		}
		userService.saveUser(user);
		return "redirect:" + "/UserList";
	}

	@RequestMapping("/delete/{id}")
	public String deleteUser(@PathVariable(name = "id") Long id) {
		hubService.deleteCourier(id);
		userService.delete(id);
		return "redirect:" + "/UserList";
	}

	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
	public String uploadFile(@RequestParam("file") MultipartFile file, Model model)
			throws EmptyFile, FileFormat, JsonFormat, JSONException, IOException {
		storageService.init();
		try {
			storageService.save(file);
		} catch (EmptyFile emptyFile) {
			model.addAttribute("emptyfile", emptyFile.getMessage());
			return "/profile";
		} catch (FileFormat fileFormat) {
			model.addAttribute("errorfile", fileFormat.getMessage());
			return "/profile";
		}
		try {
			hubService.addOrders(file);
		} catch (JsonFormat jsonFormat) {
			model.addAttribute("errorfile", jsonFormat.getMessage());
			return "/profile";
		}

		return "redirect:" + "/profile";
	}

	@RequestMapping("/lockers")
	public String viewLocker(Model model) {
		model.addAttribute("lockers", lockerService.getAllLocker());
		return "lockers";
	}

	@RequestMapping(value = "/lockers", method = RequestMethod.POST)
	public String lockerId(Model model, @RequestParam(value = "lockerId") Long lockerId) {
		model.addAttribute("lk", lockerService.getLockerById(lockerId));
		model.addAttribute("lockers", lockerService.getAllLocker());
		model.addAttribute("slots", lockerService.getLockerSlot(lockerId));
		return "lockers";
	}
}
