package de.bookstore.main.special

import de.bookstore.main.traits.Library
import scala.collection.mutable.ListBuffer
import de.bookstore.main.Book

class LibraryDRM extends Library {

  val books = ListBuffer[Book]()

  def add(book: Book) { books += book }

  class DRMBook(
    title: String,
    val author: String,
    isbn10: Long = -1)
    extends Book(title, author, isbn10) { }
}