

final class main$_ {
def args = main_sc.args$
def scriptPath = """main.sc"""
/*<script>*/


type Error = String

def readFile(filename: String): String = 
    scala.io.Source.fromFile(filename).mkString

final case class Input(column1: Seq[Int], column2: Seq[Int]):
    def sorted: Input =
        val sortedColumn1 = column1.sorted
        val sortedColumn2 = column2.sorted
        Input(sortedColumn1, sortedColumn2)

object Input:
    def fromString(str: String): Either[Error, Input] =
        str.split("\n").foldLeft(Right(Input(Seq.empty, Seq.empty)): Either[Error, Input]) { (acc, line) =>
            acc.flatMap { input =>
                line.split(" +").toList match
                    case column1 :: column2 :: Nil =>
                        for
                            c1 <- column1.toIntOption.toRight("Invalid column1")
                            c2 <- column2.toIntOption.toRight("Invalid column2")
                        yield
                            input.copy(column1 = input.column1 :+ c1, column2 = input.column2 :+ c2)
                    case _ => Left("Invalid input")
            }
        }

def appearences(column: Seq[Int]): Map[Int, Int] =
    column.groupMapReduce(identity)(_ => 1)(_ + _)

def solve1(input: Input): Int =
    val sortedInput = input.sorted

    sortedInput.column1.zip(sortedInput.column2).map((c1, c2) => Math.abs(c1 - c2)).sum

def solve2(input: Input): Int =
    val appearencesMap = appearences(input.column2)

    input.column1.map(x => x * appearencesMap.getOrElse(x, 0)).sum


val filename = args(0)

println(readFile(filename))

val res = for
    input <- Input.fromString(readFile(filename))
yield
    println("Part 1: " + solve1(input))
    println("Part 2: " + solve2(input))

/*</script>*/ /*<generated>*//*</generated>*/
}

object main_sc {
  private var args$opt0 = Option.empty[Array[String]]
  def args$set(args: Array[String]): Unit = {
    args$opt0 = Some(args)
  }
  def args$opt: Option[Array[String]] = args$opt0
  def args$: Array[String] = args$opt.getOrElse {
    sys.error("No arguments passed to this script")
  }

  lazy val script = new main$_

  def main(args: Array[String]): Unit = {
    args$set(args)
    val _ = script.hashCode() // hashCode to clear scalac warning about pure expression in statement position
  }
}

export main_sc.script as `main`

