package de.bookstore.main

class EBook(
  title: String,
  val author: String,
  isbn10: Long = -1,
  val formats: List[String] = List[String](),
  quantity: Int = 1
  )
  extends Book(title, author, isbn10, quantity) {

    def this(title: String) = {
      this(title, "John", -1)
    }

  override def toXml = {
      <book>
          <title>{title}</title>
          <author>{author}</author>
          <isbn10>{isbn10}</isbn10>
          <formats>{formats.mkString(",")}</formats>
      </book>
  }

  // Actually s is a method
  override def toString = s"$title;$author;$isbn10;$formats"
}

object EBook {
  def main(args: Array[String]): Unit = {
    val ebook = new EBook(title="Learning Scala", "John");
    println(ebook.author) // Prints John
    println(ebook.isbn10) // Prints -1
    println(ebook.formats) // Prints List()
    println(ebook)
    println(s"Title: ${ebook.title}")

    // printf style
    val price = 200.0
    println(f"Price: $price")
    println(f"Price: $price%.0f")

    // raw
    println(raw"no line\nbreak")
  }

  def fromXml(node: scala.xml.Node): Book = {
    val title = (node \ "title").text
    val author = (node \ "author").text
    val isbn10 = (node \ "isbn10").text.toLong
    val formats: List[String] = (node \ "formats").text.split(",").toList
    new EBook(title, author, isbn10, formats)
  }
}