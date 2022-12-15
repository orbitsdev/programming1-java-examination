
import java.util.*;
import java.util.Scanner;
import javax.swing.JOptionPane;
import javax.swing.text.StyledEditorKit.BoldAction;

class Main {
    public static double companyInterestRate = .06;
    public static List<Loan> loanDatabase = new ArrayList<>();

    public static void main(String[] args) {

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
                        System.out.println("canceld");
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

    static boolean processNewLoan() {

        boolean continueNewLoanProcess = true;

        boolean hasError = false;

        String id_number = JOptionPane.showInputDialog("Enter Id ");
        String first_name = JOptionPane.showInputDialog("Enter First Name");
        String middle_name = JOptionPane.showInputDialog("Enter Middle Name");
        String last_name = JOptionPane.showInputDialog("Enter Last Name");
        String age = JOptionPane.showInputDialog("Enter age");
        double salary = Double.parseDouble(JOptionPane.showInputDialog("Enter Salary"));
        int getIsEmployed = JOptionPane.showConfirmDialog(null, "Are you employed? ", "Validatiing",
                JOptionPane.YES_NO_OPTION);
        boolean isEmployed = false;

        if (getIsEmployed == 0) {
            isEmployed = true;
        }
        if (getIsEmployed == 1) {
            isEmployed = false;
        }

        double tobe_borrowed = Double.parseDouble(JOptionPane.showInputDialog("Enter Amount to borrow"));
        int payment_terms = Integer.parseInt(JOptionPane.showInputDialog("Payment terms "));

        int getIsPayed = JOptionPane.showConfirmDialog(null, "Pay the loan ", "Validatiing",
                JOptionPane.YES_NO_OPTION);

        boolean isPayed = false;
        if (getIsPayed == 0) {
            isPayed = true;
        }
        if (getIsPayed == 1) {
            isPayed = false;
        }
        if (isEmployed == false) {
            showWarning("Sorry You Cannot Borrowed Money for now due to you are not employed. ");
            continueNewLoanProcess = false;
            hasError = true;
        }

        if (isEmployed) {

            if (Math.round(salary) < 10000) {
                showWarning(
                        "Sorry you cannot borrow money for now due to your income. 10000 was the minimum requires for this tranactions");
                continueNewLoanProcess = false;
                hasError = true;

            }

        }

        if (Math.round(tobe_borrowed) < 5000) {

            showWarning("Borrowed Ammount Should Be Greater than 5000 ");
            hasError = true;
        }

        if (payment_terms < 5) {
            showWarning("Invalid Payment terms");
            continueNewLoanProcess = false;
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

            String details = getLoan(tobe_borrowed, payment_terms);
            showDetails(details);
            // continueNewLoanProcess = false;
            showDetails("Transaction Complete Loan was recorded");

        } else {

            showWarning("Transaction Failed");
        }

        return continueNewLoanProcess;
    }

    static boolean renewProcessLoan() {
        boolean continueNewLoanProcess = true;
        int validate_number = 3;
        boolean isValidating = true;
        boolean hasError = false;
        while (isValidating) {
            String response = JOptionPane
                    .showInputDialog("Enter ID NUMBER ,  if you wish to do other transaction press e");
            System.out.println(response);

            if (response == null) {
                showDetails("IF you wish to do new transaction press e to go back");
            } else {
                if (response.toLowerCase().equals("e")) {
                    isValidating = false;
                    // continueNewLoanProcess = false;
                    System.out.println("Programm Exit");
                } else {

                    if (loanDatabase.size() > 0) {
                        System.out.println(loanDatabase.get(0).id_number);
                        System.out.println(response);

                        for (int i = 0; i < loanDatabase.size(); i++) {
                            if (response.equals(loanDatabase.get(i).id_number)) {

                                System.out.println(loanDatabase.get(i).isPayed);

                                if (loanDatabase.get(i).isPayed) {

                                    double tobe_borrowed = Double
                                            .parseDouble(JOptionPane.showInputDialog("Enter Amount to borrow"));
                                    if (Math.round(tobe_borrowed) < 5000) {

                                        showWarning("Borrowed Ammount Should Be Greater than 5000 ");
                                        // continueNewLoanProcess = false;
                                        hasError = true;

                                    }

                                    int payment_terms = Integer.parseInt(JOptionPane.showInputDialog("Payment terms "));
                                    if (payment_terms < 5) {
                                        showWarning("Invalid Payment terms");
                                        // continueNewLoanProcess = false;

                                        hasError = true;

                                    }

                                    if (!hasError) {

                                        String details = getLoan(tobe_borrowed, payment_terms);
                                        showDetails(details);
                                        showDetails("Transaction Complete Loan ");
                                        isValidating = false;

                                    }

                                } else {

                                    validate_number--;
                                    if (validate_number != 0) {

                                        showWarning("Loan was not paid yet you cannot borrow money");
                                        showWarning("Press e to change transaction");
                                    } else {
                                        showWarning(
                                                " Multiple attemp is prohibeted we are going to close the programm now  ");
                                        isValidating = false;
                                        continueNewLoanProcess = false;

                                    }
                                }

                            } else {
                                if (i == loanDatabase.size()) {

                                    showDetails("Invalid Response");
                                }
                            }
                        }

                    } else {
                        validate_number--;
                        if (validate_number != 0) {

                            showWarning("Loan Record is empty if you wish to do new transcction press e to go backe");
                        } else {
                            showWarning(" Multiple attemp is prohibeted we are going to close the programm now  ");
                            isValidating = false;
                            continueNewLoanProcess = false;

                        }

                    }

                }
            }
        }

        return continueNewLoanProcess;
    }

    static boolean tryTransaction() {
        Boolean continueProcess = true;
        String response = JOptionPane
                .showInputDialog("Press N For New Loan,  Press R for renew loan , Press E to stop transaction");
        if (response == null) {
            showWarning("If you wish to cancel transaction  Press e");
        } else {

            if (response.toLowerCase().equals("n")) {
                return continueProcess = processNewLoan();

            } else if (response.toLowerCase().equals("r")) {

                return continueProcess = renewProcessLoan();

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