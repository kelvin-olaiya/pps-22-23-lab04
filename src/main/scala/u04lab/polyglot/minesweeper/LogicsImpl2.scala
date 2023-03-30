package u04lab.polyglot.minesweeper
import u04lab.code

import java.util.Optional
import u04lab.code.List
import u04lab.code.List.{append, contains, *}
import u04lab.polyglot.{CollectionToList, OptionToOptional}
import u04lab.code.Option
import u04lab.code.Option.{None, Some}
import u04lab.polyglot.minesweeper.model.{Cell, Grid}

import java.util
import scala.annotation.tailrec
import scala.util.Random

class LogicsImpl2(private val size: Int, private val nBombs: Int) extends Logics:

  private val grid = Grid(size, nBombs)
  private var flaggedCells: List[Cell] = empty
  private var hitCells: List[Cell] = empty

  override def hasBomb(row: Int, column: Int): Boolean = grid hasBombIn Cell(row, column)
  
  override def hit(row: Int, column: Int): Boolean =
    hitCells = add(hitCells, Cell(row, column))
    def execIf(cond: Boolean)(exec: () => Unit): Boolean = { if cond then exec(); cond }
    hasBomb(row, column) || execIf(grid.bombsAdjacentTo(Cell(row, column)) == 0)(
      () => hitAdjacentCells(Cell(row, column)))
    
  private def hitAdjacentCells(cell: Cell): Unit =
    foreach(filter(grid cellsAdjacentTo cell)(!contains(hitCells, _)))(c => hit(c.row, c.column))

  override def toggleFlag(row: Int, column: Int): Unit = flaggedCells =
    if !hasFlag(row, column) then add(flaggedCells, Cell(row, column)) else remove(flaggedCells)(_ == Cell(row, column))

  override def hasFlag(row: Int, column: Int): Boolean = contains(flaggedCells, Cell(row, column))

  override def adjacentBombs(row: Int, column: Int): Optional[Integer] =
    OptionToOptional(
      if contains(hitCells, Cell(row, column))
      then Some(grid bombsAdjacentTo Cell(row, column))
      else None())

  override def hasWon: Boolean = length(hitCells) == (grid.size - grid.bombsCount)
