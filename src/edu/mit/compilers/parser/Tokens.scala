/**
 * THIS IS JUST A SUGGESTED TRAIT, YOU CAN MODIFY OR ADD ANYTHING YOU WISH
 */

package edu.mit.compilers.parser

trait Token {
  def render: String
}

object EOF extends Token {
  def render = "EOF"
}







