package fr.benco11.asynconf.exercices.ex3.entity;


import fr.benco11.asynconf.exercices.ex3.permissions.PermissionsGroup;

/**
 * Représente un utilisateur avec un nom et des permissions
 *
 * @param name  le nom
 * @param group le groupe de permission
 */
public record User(String name, PermissionsGroup group) {
}
