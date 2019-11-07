//JavaFX Project -- Section 11.8 -- Anna Collins
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.Date;
import java.lang.String;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;


public class Account extends Application{

    //Define the variables
    private int id;
    private double balance;
    private static double annualInterestRate;
    private Date dateCreated;
    private String name;
    ArrayList transactions = new ArrayList();


    //default constructor
    public Account(){
        name = "";
        id = 0;
        balance = 0;
        annualInterestRate = 0;
        dateCreated = new Date();
        transactions = new ArrayList<Transaction>();
    }
    //Defined constructor for setting ID and balance
    public Account(int id, double balance) {
        name = "";
        this.id = id;
        this.balance = balance;
        dateCreated = new Date();
        transactions = new ArrayList<Transaction>();
    }

    public Account(int newId, double newBalance, String name){
        id = newId;
        balance = newBalance;
        this.name = name;
        dateCreated = new Date();
    }

    public Account(String name, double newAnnualInterestRate, int newId){
        id = newId;
        this.name = name;
        annualInterestRate = newAnnualInterestRate;
    }

    //Getters and Setters
    public int getId(){
        return this.id;
    }

    public String getName(){
        return this.name;
    }

    public double getBalance(){
        return balance;
    }

    public double getAnnualInterestRate() {
        return annualInterestRate;
    }

    public void setId(int newId) {
        this.id = newId;
    }

    public void setBalance(double newBalance){
        this.balance = newBalance;
    }

    public void setAnnualInterestRate(double annualInterestRate){
        this.annualInterestRate = annualInterestRate;
    }


    public double getMontlyInterest(){
        return balance * (annualInterestRate / 1200);
    }

    public Date getDateCreated(){
        return dateCreated;
    }

    public ArrayList getTransactions(){
        return this.transactions;
    }

    //method for obtaining transactions and adding them either for withdraw or deposit
    public void withdraw(double amount){
        balance -= amount;
        transactions.add(new Transaction('W', amount, balance, ""));

    }

    public void deposit(double amount){
        balance += amount;
        transactions.add(new Transaction('D', amount, balance, "" ));

    }

    public void start(Stage primaryStage)
    //Main
    {

        //create the GUI
        primaryStage.setTitle("Banking 101");
        BorderPane pane = new BorderPane();
        Button _depositBtn = new Button("Deposit");
        Button _withdrawBtn = new Button("Withdraw");
        Button _completeBtn = new Button("Complete");
        Label _welcomeLabel = new Label("Welcome to your online banking");
        TextField _name = new TextField();
        TextField _amount = new TextField();
        TextField _id = new TextField();
        Label _nameLabel = new Label("Name: ");
        Label _amountLabel = new Label("Amount: ");
        Label _idLabel = new Label("ID: ");
        Button _enter = new Button("Enter");
        TextField _transactionTextField = new TextField();
        TextArea _transactions = new TextArea();
        VBox transactionBox = new VBox(_transactionTextField, _depositBtn,_withdrawBtn, _completeBtn);
        HBox infoBox = new HBox(_idLabel, _id, _nameLabel, _name, _amountLabel, _amount, _enter);
        infoBox.setSpacing(10);
        transactionBox.setSpacing(10);
        _transactions.setEditable(false);
        _transactions.scrollLeftProperty();
        pane.setBottom(_welcomeLabel);
        pane.setRight(transactionBox);
        pane.setCenter(_transactions);
        pane.setTop(infoBox);

        //create the new Account
        Account acc = new Account();
        acc.setAnnualInterestRate(1.5);//define the interest rate

        //Button action for user info
        _enter.setOnAction(e -> {
            try{
                int _idInput = Integer.parseInt(_id.getText());
                double _amountInput = Double.parseDouble(_amount.getText());
                acc.id = _idInput;
                acc.balance = _amountInput;
            }
            catch (NumberFormatException ex){
                _transactions.setText("ERROR: Please enter a numeric number for the ID and/or balance \n " +
                        "this message will disappear after you complete the transactions");
            }


            String _nameInput = _name.getText();
            acc.name = _nameInput;
            _amount.setText("");
            _name.setText("");
            _id.setText("");

        });

        //Deposit button
        _depositBtn.setOnAction(e ->{
            try {
                int amountEntered = Integer.parseInt(_transactionTextField.getText());
                acc.deposit(amountEntered);
            }
            catch (NumberFormatException ex){
                _transactions.setText("Deposit amount is not a valid number");
            }
            _transactionTextField.setText("");

        });

        //withdraw button
        _withdrawBtn.setOnAction(e -> {
            try{
                int amountEntered = Integer.parseInt(_transactionTextField.getText());
                acc.withdraw(amountEntered);
            }
            catch (NumberFormatException ex) {
                _transactions.setText("Withdraw amount is not a valid number");
            }

            _transactionTextField.setText("");
        });

        //list for all transactions performed
        ArrayList list = acc.getTransactions();


        //Complete button. Will show all info the text field
        _completeBtn.setOnAction(e ->
    {
        _transactions.setText("Name: " + acc.getName() +  "\n"
                + "Annual Interest: $" + acc.getMontlyInterest() + "\n" +
                "Current Balance: $" + acc.getBalance() + "\n\n Transactions:");

        for(int i =0; i < list.size(); i++) {
            Transaction t = (Transaction) list.get(i);
            _transactions.appendText("\n" +
                    (t.getDate().toString()) + " " + (String.format("%.2f",t.getAmount()) +
                    " " + (Character.toString(t.getType())) + " " + (String.format("%.2f",t.getBalance()))));
        }

    });


        Scene scene = new Scene(pane);
        primaryStage.setScene(scene);
        primaryStage.show();

    }
    public static void main(String[] args) {
        Application.launch(args);
    }
}
