package fr.benco11.asynconf.utils;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class CollectionUtils {
    /**
     * Méthode statique pour faire une linkedHashMap à l'instar de Map.of()
     *
     * @param entries les différentes entrées de la map
     * @param <K>     le type des clés
     * @param <V>     le type des valeurs
     * @return la LinkedHashMap
     */
    public static <K, V> Map<K, V> linkedHashMap(
            Collection<Map.Entry<? extends K, ? extends V>> entries) {
        final LinkedHashMap<K, V> map = new LinkedHashMap<>();
        entries.forEach(entry -> map.put(entry.getKey(), entry.getValue()));
        return map;
    }

    private CollectionUtils() {
    }
}
