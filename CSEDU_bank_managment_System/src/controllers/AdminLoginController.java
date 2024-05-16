package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AdminLoginController {
	@FXML
	public PasswordField passwordField;
	@FXML
	public TextField adminId;
	static int id;

	public void signIn(ActionEvent actionEvent) {
		try {
			String query1="Select username , password from admin where username='"+adminId.getText()+"';";
			Statement st=Bank.con.createStatement();
			ResultSet rs=st.executeQuery(query1);
			rs.next();
			String dbpassword=rs.getString(2);

			if(dbpassword.contentEquals(passwordField.getText())) {
				System.out.println("Right password");

				System.out.println("ekhane");

				new Bank().changeScene("adminPage.fxml", "Option Menu", 682, 402);
			}
		}
		catch(SQLException sqlException) {
			System.out.println("Check username and password");
			Bank.showAlert(Alert.AlertType.ERROR, "Error", "Wrong Information", "Please check username or passwords");
		}
		//new Main().changeScene("adminPage.fxml", "Admin", 682,402);
	}

	public void backToMainMenu(ActionEvent actionEvent) {
		new Bank().changeScene("openingPage.fxml","Start Screen",651,422);
	}

	public void registerAdmin(ActionEvent actionEvent) {
		new Bank().changeScene("adminRegister.fxml","Admin - Registratio", 600,495);
	}
}
