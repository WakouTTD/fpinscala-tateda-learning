package fpinscala.datastructures

/**
  * Created by tateda on 2016/06/08.
  */
object MyApp extends App {
  println(s"## Cons${Cons(List(1, 2), List(1, 2))}")
  println(s"## List.x:${List.x}")

  println(s"## EXERCISE 3.2 tail2:${List.tail2(List(4,5,6,7))}")

  println(s"## EXERCISE 3.3 setHead2:${List.setHead2(List(4,5,6,7),List(3))}")
}
