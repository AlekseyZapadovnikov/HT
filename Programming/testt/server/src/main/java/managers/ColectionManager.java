package managers;

import IO.RouteXMLScaner;
import IO.RouteXMLWriter;
import itemsInArrea.Route;
import java.io.*;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Set;

/**
 * Manages a collection of {@link Route} objects, providing methods to add,
 * update, remove, save, and retrieve routes by their ID.
 * <p>
 * The class also keeps track of initialization, update, and save times.
 */
public class ColectionManager {
    /**
     * The list of routes (used as the main collection).
     */
    LinkedList<Route> routes;

    /**
     * A map from route IDs to {@link Route} objects for quick lookup.
     */
    HashMap<Long, Route> routesMap = new HashMap<>();

    long currentId = 5;
    private LocalDateTime lastInitTime;
    private LocalDateTime lastUpdateTime;
    private LocalDateTime lastSaveTime;

    private RouteXMLScaner scaner;
    private RouteXMLWriter writer;

    /**
     * Constructs a {@code ColectionManager} by reading data via the given scanner.
     * Initializes the collection and sets {@link #lastInitTime} to the current time.
     *
     * @param scaner the XML scanner to read initial data from
     * @param writer the XML writer to save data
     */
    public ColectionManager(RouteXMLScaner scaner, RouteXMLWriter writer) {
        this.lastInitTime = LocalDateTime.now();
        this.scaner = scaner;
        this.routes = scaner.readData();
        this.writer = writer;

        for (Route route : routes) {
            routesMap.put(route.getId(), route);
        }
    }

    /**
     * Returns the last initialization time of the collection manager.
     *
     * @return the {@link LocalDateTime} of the last initialization
     */
    public LocalDateTime getLastInitTime() {
        return lastInitTime;
    }

    /**
     * Returns the last time the routes were saved to file.
     *
     * @return the {@link LocalDateTime} of the last save
     */
    public LocalDateTime getLastSaveTime() {
        return lastSaveTime;
    }

    /**
     * Returns the current list of routes.
     *
     * @return a {@link LinkedList} of routes
     */
    public LinkedList<Route> getroutes() {
        return routes;
    }

    /**
     * Gets a {@link Route} object by its unique ID.
     *
     * @param id the route's ID
     * @return the {@code Route} with the given ID, or {@code null} if not found
     */
    public Route byId(long id) {
        return routesMap.get(id);
    }

    /**
     * Checks if the given {@link Route} is contained in this collection.
     *
     * @param e the route to check
     * @return {@code true} if it is present or {@code e} is null,
     *         {@code false} otherwise
     */
    public boolean isСontain(Route e) {
        return e == null || byId(e.getId()) != null;
    }

    /**
     * Finds the next free ID that is not yet used in the collection.
     *
     * @return a long value representing a free ID
     */
    public long getFreeId() {
        long newId = currentId + 1;
        while (byId(newId) != null) {
            newId++;
        }
        currentId = newId; // Обновляем currentId до последнего найденного свободного ID
        return newId;
    }

    /**
     * Adds a new {@link Route} to the collection if it is not already present.
     *
     * @param a the route to add
     * @return {@code true} if added, {@code false} otherwise
     */
    public boolean add(Route a) {
        System.out.println("[DEBUG] Попытка добавления объекта: " + a);
        if (isСontain(a)) {
            System.out.println("[DEBUG] Объект уже существует в коллекции.");
            return false;
        }
        long newId = getFreeId();
        System.out.println("[DEBUG] Присвоен ID: " + newId);
        a.setId(newId);
        routesMap.put(newId, a);
        routes.add(a);
        update();
        System.out.println("[DEBUG] Объект успешно добавлен. Текущий размер коллекции: " + routes.size());
        return true;
    }

    /**
     * Updates an existing {@link Route} in the collection.
     *
     * @param a the route with updated values
     * @return {@code true} if successfully updated, {@code false} if not found
     */
    public boolean update(Route a) {
        if (!isСontain(a)) return false;
        routes.remove(byId(a.getId()));
        routesMap.put(a.getId(), a);
        routes.add(a);
        update();
        return true;
    }

    /**
     * Removes the {@link Route} with the specified ID from the collection.
     *
     * @param id the ID of the route to remove
     * @return {@code true} if the route was removed, {@code false} if not found
     */
    public boolean remove(long id) {
        Route a = byId(id);
        if (a == null) return false;
        routesMap.remove(a.getId());
        routes.remove(a);
        update();
        return true;
    }

    /**
     * Clears all routes from the collection.
     */
    public void clear(){
        routes.clear();
        routesMap.clear();
    }

    /**
     * Sorts the routes, updates {@link #lastUpdateTime} to the current time,
     * and initializes {@link #lastInitTime} if it was null.
     */
    public void update() {
        Collections.sort(routes);
        lastUpdateTime = LocalDateTime.now();
        if (lastInitTime == null) {
            lastInitTime = lastUpdateTime;
        }
    }

    /**
     * Saves the routes to an external file using {@link RouteXMLWriter}.
     *
     * @throws IOException if an I/O error occurs while saving
     */
    public void saveRoutes() throws IOException {
        writer.writeRoutes(routes);
        lastSaveTime = LocalDateTime.now();
    }

    /**
     * Checks if the collection contains a route with the given ID.
     *
     * @param id the ID to check
     * @return {@code true} if the ID is present, {@code false} otherwise
     */
    public boolean isContainId(Long id) {
        return routesMap.containsKey(id);
    }

    /**
     * Returns a string with information about the collection:
     * <ul>
     *   <li>Initialization time</li>
     *   <li>Last update time</li>
     *   <li>Type of objects</li>
     *   <li>Number of elements</li>
     * </ul>
     *
     * @return info about the collection
     */
    @Override
    public String toString() {
        if (routes.isEmpty()) return "Коллекция пуста!";

        String info = "Время инициализации: " + lastInitTime.toString() + '\n' +
                "Время последнего изменения: " + lastUpdateTime.toString() + '\n' +
                "Тип объектов: Routes" + '\n' +
                "Количество элементов: " + routes.size();

        return info;
    }

    /**
     * Returns the current size of the collection.
     *
     * @return the number of routes
     */
    public long getSize() {
        return routes.size();
    }

    /**
     * Returns all IDs stored in the collection as a {@link Set}.
     *
     * @return a set of route IDs
     */
    public Set<Long> getIdSet() {
        return routesMap.keySet();
    }
}