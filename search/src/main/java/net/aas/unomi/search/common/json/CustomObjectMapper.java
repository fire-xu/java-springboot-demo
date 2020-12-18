package net.aas.unomi.search.common.json;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;
import org.apache.unomi.api.CustomItem;
import org.apache.unomi.api.Event;
import org.apache.unomi.api.Item;
import org.apache.unomi.api.Persona;
import org.apache.unomi.api.Session;
import org.apache.unomi.api.actions.ActionType;
import org.apache.unomi.api.campaigns.Campaign;
import org.apache.unomi.api.campaigns.events.CampaignEvent;
import org.apache.unomi.api.conditions.Condition;
import org.apache.unomi.api.conditions.ConditionType;
import org.apache.unomi.api.goals.Goal;
import org.apache.unomi.api.rules.Rule;
import org.apache.unomi.api.segments.Scoring;
import org.apache.unomi.api.segments.Segment;

import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

public class CustomObjectMapper extends ObjectMapper {

    private static final long serialVersionUID = 4578277612897061535L;

    public CustomObjectMapper() {
        super();
        super.registerModule(new JaxbAnnotationModule());
        configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        ISO8601DateFormat dateFormat = new ISO8601DateFormat();
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        setDateFormat(dateFormat);
        SimpleModule deserializerModule =
                new SimpleModule("PropertyTypedObjectDeserializerModule",
                        new Version(1, 0, 0, null, "org.apache.unomi.rest", "deserializer"));

        PropertyTypedObjectDeserializer propertyTypedObjectDeserializer = new PropertyTypedObjectDeserializer();
        propertyTypedObjectDeserializer.registerMapping("type=.*Condition", Condition.class);
        deserializerModule.addDeserializer(Object.class, propertyTypedObjectDeserializer);

        ItemDeserializer itemDeserializer = new ItemDeserializer();
        deserializerModule.addDeserializer(Item.class, itemDeserializer);


        Map<String, Class<? extends Item>> classes = new HashMap<>();
        classes.put(Campaign.ITEM_TYPE, Campaign.class);
        classes.put(CampaignEvent.ITEM_TYPE, CampaignEvent.class);
        classes.put(Event.ITEM_TYPE, Event.class);
        classes.put(Goal.ITEM_TYPE, Goal.class);
        classes.put(Persona.ITEM_TYPE, Persona.class);
        classes.put(Rule.ITEM_TYPE, Rule.class);
        classes.put(Scoring.ITEM_TYPE, Scoring.class);
        classes.put(Segment.ITEM_TYPE, Segment.class);
        classes.put(Session.ITEM_TYPE, Session.class);
        classes.put(ConditionType.ITEM_TYPE, ConditionType.class);
        classes.put(ActionType.ITEM_TYPE, ActionType.class);
        for (Map.Entry<String, Class<? extends Item>> entry : classes.entrySet()) {
            propertyTypedObjectDeserializer.registerMapping("itemType=" + entry.getKey(), entry.getValue());
            itemDeserializer.registerMapping(entry.getKey(), entry.getValue());
        }
        propertyTypedObjectDeserializer.registerMapping("itemType=.*", CustomItem.class);


        super.registerModule(deserializerModule);
    }

    public static ObjectMapper getObjectMapper() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        static final CustomObjectMapper INSTANCE = new CustomObjectMapper();
    }
}