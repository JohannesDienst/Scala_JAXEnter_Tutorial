package de.bookstore.main

import scala.xml.Elem
import scala.xml.NodeSeq

class Book_Test extends UnitTest("Book") {
  "A Book" should "print the right digit" in {
    val book = new Book("Clean Code", "Uncle Bob", 3826655486L)
    assert(book.getDigit === "6")
  }

  "A Book" should "output a valid XML" in {
    val book = new Book("Clean Code", "Uncle Bob", 3826655486L)
    val bookXML: Elem = book.toXml
    val title: NodeSeq = bookXML \\ "title"
    val author = bookXML \\ "author"
    val isbn10 = bookXML \\ "isbn10"
    assert(title.text === "Clean Code")
    assert(author.text === "Uncle Bob")
    assert(isbn10.text === "3826655486")
  }

  "A Book" should "should be deserialisable from a valid XML" in {
    val book = new Book("Clean Code", "Uncle Bob", 3826655486L)
    val bookXml = "<book>" + 
            "<title>Clean Code</title>" +
            "<author>Uncle Bob</author>" +
            "<isbn10>3826655486</isbn10>" +
        "</book>"
    val elem = scala.xml.XML.loadString(bookXml)
    assert(Book.fromXml(elem).toString() === book.toString())
  }

  "A ISBN10" should "be valid without leading zero" in {
    assert(Book.validateISBN10(735619674L) === true)
  }
}