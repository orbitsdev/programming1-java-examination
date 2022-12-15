
import java.util.*;
import java.util.Scanner;
import javax.swing.JOptionPane;
import javax.swing.text.StyledEditorKit.BoldAction;

class Main {
    public static double companyInterestRate = .06;
    public static List<Loan> loanDatabase = new ArrayList<>();

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        // company interest

        // create 3 ccount
        UserAccount user1 = new UserAccount("user", "user");
        UserAccount user2 = new UserAccount("user2@123", "user2password");
        UserAccount user3 = new UserAccount("user3@123", "user3password");

        // put 3 acccount inside array
        UserAccount registered_account[] = { user1, user2, user3 };

        Boolean programmIsRunning = true;
        Boolean loginAttemp = true;
        Boolean isLogin = false;

        // keep running if true
        while (programmIsRunning) {

            // keep looping if true
            while (loginAttemp) {

                isLogin = tryLogin(registered_account);

                if (isLogin == false) {
                    showWarning("User Not Found");
                    String response = JOptionPane
                            .showInputDialog("Do you want to try login again?  Pres any key to continue and E to stop");

                    if (response == null) {
                        System.out.println("CANCELD");
                    } else {

                        if (response.toLowerCase().equals("e")) {
                            loginAttemp = false;
                            programmIsRunning = false;
                        }

                    }

                } else {

                    loginAttemp = false;
                    Boolean transaction = true;
                    Boolean isContinueProcess = true;
                    while (transaction) {

                        while (isContinueProcess) {

                            isContinueProcess = tryTransaction();
                            if (isContinueProcess == false) {
                                transaction = false;
                            }

                        }

                    }

                }

            }

        }

    }

    public static void showWarning(String message) {
        JOptionPane.showMessageDialog(null, message, "ERROR", JOptionPane.ERROR_MESSAGE);

    }

    public static void showDetails(String message) {
        JOptionPane.showMessageDialog(null, message, "Details", JOptionPane.INFORMATION_MESSAGE);

    }

    public static void newLoan() {
        System.out.println("new Loan");
    }

    static boolean tryTransaction() {
        Boolean continueProcess = true;
        String response = JOptionPane
                .showInputDialog("Press N For New Loan,  Press R for renew loan , Press E to stop transaction");
        if (response == null) {
            showWarning("If you wish to cancel transaction  Press e");
        } else {
            if (response.toLowerCase().equals("n")) {

                Boolean hasError = false;

                String id_number = JOptionPane.showInputDialog("Enter Id ");
                String first_name = JOptionPane.showInputDialog("Enter First Name");
                String middle_name = JOptionPane.showInputDialog("Enter Middle Name");
                String last_name = JOptionPane.showInputDialog("Enter Last Name");
                int age = Integer.parseInt(JOptionPane.showInputDialog("Enter age"));
                double salary = Double.parseDouble(JOptionPane.showInputDialog("Enter Salary"));
                int getIsEmployed = JOptionPane.showConfirmDialog(null, "Are you employed? ", "Validatiing",
                        JOptionPane.YES_NO_OPTION);
                boolean isEmployed = false;

                if (getIsEmployed == 1) {
                    isEmployed = false;
                }

                if (getIsEmployed == 0) {
                    isEmployed = true;
                }

                double tobe_borrowed = Double.parseDouble(JOptionPane.showInputDialog("Enter Amount to borrow"));
                int payment_terms = Integer.parseInt(JOptionPane.showInputDialog("Payment terms "));


                boolean isPayed = false;
              

                if (isEmployed == false) {
                    showWarning("Sorry You Cannot Borrowed Mony ");
                    continueProcess = false;
                    hasError = true;
                }

                if (isEmployed) {

                    if (Math.round(salary) < 10000) {
                        showWarning("You cannot borrrow  Money ");
                        continueProcess = false;
                        hasError = true;

                    }

                }

                if (Math.round(tobe_borrowed) < 5000) {

                    showWarning("Borrowed Ammount Should Be Greater than 5000 ");
                    hasError = true;
                }

                if (payment_terms < 5) {
                    showWarning("Invalid Payment terms");
                    continueProcess = false;
                    hasError = true;

                }

                if (hasError == false) {

                    Loan loan = new Loan(id_number,
                            first_name,
                            last_name,
                            middle_name,
                            age,
                            salary,
                            isEmployed,
                            tobe_borrowed,
                            payment_terms,
                            isPayed);

                            loanDatabase.add(loan);

                            System.out.println(loanDatabase.size());


                    System.out.println("id_number" + loanDatabase.get(0).id_number);
                    System.out.println("first_name" + loanDatabase.get(0).first_name);
                    System.out.println("last_name" + loanDatabase.get(0).last_name);
                    System.out.println("middle_name" + loanDatabase.get(0).middle_name);
                    System.out.println("age" + loanDatabase.get(0).age);
                    System.out.println("salary" + loanDatabase.get(0).salary);
                    System.out.println("isEmployed" + loanDatabase.get(0).isEmployed);
                    System.out.println("payment" + loanDatabase.get(0).payment_terms);
                    System.out.println("isPayed" + loanDatabase.get(0).isPayed);

                    String details = getLoan(tobe_borrowed, payment_terms);
                    showDetails(details);
                    continueProcess = false;
                    showDetails("Transaction Complete");

                } else {

                    showWarning("Transaction Failed");
                }

            } else if (response.toLowerCase().equals("r")) {
                System.out.println("RewnewEw Loand");
            } else if (response.toLowerCase().equals("e")) {

                String decision = JOptionPane.showInputDialog(
                        "Transaction was canceled. Pess any key to continue . Press E to exit programm");

                if (decision != null) {
                    if (decision.toLowerCase().equals("e")) {
                        continueProcess = false;
                    }
                }

            } else {
                showWarning("Invalid Input Please Follow Instructions");

            }
        }

        return continueProcess;

    }

    static String getLoan(double amount_loan, int payment_terms) {
        String loanDetails = "";

        double interest = amount_loan * companyInterestRate;
        double amount_loan_with_interest = interest + amount_loan;
        double loan = amount_loan_with_interest / payment_terms;

        loanDetails = "Total amount: " + Math.round(amount_loan_with_interest) + "\nTotal Interest: "
                + Math.round(interest) + "\nTotal Loan: "
                + Math.round(loan);
        return loanDetails;
    }

    static boolean tryLogin(UserAccount[] registered_account) {
        Boolean isAuthenticated = false;
        String username = JOptionPane.showInputDialog("Enter Username");
        String password = JOptionPane.showInputDialog("Enter Password");

        if (username == null) {
            return false;
        }

        if (password == null) {
            return false;
        }

        for (int index = 0; index < registered_account.length; index++) {
            if (username.equals(registered_account[index].username)
                    && password.equals(registered_account[index].password)) {
                isAuthenticated = true;
            }
        }

        return isAuthenticated;
    }

}