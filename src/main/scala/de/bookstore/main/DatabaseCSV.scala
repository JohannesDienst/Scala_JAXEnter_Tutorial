package de.bookstore.main

import scala.collection.mutable.ListBuffer
import java.io.BufferedReader
import java.io.FileReader
import java.io.IOException
import scala.util.control.NonFatal

// TODO: Option, Try
class DatabaseSQL(dbPath: String = "src/main/resources/database.csv") extends Database {

  val books: ListBuffer[Book] = readFromFile()

  private def readFromFile() = {
    val reader = new BufferedReader(new FileReader(dbPath))
    try {
      for (line <- Iterator.continually(reader.readLine()).takeWhile(_ != null)) {
        println(line)
      }
    } catch {
      case e: IOException => e.printStackTrace()
      
      // DO NOT USE THIS: Catches JVM errors too!
      //case e: Throwable => e.printStackTrace()

      // Use this instead to catch everything none fatal
      case NonFatal(e) => e.printStackTrace()
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

    /*
     *  We can do better than this!
     *
    if (!Book.validateISBN10(book.isbn10)) {
      throw new IllegalArgumentException("Invalid ISBN10");
    }
    */
    
    def validate(book: Book): Either[String, Book] = {
      if (Book.validateISBN10(book.isbn10)) {
        Right(book)
      } else {
        Left("Book has invalid ISBN10: " + book)
      }
    }

    validate(book).fold(
      error => println(error),
      success => { this.books += book }
    )
  }

  def findBooks(title: String, bookType: String): List[Book] = {
    return null
  }
}