import javax.swing.*;

public class PokerGUI implements PokerUserInterface
{
    Account acc = new Account();

    public void signinMenu()
    {
        String[] commands = {"Create Account",
                "Sign In",
                "Quit"};

        int choice;

        do {
            choice = JOptionPane.showOptionDialog(null,
                    "Select a command",
                    "Sign In Menu",
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    commands,
                    commands[commands.length - 1]);

            switch (choice) {
                case 0: createAccount(); break;
                case 1: signIn(); break;
                case 2: System.exit(0); break;
                default:  // do nothing
            }

        } while (choice != commands.length-1);
        System.exit(0);
    }


    public void mainMenu()
    {
        String[] commands = {"Start Game",
                "Account Information",
                "Save and Quit"};

        int choice;

        do {
            choice = JOptionPane.showOptionDialog(null,
                    "Select a command",
                    "Sign In Menu",
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    commands,
                    commands[commands.length - 1]);

            switch (choice) {
                case 0: //Start Game
                    System.out.println("Game GUI to be added."); Game runTest = new Game();
                    break;
                case 1: displayAccountInfo(); break;
                case 2: acc.save(); System.exit(0);
                default:  // do nothing
            }

        } while (choice != commands.length-1);
        System.exit(0);
    }


    public void createAccount()
    {
        // Creating a new account overrides your previous account. Only one account is needed.

        String username = JOptionPane.showInputDialog("Enter username");
        if (username == null)
            return;		// cancel selected

        String password = JOptionPane.showInputDialog("Enter password");
        if (password == null)
            return;		// cancel selected

        String first_name = JOptionPane.showInputDialog("Enter your first name");
        if (first_name == null)
            return;		// cancel selected

        String email = JOptionPane.showInputDialog("Enter email");
        if (email == null)
            return;		// cancel selected

        acc.initializeAccount(username,password,first_name,email);

        mainMenu();
    }


    public void signIn()
    {
        String validateUser = JOptionPane.showInputDialog("Enter your username. ");
        if (validateUser == null)
            return;		// cancel selected

        String validatePass = JOptionPane.showInputDialog("Enter your password. ");
        if (validatePass == null)
            return;		// cancel selected

        int output = acc.accountValidation(validateUser,validatePass);

        switch(output)
        {
            case 0:
                JOptionPane.showMessageDialog(null,
                        "Successfully signed in.", "Sign In Confirmation",
                        JOptionPane.INFORMATION_MESSAGE);
                mainMenu();
                break;
            case 1:
                JOptionPane.showMessageDialog(null,
                        "No accounts exist on this machine.", "Error",
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            case 2:
                JOptionPane.showMessageDialog(null,
                        "Username doesn't exist.", "Error",
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            case 3:
                JOptionPane.showMessageDialog(null,
                        "Incorrect password.", "Error",
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            case 4:
                JOptionPane.showMessageDialog(null,
                        "Code Error.", "Error",
                        JOptionPane.INFORMATION_MESSAGE);
                return;
        }
    }


    public void displayAccountInfo()
    {
        JOptionPane.showMessageDialog(null, "Username: " + acc.getUsername() +
                "\nFirst Name: " + acc.getFirst_name() + "\nTotal cash: " + acc.getTotal_cash() + "\nTotal winnings: "
                + acc.getTotal_winnings(), "Account Information", JOptionPane.INFORMATION_MESSAGE);
    }
}
