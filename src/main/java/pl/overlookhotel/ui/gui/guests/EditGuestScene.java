package pl.overlookhotel.ui.gui.guests;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import pl.overlookhotel.domain.ObjectPool;
import pl.overlookhotel.domain.guest.GuestService;
import pl.overlookhotel.domain.guest.dto.GuestDTO;
import pl.overlookhotel.util.SystemUtils;

public class EditGuestScene {

    private final Scene mainScene;
    private final GuestService guestService = ObjectPool.getGuestService();

    public EditGuestScene(Stage stage, TableView<GuestDTO> tableView, GuestDTO value) {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setVgap(15);

        Label guestFirstNameLabel = new Label("Imię");
        TextField guestFirstNameField = new TextField();
        guestFirstNameField.setText(value.getFirstName());


        guestFirstNameField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\p{L}*")) {
                guestFirstNameField.setText(oldValue);
            }
        });

        gridPane.add(guestFirstNameLabel, 0, 0);
        gridPane.add(guestFirstNameField, 1, 0);

        Label guestLastNameLabel = new Label("Nazwisko");
        TextField guestLastNameField = new TextField();
        guestLastNameField.setText(value.getLastName());

        guestLastNameField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\p{L}*")) {
                guestLastNameField.setText(oldValue);
            }
        });

        gridPane.add(guestLastNameLabel, 0, 1);
        gridPane.add(guestLastNameField, 1, 1);

        Label guestAgeLabel = new Label("Wiek");
        TextField guestAgeField = new TextField();
        guestAgeField.setText(String.valueOf(value.getAge()));

        guestAgeField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                guestAgeField.setText(oldValue);
            }
        });

        gridPane.add(guestAgeLabel, 0, 2);
        gridPane.add(guestAgeField, 1, 2);

        Label guestGenderLabel = new Label("Płeć");
        ComboBox<String> guestGenderField = new ComboBox<>();
        guestGenderField.getItems().addAll(SystemUtils.FEMALE, SystemUtils.MALE);
        guestGenderField.setValue(SystemUtils.FEMALE);

        gridPane.add(guestGenderLabel, 0, 3);
        gridPane.add(guestGenderField, 1, 3);


        Button addNewGuestButton = new Button("Zapisz zmiany");

        addNewGuestButton.setOnAction(actionEvent -> {

            String firstName = guestFirstNameField.getText();
            String lastName = guestLastNameField.getText();

            int age = Integer.parseInt(guestAgeField.getText());

            String gender = guestGenderField.getValue();

            boolean isMale = false;

            if (gender.equals(SystemUtils.MALE)) {
                isMale = true;
            }

            this.guestService.editGuest(value.getId(), firstName, lastName, age, isMale);

            tableView.getItems().clear();
            tableView.getItems().addAll(this.guestService.getAllGuestsAsDTO());

            stage.close();
        });

        gridPane.add(addNewGuestButton, 1, 4);

        this.mainScene = new Scene(gridPane, 640, 480);
        this.mainScene.getStylesheets().add(getClass().getClassLoader()
                .getResource("hotelReservation.css").toExternalForm());
    }


    public Scene getMainScene() {
        return mainScene;
    }
}
