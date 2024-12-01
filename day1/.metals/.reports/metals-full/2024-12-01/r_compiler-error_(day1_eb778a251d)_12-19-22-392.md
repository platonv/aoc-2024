file://<WORKSPACE>/main.sc
### java.util.NoSuchElementException: last of empty array

occurred in the presentation compiler.

presentation compiler configuration:


action parameters:
uri: file://<WORKSPACE>/main.sc
text:
```scala


final class main$_ {
def args = main_sc.args$
def scriptPath = """main.sc"""
/*<script>*/
#!/usr/bin/env -S scala-cli

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
        str.split("\n")fold

val filename = args(0)

println(readFile(filename))}
```



#### Error stacktrace:

```
scala.collection.ArrayOps$.last$extension(ArrayOps.scala:239)
	dotty.tools.pc.EndMarker$.getPosition(PcCollector.scala:320)
	dotty.tools.pc.PcCollector.collectEndMarker$1(PcCollector.scala:161)
	dotty.tools.pc.PcCollector.collectNamesWithParent$1(PcCollector.scala:172)
	dotty.tools.pc.PcCollector.$anonfun$9(PcCollector.scala:277)
	dotty.tools.pc.PcCollector$WithParentTraverser.traverse(PcCollector.scala:288)
	dotty.tools.pc.PcCollector$WithParentTraverser.traverse$$anonfun$1(PcCollector.scala:290)
	scala.collection.LinearSeqOps.foldLeft(LinearSeq.scala:183)
	scala.collection.LinearSeqOps.foldLeft$(LinearSeq.scala:179)
	scala.collection.immutable.List.foldLeft(List.scala:79)
	dotty.tools.pc.PcCollector$WithParentTraverser.traverse(PcCollector.scala:290)
	dotty.tools.pc.PcCollector$WithParentTraverser.traverse$$anonfun$1(PcCollector.scala:290)
	scala.collection.LinearSeqOps.foldLeft(LinearSeq.scala:183)
	scala.collection.LinearSeqOps.foldLeft$(LinearSeq.scala:179)
	scala.collection.immutable.List.foldLeft(List.scala:79)
	dotty.tools.pc.PcCollector$WithParentTraverser.traverse(PcCollector.scala:290)
	dotty.tools.pc.PcCollector$WithParentTraverser.traverse$$anonfun$1(PcCollector.scala:290)
	scala.collection.LinearSeqOps.foldLeft(LinearSeq.scala:183)
	scala.collection.LinearSeqOps.foldLeft$(LinearSeq.scala:179)
	scala.collection.immutable.List.foldLeft(List.scala:79)
	dotty.tools.pc.PcCollector$WithParentTraverser.traverse(PcCollector.scala:290)
	dotty.tools.pc.PcCollector$WithParentTraverser.traverse$$anonfun$1(PcCollector.scala:290)
	scala.collection.LinearSeqOps.foldLeft(LinearSeq.scala:183)
	scala.collection.LinearSeqOps.foldLeft$(LinearSeq.scala:179)
	scala.collection.immutable.List.foldLeft(List.scala:79)
	dotty.tools.pc.PcCollector$WithParentTraverser.traverse(PcCollector.scala:290)
	dotty.tools.pc.PcCollector$WithParentTraverser.traverse$$anonfun$1(PcCollector.scala:290)
	scala.collection.LinearSeqOps.foldLeft(LinearSeq.scala:183)
	scala.collection.LinearSeqOps.foldLeft$(LinearSeq.scala:179)
	scala.collection.immutable.List.foldLeft(List.scala:79)
	dotty.tools.pc.PcCollector$WithParentTraverser.traverse(PcCollector.scala:290)
	dotty.tools.pc.PcCollector$WithParentTraverser.traverse$$anonfun$1(PcCollector.scala:290)
	scala.collection.LinearSeqOps.foldLeft(LinearSeq.scala:183)
	scala.collection.LinearSeqOps.foldLeft$(LinearSeq.scala:179)
	scala.collection.immutable.List.foldLeft(List.scala:79)
	dotty.tools.pc.PcCollector$WithParentTraverser.traverse(PcCollector.scala:290)
	dotty.tools.pc.PcCollector$WithParentTraverser.traverse$$anonfun$1(PcCollector.scala:290)
	scala.collection.LinearSeqOps.foldLeft(LinearSeq.scala:183)
	scala.collection.LinearSeqOps.foldLeft$(LinearSeq.scala:179)
	scala.collection.immutable.List.foldLeft(List.scala:79)
	dotty.tools.pc.PcCollector$WithParentTraverser.traverse(PcCollector.scala:290)
	dotty.tools.pc.PcCollector$DeepFolderWithParent.apply(PcCollector.scala:296)
	dotty.tools.pc.PcCollector.traverseSought(PcCollector.scala:278)
	dotty.tools.pc.PcCollector.traverseSought$(PcCollector.scala:32)
	dotty.tools.pc.SimpleCollector.traverseSought(PcCollector.scala:348)
	dotty.tools.pc.PcCollector.resultAllOccurences(PcCollector.scala:44)
	dotty.tools.pc.PcCollector.resultAllOccurences$(PcCollector.scala:32)
	dotty.tools.pc.SimpleCollector.resultAllOccurences(PcCollector.scala:348)
	dotty.tools.pc.SimpleCollector.result(PcCollector.scala:353)
	dotty.tools.pc.PcSemanticTokensProvider.provide(PcSemanticTokensProvider.scala:88)
	dotty.tools.pc.ScalaPresentationCompiler.semanticTokens$$anonfun$1(ScalaPresentationCompiler.scala:116)
```
#### Short summary: 

java.util.NoSuchElementException: last of empty array