package u04lab.polyglot.minesweeper

import u04lab.code.List
import u04lab.code.List.empty

trait SimpleSet[T]:
  def add(elem: T): Unit
  def remove(elem: T): Unit
  def contains(elem: T): Boolean
  def size: Int

object SimpleSet:
  def apply[T]: SimpleSet[T] = SimpleSetImpl[T]
  private class SimpleSetImpl[T] extends SimpleSet[T]:
    private var elements = empty[T]
    override def add(elem: T): Unit = if !List.contains(elements, elem) then elements = List.add(elements, elem)
    override def remove(elem: T): Unit = elements = List.remove(elements)(_ == elem)
    override def contains(elem: T): Boolean = List.contains(elements, elem)
    override def size: Int = List.length(elements)
