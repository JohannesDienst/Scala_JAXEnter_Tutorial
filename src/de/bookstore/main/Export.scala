package de.bookstore.main

trait Export {
  
  def isEncoded() = println(encode)
  def encode: Boolean;
  def exportCSV(): String;
  
}