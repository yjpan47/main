package seedu.address.ui;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import seedu.address.commons.core.UserType;

/**
 * Opens up a login box for users to enter their login details.
 */
public class LoginBox {
    private UserType userType = null;
    private TextField textField1;
    private TextField textField2;
    private Label label;
    private Stage window;
    /**
     * Display box parameters.
     */
    public UserType display() {
        window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Login");
        window.setMinWidth(500);
        window.setMinHeight(250);

        textField1 = new TextField("Your nric");
        textField1.setOnAction(e -> findAccount());
        textField2 = new PasswordField();
        textField2.setPromptText("Your password");
        textField2.setOnAction(e -> findAccount());

        label = new Label();

        VBox layout = new VBox();
        layout.getChildren().addAll(textField1, textField2, label);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();

        return userType;

    }
    /**
     * Finds the account in the list of accounts and lets the user login.
     */
    private void findAccount() {
        if (textField1.getText().equals("General") && textField2.getText().equals("General")) {
            userType = UserType.GENERAL;
            window.close();
        } else if (textField1.getText().equals("Admin") && textField2.getText().equals("Admin")) {
            userType = UserType.ADMIN;
            window.close();
        } else {
            label.setText("User not found!");
        }
    }

}

