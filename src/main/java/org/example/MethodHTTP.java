package org.example;

import com.google.gson.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class MethodHTTP {
    private final String URL = "https://jsonplaceholder.typicode.com/users";

    public void createPerson(Person person) throws URISyntaxException, IOException, InterruptedException {
        Gson gson = new GsonBuilder().create();

        HttpRequest createRequest = HttpRequest.newBuilder(new URI(URL))
                .method("POST", HttpRequest.BodyPublishers.ofString(gson.toJson(person)))
                .version(HttpClient.Version.HTTP_1_1)
                .build();

        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> response = httpClient.send(createRequest, HttpResponse.BodyHandlers.ofString());

        System.out.println(response.statusCode());
        System.out.println(response.body());
    }

    public void update(int id, Person person) throws URISyntaxException, IOException, InterruptedException {
        Gson gson = new GsonBuilder().create();
        HttpRequest updateRequest = HttpRequest.newBuilder(new URI(URL + "/" + id))
                .method("PUT", HttpRequest.BodyPublishers.ofString(gson.toJson(person)))
                .version(HttpClient.Version.HTTP_1_1)
                .build();

        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> response = httpClient.send(updateRequest, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.statusCode());
        System.out.println(response.body());
    }

    public int delete(int id) throws URISyntaxException, IOException, InterruptedException {
        HttpRequest deleteRequest = HttpRequest.newBuilder(new URI(URL + "/" + id))
                .DELETE()
                .version(HttpClient.Version.HTTP_1_1)
                .build();

        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> response = httpClient.send(deleteRequest, HttpResponse.BodyHandlers.ofString());
        return response.statusCode();
    }

    public ArrayList<Person> getData() throws URISyntaxException, IOException, InterruptedException {
        Gson gson = new Gson();
        HttpRequest httpRequest = HttpRequest.newBuilder(new URI(URL))
                .GET()
                .version(HttpClient.Version.HTTP_1_1)
                .build();

        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        JsonArray jsonArray = gson.fromJson(response.body(), JsonArray.class);
        ArrayList<Person> result = new ArrayList<>();
        for (JsonElement js : jsonArray) {
            Person person = gson.fromJson(js, Person.class);
            result.add(person);
        }
        return result;
    }

    public Person getPersonId(int id) throws URISyntaxException, IOException, InterruptedException {
        Gson gson = new Gson();
        HttpRequest httpRequest = HttpRequest.newBuilder(new URI(URL + "/" + id))
                .GET()
                .version(HttpClient.Version.HTTP_1_1)
                .build();
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        return gson.fromJson(response.body(), Person.class);
    }

    public String getPersonUsername(String username) throws URISyntaxException, IOException, InterruptedException {
        Gson gson = new Gson();
        HttpRequest httpRequest = HttpRequest.newBuilder(new URI(URL + "?username=" + username))
                .GET()
                .version(HttpClient.Version.HTTP_1_1)
                .build();
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    public void getComments(int id) throws URISyntaxException, IOException, InterruptedException {
        String url = "https://jsonplaceholder.typicode.com/users/" + id + "/posts";
        HttpRequest postRequest = HttpRequest.newBuilder(new URI(url))
                .GET()
                .version(HttpClient.Version.HTTP_1_1)
                .build();
        HttpClient postClient = HttpClient.newHttpClient();
        HttpResponse<String> response = postClient.send(postRequest, HttpResponse.BodyHandlers.ofString());
        Gson gson = new Gson();
        JsonArray jsonArray = gson.fromJson(response.body(), JsonArray.class);

        JsonObject lastPost = jsonArray.get(jsonArray.size() - 1).getAsJsonObject();
        Posts post = new Gson().fromJson(lastPost, Posts.class);

        HttpRequest commentsRequest = HttpRequest.newBuilder(new URI("https://jsonplaceholder.typicode.com/posts/" + post.getId() + "/comments"))
                .GET()
                .version(HttpClient.Version.HTTP_1_1)
                .build();
        HttpClient commentClient = HttpClient.newHttpClient();

        HttpResponse<String> responseComment = commentClient.send(commentsRequest, HttpResponse.BodyHandlers.ofString());
        JsonArray jsonArray2 = gson.fromJson(responseComment.body(), JsonArray.class);
        AtomicInteger userId = new AtomicInteger();
        AtomicInteger commID = new AtomicInteger();
        jsonArray2.forEach(
        jsonElement -> {
            JsonObject obj = jsonElement.getAsJsonObject();
            userId.set(obj.get("postId").getAsInt());
            commID.set(obj.get("id").getAsInt());
            String comments = obj.get("body").getAsString();
            System.out.println(comments);
            File file = new File("user-" + userId + "-post-" + commID + "-comments.json");
            try (FileWriter writer = new FileWriter(file))
            {
                writer.write(comments);
                writer.flush();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        });

    }

    public void openTasks(int X) throws URISyntaxException, IOException, InterruptedException {
        HttpRequest httpRequest = HttpRequest.newBuilder(new URI("https://jsonplaceholder.typicode.com/users/1/todos"))
                .GET()
                .version(HttpClient.Version.HTTP_1_1)
                .build();
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        Gson gson = new Gson();
        JsonArray jsonArray = gson.fromJson(response.body(), JsonArray.class);
        for (JsonElement element : jsonArray) {
            UserTaskThree userTaskThree = gson.fromJson(element, UserTaskThree.class);
            if (userTaskThree.getUserId() == X && !userTaskThree.isCompleted()) {
                System.out.println(userTaskThree.getTitle());
            }
        }
    }
}
