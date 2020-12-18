package com.unomi.es.high;

import org.apache.http.HttpHost;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.DocWriteRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchAction;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.Strings;
import org.elasticsearch.common.document.DocumentField;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class HighLevel1 {
    public static void main(String[] args) {
        RestHighLevelClient client = null;
        try {
            client = new RestHighLevelClient(
                    RestClient.builder(
                            new HttpHost("localhost", 9200, "http")
//                        ,new HttpHost("localhost", 9201, "http")
                    ));

            /**
             * 高级客户端将基于提供的构建器在内部创建用于执行请求的低级客户端。
             * 该低级客户端维护一个连接池并启动一些线程，因此您应该在完全正确地使用它之后关闭高级客户端，
             * 这反过来又将关闭内部低级客户端以释放这些资源。
             * 这可以通过关闭完成：
             */
            /*在本文档的其余部分中，有关Java High Level Client的内容，将把RestHighLevelClient实例称为客户端。*/
            /**/
//            test1();
//            indexApi(client);

            searchApi(client);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void searchApi(RestHighLevelClient client) {

    }

    private static void indexApi(RestHighLevelClient client) throws IOException {
//        createIndex(client);
        getApi(client);

    }

    private static void getApi(RestHighLevelClient client) throws IOException {
        GetRequest g1 = new GetRequest("posts", "1");
        GetResponse resp = client.get(g1, RequestOptions.DEFAULT);
        showResp(resp);

        //options
        //不取source
        GetRequest dontFetchSource = g1.fetchSourceContext(FetchSourceContext.DO_NOT_FETCH_SOURCE);
        GetResponse resp2 = client.get(dontFetchSource, RequestOptions.DEFAULT);
        showResp(resp2);

        //取source
        GetRequest fetchSource = g1.fetchSourceContext(FetchSourceContext.FETCH_SOURCE);
        GetResponse resp3 = client.get(fetchSource, RequestOptions.DEFAULT);
        showResp(resp3);

//      取特定字段
        FetchSourceContext specifyContext = new FetchSourceContext(true, new String[]{
                "message", "*Date"
        }, Strings.EMPTY_ARRAY);
        GetRequest context = g1.fetchSourceContext(specifyContext);
        GetResponse resp4 = client.get(context, RequestOptions.DEFAULT);


    }

    private static void showResp(GetResponse resp) {
//        Map<String, DocumentField> fields = resp.getFields();
//        System.out.println("====================");
//        System.out.println(resp.getIndex() + " == " + resp.getId());
//
//        fields.forEach((key, value) -> {
//            System.out.print(key);
//            System.out.print(value);
//        });
//
//        resp.getSource().forEach((key, value) -> {
//            System.out.println(key + " : " + value);
//        });
//
//        System.out.println("====================");
    }

    /**
     * Document API
     *
     * @param client
     * @throws IOException
     */
    private static void createIndex(RestHighLevelClient client) throws IOException {
        //创建index

        //以字符串形式提供的文档源
        IndexRequest request = new IndexRequest("posts");
        request.id("1");
        String jsonString = "{" +
                "\"user\":\"kimchy\"," +
                "\"postDate\":\"2013-01-30\"," +
                "\"message\":\"trying out Elasticsearch\"" +
                "}";
        request.source(jsonString, XContentType.JSON);
        IndexResponse response = client.index(request, RequestOptions.DEFAULT);
        System.out.println(response.getId());
        System.out.println(response.status().getStatus());


        //提供为map的文档源，该地图会自动转换为JSON格式
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("user", "kimchy");
        jsonMap.put("postDate", "2013-01-30");
        jsonMap.put("message", "trying out Elasticsearch");
        IndexRequest request1 = new IndexRequest("post1").id("2").source(jsonMap);
        IndexResponse response1 = client.index(request1, RequestOptions.DEFAULT);
        System.out.println(response1.getId());
        System.out.println(response1.status().getStatus());


//      作为XContentBuilder对象提供的文档源，Elasticsearch内置帮助器可生成JSON内容
        XContentBuilder builder = XContentFactory.jsonBuilder();
        builder.startObject();
        builder.field("user", "kimchy");
        builder.timeField("postDate", "2013-01-30");
        builder.field("message", "trying out Elasticsearch");
        builder.endObject();
        IndexRequest request2 = new IndexRequest("post2").id("3").source(builder);
        IndexResponse response2 = client.index(request2, RequestOptions.DEFAULT);
        System.out.println(response2.getId());
        System.out.println(response2.status().getStatus());

//      作为对象密钥对提供的文档源，转换为JSON格式
        IndexRequest request3 = new IndexRequest("post3").id("4")
                .source("user", "kimchy", "postDate", new Date(), "message", "trying out Elasticsearch");
        IndexResponse response3 = client.index(request3, RequestOptions.DEFAULT);
        System.out.println(response3.getId());
        System.out.println(response3.status().getStatus());


        //创建index 冲突问题
        IndexRequest request4 = request3.opType(DocWriteRequest.OpType.CREATE);
        try {
            IndexResponse index = client.index(request4, RequestOptions.DEFAULT);
        } catch (ElasticsearchException e) {
            System.out.println(e);
            System.out.println("冲突");
        }
    }

    /**
     * RestHighLevelClient中的所有API都接受一个RequestOptions，
     * 您可以使用它们以不会改变Elasticsearch执行请求的方式自定义请求。
     * 例如，在这里您可以指定NodeSelector来控制哪个节点接收请求。
     * 有关自定义选项的更多示例，请参见低级客户端文档。
     * <p>
     * 1. RequestOptions类保存应在同一应用程序中的许多请求之间共享的部分请求。
     * 您可以创建一个单例实例，并在所有请求之间共享它：
     */
    private static void test1() {


    }

}
