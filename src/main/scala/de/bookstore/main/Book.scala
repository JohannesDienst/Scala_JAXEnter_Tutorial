package de.bookstore.main

/*
 * Scala is a pure-bred object-oriented language.
 * Conceptually, every value is an object and every
 * operation is a method-call. The language supports
 * advanced component architectures through classes
 * and traits.
 */
class Book (val title: String, author: String, var isbn10: Long)
  extends Export {
  
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
    isbn10.toString() takeRight(1)
  }
  
  override def toString: String =
    title + ", " + author + ", " + this.isbn10

  def encode = false
  def exportCSV: String = {
    title + ";" + author + ";" + this.isbn10
  }

  def toXml = {
      <book>
          <title>{title}</title>
          <author>{author}</author>
          <isbn10>{isbn10}</isbn10>
      </book>
  }
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
    println(book.exportCSV)

    println(book.toXml)
  }

  def validateISBN10(isbn: Long): Boolean = {
    var s: Int = 0
    var t: Int = 0

    var digits = isbn.toString().map(_.toChar)
    if (digits.length() < 10) digits = "0" + digits
    if (digits.length() < 10) return false
    for (i <- 0 to 8) {
      t += digits(i);
      s += t;
    }

    (s % 11) == digits(9).asDigit
  }

  def fromXml(node: scala.xml.Node): Book = {
    val title = (node \ "title").text
    val author = (node \ "author").text
    val isbn10 = (node \ "isbn10").text.toLong
    new Book(title, author, isbn10)
  }

  def unapply(book: Book) = Some(book.title, book.isbn10)
}