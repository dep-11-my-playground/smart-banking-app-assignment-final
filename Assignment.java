
import java.util.Scanner;

public class Assignment {
    private static Scanner SCANNER = new Scanner(System.in);

        final static String CLEAR = "\033[H\033[2J";
        final static String COLOR_BLUE_BOLD = "\033[34;1m";
        final static String COLOR_RED_BOLD = "\033[31;1m";
        final static String COLOR_GREEN_BOLD = "\033[32;1m";
        final static String COLOR_GREEN = "\033[32m";
        final static String RESET = "\033[0m";

        final static String DASHBOARD = "💰 Welcome to Smart Banking App";
        final static String OPEN_ACCOUNT = "💵 Open New Account";   
        final static String DEPOSIT = "Deposit Money";
        final static String WITHDRAW = "Withdraw Money";
        final static String TRANSFER = "💸 Transfer Money";
        final static String ACCOUNT_BALANCE = "Check Acoount Balance";
        final static String DROP_ACCOUNT = "Drop Existing Account";

        final static String ERROR_MSG = String.format("%s%s%s\n", COLOR_RED_BOLD, "%s", RESET);
        final static String SUCCESS_MSG = String.format("%s%s%s\n", COLOR_GREEN_BOLD, "%s", RESET);
        final static String TRY_AGAIN_MSG = String.format("%s%s%s", COLOR_GREEN_BOLD, "Do you want to try again (Y/n)? ", RESET);

        static String[][] accounts = new String[0][];
        static boolean valid;
        static String accountNo;
        static String amount;
        static String accountName;
        static String accountID;
        static Double withdrawAmount;

        static String fromAccountAmount;
        static String toAccountAmount;
        static String fromAccountName;
        static String toAccountName;
        static Double transferAmount;
        static String fromAccountId;
        static String toAccountId;

        static String screen = DASHBOARD;
    public static void main(String[] args) {
          

        mainLoop:
        do{

            String APP_TITLE = String.format("%s%s%s", COLOR_BLUE_BOLD, screen, RESET);

            System.out.println(CLEAR);
            System.out.printf("%s%s%s\n", COLOR_BLUE_BOLD, "-". repeat(30), RESET);
            System.out.println(APP_TITLE);
            System.out.printf("%s%s%s\n", COLOR_BLUE_BOLD, "-". repeat(30), RESET);

            switch(screen){

                case DASHBOARD:
                    System.out.println("\n[1]. Open New Account");
                    System.out.println("[2]. Deposit Money");
                    System.out.println("[3]. Withdraw Money");
                    System.out.println("[4]. Transfer Money");
                    System.out.println("[5]. Check Acoount Balance");
                    System.out.println("[6]. Drop Existing Account");
                    System.out.println("[7]. Exit\n");
                    System.out.print("Enter an option to continue > ");

                    int option = SCANNER.nextInt();
                    SCANNER.nextLine();

                    switch(option){
                        case 1: screen = OPEN_ACCOUNT; break;
                        case 2: screen = DEPOSIT; break;
                        case 3: screen = WITHDRAW; break;
                        case 4: screen = TRANSFER; break;
                        case 5: screen = ACCOUNT_BALANCE; break;
                        case 6: screen = DROP_ACCOUNT;break;
                        case 7: System.exit(0); break;
                        default: continue;

                    }
                    break;
                
                case OPEN_ACCOUNT:{
                    String id = String.format("SDB-%05d", accounts.length + 1);
                    System.out.printf("ID: %s\n", id);
    
                    // Name validation
                    boolean valid;
                    String name;
                    double initialDeposit;
    
                    nameValidation:
                    do{
    
                        valid = true;
                        System.out.print("Name: ");
                        name = SCANNER.nextLine().strip();
    
                        if(name.isBlank()){
                            System.out.printf(ERROR_MSG,"Name can't be empty!" );
                            valid = false;
                            continue nameValidation;
                        }
    
                        for (int i = 0; i < name.length(); i++) {
                            if(!(Character.isLetter(name.charAt(i)) || Character.isSpaceChar(name.charAt(i)))){
                                System.out.printf(ERROR_MSG, "Invalid Name!");
                                valid = false;
                                continue nameValidation;
                            }
                        }
    
                    }while(!valid);
    
                    // Initial Deposit validation
                    initialDepositValidation:
                    do{
    
                        valid = true;
                        System.out.print("Initial Deposit: ");
                        initialDeposit = SCANNER.nextDouble();
                        SCANNER.nextLine();
                            
                        if(!(initialDeposit >= 5000)){
                            System.out.printf(ERROR_MSG, "Insufficient amount to deposit initially!");
                            valid = false;
                            continue initialDepositValidation;
                        }
    
                    }while(!valid);
    
                    String[][] newAccounts = new String[accounts.length +1][3];
                    for (int i = 0; i < accounts.length; i++) {
                        newAccounts[i] = accounts[i];
                    }
    
                    newAccounts[newAccounts.length-1][0] = id;
                    newAccounts[newAccounts.length-1][1] = name;
                    newAccounts[newAccounts.length-1][2] = initialDeposit+"";
    
                    accounts = newAccounts;
    
                    System.out.printf(SUCCESS_MSG, String.format("%s:%s has created successfully \n", id, name));
                    System.out.print("Do you want to add another account (Y/n)?");
                    if(SCANNER.nextLine().toUpperCase().strip().equals("Y")) continue;
                    else screen = DASHBOARD;
                    break;  
                }
                    
                case DEPOSIT:{
                    // acoount number validation
                    validateAccountNo("Enter Account No: ");
                    if(!valid) continue;

                    // Finding current balance
                    checkCurrentBalance();

                    // Deposit amount validation
                    Double depositAmount;
                    depositValidation:
                    do{
                        valid = true;
                        System.out.print("Deposit Amount: ");
                        depositAmount = SCANNER.nextDouble();
                        SCANNER.nextLine();
                        
                        if(depositAmount < 500){
                            System.out.printf(ERROR_MSG, "Insufficient Amount!");
                            valid = false;  
                            //continue;                       
                        }

                        if(valid == false){
                            System.out.print(TRY_AGAIN_MSG);
                            if(SCANNER.nextLine().strip().toUpperCase().equals("Y")){
                                continue depositValidation;
                            
                            }else {
                                screen = DASHBOARD;
                                continue mainLoop;
                            }                            
                        }    

                    }while(!valid);

                    Double newBalance = Double.valueOf(amount) + depositAmount;
                    System.out.printf("New Balance: %,.2f\n", newBalance);

                    for (int i = 0; i < accounts.length; i++) {
                        if(accounts[i][0].equals(accountNo)){
                            accounts[i][2] = newBalance+"";
                        }
                    }

                    System.out.print("Do you want to continue depositing (Y/n)?");
                    if(SCANNER.nextLine().toUpperCase().strip().equals("Y")) continue;
                    else screen = DASHBOARD;
                    break; 
                    }
           
                case WITHDRAW:{
                    // Account Validation
                    validateAccountNo("Enter Account No:");
                    if(!valid) continue;

                    // Cheking current balance
                    checkCurrentBalance();

                    // Withdraw amount validation                   
                    validateWithdrawAmount("Withdraw Amount: ");
                    if (!valid) continue;

                    Double newBalance = Double.valueOf(amount) - withdrawAmount;
                    System.out.printf("%sNew Balance:%s %,.2f\n", COLOR_GREEN, RESET, newBalance);

                    for (int i = 0; i < accounts.length; i++) {
                        if(accounts[i][0].equals(accountNo)){
                            accounts[i][2] = newBalance+"";
                        }
                    }

                    System.out.print("Do you want to withdraw again (Y/n)?");
                    if(SCANNER.nextLine().toUpperCase().strip().equals("Y")) continue;
                    else screen = DASHBOARD;
                    break; 
                    }

                case ACCOUNT_BALANCE:{
                    validateAccountNo("Enter Account No: ");
                    if(!valid) continue;
    
                    for (int i = 0; i < accounts.length; i++) {
                        if(accounts[i][0].equals(accountNo)){
                            amount = accounts[i][2];
                            accountName = accounts[i][1];
                            System.out.printf("%sName:%s %s\n", COLOR_GREEN, RESET, accountName);
                            System.out.printf("%sCurrent Account Balance:%s %s%,.2f\n", COLOR_GREEN, RESET,"Rs. ", Double.valueOf(amount));
                            System.out.printf("%sAvailable Balance to withdraw:%s %s%,.2f\n", COLOR_GREEN, RESET,"Rs. ",Double.valueOf(amount)-500);
                            break;
                        }
                    }
    
                    System.out.println();
                    System.out.print("Do you want to continue checking (Y/n)?");
                    if(SCANNER.nextLine().toUpperCase().strip().equals("Y")) continue;
                    else screen = DASHBOARD;
                    break; 
    
                }
    
                case DROP_ACCOUNT:{
                    int index = 0;
                    validateAccountNo("Enter Account No: ");
                    if(!valid) continue;
    
                    for (int i = 0; i < accounts.length; i++) {
                        if(accounts[i][0].equals(accountNo)){
                            amount = accounts[i][2];
                            accountName = accounts[i][1];
                            accountID = accounts[i][0];
                            index = i;
                            System.out.printf("%sName:%s %s\n", COLOR_GREEN, RESET, accountName);
                            System.out.printf("%sBalance:%s %s%,.2f\n", COLOR_GREEN, RESET,"Rs. ", Double.valueOf(amount));                                
                            break;
                        }
                    }
    
                    System.out.println();
                    System.out.print("Are you sure, you want to delete (Y/n)?");
                    if(!SCANNER.nextLine().toUpperCase().strip().equals("Y")){
                        screen = DASHBOARD;
                        continue mainLoop;
    
                    }
    
    
                    String[][] newAccounts = new String[accounts.length -1][3];
                    for (int i = 0; i < accounts.length; i++) {
                        if (i < index)newAccounts[i] = accounts[i];    
                        else if(i == index)continue;                           
                        else newAccounts[i-1] = accounts[i];                                                  
                    }
    
                    accounts = newAccounts;
    
                    System.out.printf(SUCCESS_MSG, String.format("%s:%s has been deleted successfully \n", accountID, accountName));
                    System.out.print("Do you want to continue deleting (Y/n)?");
                    if(SCANNER.nextLine().toUpperCase().strip().equals("Y")) continue;
                    else screen = DASHBOARD;
                    break; 
    
                }

                default:
                    System.exit(0);                   
            }

       }while(true);
    }

    public static void validateAccountNo(String input){
        do{

            valid = true;
            System.out.print(input);
            accountNo = SCANNER.nextLine().toUpperCase().strip();

            if(accountNo.isBlank()){
                System.out.printf(ERROR_MSG, "Account number can't be empty!");
                valid = false;

            }else if(!(accountNo.startsWith("SDB-") && accountNo.length() == 9)){
                System.out.printf(ERROR_MSG, "Inavlid format!");
                valid = false;
                
            }else{
                String number = accountNo.substring(4);
                for (int i = 0; i < number.length(); i++) {
                    if(!Character.isDigit(number.charAt(i))){
                        System.out.printf(ERROR_MSG, "Inavlid format!");
                        valid = false;
                        break;
                    }
                }

                boolean exists = false;
                for (int i = 0; i < accounts.length; i++) {
                    if (accounts[i][0].equals(accountNo)){
                        exists = true;
                        break;
                    }
                }    
                if (!exists){
                    valid = false;
                    System.out.printf(ERROR_MSG, "Account not found!");
                }
            } 

            if(!valid){
                System.out.print(TRY_AGAIN_MSG);
                if(SCANNER.nextLine().strip().toUpperCase().equals("Y")){
                    continue;     
                }
                screen = DASHBOARD;
                return;
            }
            

        }while(!valid);                   
    }

    public static void checkCurrentBalance(){
        for (int i = 0; i < accounts.length; i++) {
            if(accounts[i][0].equals(accountNo)){
                amount = accounts[i][2];
                accountName = accounts[i][1];
                System.out.printf("%sCurrent Balance:%s %s%,.2f\n", COLOR_GREEN, RESET, "Rs. ",Double.valueOf(amount));
                break;
            }
        }
    }

    public static void validateWithdrawAmount(String input){
        do{
            valid = true;
            System.out.print(input);
            withdrawAmount = SCANNER.nextDouble();
            SCANNER.nextLine();

            if(withdrawAmount < 100){
                System.out.printf(ERROR_MSG, "Insufficient Amount!");
                valid = false;
            }

            if ((Double.valueOf(amount) - withdrawAmount) < 500){
                System.out.printf(ERROR_MSG, "Invalid amount, There should be at least Rs. 500.00 after withdrawl!");
                valid = false;
            }

            if(valid == false){
                System.out.print(TRY_AGAIN_MSG);
                if(SCANNER.nextLine().strip().toUpperCase().equals("Y")){
                    continue;
                            
                }else {
                    screen = DASHBOARD;
                    return;
                }                            
            } 

        }while(!valid);

    }


}
