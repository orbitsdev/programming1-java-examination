
import java.util.*;
import java.util.Scanner;
import javax.swing.JOptionPane;
import javax.swing.text.StyledEditorKit.BoldAction;

class Main {

    // GLOBAL VARIABLE
    public static double companyInterestRate = .06;
    public static List<Loan> loanDatabase = new ArrayList<>();

    public static void main(String[] args) {


        // CREATE USERACCOUNT CLASSS
        // CREATE 3 USERS
        UserAccount user1 = new UserAccount("user", "user");
        UserAccount user2 = new UserAccount("user2@123", "user2password");
        UserAccount user3 = new UserAccount("user3@123", "user3password");

        // PUT USERS INSIDE REGISTERED ARRAY
        UserAccount registered_account[] = {user1, user2, user3};

        // USE TO STOP WHOLE SYSTEM
        Boolean programmIsRunning = true;

        // USE FOR LOGIN
        Boolean loginAttemp = true;
        Boolean isLogin = false;

        // KEEP RUNNIN IF TRUE
        while (programmIsRunning) {

            // KEEP LOOPING IF TRUE
            while (loginAttemp) {

                // RETURN FALSE WHEN NOT CORRECT
                isLogin = tryLogin(registered_account);

                // NO USER FOUND SHOW DIALOG
                if (isLogin == false) {
                    // SHOW WARNING
                    showWarning("User Not Found");
                    String response = JOptionPane.showInputDialog(
                            "Do you want to try login again?  Pres any key to continue and E to stop");
                    if (response == null) {
                        // WHEN USER CLICK CANCEL DO NOTHING
                    } else {
                        // WHEN USER ENTER STOP THE LOGIN
                        if (response.toLowerCase().equals("e")) {
                            loginAttemp = false;
                            programmIsRunning = false;
                        }

                    }

                } else {

                    // USER IS AUTHENTICATED
                    loginAttemp = false;
                    Boolean transaction = true;
                    Boolean isContinueProcess = true;

                    // NEW AND RENEW TRANSACTION
                    while (transaction) {
                        while (isContinueProcess) {
                            // SEPRATE LOGIC TO ORGANIZE
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

    // SHOW WARNING
    public static void showWarning(String message) {
        JOptionPane.showMessageDialog(null, message, "ERROR", JOptionPane.ERROR_MESSAGE);

    }


    // SHOW DETAILS
    public static void showDetails(String message) {
        JOptionPane.showMessageDialog(null, message, "Details", JOptionPane.INFORMATION_MESSAGE);
    }


    // ALWAYS RETURN TRUE UNLESS USERS PRESS E
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

        // JOPTIONPANE RETURN STRING AS DEFAULT CONVERT INT NUMBER TO BOLEAN
        if (getIsEmployed == 0) {
            isEmployed = true;
        }
        if (getIsEmployed == 1) {
            isEmployed = false;
        }

        // JOPTION IS RETRUN STRING AS DEFAULT CONVERT DATA BASE ON DATA TYPES
        double tobe_borrowed =
                Double.parseDouble(JOptionPane.showInputDialog("Enter Amount to borrow"));
        int payment_terms = Integer.parseInt(JOptionPane.showInputDialog("Payment terms "));
        int getIsPayed = JOptionPane.showConfirmDialog(null, "Pay the loan ", "Validatiing",
                JOptionPane.YES_NO_OPTION);

        // JOPTIONPANE RETURN NUMBER WE CREATE NEW VAARIABLE TO CONVERT INT NUMBER TO BELEAN
        boolean isPayed = false;
        if (getIsPayed == 0) {
            isPayed = true;
        }
        if (getIsPayed == 1) {
            isPayed = false;
        }

        // NOT EMPLOYED
        if (isEmployed == false) {
            showWarning("Sorry You Cannot Borrowed Money for now due to you are not employed. ");
            continueNewLoanProcess = false;
            hasError = true;
        }
        // EMPLOYED AND SALARY IS LOW
        if (isEmployed) {

            if (Math.round(salary) < 10000) {
                showWarning(
                        "Sorry you cannot borrow money for now due to your income. 10000 was the minimum requires for this tranactions");
                continueNewLoanProcess = false;
                hasError = true;

            }

        }
        // LESS THEAN 5000 SHOW ERROR
        if (Math.round(tobe_borrowed) < 5000) {

            showWarning("Borrowed Ammount Should Be Greater than 5000 ");
            hasError = true;
        }

        // LESS

        if (payment_terms < 5) {
            showWarning("Invalid Payment terms");
            continueNewLoanProcess = false;
            hasError = true;

        }

        if (hasError == false) {
            // CREATE LOAN OBJECT
            Loan loan = new Loan(id_number, first_name, last_name, middle_name, age, salary,
                    isEmployed, tobe_borrowed, payment_terms, isPayed);

            // STORE TO GLOBAL SO THAT IN RENEW LOAN USER CAN STILL ACCES THE DATA
            loanDatabase.add(loan);

            // COMPUTE LOAN AND RETURN STRING
            String details = getLoan(tobe_borrowed, payment_terms);
            // SHOW DETAILS
            showDetails(details);
            // SHOW SUCCESS
            showDetails("Transaction Complete Loan was recorded");

        } else {

            showWarning("Transaction Failed");
        }

        // ALWAYS RETURN TRUE ONLY WHEN USER PRESS E WILL RETURN FALSE. THIS WILL BE USE TO STOP THE
        // PROGRAMM
        return continueNewLoanProcess;
    }

    static boolean renewProcessLoan() {
        boolean continueNewLoanProcess = true;
        int validate_number = 3;

        // USE TO TRANSACTION LOOP
        boolean isValidating = true;
        boolean hasError = false;

        // KEEP TRANSACTION RUNNING IF TRUE
        while (isValidating) {
            boolean haValidationError = false;
            // GET ID NUMBER
            String response = JOptionPane.showInputDialog(
                    "Enter ID NUMBER ,  if you wish to do other transaction press e");

            // WHEN USER CLICK CANCEL
            if (response == null) {
                // SHOW DIALOG
                showDetails("IF you wish to do new transaction press e to go back");
            } else {
                // WHEN USER ENNTER E STOP TRANSACTION
                if (response.toLowerCase().equals("e")) {
                    isValidating = false;
                } else {

                    // CHECK IF DATABASW HA RECORD
                    if (loanDatabase.size() > 0) {


                        // LOOP RECORD
                        for (int i = 0; i < loanDatabase.size(); i++) {

                            // IF ID NUMBER IS EQUAL TO DATABASE RECORD
                            if (response.equals(loanDatabase.get(i).id_number)) {
                                // IF LOAN WAS PAYED
                                if (loanDatabase.get(i).isPayed) {

                                    // COMPUTE GET THE ID NUMBER AND CONPUTE LOAN AGAIN
                                    double tobe_borrowed = Double.parseDouble(
                                            JOptionPane.showInputDialog("Enter Amount to borrow"));

                                    // PROMP WHEN ENTER AMOUNT LESS HAN 5000
                                    if (Math.round(tobe_borrowed) < 5000) {
                                        showWarning(
                                                "Borrowed Ammount Should Be Greater than 5000 ");
                                        haValidationError = true;
                                    }
                                    if (!haValidationError) {
                                        int payment_terms = Integer.parseInt(
                                                JOptionPane.showInputDialog("Payment terms "));
                                        if (payment_terms < 5) {
                                            showWarning("Invalid Payment terms");
                                            haValidationError = true;
                                        }
                                        if (!haValidationError) {

                                            String details = getLoan(tobe_borrowed, payment_terms);
                                            showDetails(details);
                                            showDetails("Transaction Complete Loan ");
                                            isValidating = false;
                                        }
                                    }



                                } else {
                                    // EXIT PROGRAMM WHEN USER INTER INVALID OR TRYING TO ACCESS
                                    // THAT DOESNT EXIST IN DATABASE
                                    validate_number--;
                                    // IF ATTEM IS NOT ZERO YET SHOW ONLY WARNING
                                    if (validate_number != 0) {
                                        showWarning(
                                                "Loan was not paid yet you cannot borrow money");
                                        showWarning("Press e to change transaction");
                                    } else {
                                        // IF ZERO SHOW WARNING AND EXIT IN PROGRAMM
                                        showWarning(
                                                " Multiple attemp is prohibeted we are going to close the programm now  ");

                                        // STOP TRANSACTION
                                        isValidating = false;
                                        // STOP PROGRMM
                                        continueNewLoanProcess = false;

                                    }
                                }

                            } else {
                                // IF ID NUMBER DOESNT EXISt

                                validate_number--;
                                if (validate_number != 0) {

                                    showWarning("Invalid Response");
                                    hasError = true;

                                } else {
                                    showWarning(
                                            " Multiple attemp is prohibited we are going to close the programm now  ");
                                    isValidating = false;
                                    continueNewLoanProcess = false;

                                }



                            }
                        }

                    } else {
                        validate_number--;
                        if (validate_number != 0) {

                            showWarning(
                                    "Loan Record is empty if you wish to do new transcction press e to go backe");
                        } else {
                            showWarning(
                                    " Multiple attemp is prohibeted we are going to close the programm now  ");
                            isValidating = false;
                            continueNewLoanProcess = false;

                        }

                    }

                }
            }
        }

        // ALSWAYS RETURN TRUE ONLY IF USER ENTER E WILL STOP THE PROGRAMM OR WHEN USER ENTER
        // INVALID ID MULTIPLES TIMES
        return continueNewLoanProcess;
    }


    static boolean tryTransaction() {
        // THIS WILL BE USE TO STOP PROGRAMM
        Boolean continueProcess = true;

        // GET USER INPUTE
        String response = JOptionPane.showInputDialog(
                "Press N For New Loan,  Press R for renew loan , Press E to stop transaction");

        // WHEN USER PRESS CANCEL
        if (response == null) {
            // WHOW INFO MESSAGE
            showWarning("If you wish to cancel transaction  Press e");
        } else {

            // CONVERT USER INPUT TO LOWER CASE SO THAT WE WELL NOT WORRY IF USER ENTER CAPITALIZE
            // LETTER OR SMALL LETTER
            if (response.toLowerCase().equals("n")) {

                // WHEN USER PRESS N
                return continueProcess = processNewLoan();

                // WHEN USER PRESS R
            } else if (response.toLowerCase().equals("r")) {

                return continueProcess = renewProcessLoan();

                // WHEN USER PRESS E
            } else if (response.toLowerCase().equals("e")) {

                String decision = JOptionPane.showInputDialog(
                        "Transaction was canceled. Pess any key to continue . Press E to exit programm");

                if (decision != null) {
                    if (decision.toLowerCase().equals("e")) {
                        continueProcess = false;
                    }
                }

            } else {
                // WHEN USER ENTER OTHER CHRACTER
                showWarning("Invalid Input Please Follow Instructions");

            }
        }


        // ALWAYST RETURN TRUE. TO PROGRAMM KEEP LOPPIG / IT WILL RETURN FALSE IF USER PREESS E OR
        // NEWLOANTRANSACTION AND RENEW LOAN WILL RETURN FALSE
        return continueProcess;

    }

    static String getLoan(double amount_loan, int payment_terms) {
        String loanDetails = "";

        double interest = amount_loan * companyInterestRate;
        double amount_loan_with_interest = interest + amount_loan;
        double loan = amount_loan_with_interest / payment_terms;

        loanDetails = "Total amount: " + Math.round(amount_loan_with_interest)
                + "\nTotal Interest: " + Math.round(interest) + "\nTotal Loan: " + Math.round(loan);
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
