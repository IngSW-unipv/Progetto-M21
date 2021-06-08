var arrayCourier = [];
var arrayCustomer = [];


function notify(orderId, role) {
	if (Notification.permiss == 'granted') {
		roleNotification();
	} else if (Notification.permission !== "denied") {
		Notification.requestPermission().then(permission => {
			if (permission == "granted") {
				roleNotification(orderId, role);
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
	var orders = getCookie('customer');

	if (orders.isEmpty) {
		createCookie('customer', arrayCustomer);
	}

	if (!orders.includes(orderId)) {
		new Notification("Notifica consegna avvenuta", {
			body: "ordine " + orderId + " CONSEGNATO",
			icon: "https://cdn.iconscout.com/icon/premium/png-256-thumb/delivered-1681114-1427946.png"
		});
		arrayCustomer.push(orderId);
		createCookie('customer', arrayCustomer);
	}
}

function courierNotification(orderId) {
	var orders = getCookie('courier');

	if (orders.isEmpty) {
		createCookie('courier', arrayCourier);
	}

	if (!orders.includes(orderId)) {
		new Notification("Notifica ritiro avvenuto", {
			body: "ordine " + orderId + " RITIRATO",
			icon: "https://upload.wikimedia.org/wikipedia/commons/thumb/b/b0/Light_green_check.svg/480px-Light_green_check.svg.png"
		});
		arrayCourier.push(orderId);
		createCookie('courier', arrayCourier);
	}
}

function createCookie(name, value) {

	var cookie = [
		name,
		'=',
		JSON.stringify(value)

	].join('');
	document.cookie = cookie;

}

function getCookie(name) {
	var a = [];
	var nameEQ = name + "=";
	var ca = document.cookie.split(';');

	for (var i = 0; i < ca.length; i++) {
		var c = ca[i];
		while (c.charAt(0) == ' ')
			c = c.substring(1, c.length);
			
		if (c.indexOf(nameEQ) === 0) {
			return JSON.parse(
				c.substring(nameEQ.length, c.length)
			);
		}
	}
	return a;
}

