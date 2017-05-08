package de.bookstore.main.traits

trait Export {
  def isEncoded() = println(encode)
  def encode: Boolean;
  def exportCSV(): String;
}