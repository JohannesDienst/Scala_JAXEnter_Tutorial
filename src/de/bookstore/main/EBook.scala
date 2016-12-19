package de.bookstore.main

class EBook(override val title: String, author: String, override val isbn10: Long)
  extends Book(title, author, isbn10) {
  
}

object EBook {
  def main(args: Array[String]): Unit = {
    val ebook = new EBook("Learning Scala", "test", 12345L);
  }
}