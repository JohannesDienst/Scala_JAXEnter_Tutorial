package de.bookstore.main

import scala.collection.mutable.ListBuffer
import java.io.BufferedReader
import java.io.FileReader
import java.io.IOException
import scala.util.control.NonFatal
import java.security.AccessControlException
import java.nio.file.{Paths, Files}
import java.io.PrintWriter
import java.io.File
import scala.util._

class DatabaseCSV(dbPath: String = "src/main/resources/database.csv") extends Database {

  val books: ListBuffer[Book] = ListBuffer[Book]()
  readFromFile()

  private def readFromFile() = {
    val reader = new BufferedReader(new FileReader(dbPath))
    try {
      for (line <- Iterator.continually(reader.readLine()).takeWhile(_ != null)) {
        lineToBook(line) match {
          case Some(book) => update(book)
          case None => println("Line is not valid: " + line)
        }
      }
    } catch {
      case e: IOException => e.printStackTrace()

      // DO NOT USE THIS: Catches JVM errors too!
      //case e: Throwable => e.printStackTrace()

      // Use this instead to catch everything non fatal
      case NonFatal(e) => e.printStackTrace()

    } finally {
      reader.close()
    }
  }

  private def lineToBook(line: String): Option[Book] = {
    try {
      val splitLine = line.split(";").map(_.trim)
      return Some(Book.fromCSV(splitLine))
    } catch {
      case NonFatal(e) => None
    }
  }

  def save(filePath: String = "src/main/resources/database.csv") = {
    if (!Files.exists(Paths.get(filePath))) {
      throw new AccessControlException("File does not exist")
    }
    val csv = books.map(b => b.exportCSV)
    val pw = new PrintWriter(new File(filePath))
    pw.write(csv.mkString("\n"))
    pw.close()
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

  def findBooks(title: String = "", bookType: String = ""): List[Book] = {
    return this.books.toList
  }
}

object DatabaseCSV {
  def main(args: Array[String]): Unit = {
    val db = new DatabaseCSV()

    println("/******* Option usage *******/")
    /* Ways to deal with Option */
    val books: Option[List[Book]] = Option(db.findBooks())

    // DO NOT USE THIS
    if (books.isDefined) {
      println(books)
    }


    // Use this instead
    val bookList = books.getOrElse(List())
    println(bookList)

    // foreach: provided function is called once if
    //          value is present
    books.foreach { l => println(l.size) }

    // filter
    println(books.filter( _.isEmpty )) // Returns None
    println(books.filter( !_.isEmpty )) // Returns Some(List(...))

    // map
    println(books.map(_(0).title))
    println(None.map { _ == true })

    // flatmap
    println("flatmap examples:")
    val numbers: List[List[Int]] =
      List(List(), List(7), List(42, 27))
    numbers.map(_.map(_ * 2))
    println(numbers.map(_.map(_ * 2)))
    println(numbers.flatMap(_.map(_ * 2)))

    val flatMapOption: List[Option[Int]] = List(Some(27), None, Some(42))
    /* List(Some(54), None, Some(84)) */
    println(flatMapOption.map(_.map(_ * 2)))
    /* List(54, 84) */
    println(flatMapOption.flatMap(_.map(_ * 2)))

    // for comprehension
    println("for comprehension examples:")
    val nestedOption = Some(Some("Clean Code"))
    // Return type: Option[String]
    val test = for {
      some <- nestedOption
      title <- some
    } yield title

    val listTitleOptions: List[Option[String]] =
      List(Some("Clean Code"), None, Some("Code Complete"))

    // Return type: List[String]
    for {
      option <- listTitleOptions // Option[String]
      title <- option
    } yield title

    println("/****************************/\n")

    println("/******* Either usage *******/")
    def validate(book: Book): Either[String, Book] = {
      if (Book.validateISBN10(book.isbn10)) {
        Right(book)
      } else {
        Left("Book has invalid ISBN10: " + book)
      }
    }
    val either = validate(new Book("Clean Code", "Uncle Bob", 137081073L))
    val either2 = validate(new Book("Code Complete 2", "Steve McConnell", 735619670L))

    // Pattern Matching
    println("Pattern matching")
    either match {
      case Left(error) => println("Left: " + error)
      case Right(book) => println("Right: " + book)
    }

    // Left/Right Projection
    println("Projection")
    either.left.map(println(_)) // Either[Unit, Book]
    either.right.map(println(_)) // Either[Unit, Book]

    // flatMap
    val notFlat: Either[String, Either[String, String]] = 
      either.right.map(a =>
        either2.right.map(b =>
          s"Title1: $a.title, Title2: $b.title"
        )
      )
    val flat: Either[String, String] = either.right.flatMap(a =>
      either2.right.map(b =>
        s"Title1: $a.title, Title2: $b.title"
      )
    )

    // for comprehension
    println("for comprehension")
    val comprehend: Either[String, String] =
      for {
        e1 <- either.right
        e2 <- either2.right
      } yield s"Title1: $e1.title, Title2: $e2.title"
    println(comprehend)
    val comprehend2: Either[String, String] =
      for {
        e1 <- either.right
        e2 <- either2.right
        /* Will not compile: no map on Right!!
        t1 = e1.title
        t2 = e2
        */
        t1 <- Right(e1.title).right
        t2 <- Right(e2.title).right
      } yield s"Title1: $t1, Title2: $t2"
    println(comprehend2)
    println("/****************************/\n")

    println("/******* Try usage **********/")
    /* Usage of Try */
    val saveResult = Try(db.save("blub"))
    saveResult match {
      case Failure(thrown) => {
        println(thrown)
      }
      case Success(s) => {
        println(s)
      }
    }

    println(saveResult.getOrElse("That went wrong!"))

    // Retrieve Throwable: Fails when no Exception thrown
    println(saveResult.failed.get)

    // Recover
    saveResult.recover( {
        case e => println("Your recovery logic here")
      }
    )

    // Recover with different Failure
    val diffSaveResult = saveResult.recoverWith( {
        case e => Failure(new IllegalArgumentException("Wrong filepath"))
      }
    )
    diffSaveResult.recover( {
        case e => e.printStackTrace()
      }
    )

    // Recover in for comprehension
    println(for {
      i <- Try(Integer.parseInt("42a")).recover({case e => -1})
    } yield i)
    println(for {
      i <- Try(Integer.parseInt("42a"))
    } yield i)

    println("/****************************/\n")
  }
}