package de.bookstore.main

object Lib {

  // Inheritance based approach
  trait PublicationClassic[T] {
    def getQuantity: Int
  }
  case class PublicationClassicBook(x: Book) extends PublicationClassic[Book] {
    def getQuantity = x.quantity
  }
  case class PublicationClassicEBook(x: EBook) extends PublicationClassic[EBook] {
    def getQuantity = x.quantity
  }
  def findAvailableClassic[T](xs: List[PublicationClassic[T]]): List[PublicationClassic[T]] = {
    xs.filter(p => p.getQuantity > 0)
  }

  // Type Class based approach
  trait Publication[T] {
    def getQuantity(x: T): Int
  }

  object Publication {

    def apply[A: Publication]: Publication[A] = implicitly[Publication[A]]

    implicit object PublicationMagazine extends Publication[Magazine] {
      def getQuantity(m: Magazine): Int = m.quantity
    }

    implicit object PublicationBook extends Publication[Book] {
      def getQuantity(b: Book): Int = b.quantity
    }
  }

  def findAvailable[T](xs: List[T])(implicit ev: Publication[T]): List[T] = {
    xs.filter(m => ev.getQuantity(m) > 0)
  }

  def findAvailable2[T: Publication](xs: List[T]): List[T] = {
    xs.filter(m => implicitly[Publication[T]].getQuantity(m) > 0)
  }

  def findAvailable3[T: Publication](xs: List[T]): List[T] = {
    xs.filter(m => Publication[T].getQuantity(m) > 0)
  }
}