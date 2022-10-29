package fr.benco11.asynconf.exercices.ex3.services;

import fr.benco11.asynconf.exercices.ex3.entity.Task;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Service qui gère les tâches
 */
public class TaskService {
    private final Map<String, Task> tasks;

    public TaskService() {
        tasks = new HashMap<>();
    }

    public void addTask(Task task) {
        tasks.put(task.name(), task);
    }

    public List<Task> tasks() {
        return tasks.values()
                    .stream()
                    .toList();
    }

    public Optional<Task> getTask(String name) {
        return (tasks.containsKey(name)) ? Optional.of(tasks.get(name)) : Optional.empty();
    }

    public boolean isRegistered(String name) {
        return getTask(name).isPresent();
    }

    public boolean isRegistered(Task task) {
        return isRegistered(task.name());
    }

    public void delete(String taskName) {
        tasks.remove(taskName);
    }

    public void clear() {
        tasks.clear();
    }
}
