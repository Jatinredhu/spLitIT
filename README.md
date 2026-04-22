# 💸 Expense Splitter

A full-stack web app to split expenses among a group and calculate who owes whom — built with Spring Boot and vanilla HTML/CSS/JS.

## Features

- Add/remove people to a group
- Log expenses (who paid, how much, split among whom)
- Calculate minimal settlements (who pays whom and how much)
- In-memory H2 database — no setup required

## Tech Stack

| Layer | Tech |
|-------|------|
| Backend | Spring Boot 3, Spring Data JPA |
| Database | H2 (in-memory) |
| Frontend | Vanilla HTML/CSS/JS (served as static resource) |

## How to Run

### Prerequisites
- Java 17+
- Maven

```bash
git clone https://github.com/yourusername/expense-splitter
cd expense-splitter
mvn spring-boot:run
```

Then open: [http://localhost:8080](http://localhost:8080)

## API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/people` | Get all people |
| POST | `/api/people` | Add a person |
| DELETE | `/api/people/{id}` | Remove a person |
| GET | `/api/expenses` | Get all expenses |
| POST | `/api/expenses` | Add an expense |
| DELETE | `/api/expenses/{id}` | Remove an expense |
| GET | `/api/settle` | Get settlement summary |

## Settlement Logic

Uses a greedy algorithm on net balances:
1. Calculate each person's net balance (total paid - total owed)
2. Match creditors against debtors to minimize the number of transactions
