package com.example.journal;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.SQLException;
import java.util.*;

public class HelloController implements Initializable {
    @FXML
    private TextArea outputArea;
    @FXML
    private Label idLabel;
    @FXML
    private TextField inputId;
    @FXML
    private Label nameLabel;
    @FXML
    private TextField inputName;
    @FXML
    private Label passportLabel;
    @FXML
    private TextField inputPassport;
    @FXML
    private Label gradesLabel;
    @FXML
    private TextArea inputGrades;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        outputArea.setEditable(false);
        outputArea.setText("There will be data");
    }
    @FXML
    protected void setOwnText(String string){
        outputArea.clear();
        outputArea.setText(string);
    }
    @FXML
    protected void onCreateTableClick() {
        try{
            JDBCController.createTable();
        }catch(Exception e){
            setOwnText("database already created");
            return;
        }
        setOwnText("Database has been created");
    }
    @FXML
    protected void onGetConnectionButtonClick(){
        JDBCController.getConnection(JDBCController.getUrl(), JDBCController.getUsername(), JDBCController.getPassword());
        setOwnText("Connected!");
    }
    @FXML
    protected void onCreateStudentButtonClick(){
        Student student = new Student();
        student.setName(inputName.getText());
        student.setPassport(Integer.parseInt(inputPassport.getText()));
        ArrayList<Integer> grades = new ArrayList<>();
        String[] numbers = inputGrades.getText().split("\n");
        for (int i = 0; i < numbers.length; i++) {
            grades.add(Integer.parseInt(numbers[i]));
        }
        student.setGrades(grades.stream().mapToInt(i -> (int) i).toArray());
        JDBCController.createStudent(student);
        setOwnText("Student created");
    }
    @FXML
    protected void onDropTableButtonClick(){
        try{
            JDBCController.dropTable();
        }catch(Exception e){
            setOwnText("There is no table");
            return;
        }
        setOwnText("Table has been dropped");
    }
    @FXML
    protected void onDeleteStudentButtonClick(){
        try{
            JDBCController.deleteStudent(Integer.parseInt(inputId.getText()));
        }catch (Exception e){
            setOwnText("There is no such a student");
            return;
        }
        setOwnText("Student has been deleted");
    }
    @FXML
    protected void onUpdateStudentButtonClick(){
        int id = Integer.parseInt(inputId.getText());
        String name = inputName.getText();
        int passport = Integer.parseInt(inputPassport.getText());
        ArrayList<Integer> grades = new ArrayList<>();
        String[] numbers = inputGrades.getText().split("\n");
        for (int i = 0; i < numbers.length; i++) {
            grades.add(Integer.parseInt(numbers[i]));
        }
        JDBCController.updateStudent(id, name, passport, grades.stream().mapToInt(i -> i).toArray());
    }
    @FXML
    protected void onGetStudentsButtonClick(){
        List<Student> students = JDBCController.getStudents();
        StringBuilder stringBuilder = new StringBuilder();
        for (Student x : students){
            System.out.println(x.toString());
            stringBuilder.append(x.toString());
        }
        setOwnText(stringBuilder.toString());
    }
    @FXML
    protected void onGetStudentButtonClick(){
        Student student = JDBCController.getStudent(Integer.parseInt(inputId.getText()));
        setOwnText(student.toString());
    }
}