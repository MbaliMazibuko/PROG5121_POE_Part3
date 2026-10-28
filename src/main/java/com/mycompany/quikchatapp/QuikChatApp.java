/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.quikchatapp;

import java.util.Scanner;

/**
 *
 * @author user
 */
public class QuikChatApp {
    
    private static final String[] MENU_OPTIONS = {
            "1. Register a new account",
            "2. Login",
            "3. Exit"
    };

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Login login = new Login();
        boolean running = true;

        printBanner();

        while (running) {
            printMenu();
            System.out.print("Select an option: ");
            String choice = input.nextLine().trim();

            switch (choice) {
                case "1":
                    registerFlow(input, login);
                    break;
                case "2":
                    loginFlow(input, login);
                    break;
                case "3":
                    running = false;
                    System.out.println("Thank you for using QuickChat. Goodbye.");
                    break;
                default:
                    System.out.println("Invalid option. Please enter 1, 2 or 3.");
                    break;
            }
            System.out.println();
        }

        input.close();
    }

    private static void printBanner() {
        System.out.println("========================================");
        System.out.println("          QUICKCHAT - PART 1");
        System.out.println("     Registration and Login Feature");
        System.out.println("========================================");
        System.out.println();
    }

    private static void printMenu() {
        for (int i = 0; i < MENU_OPTIONS.length; i++) {
            System.out.println(MENU_OPTIONS[i]);
        }
    }

    private static void registerFlow(Scanner input, Login login) {
        System.out.println();
        System.out.println("--- Create an account ---");
System.out.print("Enter first name: ");
        String firstName = input.nextLine().trim();
        while (firstName.equals("")) {
            System.out.print("First name cannot be empty. Enter first name: ");
            firstName = input.nextLine().trim();
        }

        System.out.print("Enter last name: ");
        String lastName = input.nextLine().trim();
        while (lastName.equals("")) {
            System.out.print("Last name cannot be empty. Enter last name: ");
            lastName = input.nextLine().trim();
        }
        login.setFirstName(firstName);
        login.setLastName(lastName);

        String username = captureUntilValid(
                input,
                "Enter username (must contain '_' and be no more than 5 characters): ",
                login::checkUserName,
                login::getUsernameMessage
        );

        String password = captureUntilValid(//Password must have 8 character,a capital letter, a number and special character.
                input,
                "Enter password (min 8 chars, 1 capital, 1 number, 1 special character): ",
                login::checkPasswordComplexity,
                login::getPasswordMessage
        );

        String cell = captureUntilValid(//Cell number must start with +27 and be 12 characters long.
                input,
                "Enter South African cell number (example +27838968976): ",
                login::checkCellPhoneNumber,
                login::getCellPhoneMessage
        );

        String result = login.registerUser(username, password, cell);
        System.out.println(result);
    }

    private static void loginFlow(Scanner input, Login login) {
        System.out.println();
        System.out.println("--- Login ---");

        if (!login.isRegistered()) {
            System.out.println("No account has been registered yet. Please register first.");
            return;
        }

        System.out.print("Enter username: ");
        String username = input.nextLine().trim();
        System.out.print("Enter password: ");
        String password = input.nextLine();

        boolean success = login.loginUser(username, password);
        System.out.println(login.returnLoginStatus(username, password));

        if (success) {
            System.out.println("Login successful: " + login.getFullName());
            if (success) {
            quickChatMenu(input,login);
        }
        }
    }

    private static String captureUntilValid(Scanner input,
                                            String prompt,
                                            java.util.function.Predicate<String> checker,
                                            java.util.function.Function<String, String> messenger) {
        String value;
        while (true) {
            System.out.print(prompt);
            value = input.nextLine().trim();
            System.out.println(messenger.apply(value));
            if (checker.test(value)) {
                return value;
            }
        }
    }

    private static void quickChatMenu(Scanner input) {
        
        System.out.println("========================================");
        System.out.println("          Welcome to QuickChat.");
        System.out.println("========================================");
        boolean inChat = true;
    }
        private static MessageStore store = new MessageStore();

    private static void quickChatMenu(Scanner input, Login login) {
        System.out.println("Welcome to QuickChat.");
        store.loadStoredFromJSON();
        boolean inChat = true;

        while (inChat) {
            System.out.println();
            System.out.println("1) Send Messages");
            System.out.println("2) Show recently sent messages");
            System.out.println("3) Quit");
            System.out.println("4) Stored Messages");
            System.out.print("Select an option: ");
            String choice = input.nextLine().trim();

            if (choice.equals("1")) {
                sendMessages(input, login);
            } else if (choice.equals("2")) {
                System.out.println("Coming Soon.");
            } else if (choice.equals("3")) {
                inChat = false;
            } else if (choice.equals("4")) {
                storedMenu(input, login);
            } else {
                System.out.println("Invalid option.");
            }
        }
    }

    private static void sendMessages(Scanner input, Login login) {
        System.out.print("How many messages do you want to send? ");
        int limit = Integer.parseInt(input.nextLine().trim());
        Message tracker = new Message();

        for (int i = 0; i < limit; i++) {
            System.out.println();
            System.out.println("Message " + (i + 1) + " of " + limit);

            Message current = new Message();
            current.setMessageNumber(i);
            System.out.println("Message ID generated: " + current.getMessageID());

            String cellMessage = "";
            while (!cellMessage.equals("Cell phone number successfully captured.")) {
                System.out.print("Enter recipient cell number (example +27718693002): ");
                String cell = input.nextLine().trim();
                cellMessage = current.checkRecipientCell(cell);
                System.out.println(cellMessage);
            }

            String lengthMessage = "";
            while (!lengthMessage.equals("Message ready to send.")) {
                System.out.print("Enter message (max 250 characters): ");
                String text = input.nextLine();
                lengthMessage = current.checkMessageLength(text);
                System.out.println(lengthMessage);
            }

            System.out.println("Message Hash: " + current.createMessageHash());
            System.out.println("1) Send Message");
            System.out.println("2) Disregard Message");
            System.out.println("3) Store Message to send later");
            System.out.print("Select an option: ");
            int action = Integer.parseInt(input.nextLine().trim());
            System.out.println(current.SentMessage(action));

            if (action == 1) {
                store.addSent(current.getMessageText());
                store.addHashAndID(current.getMessageHash(), current.getMessageID());
            } else if (action == 2) {
                store.addDisregarded(current.getMessageText());
            } else if (action == 3) {
                store.addStored(
                        login.getFirstName() + " " + login.getLastName(),
                        current.getRecipient(),
                        current.getMessageText(),
                        current.getMessageHash(),
                        current.getMessageID()
                );
            }
        }

        System.out.println("Total messages sent: " + tracker.returnTotalMessagess());}
    
        }


   



