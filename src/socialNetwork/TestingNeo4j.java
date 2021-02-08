package socialNetwork;

import org.neo4j.driver.v1.*;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import static org.neo4j.driver.v1.Values.parameters;

public class TestingNeo4j implements AutoCloseable
{
    private final Driver driver;

    public TestingNeo4j( String uri, String user, String password )
    {
        driver = GraphDatabase.driver( uri, AuthTokens.basic( user, password ) );
    }

    @Override
    public void close() throws Exception
    {
        driver.close();
    }

    public List<Record> printGreeting( final String id )
    {
        try ( Session session = driver.session() )
        {
            List<Record> greeting = session.writeTransaction( new TransactionWork<List<Record>>()
            {
                @Override
                public List<Record> execute(Transaction tx )
                {
                    StatementResult result = tx.run( "MATCH (n)-->(a) WHERE n.id = $id RETURN n,a LIMIT 3",
                            parameters( "id", id ) );
                    while (result.hasNext()) {
                        return result.list();
                    }
                    return result.list();
                }
            } );
            return greeting;
        }
    }

    public List<Record> printOurFriend( final int id, final int me )
    {
        try ( Session session = driver.session() )
        {
            List<Record> greeting = session.writeTransaction( new TransactionWork<List<Record>>()
            {
                @Override
                public List<Record> execute(Transaction tx )
                {
                    StatementResult result = tx.run( "MATCH (n)-->(a)<--(b) WHERE n.id = $me AND b.id = $id RETURN n,a,b",
                            parameters( "id", "me",  id, me ) );
                    while (result.hasNext()) {
                        return result.list();
                    }
                    return result.list();
                }
            } );
            return greeting;
        }
    }

//    public static void main( String... args ) throws Exception
//    {
//        try ( TestingNeo4j greeter = new TestingNeo4j( "bolt://localhost:7687", "neo4j", "1234" ) )
//        {
//            greeter.printGreeting( "2" );
//        }
//    }
}