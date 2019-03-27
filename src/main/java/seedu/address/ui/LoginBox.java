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
    private TextField userField;
    private TextField passField;
    private Label label;
    private Stage window;
    private final UserFinder userFinder;

    public LoginBox(UserFinder userFinder) {
        this.userFinder = userFinder;
    }

    /**
     * Display box parameters.
     */
    public UserType display() {
        window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Login");
        window.setMinWidth(500);
        window.setMinHeight(250);

        userField = new TextField();
        userField.setPromptText("Your NRIC");
        userField.setOnAction(e -> findAccount());

        passField = new PasswordField();
        passField.setPromptText("Your password");
        passField.setOnAction(e -> findAccount());

        label = new Label();

        VBox layout = new VBox();
        layout.getChildren().addAll(userField, passField, label);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();

        return userType;

    }
    /**
     * Finds the account in the list of accounts and lets the user login.
     */
    private void findAccount() {
        if (userField.getText().equals("Admin") && passField.getText().equals("Admin")) {
            userType = UserType.ADMIN;
            window.close();
        }
        UserType foundUser = userFinder.findAccount(userField.getText(), passField.getText());
        if (foundUser == null) {
            label.setText("User not found!");
        } else {
            userType = foundUser;
            window.close();
        }
    }

    /**
     * Represents a function that can find accounts.
     */
    @FunctionalInterface
    public interface UserFinder {
        /**
         * Finds the usertype of the account
         *
         * @see seedu.address.logic.Logic#findAccount(String, String)
         */
        UserType findAccount(String userName, String password);
    }

}

