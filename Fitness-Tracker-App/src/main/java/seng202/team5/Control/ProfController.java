package seng202.team5.Control;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import seng202.team5.Data.DataBaseController;
import seng202.team5.Model.User;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


import static seng202.team5.Model.CheckGoals.convertDate;


/**
 * This class handles the controls of the profile viewing tab of the application.
 * It provides the functionality to view and edit a users profile.
 */
public class ProfController {
    @FXML
    private  TextField nameText;
    @FXML
    private  TextField ageText;
    @FXML
    private  TextField heightText;
    @FXML
    private TextField weightText;
    @FXML
    private TextField bmiText;
    @FXML
    private TextField dateText;
    @FXML
    private Button viewButton;
    @FXML
    private Button updateButton;

    private User currentUser = AppController.getCurrentUser();
    private DateFormat dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy");
    private DataBaseController db = AppController.getDb();



    @FXML
    /**
     * Called by a press to the viewButton, this method displays all of the users
     * personal information.
     */
    public void viewProfile() {
        viewButton.setDisable(true);
        viewButton.setVisible(false);
        currentUser = AppController.getCurrentUser();
        nameText.setText(currentUser.getName());
        ageText.setText(Integer.toString(currentUser.getAge()));
        heightText.setText(Double.toString(currentUser.getHeight()));
        weightText.setText(Double.toString(currentUser.getWeight()));
        bmiText.setText(Double.toString(currentUser.getBmi()));

        String dateString = dateTimeFormat.format(currentUser.getBirthDate());
        dateText.setText(dateString);
    }


    @FXML
    /**
     * Called by a press to the updateButton, this method gets the current information
     * out of the text fields on the profile view and updates the current users entry in the database.
     */
    public void updateProfile() {
        try {
            String name = nameText.getText();
            double weight = Double.parseDouble(weightText.getText());
            double height = Double.parseDouble(heightText.getText());
            int age = Integer.parseInt(ageText.getText());
            Date date = dateTimeFormat.parse(dateText.getText());
            currentUser.setName(name);
            currentUser.setWeight(weight);
            currentUser.setHeight(height);
            currentUser.setAge(age);
            currentUser.setBirthDate(date);
            db.updateUser(currentUser);
            updateButton.setDisable(true);
        } catch (Exception e) {
            System.out.println("Error updating user");
        }
    }


    @FXML
    /**
     * Called by a key release on any of the editable text fields in the profile view. This
     * method checks that if the data in all the text fields is valid to update the user. If so
     * the updateButton is enabled but if the data is invalid the button is disabled.
     */
    public void checkProfile() {
        String name = nameText.getText();
        String weight = weightText.getText();
        String age = ageText.getText();
        String date = dateText.getText();
        String height = heightText.getText();
        try {
            if (checkName(name) && checkWeight(weight) && checkHeight(height) && checkInt(age) & checkDate(date, Integer.parseInt(age))) {

                double weightVal = Double.parseDouble(weight);
                double heightVal = Double.parseDouble(height);
                int ageVal = Integer.parseInt(age);
                try {
                    Date dateVal = dateTimeFormat.parse(date);
                    if (weightVal == currentUser.getWeight() && heightVal == currentUser.getHeight() && ageVal == currentUser.getAge()
                            && name.equals(currentUser.getName()) && dateVal == currentUser.getBirthDate()) {
                        updateButton.setDisable(true);
                    } else {
                        updateButton.setDisable(false);
                        updateButton.setVisible(true);
                    }
                } catch (ParseException e) {
                    updateButton.setDisable(true);
                }
            } else {
                updateButton.setDisable(true);
            }
        } catch (NumberFormatException e) {
            System.out.println("Parse error");
        }

    }


    /**
     * This method checks if the newly entered name is valid.
     * @param name The name to be checked.
     * @return A boolean flag holding the result of the check.
     */
    private boolean checkName(String name) {
        if (name.length() > 4 && name.length() < 30) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * This method checks if a newly entered int value is valid.
     * @param value The int value to check.
     * @return A boolean flag holding the result of the check.
     */
    private boolean checkInt(String value) {
        try {
            int x = Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }


    /**
     * This method checks if a newly entered weight value is valid. Valid is
     * considered to be between 25 and 175 kg.
     * @param value The weight vlaue to be checked.
     * @return A boolean flag holding the result of the check.
     */
    private boolean checkWeight(String value) {
        try {
            double x = Double.parseDouble(value);
            if (x < 25 || x > 175 ) {
                return false;
            } else {
                return true;
            }
        } catch (NumberFormatException e) {
            return false;
        }
    }


    /**
     * This method checks if a newly entered height is valid. Valid is considered to be
     * between 2 and 10 ft.
     * @param height The height value to be checked.
     * @return A boolean flag holding the result of the check.
     */
    private boolean checkHeight(String height) {
        try {
            double x = Double.parseDouble(height);
            if (x < 0.7 || x > 3 ) {
                return false;
            } else {
                return true;
            }
        } catch (NumberFormatException e) {
            return false;
        }
    }


    /**
     * This method checks if a newly entered birth date is valid. A valid date is
     * considered to be a date which is consistent with the currently entered age.
     * @param date The date string to be checked.
     * @return A boolean flag holding the result of the check.
     */
    private boolean checkDate(String date, int newAge) {
        try {
            DateFormat dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date x = dateTimeFormat.parse(date);
            Date current = new Date();
            int[] currentDate = convertDate(dateTimeFormat.format(current).split("/"));
            int[] compDate = convertDate(date.split("/"));
            if (compDate[2] != currentDate[2] - newAge) {
                return false;
            } else {
                return true;
            }
        } catch (ParseException e) {
            return false;
        }
    }
}
