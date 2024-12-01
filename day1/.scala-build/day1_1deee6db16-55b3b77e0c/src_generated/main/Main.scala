

final class Main$_ {
def args = Main_sc.args$
def scriptPath = """./Main.sc"""
/*<script>*/


def readFile(filename: String): String = {
    scala.io.Source.fromFile(filename).mkString
}

final case class Input(column1: Seq[Int], column2: Seq[Int]) {
    def sorted: Input = {
        val sortedColumn1 = column1.sorted
        val sortedColumn2 = column2.sorted
        Input(sortedColumn1, sortedColumn2)
    }
}


val filename = args(0)

println(readFile(filename))
/*</script>*/ /*<generated>*//*</generated>*/
}

object Main_sc {
  private var args$opt0 = Option.empty[Array[String]]
  def args$set(args: Array[String]): Unit = {
    args$opt0 = Some(args)
  }
  def args$opt: Option[Array[String]] = args$opt0
  def args$: Array[String] = args$opt.getOrElse {
    sys.error("No arguments passed to this script")
  }

  lazy val script = new Main$_

  def main(args: Array[String]): Unit = {
    args$set(args)
    val _ = script.hashCode() // hashCode to clear scalac warning about pure expression in statement position
  }
}

export Main_sc.script as `Main`

