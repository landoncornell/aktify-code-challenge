
## Running The Application
### Gradle
Install gradle. You can use sdk man or homebrew. You do need java installed in order to run gradle.

### Create a Jar
From the command line run the following:

`gradle shadowJar`

### Create a Docker Image
Run the following:

`docker build -t aktify .`

### Run the Application
#### Docker 

    docker run -m512M --cpus 4 -it -p 8080:8080 -e PORT=8080 --rm aktify

#### Non-Docker

    gradle run
    
### PostgreSQL

Make sure you have postgres running with the aktify database added. If you are running the app in docker and postgres in docker
ensure that they are on the same docker network.

### Migrations

Go to `aktify-kotlin/src/main/kotlin/ninja/jwillis/migrations` and run the `AktifyMigrations.kt` file.

This will run the migration in the `/src/main/resources/db/migration` folder.

### Enjoy

You should now be able to use postman or another front end client to access campaigns. 

