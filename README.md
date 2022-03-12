To list all of gradle tasks: run "$./gradlew tasks". 

To build and run the server: run "$./gradlew run". 

To run the unit tests: run "$./gradlew test". 

This program is submitted as a project for my software engineer class with Professor Jeff Foster.

This program contains two parts: jrails, which contains the MVC framework code, and books, which contains the code for the books server.

----------------------------------------------------------------------------------------

Implementation:


Models: This program includes models that can represent the database. For example, here is the model from the book app:

```
import jrails.*;
public class Book extends Model {
    @Column public String name;
    @Column public String author_name;
    @Column public int id;
}
```

Here is some supported code for a model:

```
Book book = new Book();
book.name = "Some name";
book.author_name = "Some author";
book.num = 829292;
book.save(); // Save the book into the db
book.num = 42223; 
book.save(); // now the book in the db has num 42223
Book book2 = new Book();
book2.title = "Some name";
book2.author = "Some author";
book2.num = 42223; // This is the same book
book2.save(); // a second record is added to the database
assert(book.id() != book2.id());
Book book3 = Model.find(Book.class, 3); // finds the book with id 3 in the db
List<Book> bs = Model.all(Book.class); // returns all books in the db
book.destroy(); // remove book from db
```
  
The db is implemented using a simple txt file that contains all the db information.
  
Views:
  
In the program, I implemented a very simple view system that does not involve the HTML embeds java code. Instead, I simply create HTML by invoking methods recursively in Java.
Here is some example code

```
public static Html show_book(Book b) {
        return p(h1(t("Book name:")).t(b.name)).
                p(strong(t("Author:")).t(b.author_name)).
                p(strong(t("Num:")).t(b.num)).
                t(link_to("Edit", "/edit?id=" + b.id())).t(" | ").
                t(link_to("Back", "/"));
    }
```
  
the show_book method takes a Book and returns an Html, which is a data representation of HTML.
  
  
The Router:
  
  The router has to be configured on a per-app basis, which is done with a series of calls to addRoute. For example, here is the routing for the example app:
  
  ```
  JRouter r = new JRouter();
        r.addRoute("GET", "/", BookController.class, "index");
        r.addRoute("GET", "/show", BookController.class, "show_book");
        r.addRoute("GET", "/new", BookController.class, "new_book");
        r.addRoute("POST", "/create", BookController.class, "create");
        r.addRoute("POST", "/update", BookController.class, "update");
        r.addRoute("GET", "/delete", BookController.class, "destroy");
  ```
  



