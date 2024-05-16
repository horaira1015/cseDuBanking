package controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;

public class openingPageController {
	public void quitProgramme(ActionEvent actionEvent) {
		Platform.exit();
	}

	public void userLogin(ActionEvent actionEvent) {
		new Bank().changeScene("CustomerLogin.fxml", "Customer - Login", 553, 457);
	}

	public void adminLogin(ActionEvent actionEvent) {
		new Bank().changeScene("adminLogin.fxml", "Admin - Login", 553, 457);
	}
}
