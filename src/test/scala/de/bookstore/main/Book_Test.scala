package de.bookstore.main

class Book_Test extends UnitTest("Book") {
  
  "A Book" should "print the right digit" in {
    val book = new Book("Clean Code", "Uncle Bob", 3826655486L)
    
    assert(book.getDigit === "6")
  }
  
}