var arrayCourier = new Array();
var arrayCustomer = new Array();

function notify(orderId, role) {
	if (Notification.permiss == 'granted') {
		roleNotification(orderId, role)
	} else if (Notification.permission !== "denied") {
		Notification.requestPermission().then(permission => {
			if (permission == "granted") {
				roleNotification(orderId, role)
			}
		});
	}
}

function roleNotification(orderId, role) {
	if (role == 'CUSTOMER') {
		customerNotification(orderId);
	} else if (role == 'COURIER') {
		courierNotification(orderId);
	}
}

function customerNotification(orderId) {
	var orders = Cookies.get('customer');

	console.log("orders " + orders);
	console.log("ID " + orderId);
	console.log("arrayCustomer " + arrayCustomer);

	if (orders == null || !orders.includes(orderId)) {
		new Notification("Notifica consegna avvenuta", {
			body: "ordine " + orderId + " CONSEGNATO",
			icon: "https://cdn.iconscout.com/icon/premium/png-256-thumb/delivered-1681114-1427946.png"
		});
		arrayCustomer.push(orderId);

		//ERRORE: 
		// La prima invocazione del metodo funziona: vengono pushati gli orderId nell'array e viene settato 
		// l'array dentro il cookie
		
		// PROBLEMA: dopo l'aggiornamento della pagina arrayCustomer viene inizializzato, di conseguenza
		// vengono persi tutti gli elementi quindi il cookie verrà settato solo con  l'orderId che ha invocato il metodo
		
		// SOLUZIONE: trovare un modo per aggiungere al cookie elemento per elemento
		// for con indice che inizia da cookie.size() e termina cookie.size()+1
		
		Cookies.set('customer', JSON.stringify(arrayCustomer));
		console.log("#arrayCustomer " + arrayCustomer);
	}
}
function courierNotification(orderId) {
	var orders = Cookies.get('courier');

	console.log("orders " + orders);
	console.log("ID " + orderId);
	console.log("arrayCustomer " + arrayCustomer);

	if (orders == null || !orders.includes(orderId)) {
		new Notification("Notifica ritiro avvenuto", {
			body: "ordine " + orderId + " RITIRATO",
			icon: "https://upload.wikimedia.org/wikipedia/commons/thumb/b/b0/Light_green_check.svg/480px-Light_green_check.svg.png"
		});
		arrayCourier.push(orderId);
		Cookies.set('courier', JSON.stringify(arrayCourier));
	}
}
