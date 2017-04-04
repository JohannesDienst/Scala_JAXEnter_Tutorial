package de.bookstore.main

object Lib {

  trait Publication[T] {
    def getQuantity(x: T): Int
  }

  object Publication {
    implicit object PublicationMagazine extends Publication[Magazine] {
      def getQuantity(m: Magazine): Int = m.quantity 
    }

    implicit object PublicationBook extends Publication[Book] {
      def getQuantity(b: Book): Int = b.quantity
    }
  }
}