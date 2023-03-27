package u04lab.polyglot.minesweeper
import java.util.Optional
import u04lab.polyglot.minesweeper.model.{Cell, Grid, GridImpl}
import u04lab.code.List
import u04lab.code.List.*
import u04lab.polyglot.{CollectionToList, OptionToOptional}
import u04lab.code.Option
import u04lab.code.Option.{None, Some}

object Grid:
  def apply(size: Int, nBombs: Int): Grid = GridImpl(size, nBombs)


class LogicsImpl2(private val size: Int, private val nBombs: Int) extends Logics:

  private val grid = Grid(size, nBombs)
  private var flaggedCells: List[Cell] = empty
  private var hitCells: List[Cell] = empty

  override def hasBomb(row: Int, column: Int): Boolean = grid.hasBomb(Cell(row, column))

  override def hit(row: Int, column: Int): Boolean =
    hitCells = append(hitCells, cons(Cell(row, column), empty))
    if hasBomb(row, column) then
      true
    else
      if grid.adjacentBombs(Cell(row, column)) == 0 then
        hitAdjacentCells(Cell(row, column))
      false

  private def hitAdjacentCells(cell: Cell): Unit =
    peek(filter(CollectionToList(grid.adjacentCells(cell)))(!contains(hitCells, _)))(c => hit(c.getRow, c.getColumn))

  override def toggleFlag(row: Int, column: Int): Unit = flaggedCells =
    if hasFlag(row, column) then
      remove(flaggedCells)(_ == Cell(row, column))
    else
      append(flaggedCells, cons(Cell(row, column), empty))

  override def hasFlag(row: Int, column: Int): Boolean = contains(flaggedCells, Cell(row, column))

  override def adjacentBombs(row: Int, column: Int): Optional[Integer] =
    OptionToOptional(
      if contains(hitCells, Cell(row, column)) then
        Some(grid.adjacentBombs(Cell(row, column)))
      else
        None())

  override def hasWon: Boolean = length(hitCells) == (grid.size() - grid.bombsCount())
