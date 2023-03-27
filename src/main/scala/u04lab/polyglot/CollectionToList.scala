package u04lab.polyglot

import java.util
import java.util.Collection
import u04lab.code.List
import u04lab.code.List.*

object CollectionToList:
  def apply[E](col: util.Collection[E]): List[E] =
    col.stream().map(cons(_,empty)).reduce(empty, (acc, l) => append(acc, l))
