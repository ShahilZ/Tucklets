# Tucklets
Use for Tucklets.org

Live website here: [insert-link]


# Developer Getting Started

Make sure you have the following downloaded: 
- JDK 11 
- IntelliJ 
- Postgres - https://www.enterprisedb.com/downloads/postgres-postgresql-downloads

Running locally: 
1. Open build.grade as a project in Intellij 
2. Add application with the following: 
![Image of build/run Configurations](https://github.com/ShahilZ/Tucklets/blob/master/src/main/resources/static/images/readme/run-debug-configurations.JPG)
3. Build the project 
4. Check if application is running on http://localhost:8080/sponsor/


### Technologies used
- Java Spring Boot (Gradle build)
- Postgres Relational Database
- Hibernate - object-relational mapping (ORM) library to connect from Java classes to Postgres DB
- ThymeLeaf - Java HTML5 template engine
- Javascript 
- Html 
- Bootstrap 
- Hosting: Originally on Heroku, now moving to AWS 
- AWS Technologies: S3 
- CICD: [Shahil insert here]

Application Features:
- Suppported text translation to english and mandarin using Locales 
- Paypal Integration
- Email notification upon successful donation
- Admin Flow:
  - Used [insert name] Encryption schema to store admin credentials 
  - Security on all admin webpages
  - Uploading children via excel file
  - Exporting all children to PDF
  - Ability to upload, change, edit children and sponsor information
  


