package u04lab.polyglot.minesweeper.model

case class Cell(row: Int, column: Int):
  private object ComponentDistance: // overkill?
    def unapply(cells: (Cell, Cell)): scala.Option[(Int, Int)] = cells match
      case (a, b) => scala.Some((Math.abs(a.row - b.row), Math.abs(a.column - b.column)))

  def isAdjacentTo(cell: Cell): Boolean = (this, cell) match
    case ComponentDistance(x, y) => !(x == 0 && y == 0) && x <= 1 && y <= 1;
