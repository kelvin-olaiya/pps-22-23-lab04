package u04lab.code
import List.*
import u04lab.code.Option.{None, Some, isEmpty, toScalaOption}

trait Item {
  def code: Int
  def name: String
  def tags: List[String]
}

object Item:
  def apply(code: Int, name: String, tags: String*): Item =
    ItemImpl(code, name, tags.foldRight(empty[String])(cons))

  private case class ItemImpl(code: Int, name: String, tags: List[String]) extends Item

object sameTag:
  def unapply(items: List[Item]): scala.Option[String] = items match
    case Cons(h, _) => toScalaOption(first(foldLeft(items)(h.tags)((s, e) => intersect(s, e.tags))))
    case _ => scala.None

/**
 * A warehouse is a place where items are stored.
 */
trait Warehouse {
  /**
   * Stores an item in the warehouse.
   * @param item the item to store
   */
  def store(item: Item): Unit
  /**
   * Searches for items with the given tag.
   * @param tag the tag to search for
   * @return the list of items with the given tag
   */
  def searchItems(tag: String): List[Item]
  /**
   * Retrieves an item from the warehouse.
   * @param code the code of the item to retrieve
   * @return the item with the given code, if present
   */
  def retrieve(code: Int): Option[Item]
  /**
   * Removes an item from the warehouse.
   * @param item the item to remove
   */
  def remove(item: Item): Unit
  /**
   * Checks if the warehouse contains an item with the given code.
   * @param itemCode the code of the item to check
   * @return true if the warehouse contains an item with the given code, false otherwise
   */
  def contains(itemCode: Int): Boolean
}

object Warehouse:
  def apply(): Warehouse = WarehouseImpl()

  private class WarehouseImpl extends Warehouse:
    private var items: List[Item] = empty
    override def store(item: Item): Unit = items = cons(item, items)
    override def searchItems(tag: String): List[Item] = filter(items)(i => List.contains(i.tags, tag))
    override def retrieve(code: Int): Option[Item] = find(items)(_.code == code)
    override def remove(item: Item): Unit = items = List.remove(items)(_ == item)
    override def contains(itemCode: Int): Boolean = !isEmpty(find(items)(_.code == itemCode))
