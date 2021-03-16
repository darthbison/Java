package example;

import org.neo4j.procedure.Description;
import org.neo4j.procedure.Name;
import org.neo4j.procedure.UserAggregationFunction;
import org.neo4j.procedure.UserAggregationResult;
import org.neo4j.procedure.UserAggregationUpdate;

public class LongestString {

    @UserAggregationFunction
    @Description( "org.neo4j.function.example.longestString(string) - aggregates the longest string found")
    public LongStringAggregator longestString()
    {
        return new LongStringAggregator();
    }
    public static class LongStringAggregator
    {
        private int longest;
        private String longestString;
        @UserAggregationUpdate
        public void findLongest(
                @Name( "string" ) String string )
        {
            if ( string != null && string.length() > longest)
            {
                longest = string.length();
                longestString = string;
            }
        }
        @UserAggregationResult
        public String result()
        {
            return longestString;
        }
    }

}
