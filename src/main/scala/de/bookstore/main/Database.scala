package de.bookstore.main

trait Database {
  def save(filePath: String)
  def update(book: Book)
  def findBooks(title: String, bookType: String): List[Book]
}