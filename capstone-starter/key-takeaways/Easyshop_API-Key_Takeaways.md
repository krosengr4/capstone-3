### ***CAPSTONE 3 - KEY TAKEAWAYS FROM THIS PROJECT***

### **\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_**







###### **\*\*\*KEY FEATURES TO DEMO\*\*\***

###### **\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_**



\- Register a new account on front end







###### **The shopping cart feature**

###### **\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_**



\- Hardest part was figuring out how ShoppingCart and ShoppingCartItem is  structured. that ShoppingCart is a hashmap(key = productid, val = ShoppingCartItem)

and a ShoppingCartItem has a product, quantity of that product, and discount.



\- Dao methods to get a users shopping cart, and adding an item to the cart.



\- GET: to return a ShoppingCart you had to get quantity and productID from shopping\_cart table,

then use the productid to get the product to create a ShoppingCartItem(Product, quantity)

for each  row. Then you could finally create a ShoppingCart with all the ShoppingCartItems.

I kept the ShoppingCarts in the data base so the items in the cart would stay there if a user logged out.



\- ADD ITEM: This was a little tricky because you first had to see if the product a user was adding into

their cart was already in the cart. If it was you had run a update query and set the quantity to quantity + 1. If the product wasn't in the cart you could just run a insert query to add the product to the cart.





###### **The Order / Checkout feature**

###### **\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_**



\- We've handled every "entity" so far basically using 4 classes (5 for ShoppingCart entity (ShoppingCartItem)).

Classes: Model class, Controller class, Dao interface, and MySQLDao class. Every feature until the order/checkout feature, the starter code handled a lot of this code for us.



\- The order/checkout feature was difficult because you weren't given any of the classes in the starter code.



\- The other difficult part of this feature is that you had to insert each item in the users cart into the

order\_line\_items table when a user checks out.



\- To do this I refactored my shoppingCartDao.getByUserId() method into 2 methods, so I could have a

method that returned a list of ShoppingCartItems. (couldn't use a for each loop to iterate a hash map, didn't want to use a for i).



\- Then in the order controller I could get that list of items in a users cart, iterate through it with

the for each, and call the insertLineItems() method in the orderDao to add each Shopping Cart Item into the database whenever a user would checkout.





###### **\*\*\*Adding features to the client side\*\*\* AKA the cool things I got to work**

###### **\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_**



\- When a user is viewing their cart, and if they have items in the cart, I added a checkout button that will display a form asking for the payment information. When the user enters their payment information and clicks checkout, it calls the server side and inserts the order and order line item, and clears the users cart in the database. The payment information that user enters never gets used, it is just for show... for now (need to learn about security).



\- If a user is not logged in, and they don't have an account to log into, I added a feature where the user can register a new account!

 



###### **Unit tests**

###### **\_\_\_\_\_\_\_\_\_\_\_\_\_\_**



\- Created unit tests for CategoryDao, ProductDao, ProfileDao, and UserDao

\- I basically just used the productDao test that came with the starter code as a blueprint for the other tests

\- Note the error about .rollback() and not using the live MySQL database







###### **Little things that I found interesting**

###### **\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_**



\- The genius query in the search() method in MySqlProductDao (the one with the bug).



\- Initially thought the bug was because the query was screwed up (WHERE categoryId = 2 OR 2 = -1 ... wtf?! Also how can the categoryID = -1 ???).



\- Spent a lot of time trying to 1) make sense of the query, and 2) rewriting a query that handled 4 WHERE clauses

where each one could be null.



\- Had an "aha!" moment that if the user didn't specify a value for any of the fields in search(), let's use category for example, categoryID would be set to -1 (using a fancy ternary operator), then it would search products in the category where -1 = -1.



\- Well -1 always equals -1 ... so it would search products in all of the categories!!!

\- Freakin' genius!





