package u04lab.code

import org.junit.Assert.{assertEquals, assertFalse, assertTrue}
import org.junit.Test
import org.junit.Before
import u04lab.code.List.{cons, empty}
import u04lab.code.Option.*

class TestWarehouse:
  
  /** Hints:
   * - Implement retrieve using find
   * - Implement remove using filter
   * - Refactor the code of Item accepting a variable number of tags (hint: use _*)
   */
  private var warehouse: Warehouse = _
  private val dellXps = Item(33, "Dell XPS 15", "notebook")
  private val dellInspiron = Item(34, "Dell Inspiron 13", "notebook")
  private val xiaomiMoped = Item(35, "Xiaomi S1", "moped", "mobility")

  private def storeItems(): Unit =
    warehouse.store(dellXps)
    warehouse.store(dellInspiron)
    warehouse.store(xiaomiMoped)

  @Before def setUp(): Unit =
    warehouse = Warehouse()

  @Test def testStore(): Unit =
    assertFalse(warehouse.contains(dellXps.code))
    warehouse.store(dellXps) // side effect, add dell xps to the warehouse
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
