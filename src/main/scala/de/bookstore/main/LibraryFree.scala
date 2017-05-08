package de.bookstore.main

import de.bookstore.main.traits.Database

abstract class LibraryFree(db: Database) {

  def persist = {
    db.save("")
  }

  def add(book: Book)
}