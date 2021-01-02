# Tucklets

View live website [here](https://tucklets.net/).

### Technologies used
- Backend:
  - Language: Java
  - Framework: SpringBoot
  - Build: Gradle
  - Database: Postgres Relational Database 
  - Tools:
    - Hibernate - framework for mapping an object-oriented domain model (Java classes) to a relational database
    - ThymeLeaf - server-side Java template engine (most of the Admin flow uses this for the frontend)
- Frontend:
  - Framework: ReactJS
  - Languages: HTML, JSX, SCSS, CSS, Javascript, NodeJS 
  - Tools:
    - Bootstrap - CSS Framework
    - Webpack - JavaScript module bundler

- Deployment:
  - Heroku (used initially but now moved away from this)
  - Terraform
  - AWS Technologies: S3, Elastic Container Service (ECS), Load Balancer, CloudWatch, Elastic Container Repository (ECR), Secrets Manager
  - CircieCI - https://app.circleci.com/
  - Docker 
  

Application Features:
- Sponsor a child flow
- Suppported text translation to english and mandarin using Locales 
- Paypal Integration
- Email notification upon successful donation
- Admin Flow:
  - Used BCrypt Encryption schema to store admin credentials 
  - Security on all admin webpages
  - Uploading children via excel file
  - Exporting all children to PDF
  - Ability to upload, change, edit children and sponsor information

# Developer Getting Started

### Backend

##### Git Workflow:
1. You must first `clone` the remote repo to get the code base. 
```
$ git clone https://github.com/Tucklets/Web-Application.git
$ cd Tucklets    # if not already here
```
2. Create a new branch when making any changes.
```
$ git branch   # to see which branch you are currently on. You're probably on 'master' branch.
$ git checkout -b your_branch_name    # Creates a new branch and switches you to this new branch.
```
3. After you have made some changes to the code, Git will take notice of these changes. To get a report of the current state of your local branch use the command `git status`. It is often nice to run this before other git commands to see the current state of your local branch compared against the remote branch.
4. When you are ready to push your code to the remote branch, you must stage your files and then commit. 
```
$ git status    # to see you changed files
$ git add src/filename.java    # path may differ depending on which directory you are in. Add all files you want to submit. Use  "$ git add . " if you want to add all files.
$ git commit -m "your_descriptive_message" 
# git push origin your_branch_name    # Changes will now be pushed to the remote branch so people can view those changes. Navigate to the GitHub website to see if your changes was successfully pushed.

```
5. On the GitHub website, create a Pull Request (PR) for review.
6. Once your PR is approved, merge your changes to the respective branch (+ squash commits & merge if possible). 
 
 
##### Prerequisites:
Please download the following:
- JDK 11 
- IntelliJ 
- Postgres - https://www.enterprisedb.com/downloads/postgres-postgresql-downloads

#### Gradle
When invoking gradle commands, be sure to invoke the gradle wrappers:  
  - Windows: `gradlew.bat`  
  - Linux/Mac: `./gradlew` 
  
These wrappers allow for us to pull the correct Gradle executable + configs without having to manage our own installation of Gradle. To run Gradle commands, run the gradle wrapper and then the task--for example, `./gradlew build` for Linux/Mac and `gradlew.bat build` for Windows.

Common tasks used:
- `build` -- Builds the whole project
- `bootRun` -- Runs the project locally
- `bootJar` -- Packages the project into a jar file.
- `webpack` -- Builds the frontend portion of the project.
- `frontend:start` -- Builds and runs the frontend server.


#### Running Locally:  

###### Running via IntelliJ
1. Open the project in IntelliJ:
    - File -> Open ... --> Double click on Tucklets\build.gradle file -> Click "Open as project" when prompted.
2. Click on "Add Configuration" in top right bar to configure the Tucklets Application with the necessary data. Match the following photo. Ask Shahil/Claudia to email the environment variables (may contain sensitive information).
![Image of build/run Configurations](https://github.com/ShahilZ/Tucklets/blob/master/src/main/resources/static/images/readme/run-debug-configurations.JPG)

4. Now you are ready to run the TuckletsApplication. Click on the green 'play' icon in the top right bar.
5. You'll see a bunch of stuff running in the 'Run Output'. When you see " Tomcat initialized with port(s): 8443 (https)", application has been successfully built. 
4. You can browse the application on https://localhost:8443/

###### Running via Terminal
1. Verify current working directory is at the root directory of the project. 
2. Run the following commands (note: for MacOS, run `./gradlew` instead of `gradlew.bat`):
```
$ gradlew.bat build # Runs Gradle build
$ gradlew.bat bootRun # Runs the project
```
3. Verify output matches what is described in step 5 of "Running via IntelliJ".
4. Navigate to https://localhost:8443/ on your browser.


### Frontend

Prerequisites: 
  - Everything from backend section because we will be running the backend server in the background.
  - Download VisualCode (recommended frontend IDE)
  
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


### Database Connection

1. Download *PgAdmin* (PostgreSQL's database management tool).   We use this application to view and query tables in our database. 
https://www.pgadmin.org/download/ 

2. Open PgAdmin
3. Add a DB Server:
  - Click Object -> Create -> Server ...
  - We just need to input information in the General and Connection tabs. Match the following images:
 ![Image of ](https://github.com/ShahilZ/Tucklets/blob/master/src/main/resources/static/images/readme/postgres-setup-general.JPG)
 ![Image of ](https://github.com/ShahilZ/Tucklets/blob/master/src/main/resources/static/images/readme/postgres-setup-connection.JPG)

4. Once your backend server is running, it will create the tables in Postgres.  
 ![Image of ](https://github.com/ShahilZ/Tucklets/blob/master/src/main/resources/static/images/readme/postgres-sql-view.JPG)

Please reach out to Shahil or Claudia for any questions regarding setup. 