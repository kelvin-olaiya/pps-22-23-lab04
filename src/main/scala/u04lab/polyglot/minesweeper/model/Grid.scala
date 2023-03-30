package u04lab.polyglot.minesweeper.model

import u04lab.code.List
import u04lab.code.List.{add, append, cons, contains, empty, filter, length}

import scala.annotation.tailrec
import scala.util.Random

trait Grid:
  def cells: List[Cell]
  def bombsCount: Int
  def hasBombIn(cell: Cell): Boolean
  def cellsAdjacentTo(cell: Cell): List[Cell]
  def bombsAdjacentTo(cell: Cell): Int
  def size: Int

object Grid:
  def apply(side: Int, nBombs: Int): Grid = GridImpl(side, nBombs)

  private class GridImpl(side: Int, nBombs: Int) extends Grid:
    private val random = Random()
    private var cellsWithBombs = empty[Cell]

    @tailrec
    private def placeBombs(numberOfBombs: Int): Unit =
      if numberOfBombs > 0 then { cellsWithBombs = add(cellsWithBombs, emptyCell); placeBombs(numberOfBombs - 1) }
    placeBombs(nBombs)

    @tailrec
    private def emptyCell: Cell = Cell(random.nextInt(side), random.nextInt(side)) match
      case c if contains(cellsWithBombs, c) => emptyCell
      case c => c

    override val cells: List[Cell] = (0 until side)
      .flatMap(i => (0 until side).map(j => cons(Cell(i, j), empty)))
      .reduce((l, r) => append(l, r))
    
    override def bombsCount: Int = length(cellsWithBombs)

    override def hasBombIn(cell: Cell): Boolean = contains(cellsWithBombs, cell)
    
    override def cellsAdjacentTo(cell: Cell): List[Cell] = filter(cells)(_ isAdjacentTo cell)

    override def bombsAdjacentTo(cell: Cell): Int = length(filter(cellsAdjacentTo(cell))(hasBombIn))

    override val size: Int = side * side
    