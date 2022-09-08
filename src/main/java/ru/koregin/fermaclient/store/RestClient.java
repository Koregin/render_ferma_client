package ru.koregin.fermaclient.store;

import org.apache.tomcat.util.codec.binary.Base64;
import org.json.simple.JSONObject;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import ru.koregin.fermaclient.model.Task;
import ru.koregin.fermaclient.model.User;

import java.nio.charset.StandardCharsets;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class RestClient implements Store {

    private User currentUser;

    private final RestTemplate restTemplate;

    private static final String USER_API_CREATE = "http://localhost:8080/register";
    private static final String TASK_API = "http://localhost:8080/task";

    public RestClient() {
        this.restTemplate = new RestTemplate();
    }

    @Override
    public Task addTask(Task task) {
        HttpHeaders headers = createHeaders(currentUser.getUsername(), currentUser.getPassword());
        headers.setContentType(MediaType.APPLICATION_JSON);
        JSONObject taskJsonObject = new JSONObject();
        taskJsonObject.put("name", task.getName());
        HttpEntity<String> request = new HttpEntity<>(taskJsonObject.toString(), headers);
        Task createdTask = restTemplate.postForObject(TASK_API, request, Task.class);
        if (createdTask != null && createdTask.getCreated() != null) {
            createdTask.setCreated(createdTask.getCreated().withZoneSameInstant(ZoneId.of("Europe/Moscow")));
        }
        return createdTask;
    }

    @Override
    public Task findById(int id) {
        HttpHeaders headers = createHeaders(currentUser.getUsername(), currentUser.getPassword());
        headers.setContentType(MediaType.APPLICATION_JSON);
        Task task = null;
        try {
            ResponseEntity<Task> responseEntity = restTemplate.exchange(
                    TASK_API + "/" + id,
                    HttpMethod.GET,
                    new HttpEntity<>(headers),
                    new ParameterizedTypeReference<>() {
                    });
            if (responseEntity.hasBody()) {
                task = responseEntity.getBody();
                if (task != null && task.getCreated() != null) {
                    task.setCreated(task.getCreated().withZoneSameInstant(ZoneId.of("Europe/Moscow")));
                }
                if (task != null && task.getCompleted() != null) {
                    task.setCompleted(task.getCompleted().withZoneSameInstant(ZoneId.of("Europe/Moscow")));
                }
            }
        } catch (RestClientException e) {
            e.printStackTrace();
        }
        return task;
    }

    @Override
    public List<Task> findAll() {
        List<Task> tasks = new ArrayList<>();
        HttpHeaders headers = createHeaders(currentUser.getUsername(), currentUser.getPassword());
        headers.setContentType(MediaType.APPLICATION_JSON);
        try {
            ResponseEntity<List<Task>> responseEntity = restTemplate.exchange(
                    TASK_API,
                    HttpMethod.GET,
                    new HttpEntity<>(headers),
                    new ParameterizedTypeReference<>() {
                    });
            if (responseEntity.hasBody()) {
                tasks = responseEntity.getBody();
            }
        } catch (RestClientException e) {
            e.printStackTrace();
        }
        return tasks;
    }

    @Override
    public boolean userRegister(User user) {
        boolean result = false;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        JSONObject userJsonObject = new JSONObject();
        userJsonObject.put("username", user.getUsername());
        userJsonObject.put("password", user.getPassword());
        HttpEntity<String> request = new HttpEntity<>(userJsonObject.toString(), headers);
        try {
            restTemplate.postForObject(USER_API_CREATE, request, String.class);
            setCurrentUser(user);
            result = true;
        } catch (RestClientException e) {
            e.printStackTrace();
        }
        return result;
    }

    HttpHeaders createHeaders(String username, String password) {
        return new HttpHeaders() {{
            String auth = username + ":" + password;
            byte[] encodedAuth = Base64.encodeBase64(
                    auth.getBytes(StandardCharsets.US_ASCII));
            String authHeader = "Basic " + new String(encodedAuth);
            set("Authorization", authHeader);
        }};
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }
}
