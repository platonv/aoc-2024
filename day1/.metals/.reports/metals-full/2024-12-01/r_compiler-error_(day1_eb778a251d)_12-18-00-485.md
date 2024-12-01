file://<WORKSPACE>/.scala-build/day1_eb778a251d/src_generated/main/main.sc.scala
### dotty.tools.dotc.core.TypeError$$anon$1: Toplevel definition filename is defined in
  <WORKSPACE>/.scala-build/day1_eb778a251d/src_generated/main/main.sc.scala
and also in
  <WORKSPACE>/main.sc
One of these files should be removed from the classpath.

occurred in the presentation compiler.

presentation compiler configuration:


action parameters:
offset: 486
uri: file://<WORKSPACE>/.scala-build/day1_eb778a251d/src_generated/main/main.sc.scala
text:
```scala


final class main$_ {
def args = main_sc.args$
def scriptPath = """main.sc"""
/*<script>*/
#!/usr/bin/env -S scala-cli

def readFile(filename: String): String = 
    scala.io.Source.fromFile(filename).mkString

final case class Input(column1: Seq[Int], column2: Seq[Int]):
    def sorted: Input =
        val sortedColumn1 = column1.sorted
        val sortedColumn2 = column2.sorted
        Input(sortedColumn1, sortedColumn2)

object Input:
    def fromString(str: String): 
        s@@

val filename = args(0)

println(readFile(filename))}
```



#### Error stacktrace:

```

```
#### Short summary: 

dotty.tools.dotc.core.TypeError$$anon$1: Toplevel definition filename is defined in
  <WORKSPACE>/.scala-build/day1_eb778a251d/src_generated/main/main.sc.scala
and also in
  <WORKSPACE>/main.sc
One of these files should be removed from the classpath.