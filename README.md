# Aplazo interview: loans operation
This is a technical exercise about loans operations where we need to create the basic tasks for business logic of Aplazo company.

### Main requirements are:
1. Create a customer
2. Create a credit line for that customer.
3. Check the options of payments of a new possible loan.
    - Check the previous loans and determinate if the loan is viable and display the plan of payments for it, if not, display the reasons.
    - Display two possible plans of payments for that particular possible loan.
4. If the simulation of the loan displayed in the point number three, we can save it. So in this point we need to create a new loan.
5. Process payments of all clients per date. This should be an automatic process that runs every day. Of course, we need to offer an option to run it manually.

### Technical analysis
According to the requirements showed above. I determinate the following apis:
1. Customer API
   - POST create
   - GET findById
2. CreditLine API
   - POST create
     - This operation uses findById of Customer API to make sure the customer really exists.
   - GET findByIdCustomer
3. Scheme API
   - GET check
     - This operation uses findById of Customer API to make sure the customer really exists.
     - For doing some validations this service will use findById of CreditLine API to check if the customer has enough credit line and the previous loans of Loans API for the new possible loan
   - GET findById
4. Loans API
    - POST create
      - This operation uses findById of Customer API to make sure the customer really exists.
      - This operation uses as well the findById from Scheme API to make sure the scheme really exists, because from the scheme we know how many payments we need to generate.
    - GET loans
      - All these operations uses findById of Customers API to make sure the customer really exists.
    - POST processPayments

### Technical Requirements
- Java 17
- Docker with enough permissions to run.
- Maven 3
- Postman for manual tests of the API's (optional). 
  - At the root of this project there is a xml file of a collection of POSTMAN to check all endpoints of this project. The name of that file is AplazoInterview.postman_collection.json

### Considerations
1. Only for local environments
2. In some microservices we don't have api test, mainly when open feign clients are used.
3. The passwords are visible in the code, that's not so good.
4. Possible problems with decimal in the payments. Because for every payment I divide the total_amount/total_payments, so this could come with more decimals, I mean the sum of the amount of all payments will be major than the amount total of the loan.
5. I was not able to include all restrictions on the creation of the schemes, but I leave comments TODO
6. I don't create enough unit tests as I would like.
7. I don't implement Caching.
8. I don't implement Log functionality.

### How to run the project?
1. Execute build.sh file, it is in the root of the project.
2. Run docker compose: docker compose up