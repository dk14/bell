import scala.util.Random

/**
 * Created by user on 10/1/14.
 */
object Model {

  //Simulation
  type QSeq = Array[Boolean]
  val gen = new Random
  def generate(implicit n: Int = 101): QSeq = (1 to n).map(_ => gen.nextBoolean()).toArray
  val strategyRandom = generate(10000)

  case class Entanglement(g: QSeq = generate(1000000)) //shared immutable hidden parameter

  case class Detector(name: String = "", isLeft: Boolean = true)(implicit e: Entanglement) {
    def yes = e.g //correlated with hidden parameter
    def no = e.g.map(!_) //anti-correlated with hidden parameter

    def strategy(seqNumber: Int) = strategyRandom(seqNumber / 100) //it may also be just gen.nextBoolean

    def measure(implicit seqNumber: Int, master: Boolean = false) =
      if (strategy(seqNumber) && !isLeft && !master) no(seqNumber) else yes(seqNumber) //this is how it works

  }

  //correlation dsl
  implicit class RichBoolean(a: Boolean) {
    def ~ (b: Boolean) = if (a == b) +1 else -1
    def int = if(a) 1 else 0
  }

  implicit class RichInt(a: Int) {
    def bool = if (a == 0) false else true
  }

  def correlation(a: QSeq, b: QSeq) = a.zip(b).map{case (a, b) => a ~ b}.sum.toDouble / a.size

  implicit class RichQSeq(x: QSeq) {
    def ~ (y: QSeq) = correlation(x, y)
  }
}
