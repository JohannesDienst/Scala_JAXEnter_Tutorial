package de.bookstore.main


/*
 * Scala is a pure-bred object-oriented language.
 * Conceptually, every value is an object and every
 * operation is a method-call. The language supports
 * advanced component architectures through classes
 * and traits.
 */
class Book (val title: String, author: String, var isbn10: Long) {
  
  def this(title: String) {
    this(title, "", -1)
  }
  
  def this(title: String, isbn10: Long) {
    this(title, "", isbn10)
  }
  
  /*
  Scala style guide says...

    Omit empty parenthesis, only be used when the method in question
    has no side-effects (purely-functional). In other words, it
    would be acceptable to omit parentheses when calling queue.size,
    but not when calling println(). 
  */
  def getDigit: String = {
  
    isbn10.toString() takeRight(1) // takeRight 1
  }
  
  override def toString: String =
    title + ", " + author + ", " + this.isbn10

}

object Book {
  
  def main(args: Array[String]): Unit = {
    val book = new Book("Clean Code", "Uncle Bob", 3826655486L)
    println(book)
    
    println(book.title)
//    book.title = "Another One" // reassignment to val
//    println(book.author) // Error: not a member
//    book.author = "Robert" // Error: not a member
    println(book.isbn10)
    book.isbn10 = 1234567890
    
    println(book.getDigit)
    
    println(Book.validateISBN10(3826655486L))
  }
  
  def validateISBN10(isbn: Long) :Boolean = {
      var s: Int = 0
      var t: Int = 0

      val digits = isbn.toString().map(_.toChar)
      
      for (i <- 0 to 8) {
        t += digits(i);
        s += t;
      }

      (s % 11) == digits(9).asDigit
  }
}