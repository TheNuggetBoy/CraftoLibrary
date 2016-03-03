/*
 * Copyright (C) 2016 CraftolutionDE
 * All rights reserved
 *
 * Website: http://craftolution.de/
 * Contact: support@craftolution.de
 */
package de.craftolution.craftolibrary.database;

import java.io.IOException;
import java.sql.SQLException;

import de.craftolution.craftolibrary.database.Database.Builder.DatabaseType;
import de.craftolution.craftolibrary.database.query.Order;
import de.craftolution.craftolibrary.database.query.Query;
import de.craftolution.craftolibrary.database.table.CharSet;
import de.craftolution.craftolibrary.database.table.Engine;
import de.craftolution.craftolibrary.database.table.IndexType;
import de.craftolution.craftolibrary.database.table.Table;

/**
 * TODO: Documentation
 *
 * @author Fear837
 * @since 11.02.2016
 */
public class Tests {

	public static void main(final String[] args) throws IllegalStateException, ClassNotFoundException, SQLException, IOException {

		// Simple Insert Query
		final Query insertQuery1 = Query.insert("cp_users_test").columns("name", "age", "gender").values("Peter", "4", "MALE").onDuplicateKey("`id` = `id`");
		System.out.println(insertQuery1);

		// Simplified Insert Query
		final Query insertQuery2 = Query.insert("cp_users_test").insert("name", "Peter").insert("age", "4").insert("gender", "MALE");
		System.out.println(insertQuery2);

		// Select query
		final Query selectQuery = Query.select("name", "age").from("cp_users_test").where("`gender` = 'MALE'").orderBy("age", Order.ASCENDING).limit(4);
		System.out.println(selectQuery);

		// RemoveQuery
		final Query removeQuery = Query.remove("cp_users_test").where("age = 5", "name = 'Peter'").where("gender = 'FEMALE'");
		System.out.println(removeQuery);

		// UpdateQuery
		final Query updateQuery = Query.update("cp_users_test").columns("gender = 'MALE'").where("name = 'Peter'", "age = 40").where("name = 'Ralf'");
		System.out.println(updateQuery);

		// Raw query
		final Query rawQuery = Query.of("SELECT * FROM `cp_users_test`;");
		System.out.println(rawQuery);

		// Table creation
		final Table myTable = Table.builder("cp_users_test")
				.addPrimaryColumn(8)
				.addString("name", c -> c.comment("The users name").index(IndexType.UNIQUE))
				.addInt("age", c -> c.length(2).unsigned())
				.addEnum("gender", c -> c.values("MALE", "FEMALE").standard("MALE"))
				.addCreatedAt()
				.engine(Engine.InnoDB)
				.charSet(CharSet.UTF8)
				.index("myIndex", IndexType.INDEX, "age", "gender")
				.comment("This is a test table.")
				.build();

		// Database creation
		final Database db = Database.builder()
				.databaseName("craftodb")
				.hostname("localhost")
				.username("myuser")
				.password("12345")
				.port("4837")
				.type(DatabaseType.MYSQL)
				.build()
				.connect();

		// Some database methods
		db.createTable(myTable);

		// Queries
		final QueryResult result = db.execute(Query.select("*").from("cp_users_test").limit(4, 8));

		if (result.wasSuccess()) {
			System.out.println("Affected rows: " + result.getAffectedRows());

			for (final Row row : result.getRows()) {
				System.out.println("Reading next row:");

				for (final String column : result.getColumns()) {
					System.out.println(column + ": " + row.getString(column));
				}
			}
		}
		else if (result.getException().isPresent()) {
			final Exception exception = result.getException().get();
			exception.printStackTrace();
		}


		// A PreparedQuery
		//PreparedQuery<FXScene> selectSceneQuery = db.prepareQuery(Query.of("SELECT * FROM `cp_scene_meta` WHERE `scene_id` = ?"), scene -> Tokens.of(scene.getContainer().getId()));

		//QueryResult otherResult = selectSceneQuery.execute(new FXScene(null));

		// ...
	}

}