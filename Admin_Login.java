package e_commerce;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Admin_Login {

    Connection con;
    Scanner sc = new Scanner(System.in);
    String id1, id2, name1, price1, desc1, quality1;//for insertion
    String id, name, price, description, quality;//For retreival
    String admId = "Admin123";
    String admPass = "Admin69";
    String admIdEntered;
    String admPassEntered;
    String uName = "root";
    String pass = "root";
    String query = "select * from ecom";
    String url = "jdbc:mysql://localhost:3306/user";

    void adm_login() throws SQLException {
        System.out.println("Enter Admin Id");
        admIdEntered = sc.next();
        System.out.println("Enter Admin Password");
        admPassEntered = sc.next();
        if (admId.equals(admIdEntered) && admPass.equals(admPassEntered)) {
            System.out.println("Admin Login Successful");
            Operation();
        } else {
            System.out.println("Invalid Id or Password");
            adm_login();
        }
    }

    public void Operation() throws SQLException {
        Login_Register c1 = new Login_Register();
        System.out.println("\t\tPress Respective Keys Accordingly\n");
        System.out.println("\t\t1.View Products\n\t\t2.Insert Products\n\t\t3.Delete Products\n\t\t4.Login Details\n\t\t5.Order history\n\n");
        int inp = sc.nextInt();
        if (inp == 1) {
            productDetails();
        } else if (inp == 2) {
            Insertion();
        } else if (inp == 3) {
            Deletion();
        } else if (inp == 4) {
            loginDetails();
        } else if (inp == 5) {
            orderHistoryRetrieval();
        } else {
            System.out.println("Invalid Key");
            Operation();
        }
    } //operation

    public void productDetails() throws SQLException {

        con = DriverManager.getConnection(url, uName, pass);
        Statement smt = con.createStatement();
        ResultSet rs = smt.executeQuery(query);
        while (rs.next()) {
            id = rs.getString("id");
            System.out.println("ID = " + id + "\n");
            name = rs.getString("name");
            System.out.println("NAME =" + name + "\n");
            price = rs.getString("price");
            System.out.println("PRICE =" + price + "\n");
            description = rs.getString("description");
            System.out.println("DESCRIPTION =" + description + "\n");
            quality = rs.getString("quality");
            System.out.println("QUALITY =" + quality + "\n\n\n");
        }// retrieval of data from database
        smt.close();
        con.close();
        System.out.println("Press 1 to Exit");
        String inpt = sc.next();
        if (inpt.equals("1")) {
            return;
        }
        Operation();
    }// details

    public void Insertion() throws SQLException {
        con = DriverManager.getConnection(url, uName, pass);
        System.out.println("Enter Id of new Product");
        id2 = sc.nextLine();
        id1 = sc.nextLine();
        System.out.println("Enter Name of new Product");
        name1 = sc.nextLine();
        System.out.println(name1);
        System.out.println("Enter Price of new Product");
        price1 = sc.nextLine();
        System.out.println(price1);
        System.out.println("Enter Description of new Product");
        desc1 = sc.nextLine();
        System.out.println(desc1);
        System.out.println("Enter Quality of new Product");
        quality1 = sc.nextLine();

        String query = "insert into ecom(id,name,price,description,quality) values (?,?,?,?,?)";
        PreparedStatement psmt;
        psmt = con.prepareCall(query);
        psmt.setString(1, id1);
        psmt.setString(2, name1);
        psmt.setString(3, price1);
        psmt.setString(4, desc1);
        psmt.setString(5, quality1);
        psmt.executeUpdate();
        System.out.println("Insertion Successful");
        con.close();
        psmt.close();
        System.out.println("Press 1 to Exit");
        String inpt = sc.next();
        if (inpt.equals("1")) {
            return;
        }
        Operation();
    }//insertion

    public void Deletion() throws SQLException {
        con = DriverManager.getConnection(url, uName, pass);
        System.out.println("Enter Product Id you want to Delete");
        String id = sc.next();
        System.out.println("Enter again");
        int idCnf = sc.nextInt();
        String query = " delete from ecom where id='" + id + "'";
        String query1 = "select * from ecom";

        Statement smt = con.createStatement();
        ResultSet rs = smt.executeQuery(query1);// for retrieval of data
        PreparedStatement smtt;
        smtt = con.prepareStatement(query);

        int i = 1;
        while (rs.next()) {
            String sid = rs.getString("id");
            name = rs.getString("name");
            price = rs.getString("price");
            description = rs.getString("description");
            quality = rs.getString("quality");
            i++;
        }

        if (i < idCnf) {
            System.out.println("Product not Found Hence Unable to delete");
        } else {
            smtt.executeUpdate();
            System.out.println("Deleted Successfully");
        }
        con.close();
        smt.close();
        System.out.println("Press 1 to Exit");
        String inpt = sc.next();
        if (inpt.equals("1")) {
            return;
        }
        Operation();
    }//deletion

    private void loginDetails() throws SQLException {
        con = DriverManager.getConnection(url, uName, pass);
        String query = "select * from login_details";
        Statement smt = con.createStatement();
        ResultSet rs = smt.executeQuery(query);
        String lId, lPass, lName, lPhone, lEmail, lGender, lAddress;
        while (rs.next()) {
            lId = rs.getString("lid");
            System.out.println("LOGIN ID = " + lId + "\n");
            lPass = rs.getString("password");
            System.out.println("LOGIN PASSWORD = " + lPass + "\n");
            lName = rs.getString("name");
            System.out.println("NAME =" + lName + "\n");
            lPhone = rs.getString("phone");
            System.out.println("PHONE =" + lPhone + "\n");
            lEmail = rs.getString("email");
            System.out.println("EMAIL =" + lEmail + "\n");
            lGender = rs.getString("gender");
            System.out.println("GENDER =" + lGender + "\n");
            lAddress = rs.getString("address");
            System.out.println("ADDRESS =" + lAddress + "\n\n\n");
        }// retrieval of data from database
        smt.close();
        con.close();
        System.out.println("Press 1 to Exit");
        String inpt = sc.next();
        if (inpt.equals("1")) {
            return;
        }
        Operation();
    }

    public void orderHistoryRetrieval() throws SQLException {
        String uName = "root";
        String pass = "root";
        String query = "select * from order_history";

        String url = "jdbc:mysql://localhost:3306/user";

        Connection con = DriverManager.getConnection(url, uName, pass);
        Statement smt = con.createStatement();
        ResultSet rs = smt.executeQuery(query);
        while (rs.next()) {
            String id = rs.getString("id");
            System.out.println("ID = " + id + "\n");
            String name = rs.getString("name");
            System.out.println("NAME =" + name + "\n");
            String quantity = rs.getString("quantity");
            System.out.println("QUANTITY =" + quantity + "\n\n\n");
        }// retrieval of data from database
        smt.close();
    }
}
