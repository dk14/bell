import scala.util.Random

/**
 * Created by user on 10/1/14.
 */
object Model {

  //Simulation
  type QSeq = Array[Boolean] //type alias
  val gen = new Random
  def generate(implicit n: Int = 101): QSeq = (1 to n).map(_ => gen.nextBoolean()).toArray //generates pseudo-randoms
  val strategyRandom = generate(10000) //just pre-generated randoms

  case class Entanglement(g: QSeq = generate(1000000)) //shared immutable hidden parameter

  /**
   *
   * @param name - just name
   * @param isLeft - a or b
   * @param e - entanglement
   */
  case class Detector(name: String = "", isLeft: Boolean = true)(implicit e: Entanglement) {
    def yes = e.g //returns random correlated with hidden parameter
    def no = e.g.map(!_) //returns random anti-correlated with hidden parameter

    def strategy(seqNumber: Int) = strategyRandom(seqNumber / 100) //it may also be just gen.nextBoolean

    /**
     * measure particle state
     * @param seqNumber - sequence number (timeTag) of particle
     * @param master - detector setting: a or a', b or b'
     * @return result of measurement (may be true or false), which will participate in S calculation
     */
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
