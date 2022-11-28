package e_commerce;

import java.awt.Choice;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Login_Register {

    Scanner sc = new Scanner(System.in);
    int n, l, count, a, totalAmount, amount, i = 0;// for looping and used as length of array while comparing id 
    int quant[] = new int[20];
    int added[] = new int[20];
    String hprod[] = new String[50];
    String hName[] = new String[50];
    String hQuant[] = new String[20];
    String logId[] = new String[50];
    String logPass[] = new String[50];
    String cartId[] = new String[50];
    String itemId[] = new String[50];
    String itemName[] = new String[50];
    String itemPrice[] = new String[50];
    String itemDesc[] = new String[50];
    String itemQuality[] = new String[50];
    String myId, myPass;
    String Logged_id, Logged_Pass;
    String rName, rPhone, rEmail, rGender, rAdd, newId, newPass;// To Accept While Registering
    String buyInp, uInput, pincode, ui;

    public void Login() throws SQLException {

        System.out.println("Enter Your Login Id");
        Logged_id = sc.next();
        System.out.println("Enter Your Login Password");
        Logged_Pass = sc.next();
        Retrival();
        int entry = 0;
        for (int j = 0; j < i; j++) {
            if (Logged_id.equals(logId[j]) && Logged_Pass.equals(logPass[j])) {
                System.out.println("Login Successful\n\n");
                entry = 1;
                System.out.println("Press 1 for Shopping\nPress 2 for Order History");
                String str = sc.next();
                if (str.equals("1")) {
                    afterLogin();
                } else if (str.equals("2")) {
                    orderHistoryRetrieval();
                } else {
                    System.out.println("Enter Valid Choice");
                }
            }
        }
        if (entry == 0) {
            System.out.println("Wrong Id or Password");
//             Login();
            System.out.println("Not an User, Press 1 to register or any key to Login Again");
            String inp = sc.next();
            if (inp.equals("1")) {
                Register();
            } else {
                inp = "1";
                Login();
            }
        }
    }

    public void Retrival() throws SQLException {
        String uName = "root";
        String pass = "root";
        String url = "jdbc:mysql://localhost:3306/user";
        Connection con = DriverManager.getConnection(url, uName, pass);
        String query = "select * from login_details";

        Statement smt = con.createStatement();
        ResultSet rs = smt.executeQuery(query);
        String id, passw, name, phone, email, gender, address;
        while (rs.next()) {
            logId[i] = rs.getString("lid");
            logPass[i] = rs.getString("password");
            name = rs.getString("name");
            phone = rs.getString("phone");
            email = rs.getString("email");
            gender = rs.getString("gender");
            address = rs.getString("address");
            i++;
        }
    }

    public void Register() throws SQLException {
        System.out.println("Enter Your Name");
        rName = sc.next();
        System.out.println("Enter Your Phone No.");
        rPhone = sc.next();
        System.out.println("Enter Your Email");
        rEmail = sc.next();
        System.out.println("Enter Your Gender");
        rGender = sc.next();
        System.out.println("Enter Your Address");
        rAdd = sc.next();
        System.out.println("Registration Successful");
        double rand = Math.random() * (9999 - 1 + 1) + 1;
        int rand1 = (int) rand;
        System.out.println("Your Login Id is " + rName + rand1);
        newId = rName + rand1;
        int valid = 0;

        System.out.println("Enter Your Password");
        newPass = sc.next();
        while (valid == 0) {
            if (newPass.length() < 6) {
                System.out.println("Minimum 6 digits Password Required");
                System.out.println("Enter Your Password");
                newPass = sc.next();
            }
            if (newPass.length() >= 6) {
                valid = 1;
            }
            insertIntoDb();// Insertig the values into database
            Login();// Redirecting to login After registeration
        }
    }

    public void insertIntoDb() throws SQLException {
        String uName = "root";
        String pass = "root";
        String url = "jdbc:mysql://localhost:3306/user";
        Connection con = DriverManager.getConnection(url, uName, pass);
        System.out.println("Connected");
        String query = "insert into login_details(lid,password,name,phone,email,gender,address) values (?,?,?,?,?,?,?)";
        PreparedStatement psmt;
        psmt = con.prepareCall(query);

        psmt.setString(1, newId);
        psmt.setString(2, newPass);
        psmt.setString(3, rName);
        psmt.setString(4, rPhone);
        psmt.setString(5, rEmail);
        psmt.setString(6, rGender);
        psmt.setString(7, rAdd);
        psmt.executeUpdate();
        con.close();
        psmt.close();
    }

    private void afterLogin() throws SQLException {

        String uName = "root";
        String pass = "root";
        String url = "jdbc:mysql://localhost:3306/user";
        Connection con = DriverManager.getConnection(url, uName, pass);
        String query = "select * from ecom";
        Statement smt = con.createStatement();
        ResultSet rs = smt.executeQuery(query);
        String id, name, price, descr, quality;
        System.out.println("Which Item you want to Look After");
        System.out.println("\t\tPress Respective Keys Accordingly\n");
        i = 0;
        while (rs.next()) {
            itemId[i] = rs.getString("id");
            itemName[i] = rs.getString("name");
            System.out.println(i + 1 + "." + itemName[i] + "\n");
            itemPrice[i] = rs.getString("price");
            itemDesc[i] = rs.getString("description");
            itemQuality[i] = rs.getString("quality");
            i++;
        }
        inputForProd();
        buyingItem();
    }

    private void inputForProd() {
        uInput = sc.next();
        switch (uInput) {
            case "1":
                n = 1;
                added[0] = 1;
                count++;
                System.out.println(itemName[0] + "\n" + "Price= " + itemPrice[0] + " $" + "\n" + " Description= " + itemDesc[0] + "\n" + " Ratings= " + itemQuality[0] + "\n");
                break;
            case "2":
                n = 2;
                count++;
                added[1] = 2;
                System.out.println(itemName[1] + "\n" + "Price= " + itemPrice[1] + " $" + "\n" + " Description= " + itemDesc[1] + "\n" + " Ratings= " + itemQuality[1] + "\n");
                break;
            case "3":
                n = 3;
                count++;
                added[2] = 3;
                System.out.println(itemName[2] + "\n" + "Price= " + itemPrice[2] + " $" + " Description= " + "\n" + itemDesc[2] + "\n" + " Ratings= " + itemQuality[2] + "\n");
                break;
            case "4":
                n = 4;
                count++;
                added[3] = 4;
                System.out.println(itemName[3] + "\n" + "Price= " + itemPrice[3] + " $" + " Description= " + "\n" + itemDesc[3] + "\n" + " Ratings= " + itemQuality[3] + "\n");
                break;
            case "5":
                n = 5;
                count++;
                added[4] = 5;
                System.out.println(itemName[4] + "\n" + "Price= " + itemPrice[4] + " $" + " Description= " + "\n" + itemDesc[4] + "\n" + " Ratings= " + itemQuality[4] + "\n");
                break;
            case "6":
                n = 6;
                count++;
                added[5] = 6;
                System.out.println(itemName[5] + "\n" + "Price= " + itemPrice[5] + " $" + " Description= " + "\n" + itemDesc[5] + "\n" + " Ratings= " + itemQuality[5] + "\n");
                break;
            case "7":
                n = 7;
                count++;
                added[6] = 7;
                System.out.println(itemName[6] + "\n" + "Price= " + itemPrice[6] + " $" + "\n" + " Description= " + itemDesc[6] + "\n" + " Ratings= " + itemQuality[6] + "\n");
                break;
            case "8":
                n = 8;
                count++;
                added[7] = 8;
                System.out.println(itemName[7] + "\n" + "Price= " + itemPrice[7] + " $" + "\n" + " Description= " + itemDesc[7] + "\n" + " Ratings= " + itemQuality[7] + "\n");
                break;
            case "9":
                n = 9;
                count++;
                added[8] = 9;
                System.out.println(itemName[8] + "\n" + "Price= " + itemPrice[8] + " $" + "\n" + " Description= " + itemDesc[8] + "\n" + " Ratings= " + itemQuality[8] + "\n");
                break;
            case "10":
                n = 10;
                count++;
                added[9] = 10;
                System.out.println(itemName[9] + "\n" + "Price= " + itemPrice[9] + " $" + "\n" + " Description= " + itemDesc[9] + "\n" + " Ratings= " + itemQuality[9] + "\n");
                break;
            case "11":
                n = 11;
                count++;
                added[10] = 11;
                if (i >= 11) {
                    System.out.println(itemName[10] + "\n" + "Price= " + itemPrice[10] + " $" + "\n" + " Description= " + itemDesc[10] + "\n" + " Ratings= " + itemQuality[10] + "\n");
                } else {
                    System.out.println("Enter Valid Input");
                    inputForProd();
                }
                break;
            case "12":
                n = 12;
                count++;
                added[11] = 12;
                if (i >= 12) {
                    System.out.println(itemName[11] + "\n" + "Price= " + itemPrice[11] + " $" + "\n" + " Description= " + itemDesc[11] + "\n" + " Ratings= " + itemQuality[11] + "\n");
                } else {
                    System.out.println("Enter Valid Input");
                    inputForProd();
                }
                break;
            case "13":
                n = 13;
                count++;
                added[12] = 13;
                if (i >= 13) {
                    System.out.println(itemName[12] + "\n" + "Price= " + itemPrice[12] + " $" + "\n" + " Description= " + itemDesc[12] + "\n" + " Ratings= " + itemQuality[12] + "\n");
                } else {
                    System.out.println("Enter Valid Input");
                    inputForProd();
                }
                break;
            case "14":
                n = 14;
                count++;
                added[13] = 14;
                if (i >= 14) {
                    System.out.println(itemName[13] + "\n" + "Price= " + itemPrice[13] + " $" + "\n" + " Description= " + itemDesc[13] + "\n" + " Ratings= " + itemQuality[13] + "\n");
                } else {
                    System.out.println("Enter Valid Input");
                    inputForProd();
                }
                break;
            case "15":
                n = 15;
                count++;
                added[14] = 15;
                if (i >= 15) {
                    System.out.println(itemName[14] + "\n" + "Price= " + itemPrice[14] + " $" + "\n" + " Description= " + itemDesc[14] + "\n" + " Ratings= " + itemQuality[14] + "\n");
                } else {
                    System.out.println("Enter Valid Input");
                    inputForProd();
                }
                break;
            default:
                System.out.println("Enter Valid Input");
                inputForProd();
        }
    }

    private void buyingItem() throws SQLException {
        System.out.println("Press 1 for Add To CART or Any key to see more Product");
        ui = sc.next();
        l = 0;
        hprod[i] = ui;
        l++;
        if (ui.equals("1")) {
            cartId[n - 1] = uInput;// id for add to cart product

            switch (uInput) {
                case "1":
                    quant[0] += 1;
                    break;
                case "2":
                    quant[1] += 1;
                    break;
                case "3":
                    quant[2] += 1;
                    break;
                case "4":
                    quant[3] += 1;
                    break;
                case "5":
                    quant[4] += 1;
                    break;
                case "6":
                    quant[5] += 1;
                    break;
                case "7":
                    quant[6] += 1;
                    break;
                case "8":
                    quant[7] += 1;
                    break;
                case "9":
                    quant[8] += 1;
                    break;
                case "10":
                    quant[9] += 1;
                    break;
                case "11":
                    quant[10] += 1;
                    break;
                case "12":
                    quant[11] += 1;
                    break;
                case "13":
                    quant[12] += 1;
                    break;
                case "14":
                    quant[13] += 1;
                    break;
                case "15":
                    quant[14] += 1;
                    break;
            }

            System.out.println(" Press 1 to see Items in Your Cart \n Press Any key to add more Products ");
            buyInp = sc.next();
            if (buyInp.equals("1")) {
                System.out.println("Items in Your Cart are -->\n");
                for (int j = 0; j < 15; j++) {
                    if (added[j] != 0) {
                        System.out.println(itemName[j] + " " + itemPrice[j] + " $\t Quantity= " + quant[j] + "\n");
                        amount = Integer.parseInt(itemPrice[j]);
                        totalAmount = totalAmount + (amount);
                        hName[a] = itemName[j];
                        String dummy = "" + quant[j];
                        hQuant[a] = dummy;
                        a++;
                    }
                }
                System.out.println("Total Amount--> " + totalAmount + " $ \n");
                System.out.println("Press 1 to Buy Items in your cart\n Press 2 to Continue Shopping\n  Press Any key to Exit ");
                ui = sc.next();
                if (ui.equals("1")) {
                    Checkout();
                } else if (ui.equals("2")) {
                    afterLogin();
                } else {
                    System.out.println("More Exciting Offers are waiting for you Shop again");
                    return;
                }
            } else {
                afterLogin();
            }
        } else {
            System.out.println("\t\tPress Respective Keys Accordingly\n");
            inputForProd();
            buyingItem();
        }
    }

    private void Checkout() throws SQLException {
        System.out.println("Your Total Payable Amount is " + totalAmount + " $\n");
        System.out.println("Enter Your Pin code");
        pincode = sc.next();
        System.out.println("Your Total Payable Amount is " + totalAmount + " $\n");
        System.out.println("Press 1 to Order\n Any key for more Shopping");
        int order = sc.nextInt();
        if (order == 1) {
            History();
            System.out.println("Your Order Will Be Deleivered Shortly\n");
            System.out.println("Thank You For Shopping With Us \n\n Press 1 to Shop more\n Press Any Key to Exit\n");
            int uip = sc.nextInt();
            if (uip == 1) {
                afterLogin();
            } else {
                return;
            }
        } else {
            afterLogin();
        }
    }

    private void History() throws SQLException {
        Connection con;
        String uName = "root";
        String pass = "root";
        String url = "jdbc:mysql://localhost:3306/user";
        String query = "insert into order_history(id,name,quantity) values (?,?,?)";

        con = DriverManager.getConnection(url, uName, pass);
        PreparedStatement psmt;
        int i = 0;
        while (i < count) {
            psmt = con.prepareCall(query);
            psmt.setString(1, Logged_id);
            psmt.setString(2, hName[i]);
            psmt.setString(3, hQuant[i]);
            psmt.executeUpdate();
            i++;
        }
        System.out.println("Insertion Successful");
        con.close();
    }

    public void orderHistoryRetrieval() throws SQLException {
        String uName = "root";
        String pass = "root";
        String query = "select * from order_history where id='" + Logged_id + "'";

        String url = "jdbc:mysql://localhost:3306/user";

        Connection con = DriverManager.getConnection(url, uName, pass);
        Statement smt = con.createStatement();
        ResultSet rs = smt.executeQuery(query);
        while (rs.next()) {
            String id = rs.getString("id");
            String name = rs.getString("name");
            System.out.println("NAME =" + name + "\n");
            String quantity = rs.getString("quantity");
            System.out.println("QUANTITY =" + quantity + "\n\n\n");
        }// retrieval of data from database
        smt.close();
        con.close();
    }

}
