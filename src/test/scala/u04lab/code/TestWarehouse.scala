package u04lab.code

import org.junit.Assert.{assertEquals, assertFalse, assertTrue, fail}
import org.junit.Test
import org.junit.Before
import u04lab.code.List.{cons, empty, foreach, take}
import u04lab.code.Option.*

object ItemsDef:
  val dellXps: Item = Item(33, "Dell XPS 15", "notebook")
  val dellInspiron: Item = Item(34, "Dell Inspiron 13", "notebook")
  val xiaomiMoped: Item = Item(35, "Xiaomi S1", "moped", "mobility")
  val items: List[Item] = List(dellXps, dellInspiron, xiaomiMoped)
  val itemsWithCommonTag: List[Item] = take(items, 2)
import ItemsDef.*

class TestWarehouse:
  
  private var warehouse: Warehouse = _

  private def storeItems(): Unit = foreach(items)(warehouse.store(_))

  @Before def setUp(): Unit =
    warehouse = Warehouse()

  @Test def testStore(): Unit =
    assertFalse(warehouse.contains(dellXps.code))
    warehouse.store(dellXps)
    assertTrue(warehouse contains dellXps.code)

  @Test def testSearch(): Unit =
    storeItems()
    assertEquals(cons(xiaomiMoped, empty),  warehouse.searchItems("mobility"))
    assertEquals(cons(dellXps, cons(dellInspiron, empty)), warehouse.searchItems("notebook"))

  @Test def testRetrieve(): Unit =
    storeItems()
    assertEquals(None(), warehouse.retrieve(11))
    assertEquals(Some(dellXps), warehouse.retrieve(dellXps.code))

  @Test def testRemove(): Unit =
    storeItems()
    warehouse.remove(dellXps)
    assertEquals(None(), warehouse.retrieve(dellXps.code))

class ItemTest:
  @Test def testSameTag(): Unit =
    itemsWithCommonTag match
      case sameTag(t) => assertEquals("notebook", t)
      case _ => fail()
    items match
      case sameTag(_) => fail()
      case _ =>
