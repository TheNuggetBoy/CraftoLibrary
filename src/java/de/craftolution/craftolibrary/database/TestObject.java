package de.craftolution.craftolibrary.database;

import static de.craftolution.craftolibrary.database.table.object.Clause.equal;
import static de.craftolution.craftolibrary.database.table.object.Clause.greatherThan;
import static de.craftolution.craftolibrary.database.table.object.Clause.limit;

import java.time.DayOfWeek;
import java.time.Instant;
import java.util.Map;
import java.util.Optional;

import com.google.common.collect.Maps;

import de.craftolution.craftolibrary.database.table.CharSet;
import de.craftolution.craftolibrary.database.table.DataType;
import de.craftolution.craftolibrary.database.table.Engine;
import de.craftolution.craftolibrary.database.table.IndexType;
import de.craftolution.craftolibrary.database.table.ObjectTable;
import de.craftolution.craftolibrary.database.table.object.TableColumn;
import de.craftolution.craftolibrary.database.table.object.TableIndex;
import de.craftolution.craftolibrary.database.table.object.TableMeta;

@TableMeta(name = "test_objects", comment = "stores stuff", charSet = CharSet.UTF8, engine = Engine.InnoDB)
public class TestObject {

	@TableColumn(autoIncrement = true, length = 8, comment = "The unique id")
	@TableIndex(type = IndexType.PRIMARY)
	int id;
	
	@TableColumn(name = "firstname", type = DataType.VARCHAR, length = 32)
	String name;
	
	@TableColumn(nullable = false, type = DataType.DOUBLE, length = 8, precision = 2, unsigned = false)
	double money;

	@TableColumn(type = DataType.ENUM, values = {"MONDAY", "TUESDAY", "..."}, standard = "MONDAY")
	DayOfWeek day;
	
	@TableColumn(onUpdate = "CURRENT_TIMESTAMP")
	Instant birth;

	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		Database myDatabase = null;
		ObjectTable<TestObject> table = ObjectTable.of(TestObject.class);
		
		TestObject obj = new TestObject();
		table.insert(myDatabase, obj);
		
		// Stream api
		Map<Double, TestObject> moneyMap = Maps.newHashMap();
		table.select(myDatabase)
		.filter(i -> i.money > 10)
		.limit(10)
		.sorted((a, b) -> a.money > b.money ? 1 : -1)
		.forEachOrdered(i -> moneyMap.put(i.money, i));
		
		// Clause api
		Optional<TestObject> obj2;
		obj2 = table.selectFirst(myDatabase, equal("money", 10).or(greatherThan("money", 100)).and(limit(1)) );
	}

}