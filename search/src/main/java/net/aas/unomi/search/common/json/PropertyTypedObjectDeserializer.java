package net.aas.unomi.search.common.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonTokenId;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.UntypedObjectDeserializer;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class PropertyTypedObjectDeserializer extends UntypedObjectDeserializer {

    private static final long serialVersionUID = -2561171359946902967L;

    private Map<String, Class<? extends Object>> registry =
            new LinkedHashMap<String, Class<? extends Object>>();

    private Map<String, Set<String>> fieldValuesToMatch = new LinkedHashMap<String, Set<String>>();

    public void registerMapping(String matchExpression,
                                Class<? extends Object> mappedClass) {
        registry.put(matchExpression, mappedClass);
        String[] fieldParts = matchExpression.split("=");
        Set<String> valuesToMatch = fieldValuesToMatch.get(fieldParts[0]);
        if (valuesToMatch == null) {
            valuesToMatch = new LinkedHashSet<String>();
        }
        valuesToMatch.add(fieldParts[1]);
        fieldValuesToMatch.put(fieldParts[0], valuesToMatch);
    }

    @Override
    public Object deserialize(
            JsonParser jp, DeserializationContext ctxt)
            throws IOException {
        if (jp.getCurrentTokenId() != JsonTokenId.ID_START_OBJECT) {
            return super.deserialize(jp, ctxt);
        }
        ObjectCodec codec = jp.getCodec();
        TreeNode treeNode = codec.readTree(jp);
        Class<? extends Object> objectClass = null;
        if (treeNode instanceof ObjectNode) {
            ObjectNode root = (ObjectNode) treeNode;
            Iterator<Map.Entry<String, JsonNode>> elementsIterator =
                    root.fields();
            while (elementsIterator.hasNext()) {
                Map.Entry<String, JsonNode> element = elementsIterator.next();
                String name = element.getKey();
                if (fieldValuesToMatch.containsKey(name)) {
                    Set<String> valuesToMatch = fieldValuesToMatch.get(name);
                    for (String valueToMatch : valuesToMatch) {
                        if (element.getValue().asText().matches(valueToMatch)) {
                            objectClass = registry.get(name + "=" + valueToMatch);
                            break;
                        }
                    }
                    if (objectClass != null) {
                        break;
                    }
                }
            }
            if (objectClass == null) {
                objectClass = HashMap.class;
            }
        } else {

        }
        if (objectClass == null) {
            return super.deserialize(codec.treeAsTokens(treeNode), ctxt);
        }
        return codec.treeToValue(treeNode, objectClass);
    }
}
