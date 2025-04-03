# Hibernate Association Mappings

## Overview

This project demonstrates Hibernate association mappings by implementing different entity relationships, including:

- One-to-One
- One-to-Many
- Many-to-Many

## Task Instructions

### 🔹 Part 1 – One-to-One Mapping (Extend Existing Entities)

#### Scenario:

Each order should have exactly one invoice associated with it.

#### 🧱 Create a New Entity: Invoice

```java
@Entity
@Table(name = "invoice")
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer invoiceId;

    private String billingAddress;
    private LocalDate invoiceDate;

    @OneToOne
    @JoinColumn(name = "order_id")  // FK to Order
    private Order order;

    // getters, setters, toString
}
```

#### 📌 Definition of Done (DoD):

- Create a new order, then generate an invoice with the associated order ID.
- Fetch an order with its invoice details via `/order/{id}`.

---

### 🔹 Part 2 – One-to-Many Mapping (Using Existing Entities)

#### Scenario:

Each order can have multiple products (without creating a separate `OrderItem` table).

#### 🛠 Update `Order.java` (Unidirectional Mapping for Simplicity)

```java
@OneToMany
@JoinColumn(name = "order_id")  // Adds FK in product table
private List<Product> productList;
```

#### 📌 DoD:

- Add multiple products to an order and save.
- Fetch `/order/{id}` and return all products associated with that order.

---

### 🔹 Part 3 – One-to-Many Mapping (New Entities)

#### Scenario:

Each product may have multiple reviews.

#### 🧱 Create a New Entity: Review

```java
@Entity
@Table(name = "review")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer reviewId;

    private String comment;
    private int rating;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    // getters, setters
}
```

#### 🛠 Update `Product.java`

```java
@OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
private List<Review> reviews;
```

#### 📌 DoD:

- Add a review to a product.
- Fetch `/product/{id}` to list all reviews for a product.
- Fetch `/review/{id}` to see the review and associated product.

---

### 🔹 Part 4 – Many-to-Many Mapping (New Relationship)

#### ✅ Scenario:

Each product can belong to multiple categories, and each category can contain multiple products.

#### 🧱 Create a New Entity: Category

```java
@Entity
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer categoryId;
    private String categoryName;

    @ManyToMany(mappedBy = "categories", cascade = CascadeType.ALL)
    private List<Product> products;

    // Getters, setters, toString
}
```

#### 🛠 Update `Product.java`

```java
@ManyToMany(cascade = CascadeType.ALL)
@JoinTable(
    name = "product_category",
    joinColumns = @JoinColumn(name = "product_id"),
    inverseJoinColumns = @JoinColumn(name = "category_id")
)
private List<Category> categories;
```

#### 🧪 Example Request JSON (for `/product` POST)

```json
{
  "productName": "Wireless Headphones",
  "price": 2500.0,
  "categories": [{ "categoryName": "Electronics" }, { "categoryName": "Audio" }]
}
```

#### ✅ DoD:

| Task                                                          | Description                  |
| ------------------------------------------------------------- | ---------------------------- |
| ➕ Create a product and associate it with multiple categories | Test via `/product` POST     |
| 🔁 Fetch a product and return all its categories              | Test via `/product/{id}`     |
| 🔍 Fetch a category and return all products in that category  | Test via `/category/{id}`    |
| 🔄 Cascade persist categories when saving a product           | Ensure via `CascadeType.ALL` |
| 🎯 Validate join table `product_category`                     | Inspect DB structure         |

---

## 🚀 Getting Started

### Prerequisites

- Java 17+
- Spring Boot
- Hibernate
- PostgreSQL/MySQL
- Postman or any API testing tool

### Running the Application

1. Clone the repository:
   ```sh
   git clone <repository_url>
   cd <project_directory>
   ```
2. Configure database settings in `application.properties`.
3. Run the application:
   ```sh
   mvn spring-boot:run
   ```
4. Test APIs using Postman or cURL.

---

## 📚 References

- [Hibernate One-to-One Mapping](https://www.baeldung.com/hibernate-one-to-one)
- [Hibernate One-to-Many Mapping](https://www.baeldung.com/hibernate-one-to-many)
- [Hibernate Many-to-Many Mapping](https://www.baeldung.com/hibernate-many-to-many)

Happy Coding! 🎯
