package u04lab.polyglot.minesweeper

import u04lab.code.List
import u04lab.code.List.{cons, empty}

trait SimpleSet[T]:
  def add(elem: T): SimpleSet[T]
  def remove(elem: T): SimpleSet[T]
  def contains(elem: T): Boolean
  def size: Int

object SimpleSet:
  def apply[T](list: List[T] = empty[T]): SimpleSet[T] = SimpleSetImpl[T](list)
  private class SimpleSetImpl[T](private val set: List[T]) extends SimpleSet[T]:
    override def add(elem: T): SimpleSet[T] = if !List.contains(set, elem) then SimpleSet(cons(elem, set)) else SimpleSet(set)
    override def remove(elem: T): SimpleSet[T] = SimpleSet(List.remove(set)(_ == elem))
    override def contains(elem: T): Boolean = List.contains(set, elem)
    override def size: Int = List.length(set)
