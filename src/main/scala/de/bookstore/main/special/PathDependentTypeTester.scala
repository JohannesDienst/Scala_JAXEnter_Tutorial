package de.bookstore.main.special

object PathDependentTypeTester {

  def main(args: Array[String]): Unit = {
    val libraryOnline = new LibraryOnline()
    var onlineBook = new libraryOnline.OnlineBook("The neophytes guide to Scala", "Kaffeecoder", -1)
    var onlineBook2 = new libraryOnline.OnlineBook("Clean Code", "Uncle Bob", 3826655486L)

    val libraryOnline2 = new LibraryOnline()
    var onlineBook3 = new libraryOnline2.OnlineBook("The Pragmatic Programmer", "Andrew Hunt", 3446223096L)

    val libraryDRM = new LibraryDRM()
    var drmBook = new libraryDRM.DRMBook("TODO", "anonymous", -1)

    /*
     * Produces:
     *   type mismatch; found : libraryDRM.DRMBook
     *   required: libraryOnline.OnlineBook
     */
//    onlineBook = drmBook
    onlineBook = onlineBook2

    /*
     * Produces:
     *   type mismatch; found : libraryOnline2.OnlineBook
     *   required: libraryOnline.OnlineBook
     */
//    onlineBook = onlineBook3

  }
}