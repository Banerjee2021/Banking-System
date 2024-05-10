import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

class BankAccount {
    private int balance;

    public BankAccount(int initialBalance) {
        this.balance = initialBalance;
    }

    public synchronized void deposit(int amount) {
        balance += amount;
    }

    public synchronized void withdraw(int amount) {
        if (balance >= amount) {
            balance -= amount;
        }
    }

    public synchronized int getBalance() {
        return balance;
    }
}

class TransactionLogger {
    public static void logTransaction(String message) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timestamp = dateFormat.format(new Date());
        System.out.println(timestamp + " - " + message);
    }
}

class UserInterface extends JFrame {
    // private BankAccount account;
    private JTextArea logTextArea;
    @SuppressWarnings("unused")
    private BankAccount account;

    public UserInterface(BankAccount account) {
        this.account = account;

        setTitle("Banking System");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        logTextArea = new JTextArea();
        logTextArea.setEditable(false);

        JButton depositButton = new JButton("Deposit");
        JButton withdrawButton = new JButton("Withdraw");
        JButton balanceButton = new JButton("Check Balance");

        depositButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int amount = Integer.parseInt(JOptionPane.showInputDialog("Enter deposit amount:"));
                account.deposit(amount);
                logTransaction("Deposit: " + amount);
            }
        });

        withdrawButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int amount = Integer.parseInt(JOptionPane.showInputDialog("Enter withdrawal amount:"));
                account.withdraw(amount);
                logTransaction("Withdrawal: " + amount);
            }
        });

        balanceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int balance = account.getBalance();
                JOptionPane.showMessageDialog(null, "Current Balance: " + balance);
            }
        });

        setLayout(new BorderLayout());
        add(logTextArea, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(depositButton);
        buttonPanel.add(withdrawButton);
        buttonPanel.add(balanceButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void logTransaction(String message) {
        TransactionLogger.logTransaction(message);
        logTextArea.append(message + "\n");
    }
}

public class BankingSystem {
    public static void main(String[] args) {
        BankAccount account = new BankAccount(1000);
        UserInterface ui = new UserInterface(account);

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                ui.setVisible(true);
            }
        });
    }
}
