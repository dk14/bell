import Model._

/**
 * Created by user on 10/1/14.
 */
object Experiment extends App {

  println((1 to 10).map(_ => experiment()).max)

  def experiment() = {
    implicit val e = Entanglement()

    val trigger = true
    val p1 = Detector("a", trigger)
    val p2 = Detector("b", !trigger)

    var sab = 0.0
    var sab_ = 0.0
    var sa_b = 0.0
    var sa_b_ = 0.0

    var nab = 0
    var nab_ = 0
    var na_b = 0
    var na_b_ = 0

    var n_nulls = 0
    var n_not_nulls = 0
    val n_j = 100
    // 10000 measurements with 100 switches of detector max(S) ~ 2.41, takes about 10 min on MacBookPro 2.3 GHz
    for (((pol1, pol2), i) <- generate(100) zip generate(100) zipWithIndex) {

      val measureLeft = for(j <- 1 to n_j toArray) yield p1.measure(i*n_j + j, pol1)
      val measureRight = for(j <- 1 to n_j toArray) yield p2.measure(i*n_j + j, pol2)

      val result =  measureLeft ~ measureRight
      if((measureLeft zip measureRight).nonEmpty) {
        n_not_nulls += 1
        (pol1, pol2) match {
          case (false, false) => sa_b_ += result; na_b_ += 1
          case (false, true) => sa_b += result; na_b += 1
          case (true, false) => sab_ += result; nab_ += 1
          case (true, true) => sab += result; nab += 1
        }
      } else n_nulls += 1
    }

    val ab = sab / nab
    val ab_ = sab_ / nab_
    val a_b = sa_b / na_b
    val a_b_ = sa_b_ / na_b_

    ab - ab_ + a_b + a_b_
  }


}
