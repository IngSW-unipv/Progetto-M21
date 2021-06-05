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
	new Notification("Notifica consegna", {
		body: "ordine " + orderId + " CONSEGNATO",
		icon: "https://cdn.iconscout.com/icon/premium/png-256-thumb/delivered-1681114-1427946.png"
	});

}

function courierNotification(orderId) {
	new Notification("Notifica ritiro", {
		body: "ordine " + orderId + " RITIRATO",
		icon: "https://upload.wikimedia.org/wikipedia/commons/thumb/b/b0/Light_green_check.svg/480px-Light_green_check.svg.png"
	});
}


