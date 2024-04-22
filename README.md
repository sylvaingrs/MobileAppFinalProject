# MobileAppFinalProject

Requirements:

1. Authentication

- [x] 1.1. Allow User to Signup
- [x] 1.2. Log In using username and password
- [x] 1.3. Store userID once logged in to keep the user logged in (even after restarting the app)

2. Product Listing

- [x] 2.1. List Product Categories
- [x] 2.2. On clicking a Category, list Products in that Category
- [x] 2.3. On clicking a Product, show Product description, show buy button and controls to change quantity.

3. Cart

- [x] 3.1. Show cart summary
- [x] 3.2. Show total amount
- [x] 3.3. Purchase button to place an order, show order notification

4. Show order history

- [x] 4.1. List users orders
- [x] 4.2. On clicking an Order, show Order details and Products ordered
- [x] 4.3. On clicking a Product, take them to Product description page created for 2.3

5. Show User details

- [x] 5.1. Use the stored userID to show user details
- [x] 5.2. Show a random circular profile image
- [x] 5.3. Show Logout button, on click take back to Signup / Log In page (Restart should not auto login after logout)

6. UI/Implementational Requirements

- [x] 6.1. Lazy lists to be used for all Lists: Categories, Products, Orders
- [x] 6.2. If logged in, attach authentication token to all requests until logout
- [ ] 6.3. Add a small "About this app" button in the profile page, that shows a page on click with your copyright details and credits

7. Bonus

- [ ] 7.1. ViewPager2 with bottom TabLayout for: Shop, Cart, Orders, Profile icons
- [ ] 7.2. Show a map fragment based on the GPS co-ordinates in the user profile

# Report :

Intro :

This mobile app final project is a shop app where the user can login or sign up with their information on Firebase, choose several products arranged by category. These selected products are stored in a cart on Firebase too, so if the user exit the app and login after the cart are still there. Then, the user can "pay" the cart and see the orders history with the date and the products, also stored on Firebase. Finaly the user can see his personal information in the Profile page.

Design :

I chose the design thanks to colorunt.co and colors.muz.li. I wanted to change and take a light theme for once. I took a light background and a dark blue for the App bar and Bottom nav bar to make a contrast and to focus the eye on the products. I put some border radius to make the app prettier.

Content :
the Login page and Sign up page are pretty simple to use but was very long to achieve. Also, the "forgot password" don't work. Once logged in, the first page we see is the Category page, where we can see all the categories created and displayed in a LazyList as requested. After clicking in a category there is a new LazyList of products and when we click again on a product we have the name, the image, description and it's price. At this point we can select on or many of this product and click "add to cart". After clicking this redirect us to the list of products. Then, when we click on the Cart page we have all the products I selected, and I can delete it if I want. There is also the total amount at the bottom of the page and the button "proceed to payment". Of course the user will not really pay the products, but after clicking on it the actual cart it will disappear. Then when we click on the Orders History page we can see our order with the date and the number of products. We also can click on the order and see the details, like the products and the description as before. Finally the last page is the Profile page, where we have all the personal information of the user and a random profile image took at thispersondoesnotexist.com. We can log out so that when we exit and launch the app we are redirected to the Login page.

Challenges faced :
The first big challenge was to understand Firebase and how to connect it with my Android project. Then, how can I get and set Firebase information in the app. Then, I had to do a log of Log text to see the errors and the failed attempts. One of my worst waisted time was when I realized that I have to change the rules of Firestore to let me access to the collections and documents of the user, if he's authenticated. After that and after linked the Authentication and the Database with the User ID it was a little bit easier. After that, when for example I add an order or when I delete a product into the Cart page, I have to update the page automatically and this is pretty long to do. Also, at the beginning I didn't do a stored Cart : it was just stored into the code and when I exit the app it disappeared but I had to change it and it was very hard to change every part of my code where I had to.

Libraries :
I used a lot of common libraries to make Row, Column and so on, but I also used the "remember" library to update by itself the data stored in a remember variable. For the actual date I took the "LocalDateTime" library. I also used a Scaffold.

Conclusion :
This final project was very interesting for me and I learned a lot of useful things. I had never done some real backend before that so I'm happy to did it. I'm also a bit proud of the result of my project because I worked a lot on it, more than in the other subjects. Firebase is a very good choice to make simple Database and Authentication instead of doing all the things by myself.
