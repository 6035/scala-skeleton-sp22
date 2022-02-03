package compile

import edu.mit.compilers.parser.{Parser, Scanner}
import util.CLI
import scala.util.parsing.input.Reader
import scala.util.parsing.input.StreamReader
import scala.collection.immutable.PagedSeq
import java.io._
import scala.io.Source
import scala.collection.mutable.{StringBuilder, ListBuffer}
import scala.Console

object Compiler {
  var outFile = if (CLI.outfile == null) Console.out else (new java.io.PrintStream(
    new java.io.FileOutputStream(CLI.outfile)))
  def main(args: Array[String]): Unit = {
    CLI.parse(args, Array[String]());
    if (CLI.target == CLI.Action.SCAN) {
      scan(CLI.infile)
      System.exit(0)
    } else if (CLI.target == CLI.Action.PARSE) {
        if(parse(CLI.infile).isEmpty) {
          System.exit(1)
        }
        System.exit(0)
    }
  }

  def scan(fileName: String) {
    try {
      val inputStream: FileInputStream = new java.io.FileInputStream(fileName)
      val scanner = new Scanner(scala.io.Source.fromInputStream(inputStream).mkString)
      scanner.setTrace(CLI.debug)
      try {
        for (token <- scanner.scan()) {
          outFile.println(token.render)
        }
      } catch  {
        case ex: Exception => Console.err.println(CLI.infile + " " + ex)
                              System.exit(1)
      }

    } catch {
      case ex: Exception => Console.err.println(ex)
    }
  }

    def parse(fileName: String): Option[String] = {
    /**
    Parse the file specified by the filename. Eventually, this method
    may return a type specific to your compiler.
    */
    var inputStream : java.io.FileInputStream = null
    try {
      inputStream = new java.io.FileInputStream(fileName)
    } catch {
      case _: FileNotFoundException => Console.err.println("File " + fileName + " does not exist");
                                       return Option.empty[String]
    }
    try {
      val scanner = new Scanner(scala.io.Source.fromInputStream(inputStream).mkString)
      val parser = new Parser(scanner)
      parser.parse()
       if (!parser.hasError) {
         Option("Successfully parsed")
       } else {
         println("Error during parsing.")
         Option.empty[String]
       }
    } catch {
      case e: Exception => Console.err.println(CLI.infile + " " + e)
        Option.empty[String]
    }
  }
}
