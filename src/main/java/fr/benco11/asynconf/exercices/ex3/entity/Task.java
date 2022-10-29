package fr.benco11.asynconf.exercices.ex3.entity;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Représente une tâche avec un nom, une description, des utilisateurs assignés et un état (complété ou non)
 */
public class Task {
    private final String name;
    private final String description;
    private final List<User> assignedUsers;
    private boolean completed;

    public Task(String name, String description, List<User> assignedUsers) {
        this.name = name;
        this.description = description;
        this.assignedUsers = assignedUsers;
    }

    public void assignUser(User user) {
        assignedUsers.add(user);
    }

    public void unAssignUser(User user) {
        assignedUsers.remove(user);
    }

    public void complete() {
        this.completed = true;
    }

    public List<User> getAssignedUsers() {
        return assignedUsers;
    }

    public String name() {
        return name;
    }

    public String description() {
        return description;
    }

    public boolean isCompleted() {
        return completed;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("- '")
          .append(name)
          .append("' :\n");
        sb.append("  Description : ")
          .append(description)
          .append("\n");
        sb.append("  Personnes assignées : ")
          .append(assignedUsers.stream()
                               .map(User::name)
                               .collect(Collectors.joining(", ")))
          .append("\n");
        sb.append("  État : ")
          .append((completed ? "Complétée" : "En cours"));
        return sb.toString();
    }
}
