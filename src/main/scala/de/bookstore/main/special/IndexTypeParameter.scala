package de.bookstore.main.special

import scala.collection.mutable.ListBuffer
import de.bookstore.main.EBook

trait IndexTypeParameter[T] {
  val els = ListBuffer[T]()

  def add(e: T) = {
    els += e
  }

  def remove(e: T) = {
    els -= e
  }
}

class EBookIndex extends IndexTypeParameter[EBook] { /* Nothing */ }

trait LyricIndex {
  this: IndexTypeParameter[EBook] => {}
}

class GermanLyricIndex extends IndexTypeParameter[EBook] with LyricIndex { }

object IndexTypeParameter {
  val index = new EBookIndex()

  val book1 = new EBook("Herrmann Hesse", "Stufen", 3518013424L)
  index.add(book1)

  val bIndex = new GermanLyricIndex()
  bIndex.add(book1)
}