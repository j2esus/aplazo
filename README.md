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
   - GET findById
3. Scheme API
   - GET check
     - This operation uses findById of Customer API to make sure the customer really exists.
     - For doing some validations this service will use findById of CreditLine API to check if the customer has enough credit line and the previous loans of Loans API for the new possible loan
   - GET findById
4. Loans API
    - POST create
    - GET loans
      - All these operations uses findById of Customers API to make sure the customer really exists.
5. Payments API
   - POST processPayments
   - GET paymentsPerLoan

### Technical Requirements
- Java 17
- Docker with enough permissions to run.
- Maven 3
- Postman for manual tests of the API's (optional)