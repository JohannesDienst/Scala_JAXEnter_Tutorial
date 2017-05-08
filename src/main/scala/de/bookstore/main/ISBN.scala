package de.bookstore.main

object ISBN {

  def apply(isbn: Long, length: Int): Boolean = {
    if (length == 10) {
      return Book.validateISBN10(isbn)
    }
    return false;
  }

  def main(args: Array[String]): Unit = {
    println(ISBN(3826655486L, 10));
    println(ISBN(3826655486L, 13));
  }
}