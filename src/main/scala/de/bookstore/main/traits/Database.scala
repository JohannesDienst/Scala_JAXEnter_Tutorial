package de.bookstore.main.traits

import de.bookstore.main.Book

trait Database {
  def save(filePath: String)
  def update(book: Book)
  def findBooks(title: String, bookType: String): List[Book]
}