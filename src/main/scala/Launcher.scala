import java.io.File
import scala.collection.mutable.ListBuffer
import scala.io.Source
import scala.util.Random

/**
 * Created by user on 9/15/14.
 */

import Model._

object Launcher extends App {

  def analize(dataLeft: List[(String, String)], dataRight: List[(String, String)]) = {
    var sab = 0.0
    var sab_ = 0.0
    var sa_b = 0.0
    var sa_b_ = 0.0

    var nab = 0
    var nab_ = 0
    var na_b = 0
    var na_b_ = 0

    dataLeft zip dataRight map {
      case ((lmeasurer, l) , (rmeasurer, r)) => lmeasurer + rmeasurer -> l.toInt.bool ~r.toInt.bool
    } foreach {
      case ("a'b'", crl) => sa_b_ += crl; na_b_ += 1
      case ("a'b", crl) => sa_b += crl; na_b += 1
      case ("ab'", crl) => sab_ += crl; nab_ += 1
      case ("ab", crl) => sab += crl; nab += 1
    }

    val ab = sab / nab
    val ab_ = sab_ / nab_
    val a_b = sa_b / na_b
    val a_b_ = sa_b_ / na_b_

    //println(s"S = ${ab} - ${ab_} + ${a_b} + ${a_b_}")
    ab - ab_ + a_b + a_b_
  }

  def printChunked(dataLeft: List[(String, String)], dataRight: List[(String, String)], n: Int) {
    def getS(dataLeft: List[(String, String)], dataRight: List[(String, String)], acc: List[Double]) : List[Double] = {
      if (dataLeft.isEmpty & dataRight.isEmpty) acc
      else getS(dataLeft.drop(n), dataRight.drop(n), analize(dataLeft.take(n), dataRight.take(n)) :: acc)
    }

    val res = getS(dataLeft, dataRight, Nil)
    println(s"S = ${res.min}..${res.max}")

  }

  def readStatistics(filename: String) = {
    Source.fromFile(new File(filename)) getLines() map(_.split(" ").toList) map {
      case seqNumber :: measurer :: state :: Nil => measurer -> state
    } toList
  }

  def measure(isLeft: Boolean, rawStates: List[String], rawEntaglement: List[String]) = {
    val states = rawStates.map(_.toInt.bool)
    val e = Entanglement(rawEntaglement.map(_.toInt.bool).toArray)
    val m = Detector("", isLeft)(e)
    def name(master: Boolean) = (m.isLeft, master) match {
      case (false, false) => "b'"
      case (false, true) => "b"
      case (true, false) => "a'"
      case (true, true) => "a"
    }
    for ((s, i) <- states zipWithIndex) println(s"$i ${name(s)} ${m.measure(i, s).int}")
  }

  def readEntanglement(filename: String) = Source.fromFile(new File(filename)).getLines().toList.head.map(_.toString).toList
  def printEntanglement(e: Entanglement) = println(e.g.map(_.int).mkString(""))


  args.toList match {
    case "entangle" :: count :: Nil => printEntanglement(Entanglement(generate(count.toInt)))
    case "measureA" :: entanglementFile :: count :: "random" :: Nil =>
      measure(true, generate(count.toInt).toList.map(_.int.toString), readEntanglement(entanglementFile))
    case "measureA" :: entanglementFile :: groupSize :: measurerStates :: Nil =>
      measure(true, measurerStates.toList.map(_.toString).flatMap(List.fill(groupSize.toInt)(_)), readEntanglement(entanglementFile))
    case "measureB" ::  entanglementFile :: count :: "random" ::  Nil =>
      measure(false, generate(count.toInt).toList.map(_.int.toString), readEntanglement(entanglementFile))
    case "measureB" :: entanglementFile :: groupSize :: measurerStates :: Nil => //TODO add groupSize
      measure(false, measurerStates.toList.map(_.toString).flatMap(List.fill(groupSize.toInt)(_)), readEntanglement(entanglementFile))
    case "analize" :: aName :: bName :: Nil => println("S = " + analize(readStatistics(aName), readStatistics(bName)))
    case "analize" :: aName :: bName :: count :: Nil => printChunked(readStatistics(aName), readStatistics(bName), count.toInt)
    case _ => Experiment.experiment()

  }

}
