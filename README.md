# meli

## Backend: API Development

### API Endpoints

- Implement a RESTful API that supports the frontend by providing the necessary product details ✅
- The primary endpoint should fetch product details ✅
- You can use any backend technology or framework of your choice ✅
- Do not use real databases, persist everything in local JSON or CSV files ✅

### Data
The page should display key information such as:
  - Product images ✅
  - Title and description ✅
  - Price ✅
  - Payment methods ✅
  - Seller information ✅
  - Additional details (e.g., ratings ✅, reviews ✅, or available stock)

### Non-functional requirements

- Proper error handling ✅
- Documented ✅ (Swagger)
- At least 80% code coverage ✅

## Documentation

Along with your code submission, include a brief (1-2 page) document that:

- Explains your design choices. ✅ (README.md)
- Describes any challenges you faced and how you addressed them. ✅ (README.md)

## Submission

- Provide a link to your repository or a zipped project folder. ✅  
  [https://github.com/lucasmi/meli](https://github.com/lucasmi/meli)
- It must contain a `run.md` explaining how to run the project ✅  
  [https://github.com/lucasmi/meli/run.md](https://github.com/lucasmi/meli/run.md)


  ## Explains your design choices  
  Based on the technologies within my domain, I chose to work with Java, given my longer experience with the language. Once the primary technology was defined, I began analyzing the project requirements.

  As I needed to develop endpoints following the REST architecture, I selected Spring Boot for its development agility and ease of integration. For the persistence layer, the only constraint was to use either JSON or CSV files. Considering this, I opted for JSON due to its flexibility and better fit for the data structures I planned to handle.

  I started exploring various approaches to building the solution, fully aware of the challenges related to parallelism in file manipulation and data relationships. That’s when I had the idea to draw inspiration from JPA—a widely used library that abstracts interactions with relational databases.

  I adapted that concept to a file-based context: I organized data into folders representing “tables,” and treated each individual file as a “row” within that structure. To simplify relationships and primary key handling, I chose to use UUIDs as unique identifiers. This eliminated the need to manage sequential numbers manually, adding robustness and scalability to the project.

Relationship Diagram.
    
  ![Diagrama](https://github.com/lucasmi/meli/blob/main/diagrama.png)

  ## Challenges
  - File handling with parallel threads: To ensure data integrity during concurrent operations, I implemented locking mechanisms on the threads before performing any file manipulation. This guaranteed that only one thread could access the shared resource at a time, effectively preventing race conditions.

  - Relationship management and manipulation of generated files: To streamline and standardize development, I created the JpaJson library, inspired by JPA principles. This approach led me to structure the code into two main layers:
    - The Repository layer, responsible for persisting data in JSON format.
    - The Entity layer, in charge of representing and manipulating the data.