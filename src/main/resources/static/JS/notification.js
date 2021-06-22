function notify(callback) {
	if (Notification.permiss == "granted") {
		callback();
	} else if (Notification.permission !== "denied") {
		Notification.requestPermission().then((permission) => {
			if (permission == "granted") {
				callback();
			}
		});
	}
}

function customerNotification(orderId) {
	notify(() => {
		var orders = Cookies.get("customer");

		console.log("orders " + orders);
		console.log("ID " + orderId + " ####");

		if (orders == null || !orders.includes(orderId)) {
			new Notification("Notifica consegna avvenuta", {
				body: "ordine " + orderId + " CONSEGNATO",
				icon: "https://cdn.iconscout.com/icon/premium/png-256-thumb/delivered-1681114-1427946.png",
			});
			setCookie("customer", orderId);
		}
	});
}

function courierNotification(orderId) {
	notify(() => {
		var orders = Cookies.get("courier");
		console.log("orders " + orders);
		console.log("ID " + orderId);

		if (orders == null || !orders.includes(orderId)) {
			new Notification("Notifica ritiro avvenuto", {
				body: "ordine " + orderId + " RITIRATO",
				icon: "https://upload.wikimedia.org/wikipedia/commons/thumb/b/b0/Light_green_check.svg/480px-Light_green_check.svg.png",
			});
			setCookie("courier", orderId);
		}
	});
}

function setCookie(name, value) {
	var x = Cookies.get(name);

	if (x == null) {
		Cookies.set(name, value);
	} else {
		var y = x.concat(", " + value);
		console.log("Y " + y);
		Cookies.set(name, y);
	}
}
