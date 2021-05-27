# Runbook
- Steps we did manually to get Tucklets deployed in AWS

### AWS SES Setup
- https://us-west-2.console.aws.amazon.com/ses/home?region=us-west-2#smtp-settings:
  Follow link and create smtp credentials.
- Create new iam user if one has not already been created (maybe we move this to terraform step?)
- Register for Prod access:
https://docs.aws.amazon.com/ses/latest/DeveloperGuide/request-production-access.html: Use following to fill in form
    - Use case description: Website is for a non-profit organization. To acquire our mailing list, we allow users opt in and provide their email address. We will handle bounce emails by removing them from our email list. Recipients can opt out by sending us an email requesting to opt out. Our sending rate will be based on the number of users who opt in.
    - Mail Type: TRANSACTIONAL
    - Website URL: https://tucklets.net/

### Braintree 
- https://www.braintreegateway.com/merchants/
    - Login to Production account with Kathleen's paypal account
    - Create 2 plans - MonthlySubscription and YearlySubscription
    
