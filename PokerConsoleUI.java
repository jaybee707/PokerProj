import java.util.Scanner;

public class PokerConsoleUI {
    private Scanner input = new Scanner(System.in);

    Account acc = new Account();

    public PokerConsoleUI()
    {
        accountPrompt();
    }


    public void mainMenu()
    {
        System.out.println("Enter 0 to start a game.\n"+
                "Enter 1 to display your account's information.\n" +
                "Enter 2 to save and quit.");

        int userInput = input.nextInt();

        if(userInput == 0)
        {
            // Start Game
        }
        else if(userInput == 1)
        {
            System.out.println(acc.toString());
            mainMenu();
        }
        else if(userInput == 2)
        {
            acc.save();
            System.exit(0);
        }
        else
        {
            System.out.println("Invalid input.");
            mainMenu();
        }
    }


    private void accountPrompt()
    {
        System.out.println("Enter 0 to create a new account.\n"+
                "Enter 1 to sign in to an existing account.\n" +
                "Enter 2 to quit.");

        int userInput = input.nextInt();

        if(userInput == 0)
            createAccount();

        else if(userInput == 1)
            signIn();

        else if(userInput == 2)
            System.exit(0);

        else
        {
            System.out.println("Invalid input.");
            accountPrompt();
        }
    }


    public void createAccount()
    {
        // Creating a new account currently overrides your previous account.
        System.out.println("Enter a username: ");
        String username = input.next();
        System.out.println("Enter a password: ");
        String password = input.next();
        System.out.println("Enter your first name: ");
        String first_name = input.next();
        System.out.println("Enter your email: ");
        String email = input.next();

        acc.initializeAccount(username,password,first_name,email);

        mainMenu();
    }


    public void signIn()
    {
        System.out.println("Enter your username: ");
        String validateUser = input.next();

        System.out.println("Enter your password: ");
        String validatePass = input.next();

        int output = acc.accountValidation(validateUser,validatePass);

        switch(output)
        {
            case 0:
                System.out.println("Successful sign in.\n");
                mainMenu();
                break;
            case 1:
                System.out.println("No accounts exist on this machine.");
                accountPrompt();
                break;  // possible error
            case 2:
                System.out.println("Username doesn't exist.");
                accountPrompt();
                break;
            case 3:
                System.out.println("Incorrect password.");
                accountPrompt();
                break;
            case 4:
                accountPrompt();
                break;
        }
    }
}