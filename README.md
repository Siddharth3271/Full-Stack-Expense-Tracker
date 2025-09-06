# Full-Stack Expense Tracker

A comprehensive desktop application designed to help users manage and visualize their personal finances. Built as a full-stack project, it provides a secure, interactive platform for tracking income and expenses.

## Application ScreenShots

### Login Page
![login Page](images/login.png)

### Dashboard
![dashboard Page](images/dashboard.png)

### Bar Graph for the selected year expenses
![bargraph Page](images/bargraph.png)

### Create Category(With color picker)
![createCategory Page](images/createcategory.png)

### Transaction Creation with drop down of category selection
![createTransaction Page](images/createtransactionages.png)

### View Existing Categories
![viewCategory Page](images/viewcategory.png)

---

## ✨ Features

* **User Authentication:** Secure login and sign-up system to manage individual user accounts.
* **Transaction Management:** Full CRUD (Create, Read, Update, Delete) functionality for managing financial transactions.
* **Dashboard Overview:** A clean dashboard providing a real-time summary of current balance, total income, and total expenses.
* **Dynamic Categorization:** Users can create, view, edit, and delete custom categories (e.g., "School," "College," "Hospital") for better organization.
* **Data Visualization:** An interactive bar chart to visualize monthly income vs. expenses, making it easy to analyze spending habits.
* **Monthly Breakdown:** A detailed table showing a month-by-month breakdown of financial activity.

---

## 💻 Technology Stack

This project is built using a modern full-stack architecture.

* **Frontend:** JavaFX
* **Backend:** Spring Boot (REST API)
* **Database:** MySQL
* **Build Tool:** Maven

---

## 🚀 Getting Started

To get a copy of this project up and running on your local machine, follow these steps.

### **Prerequisites**

* Java JDK 17 or higher
* Maven
* MySQL Server

### **Database Setup**

1.  Clone the repository:
    `git clone https://github.com/Siddharth3271/Full-Stack-Expense-Tracker.git`
2.  Navigate to the `Expense-Tracker-Server` directory.
3.  Create a MySQL database named `expense_tracker_db`.
4.  Run the SQL script located at `src/main/resources/schema.sql` to create the necessary tables.
5.  Update the database configuration in `src/main/resources/application.properties` with your MySQL credentials.

### **Running the Application**

The application is split into two modules: the server (backend) and the client (frontend).

1.  **Start the Server:**
    * Open a terminal and navigate to the `Expense-Tracker-Server` directory.
    * Run the command:
        `mvn spring-boot:run`
    * The server will start on `http://localhost:8081`.

2.  **Start the Client:**
    * Open a new terminal and navigate to the `Expense-Tracker-Client` directory.
    * Run the command:
        `mvn javafx:run`
    * The JavaFX application window will open.
3. If using Intellj -> just run the server and then client

---

## ✍️ Contribution

This is a personal project, but feel free to open issues or submit pull requests. All contributions are welcome.

---

## Future Integrations
- Adding google authorization
- Yearly Bar Graph Statistics
- Making its execuatble file
