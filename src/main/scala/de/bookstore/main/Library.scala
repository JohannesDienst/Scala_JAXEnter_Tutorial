package de.bookstore.main

abstract class Library(db: Database) {

  def persist = { 
    db.save("")
  }

  def add(book: Book)
}