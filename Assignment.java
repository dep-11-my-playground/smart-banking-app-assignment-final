
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
                
                default:
                    System.exit(0);                   
            }

       }while(true);
    }



}
