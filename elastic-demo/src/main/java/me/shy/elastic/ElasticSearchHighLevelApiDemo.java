/**
 * @Since: 2019-08-27 21:14:15
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: -
 * @LastTime: 2020-02-28 15:29:02
 */
package me.shy.elastic;

import java.io.IOException;
import org.apache.http.HttpHost;
import org.elasticsearch.Build;
import org.elasticsearch.Version;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.main.MainResponse;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.cluster.ClusterName;
import org.elasticsearch.common.xcontent.XContentType;

/**
 * Hello world!
 *
 */
public class ElasticSearchHighLevelApiDemo {
    public static void main(String[] args) throws IOException {
        // Low Level Client init
        RestClient lowLevelRestClient = RestClient.builder(new HttpHost("localhost", 9200, "http")).build();
        // High Level Client init
        RestHighLevelClient client = new RestHighLevelClient(lowLevelRestClient);

        IndexRequest request = new IndexRequest("demo", // index name
            "doc", // type
            "1"); // doc id
        String jsonString =
            "{" + "\"user\":\"kimchy\"," + "\"postDate\":\"2013-01-30\"," + "\"message\":\"trying out Elasticsearch\""
                + "}";
        // request.source(jsonString,
        // XContentType.JSON).opType(DocWriteRequest.OpType.CREATE);
        request.source(jsonString, XContentType.JSON);
        IndexResponse response = client.index(request);
        System.out.println("Response=" + response);

        MainResponse response2 = client.info();
        ClusterName clusterName = response2.getClusterName();
        String clusterUuid = response2.getClusterUuid();
        String nodeName = response2.getNodeName();
        Version version = response2.getVersion();
        Build build = response2.getBuild();
        System.out.println("cluster name:" + clusterName);
        System.out.println("cluster uuid:" + clusterUuid);
        System.out.println("node name:" + nodeName);
        System.out.println("node version:" + version);
        System.out.println("node name:" + nodeName);
        System.out.println("build info:" + build);
    }
}
