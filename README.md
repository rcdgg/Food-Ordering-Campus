# Byte Me!
#### A food ordering CLI app for campus students and staff
Repo link - https://github.com/rcdgg/Food-Ordering-Campus.git

## How to use:
- Open the project in Intellij Idea
- Run the main function in the App class

## Initialised values
Below mentioned login credentials already initialised to play with
- User: rohan, password: rohan
- User: ak, password: ak
- User: bk, password: bk

Menu also initialised with few items from the get-go

## Overview of the working:
#### Admin
- A backend object exists which keeps track of all orders and customers and whether they're VIP or not
- The admin requests the backend to return an order object from the priority queue to process
- If the order is processed by admin fully(either delivered or refunded), then that order's bill gets added to the gross income of the day

#### Customer
- If no current pending order exists, customer can create an order object OR overwrite the existing one.
- After adding items from the menu, they proceed to checkout which will make the order status = 1(waiting for confirmation)
- This order then becomes visible to the admin and can be processed by them thereafter
- Before the order finishes fully processing, it can be cancelled by the customer any time, changing its status to cancelled(admin will need to process the refund thereafter)


## Assumptions taken:
- food will be available at first by default and availability is boolean
- removing item is equivalent to making it unavailable(customer cant order it)
- admin processes orders manually and will keep processing unless it is delivered or refunded
- 1 custom queue exists for all orders(orders can have statuses like pending, out for delivery, cancelled etc.)
- 1 pending order at a time(like in swiggy) so you can only be working on one order at a time
- add special requests in checkout
- can cancel all orders which havent been delivered or cancelled already
- Admin doesnt require login
- Once an order has been made invalid by removing an item off the menu, it cannot be made revalid again even if the item comes back
- shows pending order of current customer signed in only(GUI SPECIFIC)

## Testing Suite
Run the tests in `mytest.java` to check beforehand whether everything is working properly or not