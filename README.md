# Banking-System-Code
# Console Banking Application

This is a simple, command-line (CLI) banking application written in Java. It provides basic banking functionalities, allowing users to manage accounts, perform transactions, and view statements, all within a console interface.

The application is built using an in-memory database (Java Collections) and follows a layered design pattern to separate concerns.

## Features

* **Open Account:** Create a new `SAVINGS` or `CURRENT` account.
* **Deposit:** Add funds to an existing account.
* **Withdraw:** Remove funds from an existing account, with balance checks for `SAVINGS` accounts.
* **Transfer:** Move funds from one account to another.
* **Account Statement:** View a complete transaction history for a single account.
* **List Accounts:** Display a sorted list of all accounts in the bank.
* **Search Accounts:** Find an account by the customer's name.

## Core Concepts & Technologies Used

This project demonstrates a wide range of core Java concepts and design patterns:

### 1. Object-Oriented Programming (OOP)

* **Encapsulation:** Data (like `balance` in `Account.java`) is kept `private`, and access is controlled via public getters and setters.
* **Abstraction (Interface-based Design):** The application is built around a `BankService` interface (implied by `BankServiceconsoleimpl.java`), which defines the "contract" for banking operations. The `BankServiceconsoleimpl` class provides the concrete logic, allowing the `main` method to be decoupled from the implementation.
* **Domain Modeling:** The project uses simple POJOs (Plain Old Java Objects) like `Account.java`, `Customer.java`, and `Transaction.java` to model real-world entities.

### 2. Application Architecture

* **Layered Design:** The project is separated into logical layers:
    * **Presentation Layer (`app` package):** `main.java` handles all user input (`Scanner`) and console output (`System.out.println`).
    * **Service Layer (`service` package):** `BankServiceconsoleimpl.java` contains all the business logic (e.g., how to handle a withdrawal, what happens during a transfer).
    * **Repository Layer (`repository` package):** `AccountRepository.java` and `Transactionrepo.java` are responsible for all data storage and retrieval.
* **Repository Pattern:** The service layer does not know *how* the data is stored (in a `HashMap`, a file, or a database). It just calls methods like `accountRepository.findtheaccount()`.
* **In-Memory Database:** The application uses `java.util.HashMap` in the repository classes to simulate a database. The data is not persistent and will be lost when the application stops.

### 3. Java 8+ Features

* **Streams API:** Used in `BankServiceconsoleimpl.java`'s `ListAccounts` method to sort the list of accounts (`.stream().sorted(...).toList()`).
* **Lambda Expressions:** The `sorted()` method uses a lambda expression `(a, b) -> a.getAccountNo().compareTo(b.getAccountNo())` for comparison.
* **Optional:** Used extensively in `AccountRepository.java` (`Optional.ofNullable`) and `BankServiceconsoleimpl.java` (`orElseThrow`) to gracefully handle cases where an account might not be found, preventing `NullPointerException`s.
* **Java Time API:** `java.time.LocalDateTime` is used in `Transaction.java` to store a precise, modern timestamp for each transaction.
* **Modern Map Methods:** `Transactionrepo.java` uses `computeIfAbsent` to cleanly initialize a new `ArrayList` for a new account's transaction history without extra `if` checks.

### 4. Exception Handling

* **Custom Checked Exception:** `InsufficientBalanceAccount.java` (which extends `Exception`) is a checked exception. It is used for recoverable business errors (a user trying to withdraw too much money) and must be handled with a `try-catch` block.
* **Custom Unchecked Exception:** `AccountNotFoundException.java` (which extends `RuntimeException`) is an unchecked exception, used for unexpected system or logic errors (like trying to access an account that doesn't exist).
* **`try-catch`:** Used in `BankServiceconsoleimpl.java`'s `withdraw` method to manage the `InsufficientBalanceAccount` exception and report the error to the user.
* **`orElseThrow`:** A functional approach to handling exceptions, used to immediately stop an operation if a required piece of data (like an account) is not found.

### 5. Core Java & Data Structures

* **Collections Framework:**
    * `Map<String, Account>` (`HashMap`): Used in `AccountRepository` for instant $O(1)$ lookup of accounts by their account number.
    * `Map<String, List<Transaction>>`: A more complex structure in `Transactionrepo` to map a single account number to its entire list of transactions.
* **Enums:** `Type.java` provides a type-safe way to define transaction types (`DEPOSIT`, `WITHDRAW`, etc.), which is far more robust than using simple strings.
* **Utility Classes:** `java.util.UUID` is used to generate unique IDs for customers, and `java.util.Scanner` is used for all console input.
* **String Formatting:** `String.format("AC%06d", size + 1)` is used to create a clean, zero-padded account number.
* **Static vs. Instance:** `BankServiceconsoleimpl` is an *instance* (a new object), while `Transactionrepo` uses `static` methods and a `static` map, effectively making it a Singleton (a single, shared instance) for managing all transactions.
