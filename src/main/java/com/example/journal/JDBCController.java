package com.example.journal;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
public class JDBCController {
    private static String url = "jdbc:postgresql:";
    private static String username = "postgres";
    private static String password = "1234";
    public static void setUrl(String url) {
        JDBCController.url = url;
    }
    public static void setUsername(String username) {
        JDBCController.username = username;
    }
    public static void setPassword(String password) {
        JDBCController.password = password;
    }
    public static String getUrl() {
        return url;
    }
    public static String getUsername() {
        return username;
    }
    public static String getPassword() {
        return password;
    }

    public static Connection getConnection(String url, String username, String password){
        Connection connection = null;
        try{
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            System.out.println("Have some issue in connecting to database");
            throw new RuntimeException(e);
        }
        return connection;
    }
    public static Connection getConnection(){
        Connection connection = null;
        try{
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            System.out.println("Have some issue in connecting to database");
            throw new RuntimeException(e);
        }
        return connection;
    }
    public static List<Student> getStudents(){
        List<Student> students = new ArrayList<>();
        String query = "SELECT * from students";
        try(Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)){
            ResultSet resultSet = preparedStatement.executeQuery();
            ArrayList<Integer> grades = new ArrayList<>();
            while (resultSet.next()){
                int id = resultSet.getInt( "id");
                String name = resultSet.getString("name");
                int passport = resultSet.getInt("passport");
                Array z = resultSet.getArray("grades");
                Integer[] numbers = (Integer[])z.getArray();
                for (int i = 0; i < numbers.length; i++){
                    grades.add(numbers[i]);
                }
                students.add(new Student(id, name, passport, grades.stream().mapToInt(i -> i).toArray()));
            }
            return students;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static Student getStudent(int id) {
        String query = "SELECT * from students WHERE id = ?";
        Student student;
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            String name = null;
            int passport = 0;
            ArrayList<Integer> grades = new ArrayList<>();
            while (resultSet.next()) {
                name = resultSet.getString("name");
                passport = resultSet.getInt("passport");
                Array z = resultSet.getArray("grades");
                Integer[] numbers = (Integer[])z.getArray();
                for (int i = 0; i < numbers.length; i++){
                    grades.add(numbers[i]);
                }
            }
            student = new Student(id, name, passport, grades.stream().mapToInt(i -> i).toArray());
            return student;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void createTable(){
        String query = "CREATE TABLE students (id SERIAL PRIMARY KEY, name VARCHAR(30), passport INTEGER, grades INTEGER[]);";
        try(Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void createStudent(Student student){
        String query = "INSERT INTO students (name, passport, grades) VALUES (?, ?, ?);";
        try(Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setString(1, student.getName());
            preparedStatement.setInt(2, student.getPassport());
            preparedStatement.setArray(3, connection.createArrayOf("int", Arrays.stream(student.getGrades()).mapToObj(i -> Integer.valueOf(i)).toArray()));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void updateStudent(int id, String name, int passport, int[] grades){
        String query = "UPDATE students SET name = ?, passport = ?, grades = ? WHERE id = ?;";
        try(Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, passport);
            preparedStatement.setArray(3, connection.createArrayOf("int", Arrays.stream(grades).mapToObj(i -> Integer.valueOf(i)).toArray()));
            preparedStatement.setInt(4, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void deleteStudent(int id){
        String query = "DELETE FROM students WHERE id = ?;";
        try(Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void dropTable(){
        String query = "DROP TABLE students;";
        try(Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
