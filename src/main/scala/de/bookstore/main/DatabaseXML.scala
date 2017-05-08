package de.bookstore.main

import scala.xml.XML
import scala.xml.NodeSeq
import scala.xml.Node
import scala.xml.Elem
import scala.io.Source
import scala.collection.mutable.ListBuffer
import de.bookstore.main.traits.Database

class DatabaseXML(dbPath: String = "src/main/resources/database.xml") extends Database {

  val books: ListBuffer[Book] = readFromFile()

  private def readFromFile() = {
    val root: Elem = XML.loadFile(dbPath)
    val books = ListBuffer[Book]()

    for (n <- (root \\ "book") if (!(n \ "formats").isEmpty)) {
      books += EBook.fromXml(n)
    }

    for (n <- (root \\ "book") if ((n \ "formats").isEmpty)) {
      books += Book.fromXml(n)
    }

    books
  }

  def update(book: Book) = {
    this.books += book
  }

  def save(filePath: String = "src/main/resources/database.xml" ) = {
    val root = <books>{books.map(b => b.toXml)}</books>
    XML.save(filePath, root)
  }

  // Pattern matching
  // http://www.artima.com/pins1ed/case-classes-and-pattern-matching.html
  def findBooks(title: String = "", bookType: String = ""): List[Book] = {
    var retVar = books.clone()

    def titleMatch(title: String): ListBuffer[Book] = title match {
      case "" => retVar
      case _ => retVar.filter(book => book.title.contains(title))
    }

    def bookTypeMatch(bookType: String): ListBuffer[Book] = bookType match {
      case "book" => retVar.filter(b => !b.isInstanceOf[EBook])
      case "ebook" => retVar.filter(b => b.isInstanceOf[EBook])
      case _ => retVar
    }

    retVar = titleMatch(title)
    retVar = bookTypeMatch(bookType)
    retVar.toList
  }

  def findBooksAdvanced(title: String = "", bookType: String): List[Book] = {
    var retVar = books.clone()

    def aliasMatchTitleAuthor(book: Book, title: String, booktype: String): Book =
      (book, booktype) match {
        case ((Book(`title`, _)), "book") => { if (!book.isInstanceOf[EBook]) book else null }
        case ((Book(`title`, _)), "ebook") => { if (book.isInstanceOf[EBook]) book else null }
        case _ => null
      }

    val aliasMatchPartial = aliasMatchTitleAuthor(_: Book, title, bookType)

    retVar = retVar.map(aliasMatchPartial)
                   .filter(b => b != null)
    retVar.toList
  }

  // Immutability
  // https://www.slalom.com/thinking/overcoming-immutability-in-scala

  // Higher Order functions
  def validate(): List[Tuple2[Book, Boolean]] = {
    val validated = ListBuffer[Tuple2[Book, Boolean]]()
    for (book <- books) {
      if (Book.validateISBN10(book.isbn10)) {
        validated += Tuple2(book, true)
      }
      else {
        validated += Tuple2(book, false)
      }
    }
    val retVal = books.map(book => Tuple2(book, Book.validateISBN10(book.isbn10))).toList
    return retVal
  }
}

object DatabaseXML {
  def main(args: Array[String]): Unit = {
    val db = new DatabaseXML()

    db.update(new Book("Design Patterns", "Erich Gamma", 1201633612L));
    db.save("src/test/resources/database_save.xml")
  }
}