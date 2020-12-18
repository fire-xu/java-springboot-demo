package com.k8s;

import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.Configuration;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.*;
import io.kubernetes.client.util.Config;
import io.kubernetes.client.util.KubeConfig;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Objects;

public class WatchExample {
    public static void main(String[] args) throws Exception {
        listAllPod();
    }

    private static void listAllPod() throws IOException, ApiException {
        InputStream inputStream = WatchExample.class.getClassLoader().getResourceAsStream("config");
        InputStreamReader reader = new InputStreamReader(inputStream);

//        ApiClient client = Config.defaultClient();
        ApiClient client = Config.fromConfig(reader);
        Configuration.setDefaultApiClient(client);
        CoreV1Api api = new CoreV1Api();
        V1PodList list = api.listPodForAllNamespaces(null, null, null, null, null, null, null, null, null);
        list.getItems().stream().forEach(v1Pod -> showPod(v1Pod));

    }

    private static void showPod(V1Pod pod) {
        System.out.println("================================");
        String apiVersion = pod.getApiVersion();
        String kind = pod.getKind();
        V1ObjectMeta metadata = pod.getMetadata();
        V1PodSpec spec = pod.getSpec();
        V1PodStatus status = pod.getStatus();

        System.out.println(String.format("apiVersion: %s\nkind: %s", apiVersion, kind));
        System.out.println("metadata:");
        String name = metadata.getName();
        Map<String, String> labels = metadata.getLabels();
        Map<String, String> annotations = metadata.getAnnotations();
        System.out.println(String.format("\tname: %s", name));
        if (labels != null && !labels.isEmpty()) {
            System.out.println("\tlabels");
            Objects.requireNonNull(labels).entrySet().forEach(
                    e -> System.out.println(String.format("\t\t%s: %s", e.getKey(), e.getValue()))
            );
        }
        if (annotations != null && !annotations.isEmpty()) {
            System.out.println("\tannotations");
            Objects.requireNonNull(annotations).entrySet().forEach(
                    e -> System.out.println(String.format("\t%s: %s", e.getKey(), e.getValue()))
            );
        }
    }

}
