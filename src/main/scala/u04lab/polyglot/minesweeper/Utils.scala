package u04lab.polyglot.minesweeper

object Utils:
  def execIf(cond: Boolean)(exec: () => Unit): Boolean = { if cond then exec(); cond }
