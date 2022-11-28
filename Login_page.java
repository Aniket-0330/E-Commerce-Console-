package e_commerce;

import java.sql.SQLException;
import java.util.Scanner;

public class Login_page {

    Scanner sc = new Scanner(System.in);
    int choice;

    void Display() throws SQLException {
        System.out.printf("\t\t\t\t E-Com SHOPPING\n\n\n\n");
        System.out.println("\t\tPress Respective Keys Accordingly\n");
        System.out.println("\t\t1.LOGIN\n\t\t2.REGISTER\n\t\t3.ADMIN LOGIN\n");
        choice = sc.nextInt();
        Case();
    }

    void Case() throws SQLException {
        Login_Register c = new Login_Register();
        Admin_Login all = new Admin_Login();

        if (choice == 1) {
            c.Login();
        } else if (choice == 2) {
            c.Register();
        } else if (choice == 3) {
            all.adm_login();
        } else {
            System.out.println("Enter Valid Choice\n");
        }
    }

    public static void main(String[] args) throws SQLException {
        Login_page lr = new Login_page();
        lr.Display();
    }
}
