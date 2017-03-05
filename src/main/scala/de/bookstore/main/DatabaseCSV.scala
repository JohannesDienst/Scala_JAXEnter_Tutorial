package de.bookstore.main

import scala.collection.mutable.ListBuffer
import java.io.BufferedReader
import java.io.FileReader
import java.io.IOException

class DatabaseSQL(dbPath: String = "src/main/resources/database.csv") extends Database {

  val books: ListBuffer[Book] = readFromFile()

  private def readFromFile() = {
    val reader = new BufferedReader(new FileReader(dbPath))
    try {
      for (line <- Iterator.continually(reader.readLine()).takeWhile(_ != null)) {
        println(line)
      }
    } catch {
      case e:IOException => e.printStackTrace()
    } finally {
      reader.close()
    }

    val books = ListBuffer[Book]()
    books
  }

  def save(filePath: String) = {
    // TODO
  }

  def update(book: Book) = {
    if (!Book.validateISBN10(book.isbn10)) {
      throw new IllegalArgumentException("Invalid ISBN10");
    }

    this.books += book
  }

  def findBooks(title: String, bookType: String): List[Book] = {
    return null
  }
}