
public class Loan {

    public String id_number;
    public String first_name;
    public String last_name;
    public String middle_name;
    public String age;
    public double salary;
    public boolean isEmployed;
    public double borrowed;
    public int payment_terms;
    public boolean isPayed;
    public Loan(String id_number, String first_name, String last_name, String middle_name, String age, double salary,
            boolean isEmployed, double borrowed, int payment_terms, boolean isPayed) {
        this.id_number = id_number;
        this.first_name = first_name;
        this.last_name = last_name;
        this.middle_name = middle_name;
        this.age = age;
        this.salary = salary;
        this.isEmployed = isEmployed;
        this.borrowed = borrowed;
        this.payment_terms = payment_terms;
        this.isPayed = isPayed;
    }
   
    

}
