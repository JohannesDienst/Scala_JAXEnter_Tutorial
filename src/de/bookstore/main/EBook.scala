package de.bookstore.main

class EBook(
  title: String,
  val author: String,
  isbn10: Long = -1,
  val formats: List[String] = List[String]())
  extends Book(title, author, isbn10) {
  
    def this(title: String) = {
      this(title, "John", -1)
    }
}

object EBook {
  def main(args: Array[String]): Unit = {
    val ebook = new EBook(title="Learning Scala", "John");
    println(ebook.author) // Prints John
    println(ebook.isbn10) // Prints -1
    println(ebook.formats) // Prints List()
  }
}