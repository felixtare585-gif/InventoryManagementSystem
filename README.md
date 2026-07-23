# Inventory Management System

A desktop-based Inventory Management System built using JavaFX and MySQL.

This application allows users to manage products through a graphical user interface with database storage using JDBC.

## Features

- Add products
- View all products
- Search products
- Update products
- Delete products
- MySQL database integration
- JavaFX graphical user interface

## Technologies Used

- Java 17
- JavaFX 21
- MySQL Database
- JDBC
- Maven
- Git & GitHub

## Project Structure

```text
InventoryManagementSystem
│
├── pom.xml
│
└── src
    └── main
        └── java
            └── com.inventory
                │
                ├── Main.java
                │
                ├── controllers
                │   └── ProductController.java
                │
                ├── database
                │   └── DatabaseConnection.java
                │
                ├── models
                │   └── Product.java
                │
                └── views
                    ├── AddProduct.java
                    ├── ViewProducts.java
                    ├── SearchProduct.java
                    ├── UpdateProduct.java
                    └── DeleteProduct.java


## How To Run

1. Clone the repository:

git clone https://github.com/felixtare585-gif/InventoryManagementSystem.git


2. Configure MySQL database connection.

3. Open the project in VS Code or IntelliJ IDEA.

4. Run:

mvn javafx:run


## Future Improvements

- Login system
- Admin and staff roles
- Stock alerts
- Reports generation
- Improved dashboard design


## Author

Felix Tare