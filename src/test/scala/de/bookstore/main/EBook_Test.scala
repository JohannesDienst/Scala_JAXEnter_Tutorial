package de.bookstore.main

class EBook_Test extends UnitTest("EBook") {
  "A EBook" should "output a valid XML" in {
    val ebook = new EBook("Clean Code", "Uncle Bob", 3826655486L, List("epub", "pdf"))
    val title = ebook.toXml \\ "title"
    val author = ebook.toXml \\ "author"
    val isbn10 = ebook.toXml \\ "isbn10"
    val formats = ebook.toXml \\ "formats"
    assert(title.text === "Clean Code")
    assert(author.text === "Uncle Bob")
    assert(isbn10.text === "3826655486")
    assert(formats.text === "epub,pdf")
  }

  "A EBook" should "be deserialisable from a valid XML" in {
    val ebook = new EBook("Clean Code", "Uncle Bob", 3826655486L, List("epub", "pdf"))
    val ebookXml = "<book>" + 
            "<title>Clean Code</title>" +
            "<author>Uncle Bob</author>" +
            "<isbn10>3826655486</isbn10>" +
            "<formats>epub,pdf</formats>" +
        "</book>"
    val elem = scala.xml.XML.loadString(ebookXml)
    assert(EBook.fromXml(elem).toString() === ebook.toString())
  }
}