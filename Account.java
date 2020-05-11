import javax.swing.*;
import java.io.*;
import java.nio.file.Paths;
import java.util.Scanner;

// All console output can be deleted or replaced with GUI later on.

public class Account {
    private String username;
    private String password;
    private String first_name;
    private String email;
    private int total_cash;
    private int total_winnings;

    private String sourceName = "user_info.txt";


    public Account()
    {
    }


    // Sets up a new account
    public void initializeAccount(String username, String password, String first_name, String email)
    {
        File tempFile = new File(sourceName);

        //Creates the user_info.txt file for storing account details if it doesn't already exist.
        try
        {
            if (tempFile.createNewFile())
                System.out.println("user_info.txt file created."); // to be deleted
            else
                System.out.println("");  // user_info.txt file already exists, to be deleted
        }
        catch(IOException e)
        {
            System.out.println("Error checking for file creation.");
        }

        this.username = username;    // Takes the user info from the text file and stores it into the account
        this.password = password;
        this.first_name = first_name;
        this.email = email;
        total_cash = 50;
        total_winnings = 0;

        //save?
    }


    public void save()
    {
        try
        {
            FileWriter filewriter = new FileWriter(sourceName);

            filewriter.write(username + ", " + password + ", " + first_name + ", " + email
                    + ", " + total_cash + ", " + total_winnings); //might need to add \n?

            filewriter.close();
        }
        catch(IOException e)
        {
            System.out.println("Error storing account information into text file.");
        }
    }


    // Used to display account information.
    public String toString()
    {
        return("\nAccount Information\nUsername: " + username + "\nFirst Name: " + first_name +
                "\nTotal cash: " + total_cash + "\nTotal winnings: " + total_winnings + "\n");
    }

                                        /*--------------- Anchors ----------------*/


    // Anchor to display a players username to others at the table
    public String getUsername() {
        return username;
    }

    public String getFirst_name() {
        return first_name;
    }

    // Anchor to check if a player has sufficient funds
    public int getTotal_cash() {
        return total_cash;
    }

    public int getTotal_winnings() {
        return total_winnings;
    }


    // Anchor for modifying a player's total cash.
    public void setTotal_cash(int total_cash_change) {  //this can be increased/decreased depending on if the
        this.total_cash += total_cash_change;           //total_cash_change is positive or negative

        if (this.total_cash < 0)    //ensures a player's total_cash can't go below 0
            this.total_cash = 0;
    }


    // Anchor for updating a player's total winnings.
    public void setTotal_winnings(int total_winnings_change) {  //this can only be increased
        this.total_winnings += total_winnings_change;
    }




    /* Anchor for verifying if a user logged in to their account.

    The method type can be changed, my thought process is that depending on the number returned,
    the GUI can display a message detailing whether sign in was successful or not.
    A return value of 0 would mean the user signed in successfully.
    A return value of 1 would mean no account information exists on this computer.
    A return value of 2 would mean the username was incorrect.
    A return value of 3 would mean the password was incorrect.
    A return value of 4 would would be an exception error.
    */
    public int accountValidation(String tempUsername, String tempPassword)
    {
        String nLine;
        // int i = 0; is used if multiple accounts is supported
        // increasing i by 6 means we're checking the next account on the computer

        File tempFile = new File(sourceName);
        boolean t = tempFile.exists();

        if(!t || tempFile.length() == 0)  // if file doesn't exist yet or file is empty
        {
            return 1;
        }

        try (Scanner txtData = new Scanner(Paths.get(sourceName)))
        {
            try
            {
                while(txtData.hasNextLine())
                {
                    nLine = txtData.nextLine();
                    String[] tempArray = nLine.split(", ");

                    if(tempArray[0].equals(tempUsername))             //ta[0+i] if support for multiple accounts
                    {
                        if(tempArray[1].equals(tempPassword))         //ta[1+i] if support for multiple accounts
                        {
                            // username and password are correct, login accepted
                            // store user info from text file into account

                            username = tempArray[0];
                            password = tempArray[1];
                            first_name = tempArray[2];
                            email = tempArray[3];
                            total_cash = Integer.parseInt(tempArray[4]);
                            total_winnings = Integer.parseInt(tempArray[5]);

                            return 0;
                        }
                        else
                            return 3;
                    }
                    else
                        return 2; // else statement with i+=6 if support for multiple accounts, remove return 2 else
                }
                return 1;                // return 2 if support multiple accounts
            }
            catch(ArrayIndexOutOfBoundsException e)
            {
                System.out.println("Error in while loop.");
                return 4;
            }
        }
        catch(IOException e)
        {
            System.out.println("Error reading in user data into temp array.");
            return 4;
        }
    }


    /*
    Add a method to open a credit line?
     */
}
