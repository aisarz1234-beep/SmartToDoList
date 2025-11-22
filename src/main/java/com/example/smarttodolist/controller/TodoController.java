package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Task;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class TodoController implements Initializable {

    @FXML private TextField titleField;
    @FXML private TextField descriptionField;
    @FXML private DatePicker dueDatePicker;
    @FXML private ComboBox<String> categoryComboBox;
    @FXML private ComboBox<String> priorityComboBox;
    
    @FXML private TableView<Task> taskTableView;
    @FXML private TableColumn<Task, Boolean> completedColumn;
    @FXML private TableColumn<Task, String> titleColumn;
    @FXML private TableColumn<Task, String> descriptionColumn;
    @FXML private TableColumn<Task, LocalDate> dueDateColumn;
    @FXML private TableColumn<Task, String> categoryColumn;
    @FXML private TableColumn<Task, String> priorityColumn;
    
    private ObservableList<Task> taskList;
    private Task selectedTask;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialize task list
        taskList = FXCollections.observableArrayList();
        
        // Set up category options
        categoryComboBox.setItems(FXCollections.observableArrayList(
            "Work", "Personal", "Shopping", "Health", "Education", "Other"
        ));
        
        // Set up priority options
        priorityComboBox.setItems(FXCollections.observableArrayList(
            "High", "Medium", "Low"
        ));
        
        // Set up table columns
        completedColumn.setCellValueFactory(new PropertyValueFactory<>("completed"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        dueDateColumn.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        priorityColumn.setCellValueFactory(new PropertyValueFactory<>("priority"));
        
        // Set up completed column with checkboxes
        completedColumn.setCellFactory(col -> new TableCell<Task, Boolean>() {
            private final CheckBox checkBox = new CheckBox();
            
            {
                checkBox.setOnAction(e -> {
                    Task task = getTableView().getItems().get(getIndex());
                    task.setCompleted(checkBox.isSelected());
                    refreshTable();
                });
            }
            
            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    Task task = getTableView().getItems().get(getIndex());
                    checkBox.setSelected(task.isCompleted());
                    setGraphic(checkBox);
                }
            }
        });
        
        // Set table data
        taskTableView.setItems(taskList);
        
        // Add selection listener
        taskTableView.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> selectTask(newValue)
        );
        
        // Set current date as default
        dueDatePicker.setValue(LocalDate.now());
    }
    
    @FXML
    private void handleAddTask() {
        if (validateInput()) {
            Task task = new Task(
                titleField.getText(),
                descriptionField.getText(),
                dueDatePicker.getValue(),
                categoryComboBox.getValue(),
                priorityComboBox.getValue()
            );
            
            taskList.add(task);
            clearForm();
            refreshTable();
        }
    }
    
    @FXML
    private void handleUpdateTask() {
        if (selectedTask != null && validateInput()) {
            selectedTask.setTitle(titleField.getText());
            selectedTask.setDescription(descriptionField.getText());
            selectedTask.setDueDate(dueDatePicker.getValue());
            selectedTask.setCategory(categoryComboBox.getValue());
            selectedTask.setPriority(priorityComboBox.getValue());
            
            refreshTable();
            clearForm();
            selectedTask = null;
        }
    }
    
    @FXML
    private void handleDeleteTask() {
        if (selectedTask != null) {
            taskList.remove(selectedTask);
            clearForm();
            selectedTask = null;
        }
    }
    
    private void selectTask(Task task) {
        if (task != null) {
            selectedTask = task;
            titleField.setText(task.getTitle());
            descriptionField.setText(task.getDescription());
            dueDatePicker.setValue(task.getDueDate());
            categoryComboBox.setValue(task.getCategory());
            priorityComboBox.setValue(task.getPriority());
        }
    }
    
    private boolean validateInput() {
        if (titleField.getText().isEmpty()) {
            showAlert("Error", "Title is required!");
            return false;
        }
        if (categoryComboBox.getValue() == null) {
            showAlert("Error", "Please select a category!");
            return false;
        }
        if (priorityComboBox.getValue() == null) {
            showAlert("Error", "Please select a priority!");
            return false;
        }
        return true;
    }
    
    private void clearForm() {
        titleField.clear();
        descriptionField.clear();
        dueDatePicker.setValue(LocalDate.now());
        categoryComboBox.setValue(null);
        priorityComboBox.setValue(null);
    }
    
    private void refreshTable() {
        taskTableView.refresh();
    }
    
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

