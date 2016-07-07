[![Build status](https://travis-ci.org/fpinscala/fpinscala.svg?branch=master)](https://travis-ci.org/fpinscala/fpinscala) [![Join the chat at https://gitter.im/fpinscala/fpinscala](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/fpinscala/fpinscala?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge) 

This repository contains exercises, hints, and answers for the book
[Functional Programming in Scala](http://manning.com/bjarnason/). Along
with the book itself, it's the closest you'll get to having your own
private functional programming tutor without actually having one.

Here's how to use this repository:

Each chapter in the book develops a fully working library of functions
and data types, built up through a series of exercises and example code
given in the book text. The shell of this working library and exercise
stubs live in
`exercises/src/main/scala/fpinscala/<chapter-description>`, where
`<chapter-description>` is a package name that corresponds to the
chapter title (see below). When you begin working on a chapter, we
recommend you open the exercise file(s) for that chapter, and when you
encounter exercises, implement them in the exercises file and make sure
they work.

If you get stuck on an exercise, let's say exercise 4 in the chapter,
you can find hints in `answerkey/<chapter-description>/04.hint.txt` (if
no hints are available for a problem, the file will just have a single
'-' as its contents) and the answer along with an explanation of the
answer and any variations in
`answerkey/<chapter-description>/04.answer.scala` or
`04.answer.markdown`. The finished Scala modules, with all answers for
each chapter live in
`answers/src/main/scala/fpinscala/<chapter-description>`. Please feel
free to submit pull req

# 第2章 Scala 関数型プログラミングの準備

## 概要・用語
```
イマージョン(没入方)でいきなりコード読む
末尾再帰最適化でループ
高階関数:他の関数を引数として受け取り出力として関数を返す
多層高階関数:高階関数をさらに型に基づいて実装を導く
objectキーワードは新しいシングルトン型を作成
メソッドの等号の前にある宣言部分をシグネチャと呼び、等号の後ろにあるコードを右辺または定義と呼ぶ

```
'def abs(n: Int): Int = ???'
副作用があるメソッドを、関数ではなくプロシージャ(procedure)または非純粋関数(impure function)と呼ぶこともある
Scalaインタープリタの対話モードREPL(Read-Evaluate-Print Loop)



## EXERCISE 2.1
n 番目のフィボナッチ数を取得する再帰関数を記述せよ。最初の２つのフィボナッチ数は０と１である。n番目の数字は常に前の2つの数字の合計となる。
この数列は0,1,1,2,3,5のように始まる。




## EXERCISE 2.2
指定された比較関数にしたがってArray[A] がソートされているかを調べる isSorted を実装せよ
```
def isSorted[A](as: Array[A], orderd: (A,A) => Boolean): Boolean
```



## EXERCISE 2.3 
カリー化(curring)では、引数2つの関数fが、fを部分的に適用する引数1つの関数に変換される。この場合も、コンパイルできる実装は1つだけである。
この実装を記述せよ。
def curry[A,B,C](f:(A,B) => C):A => (B,C)


## EXERCISE 2.4 カリー化を逆向きに行う uncurry を実装せよ

## EXERCISE 2.5 2 つの関数を合成する高階関数を実装せよ

# 第3章 関数型プログラミングのデータ構造
```
関数型データ構造は本質的にイミュータブル
空のリストをList()またはNil 
aとbの2つのリストを連結する構文は a ++ b だが、データを余分にコピーするわけではない <= これが言いたいがためのEXERCISEか。
たとえば、xsというリストがある場合、要素1をリストの戦闘に追加したら、新しいリスト'Cons(1, xs)'を返す。
Consはコンストラクタ(データ構築子、Constructor)の略。
この場合、リストはイミュータブルなので xsを実際にコピーする必要はなく、それを再利用するだけで済む。
これをデータ共有(data sharing)と呼ぶ。
sealed traitは同一ファイル内でそのトレイトの実装を宣言しなければならない。
型パラメータ[+A]の+記号は、型パラメータAが共変(covariant)であることを意味する。
+は変異アノテーション(variance annotation)と呼ばれる
DogがAnimalの部分型であるとすれば、List[Dog]はList[Animal]の部分型であると見なされる
全ての型XおよびYに対して、XがYの部分型であるとすれば、List[X]はList[Y]の部分型です。
この+を省略することも可能であり、その場合、Listはその型パラメータに対して不変(invariant)になる。
=> 下限境界、上限境界との関連は？
NilはList[Nothing]を拡張したものであり、Nothingは全ての型の部分型であり、
変位アノテーションとの組み合わせにより、NilをList[Int]やList[Double]と見なせることになる。
コンパニオンオブジェクト
ターゲット、被検査体
```

https://github.com/fpinscala/fpinscala/blob/master/exercises/src/main/scala/fpinscala/datastructures/List.scala
`この章で使用しているListとScalaの標準ライブラリとの主な違いは、Consが::として参照され、右結合になることです。`


## EXERCISE 3.1

以下のマッチ式はどのような結果になるか。
```
  val x = List(1,2,3,4,5) match {
    case Cons(x, Cons(2, Cons(4, _))) => x
    case Nil => 42
    case Cons(x, Cons(y, Cons(3, Cons(4, _)))) => x + y
    case Cons(h, t) => h + sum(t)
    case _ => 101
  }
```
※これは本文中のConsを読んでないとできるわけない
```
case object Nil extends List[Nothing] 
case class Cons[+A](head: A, tail: List[A]) extends List[A]
List("a", "b")
同じ意味
Cons("a", Cons("b", Nill))
```

http://kmizu.hatenablog.com/entry/20091130/1259557761 から引用
```
scalaのListはcovariantであり、二つの型List[A]とList[B]について、
AがBのサブタイプならばList[A]もList[B]のサブタイプであるという性質を持つ。
ここで、Nothingは全ての型のサブタイプであるため、List[Nothing]とList[B]という二つの型について、
常にList[Nothing]はList[B]のサブタイプという性質が成り立つ。
つまり、List[Nothing]はどんな要素型のListにも化けられるListということになる。
List[Nothing]はどんな型のListにも化けられるため、
List()やNilの型として、List[Nothing]を与えることで、空リストに対して適切な型を与えることができる。
```
## EXERCISE 3.2
Listの最初の要素を削除する関数tailを実装せよ。この関数の実行時間が一定であることに注意。
ListがNilである場合、実装上の選択肢として他に何があるか。この質問については、次章で再び取り上げる。
```
  def tail[A](l: List[A]): List[A] = sys.error("todo")
```
@see exercises/src/main/scala/fpinscala/datastructires/List.scala

## EXERCISE 3.3
EXERCISE 3.2と同じ考え方に基づいて、Listの最初の要素を別の値と置き換えるsetHead関数を実装せよ。
```
  def setHead[A](l: List[A], h: A): List[A] = sys.error("todo")
```

## EXERCISE 3.4
tailを一般化して、リストの先頭からn個の要素を削除するdropという関数に置き換えよ。
この関数の実行時間は削除する要素の数にのみ比例することに注意。List全体のコピーを作成する必要はない。

## EXERCISE 3.5
述語とマッチする場合に限り、Listからその要素までの要素を削除するdropWhileを実装せよ。

## EXERCISE 3.6
すべてがこのようにうまくいくわけではない。Listの末尾を除く全ての要素で構成されたListを返すinit関数を実装せよ。
List(1,2,3,4)が与えられた場合、initはList(1,2,3)を返す。この関数をtailのように一定時間で実装できないのはなぜか。
```
def init[A](l: List[A]): List[A]
```

## EXERCISE 3.7
foldRightを使って実装されたproductは、0.0を検出した場合に、直ちに再帰を中止して0.0を返せるか。その理由を説明せよ。
大きなリストでfoldRightを呼び出した場合の短絡の仕組みについて検討せよ。この問題は奥が深いため、第5章で改めて取り上げる。

## EXERCISE 3.8
foldRight(List(1,2,3),Nil:List[Int])(Cons(_,_))のように、NilおよびCons自体をfoldRightに渡した場合はどうなるか。
これがfoldRightとListのデータコンストラクタとの関係について何を表していると思うか。

## EXERCISE 3.9
foldRightを使ってリストの長さを計算せよ。

## EXERCISE 3.10
このfoldRightの実装は末尾再帰ではなく、リストが大きい場合はStackOverFlowErrorになってしまう。これをスタックセーフではないと言う。
そうした状況であると仮定し、前章で説明した手法を使って、リスト再帰の総称関数foldLeftを記述せよ。シグネチャは以下のとおり
```
def foldLeft[A,B](l: List[A], z: B)(f: (B, A) => B): B
```

## EXERCISE 3.11
```
foldLeftを使ってsum,product,およびリストの長さを計算する関数を記述せよ。
```

## EXERCISE 3.12
要素が逆に並んだリストを返す関数を記述せよ。List(1,2,3)が与えられた場合、この関数はList(3,2,1)を返す。
畳み込みを使って記述できるかどうかを確認すること。


## EXERCISE 3.13
難問: foldRightをベースとしてfoldLeftを記述することは可能か。その逆はどうか。
foldLeftを使ってfoldRightを実装すると、foldRightを末尾再帰的に実装することが可能となり、
大きなリストでもスタックオーバーフローが発生しなくなるので便利である。


## EXERCISE 3.14
foldLeftまたはfoldRightをベースとしてappendを実装せよ。

## EXERCISE 3.15
難問: 複数のリストからなるリストを1つのリストとして連結する関数を記述せよ。
この関数の実行時間はすべてのリストの長さの合計に対して線形になるはずである。
すでに定義した関数を使ってみること。

## EXERCISE 3.
各要素に1を足すことで整数のリストを変換する関数を記述せよ。
注意: これは新しいListを返す純粋関数になるはずである。

## EXERCISE 3.17
List[Double]の各値をStringに変換する関数を記述せよ。
d.toStringという式を使ってd: DoubleをStringに変換できる


## EXERCISE 3.18
リストの各要素を変更し、かつリストの構造をそのまま保つ総称関数mapを記述せよ。
この関数のシグネチャは以下のとおり
def map[A,B](as: List[A])(f: A => B): List[B]

## EXERCISE 3.19
与えられた述語条件が満たされるまでリストから要素を削除するfilter関数を記述せよ。この関数を使ってList[Int]から奇数をすべて削除せよ。
def filter[A](as: List[A])(f: A => Boolean): List[A]

## EXERCISE 3.20
mapと同じような動きをするflatMap関数を記述せよ。
この関数は単一の結果ではなくリストを返し、そのリストは最終的な結果のリストに挿入されなければならない。
この関数のシグネチャは以下のとおり。
def flatMap[A,B](as: List[A])(f: A => List[B]): List[B]


## EXERCISE 3.21
flatMapを使ってfilterを実装せよ


## EXERCISE 3.22
リストを2つ受け取り、対応する要素動詞を足しあわせて新しいリストを生成する関数を記述せよ。
たとえば、List(1,2,3)とList(4,5,6)はList(5,7,9)になる。


## EXERCISE 3.23
EXERCISE 3.22で作成した関数を、整数又は加算に限定されないように一般化せよ。
一般化された関数にはzipWithという名前をつけること。


## EXERCISE 3.24
難問: 例として、Listに別のListがサブシーケンスとして含まれているかどうかを調べるhasSubsequenceを実装せよ。
たとえばList(1,2,3,4)には、List(1,2),List(2,3),List(4)などがサブシーケンスとして含まれている。
純粋関数型で、コンパクトで、かつ効率的な実装を見つけ出すのは難しいかもしれない。その場合は、それでかまわない。
どのようなものであれ、最も自然な関数を実装すること。この実装については、第5章で改めて取り上げ、改良する予定である。
なおScalaでは、任意の値xおよびyに対し、x == yという式を使って等しいかどうかを比較できる。
def hasSubsequence[A](sup: List[A], sub: List[A]): Boolean

## EXERCISE 3.25
2分木のノード(LeafとBranch)の数を数えるsize関数を記述せよ

## EXERCISE 3.26
Tree[Int]の最大の要素を返すmaximum関数を記述せよ。
なおScalaでは、x.max(y)またはx max yを使って2つの整数xとyの最大値を計算できる

## EXERCISE 3.27
2分木のルートから任意のLeafまで最長のパスを返すdepth関数を記述せよ。

## EXERCISE 3.28
2分木の各要素を特定の関数を使って変更するmap関数を記述せよ。
この関数はListの同じ名前のメソッドに類似している。


## EXERCISE 3.29 fold を定義し size 等をそれを用いて実装せよ
size,maximum,depth,mapを一般化し、それらの類似点を抽象化する新しいfold関数を記述せよ。
そして、このより汎用的なfold関数を使ってそれらを再実装せよ。
このfold関数とListの左畳み込み及び右畳み込みの間にある類似性を抽出することは可能か。













uests for alternate answers, improved hints, and
so on, so we can make this repo the very best resource for people
working through the book.

Chapter descriptions:

* Chapter 2: gettingstarted
* Chapter 3: datastructures
* Chapter 4: errorhandling
* Chapter 5: laziness
* Chapter 6: state
* Chapter 7: parallelism
* Chapter 8: testing
* Chapter 9: parsing
* Chapter 10: monoids
* Chapter 11: monads
* Chapter 12: applicative
* Chapter 13: iomonad
* Chapter 14: localeffects
* Chapter 15: streamingio

To build the code for the first time, if on windows:

    $ .\sbt.cmd

If on mac/linux, first make sure you have not checked out the code onto
an encrypted file system, otherwise you will get compile errors
regarding too long file names (one solution is to put the fpinscala repo
on a unencrypted usb key, and symlink it into your preferred code
location).

    $ chmod a+x ./sbt
    $ ./sbt

This will download and launch [sbt](http://scala-sbt.org), a build tool
for Scala. Once it is finished downloading, you'll get a prompt from
which you can issue commands to build and interact with your code. Try
the following:

    > project exercises
    > compile

This switches to the exercises project, where your code lives, and
compiles the code. You can also do:

    > console

to get a Scala REPL with access to your exercises, and

    > run

To get a menu of possible main methods to execute.

To create project files for the eclipse IDE you can install the
[sbteclipse](https://github.com/typesafehub/sbteclipse)
[sbt](http://scala-sbt.org) plugin. This makes a new command available
in [sbt](http://scala-sbt.org):

    > eclipse

All code in this repository is
[MIT-licensed](http://opensource.org/licenses/mit-license.php). See the
LICENSE file for details.

Have fun, and good luck! Also be sure to check out [the community
wiki](https://github.com/fpinscala/fpinscala/wiki) for the **chapter
notes**, links to more reading, and more.

_Paul and Rúnar_

