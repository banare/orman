package demo3;

import java.util.List;

import org.orman.datasource.Database;
import org.orman.mapper.MappingSession;
import org.orman.mapper.Model;
import org.orman.mapper.ModelQuery;
import org.orman.mapper.SchemeCreationPolicy;
import org.orman.mysql.MySQL;
import org.orman.mysql.MySQLSettingsImpl;
import org.orman.util.logging.ILogger;
import org.orman.util.logging.Log;
import org.orman.util.logging.Log4jAdapter;
import org.orman.util.logging.LoggingLevel;

public class UsualProgram {
	public static void main(String[] args) {
		//SQLite db = new SQLite("lite.db");
		Database db = new MySQL(new MySQLSettingsImpl("root", "root", "test"));

		ILogger log = new Log4jAdapter();
		Log.setLogger(log);
		Log.setLevel(LoggingLevel.TRACE);
		MappingSession.registerPackage("demo3");

		MappingSession.getConfiguration().setCreationPolicy(
				SchemeCreationPolicy.UPDATE);
		MappingSession.registerDatabase(db);
		MappingSession.start();

		Payment p = new Payment();
		p.amount = (float) (Math.random() * 1000);
		p.insert();

		Ticket t = new Ticket();
		t.seat = "abc" + Math.random();
		t.payment = p;
		t.insert();

		List<Ticket> tickets = Model
				.fetchQuery(ModelQuery.select().from(Ticket.class).getQuery(),
						Ticket.class);
		System.out.println(tickets);

		Object count = Model.fetchSingleValue(
				ModelQuery.select().count().from(Ticket.class).getQuery());
		System.out.println("Count = " + count);
	}
}