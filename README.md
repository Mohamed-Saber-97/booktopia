# Booktopia
## Quality Reports
* [SpotBugs Report](https://mohamed-saber-97.github.io/booktopia/spotbugs.html)
* [Jacoco Report](https://Mohamed-Saber-97.github.io/booktopia/jacoco-report/index.html)
* [CheckStyle Report](https://mohamed-saber-97.github.io/booktopia/site/checkstyle.html)
* [Prevention of Malicious Defects Report](https://mohamed-saber-97.github.io/booktopia/site/pmd.html)
* [Copy/Paste Detector Report](https://mohamed-saber-97.github.io/booktopia/site/cpd.html)
## Requirements and Goals of the System
We will be designing a system with the following requirements:
1. Admin should be able to add new products to sell.
2. Users should be able to search for products by their name or category.
3. Users can search and view all the products, but they will have to become a registered member to buy a product.
4. Users should be able to add/remove/modify product items in their shopping cart. 
5. Users can check out and buy items in the shopping cart.
6. Users can rate and add a review for a product. 
7. The user should be able to specify a shipping address where their order will be delivered.
8. Users can cancel an order if it has not shipped.
9. Users should be able to pay through a credit limit to be set when the user account is created.
11. User and Admin should be able to view the history of products ordered
## Use case Diagram
### We have four main Actors in our system
* **_Admin_** is mainly responsible for account management and adding or modifying new product categories for selling.
* **_Guest_** can search the catalog, add/remove items to the shopping cart, as well as become registered members.
* **_Member_** can perform all the activities that guests can, in addition to which, they can place orders
### Here are the top use cases of the Online Shopping System
1. Add/update products; whenever a product is added or modified, we will update the catalog.
2. Search for products by their name or category.
3. Add/remove product items in the shopping cart.
4. Check-out to buy product items in the shopping cart.
5. Make a payment to place an order.
6. Add a new product category.

![Booktopia Use Case Diagram](./Booktopia.svg "Booktopia Use Case Diagram")