package example;

import org.junit.Rule;
import org.junit.Test;
import org.neo4j.driver.v1.Config;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Session;
import org.neo4j.harness.junit.Neo4jRule;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;


public class LongestStringTest {

    @Rule
    public Neo4jRule neo4j = new Neo4jRule()
// This is the function we want to test
            .withAggregationFunction( LongestString.class );
    @Test
    public void shouldAllowIndexingAndFindingANode() throws Throwable
    {
// This is in a try-block, to make sure we close the driver after the test
        try( Driver driver = GraphDatabase.driver( neo4j.boltURI() , Config.build().withEncryptionLevel(
                Config.EncryptionLevel.NONE ).toConfig() ) )
        {
// Given
            Session session = driver.session();
// When
            String result = session.run( "UNWIND ['abc', 'abcd', 'ab'] AS string RETURN example.longestString(string) AS result").single().get("result").asString();
// Then
                    assertThat( result, equalTo( "abcd" ) );
        }
    }
}
