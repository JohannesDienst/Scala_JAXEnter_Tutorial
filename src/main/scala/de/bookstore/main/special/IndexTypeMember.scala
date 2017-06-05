package de.bookstore.main.special

import scala.collection.mutable.ListBuffer
import de.bookstore.main.Book
import de.bookstore.main.EBook

/**
 * Useful links about type members vs type parameters(aka Generics)
 * http://typelevel.org/blog/2015/07/13/type-members-parameters.html
 * https://stackoverflow.com/questions/3170821/abstract-types-versus-type-parameters
 * https://stackoverflow.com/questions/1154571/scala-abstract-types-vs-generics/1154727#1154727
 *
 * Excellent blog with discussion about possible use cases:
 * http://www.artima.com/weblogs/viewpost.jsp?thread=270195
 * Discussion with good examples: http://www.artima.com/forums/flat.jsp?forum=106&thread=270195
 *
 * Self types: http://marcus-christie.blogspot.de/2014/03/scala-understanding-self-type.html
 */
trait IndexTypeMember {

  type ElementType

  val els = ListBuffer[ElementType]()

  def add(e: ElementType) = {
    els += e
  }

  def remove(e: ElementType) = {
    els -= e
  }
}

class BookIndex extends IndexTypeMember {
  type ElementType = Book
}

trait ProseIndex {
  this: IndexTypeMember => type ElementType = Book
}

class AIndex extends IndexTypeMember with ProseIndex {
  // Nothing
}

object IndexTypeMember {
  val index = new BookIndex()
  val book1 = new Book("The Pragmatic Programmer", "Andrew Hunt", 3446223096L)
  index.add(book1)

  /*
   * Produces:
   * type mismatch; found : String("This is Library!")
   * required: de.bookstore.main.special.Index.index.ElementType
   *           (which expands to) de.bookstore.main.Book
   */
//  index.add("This is Library!");

  val aIndex = new AIndex()
  aIndex.add(book1)

  /*
   * Little demo code for self types
   */
  trait A { }
  trait B { this: A => {}}

  /*
   * Produces:
   * illegal inheritance; self-type de.bookstore.main.special.IndexTypeMember.C
   * does not conform to de.bookstore.main.special.IndexTypeMember.B's selftype
   * de.bookstore.main.special.IndexTypeMember.B with de.bookstore.main.special.IndexTypeMember.A
   */
  //trait C extends B {}

  trait C extends A with B {}

  /*
   * Multiple type members
   */
  trait X { type T; type U; type W }
  trait Y extends X {}
  trait Z extends Y { type T = String }
  class AClass[M] extends Z { type U = M; type W = String }
  new AClass[Boolean]

  /*
   * Multiple type parameters
   * Difference: You cannot leave out a type parameter in I or J or BClass!
   */
  trait H[T, U, W] {}
  trait I[T, U, W] extends H[T, U, W] {}
  trait J[U, W] extends I[String, U, W] {}
  class BClass[M] extends J[M, String] {}
  new AClass[Boolean]
}