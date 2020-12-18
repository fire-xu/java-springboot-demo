package net.aas.unomi.search.goals;

import feign.Param;
import feign.RequestLine;
import org.apache.unomi.api.Metadata;
import org.apache.unomi.api.goals.Goal;
import org.apache.unomi.api.query.Query;

import java.util.Set;

public interface GoalFeign {
    @RequestLine("GET /goals/")
    Set<Metadata> getGoalMetadatas();

    @RequestLine("POST /goals/query")
    Set<Metadata> getGoalMetadatas(Query query);

    @RequestLine("GET /goals/{goalId}")
    Goal getGoal(@Param("goalId") String goalId);
}
