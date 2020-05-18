# Tucklets
Use for Tucklets.org

Live website here: [insert-link]

### Technologies used
- Java Spring Boot
- Gradle
- Postgres Relational Database
- Hibernate - object-relational mapping (ORM) library to connect from Java classes to Postgres DB
- ThymeLeaf - Java HTML5 template engine
- Javascript 
- Html 
- Bootstrap 
- Hosting: Originally on Heroku, now moved to AWS 
- AWS Technologies: S3, Elastic Container Service (ECS), Load Balancer, CloudWatch, Elastic Container Repository (ECR)
- Docker
- CircleCI: https://app.circleci.com/
- ReactJS
- Node.js
- Webpack 


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
  


# Developer Getting Started: Backend

Prerequisites:
- JDK 11 
- IntelliJ 
- Postgres - https://www.enterprisedb.com/downloads/postgres-postgresql-downloads

Running locally: 
1. Open build.grade as a project in Intellij 
2. Add application with the following: 
![Image of build/run Configurations](https://github.com/ShahilZ/Tucklets/blob/master/src/main/resources/static/images/readme/run-debug-configurations.JPG)
3. Build the project 
4. Check if application is running on http://localhost:8080/sponsor/

# Developer Getting Started: Frontend

When invoking grade commands, be sure to invoke the gradle wrappers:  
  - Windows: `gradlew.bat`  
  - Linux/Mac: `gradlew` 

Prerequisites: 
  - Everything from the Backend prerequisites
  - Configurations from backend (such as all the environment variables)
  
Running locally:
  1. In terminal `gradlew.bat bootRun` - this runs the backend server
  2. In terminal, run the following command:
  `gradlew.bat frontend:start`
    .This command executes the frontend gradle task that will install all frontend dependencies such as node, npm, package.json dependencies. 
  3. View changes by navigating to http://localhost:3000/ 
    - You can now edit any jsx files, save, and just refresh the page (no need to kill the server for frontend changes). 
  5. After killing server (Ctrl-C), run the following in Terminal to stop all nodes:
     `gradlew.bat frontend:stopNodes`

How it works:
  - The purpose configuring this was to help with developer experience so that frontend/React changes no longer require rebuilding the server. Additionally, the developer does not need to have node or npm downloaded on their machine. The frontend server runs on http://localhost:3000/ as a proxy for http://localhost:8083 (backend server).




