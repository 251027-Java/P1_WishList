# P1_WishList

## How it's set up

* There are two main folders, the backend located in the "API" folder and the frontend located in the "Angular" folder
* The postgres database will be set up seperately inside of a Docker container
* The program contains the necessary information to connect to the database within the `/API/src/main/resources/application.properties` file
* To run everything, you'll need to set up the Docker container yourself within Docker Desktop, run the backend by running the `WishListApplication.java` file, and run the frontend by typing `ng serve` within the frontend folder