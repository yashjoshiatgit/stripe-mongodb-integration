# Stripe & MongoDB Integration with Spring Boot

Easily integrate **Stripe Checkout** with **Spring Boot** for seamless payment processing, and store transaction data in **MongoDB**. This repository is designed for beginners and professionals looking to build scalable payment systems.

## 🛠 Features
- Stripe Checkout integration for secure payments.
- MongoDB integration for efficient data storage.
- REST APIs to handle payment workflows.
- Beginner-friendly and scalable for production. 


## Quick Start

### 1. Clone the Repository
```bash
git clone https://github.com/yashjoshiatgit/stripe-mongodb-integration.git
cd stripe-mongodb-integration
```
### 2.Configure Application
Add your Stripe Secret Key and MongoDB URI in application.properties
```bash
stripe.secretKey=your_stripe_secret_key
spring.data.mongodb.uri=mongodb://localhost:27017/stripe_demo
```
### 3. Run the Application
```bash
mvn spring-boot:run
```
### 4.Test With Postman
Use the POST API to create a Stripe Checkout session.
```bash
/api/v1/product/checkout
```
![image](https://github.com/user-attachments/assets/83971824-3fe6-46a2-af15-c3583225e11b)

![image](https://github.com/user-attachments/assets/6d8dc6b3-848c-495c-8020-a5251647c185)




## Directory Structure
```bash
stripe-mongodb-integration/
├── src/main/java/com/yourname/stripeintegration/
│   ├── controller/       # REST Controllers
│   ├── dto/              # Data Transfer Objects
│   ├── model/            # MongoDB Models
│   ├── repository/       # MongoDB Repositories
│   ├── service/          # Service Layer
│   └── config/           # Configuration Files
├── src/main/resources/
│   ├── static/           # Static Files (HTML, CSS)
│   └── application.properties # App Config
└── postman/
    └── stripe-mongodb.postman_collection.json

```
## Use Cases
  -	Hackathons: Quickly build payment-enabled prototypes.</br>
  -	E-commerce: Handle checkout and store orders.</br>
  -	SaaS Platforms: Accept payments with scalability.</br>

## Documentation
 -	[Stripe API Docs](https://stripe.com/docs/api)
 -  [MongoDB Docs](https://www.mongodb.com/docs/)
## Tech Stack
 -	Spring Boot
 - 	Stripe API
 -  MongoDB
## Contributing
  1.	Fork the repo and create a new branch.
  2.	Commit changes and submit a pull request.

## License
This project is licensed under the [MIT License](https://opensource.org/license/mit).







