package u04lab.polyglot.minesweeper
import u04lab.code

import java.util.Optional
import u04lab.polyglot.OptionToOptional
import u04lab.code.Option
import u04lab.code.Option.{None, Some}
import u04lab.polyglot.minesweeper.model.{Cell, Grid}
import u04lab.code.List.*

import java.util
import scala.annotation.tailrec
import scala.util.Random
import SimpleSet.*

class LogicsImpl2(private val size: Int, private val nBombs: Int) extends Logics:

  private val grid = Grid(size, nBombs)
  private var flaggedCells = SimpleSet[Cell]()
  private var hitCells = SimpleSet[Cell]()

  override def hasBomb(row: Int, column: Int): Boolean = grid hasBombIn Cell(row, column)

  override def hit(row: Int, column: Int): Boolean =
    hitCells = hitCells add Cell(row, column)
    hasBomb(row, column) || { 
      if grid.bombsAdjacentTo(Cell(row, column)) == 0 then hitAdjacentCells(Cell(row, column)) 
      false 
    }

  private def hitAdjacentCells(cell: Cell): Unit =
    foreach(filter(grid cellsAdjacentTo cell)(c => !(hitCells contains c)))(c => hit(c.row, c.column))

  override def toggleFlag(row: Int, column: Int): Unit =
    if !hasFlag(row, column) then
      flaggedCells = flaggedCells add Cell(row, column)
    else
      flaggedCells = flaggedCells remove Cell(row, column)

  override def hasFlag(row: Int, column: Int): Boolean = flaggedCells contains Cell(row, column)

  override def adjacentBombs(row: Int, column: Int): Optional[Integer] =
    OptionToOptional(
      if hitCells contains Cell(row, column)
      then Some(grid bombsAdjacentTo Cell(row, column))
      else None())

  override def hasWon: Boolean = hitCells.size == (grid.size - grid.bombsCount)
