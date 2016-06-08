package fpinscala.datastructures

/**
  * Created by tateda on 2016/06/08.
  */
object MyApp extends App {
  val list = List(1)
  println(s"## Cons:${Cons(list, Nil)}")
  println(s"## List.x:${List.x}")
}
