package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import programmeClasses.Customer;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class LoanAccountEnterViewControl implements Initializable {
	public DatePicker starting;
	public Label interestRateShow;
	public DatePicker ending;
	public TextField amount;
	public TextField amount1;
	public TextField repayAcNu;
	static Loan x;

	public void goToAccountSelection(ActionEvent actionEvent) {
		new Bank().changeScene("CustomerAccountSelection.fxml", "Please Select Account", 663, 432);
	}
	public void deleteAc_go_to_selection() throws SQLException {
		if(ending.getValue() == null){
			Bank.showAlert(Alert.AlertType.ERROR, "Error", "Selection Error", "Please select date");
			return;
		}
		try {
			if(ending.getValue() == null || String.valueOf(ending.getValue()) == null){
				Bank.showAlert(Alert.AlertType.ERROR, "Error", "Selection Error", "Please select date");
				return;
			}
		} catch (Exception e) {
			Bank.showAlert(Alert.AlertType.ERROR, "Error", "Selection Error", "Please select date");
			return;
		}
		Customer customer = CustomerLoginController.customer;

		x.setEndDate(ending.getValue().toString());
		float jototakanisi = Float.parseFloat( amount.getText());
		float jotoTakaJomaDibo = Float.parseFloat(amount1.getText());
		customer.balance = customer.balance + jototakanisi;

		String query = "UPDATE customerall set balance = "+customer.balance+" WHERE username = '" +customer.getUsername()+"'";
		Statement stmt = Bank.con.createStatement();
		stmt.executeUpdate(query);
		query = "INSERT INTO transactions (AccountNumber, CustomerName, Transactiontype, Amount, ToAccountNumber) " +
				"VALUES (" + x.acnumber + ", '" + customer.getUsername() + "', 'deposit', " +  jotoTakaJomaDibo + ", " + x.acnumber + ")";

		stmt = Bank.con.createStatement();
		stmt.executeUpdate(query);

		customer.balance = customer.balance + jototakanisi;

		deleteAccount(x.acnumber);

		Bank.balance += jotoTakaJomaDibo;

		new Bank().changeScene("CustomerAccountSelection.fxml", "Please Select Account", 663, 432);
	}

	private void deleteAccount(int accountNumber) throws SQLException {
		try (Statement st = Bank.con.createStatement()) {
			st.executeUpdate("DELETE FROM useraccounts WHERE accountNumber = " + accountNumber);
			st.executeUpdate("DELETE FROM advance WHERE accountNumber = " + accountNumber);
		}
	}


	public void calculate(ActionEvent actionEvent) throws SQLException {
		float total = x.calculateBalanceToDate(ending.getValue().toString());
		amount1.setText(String.valueOf(total));
	}

	public void cashPaid(ActionEvent actionEvent) throws SQLException {
		System.out.println("Paid_Cash");
		deleteAc_go_to_selection();
	}

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		try {
			x = LoanAccountStartViewControl.downloadLoanAccount(CustomerAccountSelectionCon.acnumber);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		amount.setText(String.valueOf(x.balance));
		interestRateShow.setText(String.valueOf(AdvanceFeature.LOAN_INTEREST_RATE));
	}
}
