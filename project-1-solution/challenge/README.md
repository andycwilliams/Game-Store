# Game Store Project

This group project involves the creation of a full-stack application that includes the following:

- A simple back-end REST inventory management web service for a video game store, developed using agile techniques in a group setting. You are responsible for designing and documenting the REST API and implementing the controllers, service, layering, repository, Java data objects, and unit tests for the application based on the provided database structure.

- A simple user interface that exercises your REST API functionality.

- The use of Agile methodology to plan and track your project, an important skill upon which you will likely be evaluated by hiring managers.


## Back-End Structure

Your back-end solution must have the following structural elements:

- Your solution must be in an IntelliJ project called `FirstNameLastNameU1Capstone`, where `FirstName` and `LastName` are your first and last names, respectively.

- Your project must be built using Spring Boot and Spring MVC. Initialize your project using [Spring Initializr](https://start.spring.io/).

- Your solution must include the use of JPA (Java Persistence API) to interact with the back-end database.

- Your REST API must be documented with Swagger/OpenAPI.

- Your REST API must accept and return data in JSON format where appropriate.

- You must implement `ControllerAdvice` to handle exceptions and return proper HTTP status codes and data when exceptions occur. This includes handling all violations of business rules.

## Methodology

- You must manage your work in Jira including the use of Kanban boards.

- You must create stories and epics organized into Kanban cards.

- You must estimate your work using story points.

- You must organize your work into sprints of 2-3 hours.

- You must use a test-driven development approach (including Red/Green/Refactor) for your code.

- You must use JUnit for unit and integration tests.

- Your design must include a service layer.

- Your unit test suite should use mock objects where appropriate.

- You should use JSR303 for input validation.

## API Features

This system must manage the inventory of video games, game consoles, and T-shirts.

- Your REST API must allow the end user to do the following in each category:

  - Games:

    - Perform standard CRUD operations for games.

    - Search for games by studio.

    - Search for games by ESRB rating.

    - Search for games by title.

    > **Note:** You must create a separate repository for games.
  
  - Consoles:
  
    - Perform standard CRUD operations for consoles.
  
    - Search for consoles by manufacturer.
  
    > **Note:** You must create a separate repository for consoles.
  
  - T-shirts:
  
    - Perform standard CRUD operations for T-shirts.
  
    - Search for T-shirts by color.
  
    - Search for T-shirts by size.
  
    > **Note:** You must create a separate repository for T-shirts.
  
  - Purchasing items:
  
    - Purchase items in inventory by supplying the following information to the endpoint:
  
      - Name

      - Street

      - City

      - State

      - Zip

      - Item type

      - Item ID

      - Quantity
  
    - The endpoint returns invoice data based on the provided invoice table.
  
    - All invoice calculations must be done in the service layer.
  
    > **Note:** You must create a repository for both taxes and processing fees.

You must use the following database structure:

```sql
create schema if not exists game_store;
use game_store;

create table if not exists game (
    game_id int not null auto_increment primary key,
    title varchar(50) not null,
    esrb_rating varchar(50) not null,
    description varchar(255) not null,
    price decimal(5, 2) not null,
    studio varchar(50) not null,
    quantity int not null
);

create table if not exists console (
    console_id int not null auto_increment primary key,
    model varchar(50) not null,
    manufacturer varchar(50) not null,
    memory_amount varchar(20),
    processor varchar(20),
    price decimal(5, 2) not null,
    quantity int not null
);

create table if not exists t_shirt (
    t_shirt_id int not null auto_increment primary key,
    size varchar(20) not null,
    color varchar(20) not null,
    description varchar(255) not null,
    price decimal(5,2) not null,
    quantity int not null
);

create table if not exists sales_tax_rate (
    state char(2) not null,
    rate decimal(3,2) not null
);

create unique index ix_state_rate on sales_tax_rate (state);

create table if not exists processing_fee (
    product_type varchar(20) not null,
    fee decimal (4,2)
);

create unique index ix_product_type_fee on processing_fee (product_type);

create table if not exists invoice (
    invoice_id int not null auto_increment primary key,
    name varchar(80) not null,
    street varchar(30) not null,
    city varchar(30) not null,
    state char(2) not null,
    zipcode varchar(5) not null,
    item_type varchar(20) not null,
    item_id int not null,
    unit_price decimal(5,2) not null,
    quantity int not null,
    subtotal decimal(5,2) not null,
    tax decimal(5,2) not null,
    processing_fee decimal (5,2) not null,
    total decimal(5,2) not null
);
```

## User Stories

Your application must complete the following user stories:

- As a user, I would like to be able to create, read, update and delete game information.

- As a user, I would like to be able to search for games by studio, ESRB rating and title.

- As a user, I would like to be able to create, read, update and delete console information.

- As a user, I would like to be able to search for consoles by manufacturer.

- As a user, I would like to be able to create, read, update and delete T-shirt information.

- As a user, I would like to be able to search for games by color and size.

- As a user, I would like to be able to purchase a specified quantity of products (games, consoles, T-shirts).

## Test Requirements

- You must test all routes using MockMVC.
  
  - This includes testing for both expected return values and expected controller failures (4xx and 5xx status codes).

- Test all service layer methods.

  - You should have 100% code coverage of the service layer.

  - These should be unit tests&mdash;in other words, they should employ mocking.

- You must have integration tests for all repositories.

  - These should test the basic CRUD operations.

  - These should also test any custom methods you've defined (such as `findByCategory`).

## Business Rules

- Sales tax applies only to the cost of the items.

- Sales tax does not apply to any processing fees for an invoice.

- The processing fee is applied only once per order, regardless of the number of items in the order, unless the number of items in the order is greater than 10, in which case an **additional** processing fee of $15.49 is applied to the order.

- The order-processing logic must properly update the quantity available for the item in the order.

- Order quantity must be greater than zero.

- Order quantity must be less than or equal to the number of items available in inventory.

- The order must contain a valid state code.

- The REST API must properly handle and report all violations of business rules.

## Data

### Tax Rates

Load the following tax rates into your database:

- Alabama&mdash;AL: .05

- Alaska&mdash;AK: .06

- Arizona&mdash;AZ: .04

- Arkansas&mdash;AR: .06

- California&mdash;CA: .06

- Colorado&mdash;CO: .04

- Connecticut&mdash;CT: .03

- Delaware&mdash;DE: .05

- Florida&mdash;FL: .06

- Georgia&mdash;GA: .07

- Hawaii&mdash;HI: .05

- Idaho&mdash;ID: .03

- Illinois&mdash;IL: .05

- Indiana&mdash;IN: .05

- Iowa&mdash;IA: .04

- Kansas&mdash;KS: .06

- Kentucky&mdash;KY: .04

- Louisiana&mdash;LA: .05

- Maine&mdash;ME: .03

- Maryland&mdash;MD: .07

- Massachusetts&mdash;MA: .05

- Michigan&mdash;MI: .06

- Minnesota&mdash;MN: .06

- Mississippi&mdash;MS: .05

- Missouri&mdash;MO: .05

- Montana&mdash;MT: .03

- Nebraska&mdash;NE: .04

- Nevada&mdash;NV: .04

- New Hampshire&mdash;NH: .06

- New Jersey&mdash;NJ: .05

- New Mexico&mdash;NM: .05

- New York&mdash;NY: .06

- North Carolina&mdash;NC: .05

- North Dakota&mdash;ND: .05

- Ohio&mdash;OH: .04

- Oklahoma&mdash;OK: .04

- Oregon&mdash;OR: .07

- Pennsylvania&mdash;PA: .06

- Rhode Island&mdash;RI: .06

- South Carolina&mdash;SC: .06

- South Dakota&mdash;SD: .06

- Tennessee&mdash;TN: .05

- Texas&mdash;TX: .03

- Utah&mdash;UT: .04

- Vermont&mdash;VT: .07

- Virginia&mdash;VA: .06

- Washington&mdash;WA: .05

- West Virginia&mdash;WV: .05

- Wisconsin&mdash;WI: .03

- Wyoming&mdash;WY: .04

### Processing Fees

Load the following processing fees into your database:

- Consoles: 14.99

- T-shirts: 1.98

- Games: 1.49

## User Interface

Create a simple JavaScript-based front-end component to interface with your REST API. For this component, the choice of implementation framework (such as React or Angular) is up to you.

The only requirement for the front end is that it exercises the entirety of your REST API functionality. You will not be graded on presentation, so the user interface can be as simple as you choose.

## Code Review

This project includes a code review. A code review is a group interview with either your instructor or TA that will consist of both questions about your code and conceptual questions that are relevant to the unit you have completed. The main goal of this process is to make sure that you understand the concepts covered and can clearly and concisely answer questions in an interview format.

---

© 2022 Trilogy Education Services, a 2U, Inc. brand. All Rights Reserved.