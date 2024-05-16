package controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import programmeClasses.Customer;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class registerController implements Initializable {
	@FXML
	TextField fname;
	@FXML
	TextField lname;
	@FXML
	TextField username;
	@FXML
	PasswordField password;
	@FXML
	TextField phoneno;
	@FXML
	TextField email;
	@FXML
	DatePicker dob;
	@FXML
	ComboBox<String> gender;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		gender.setItems(FXCollections.observableArrayList("Male", "Female"));
	}

	public void back() {
		new Bank().changeScene("CustomerLogin.fxml", "Customer - Login", 553, 457);
	}

	public void submit() throws SQLException {
		String usernameText = username.getText();
		String passwordText = password.getText();
		String emailText = email.getText();
		String fnameText = fname.getText();
		String lnameText = lname.getText();
		String phonenoText = phoneno.getText();
		LocalDate dobValue = dob.getValue();
		String genderValue = gender.getSelectionModel().getSelectedItem();

		// Check if any field is empty
		if (usernameText.isEmpty() || passwordText.isEmpty() || emailText.isEmpty() || fnameText.isEmpty() || lnameText.isEmpty() || phonenoText.isEmpty() || dobValue == null || genderValue == null) {
			Bank.showAlert(Alert.AlertType.ERROR, "Error", "Incomplete Information", "Please fill in all fields.");
			return;
		}

		// Check if username has at least 3 characters
		if (usernameText.length() < 3) {
			Bank.showAlert(Alert.AlertType.ERROR, "Error", "Invalid Username", "Username must be at least 3 characters long.");
			return;
		}

		// Check password validity (e.g., at least 8 characters)
		if (passwordText.length() < 8) {
			Bank.showAlert(Alert.AlertType.ERROR, "Error", "Invalid Password", "Password must be at least 8 characters long.");
			return;
		}

		// Additional password validation rules can be added here
		if (!phonenoText.matches("\\d{11}")) {
			Bank.showAlert(Alert.AlertType.ERROR, "Error", "Invalid Phone Number", "PhoneNumber must be at least 11 digits long.");
			return;
		}
		if (!emailText.matches("^[a-zA-Z0-9._%+-]{3,}@gmail\\.com$")) {
			Bank.showAlert(Alert.AlertType.ERROR, "Error", "Invalid Email", "Email must be at ends with @gmail.com followed by three character");
			return;
		}
		// Proceed with registration if all checks pass
		try {
			Customer c = new Customer(usernameText, passwordText, emailText, fnameText, lnameText, phonenoText, dobValue.toString(), genderValue);
			CustomerLoginController.uploadCustomer(c);
			new Bank().changeScene("CustomerLogin.fxml", "Customer - Login", 553, 457);
		} catch (Exception e) {
			Bank.showAlert(Alert.AlertType.ERROR, "Error", "Registration Error", "Same username is not allowed. Change your username");
		}
	}


}
