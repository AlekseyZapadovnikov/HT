package managers;

import IO.RouteXMLScaner;
import IO.RouteXMLWriter;
import itemsInArrea.Route;
import java.io.*;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;

public class ColectionManager {
    LinkedList<Route> routes;
    HashMap<Long, Route> routesMap = new HashMap<>();
    private int currentId = 1;
    private LocalDateTime lastInitTime;
    private LocalDateTime lastUpdateTime;
    private LocalDateTime lastSaveTime;
    private RouteXMLScaner scaner;
    private RouteXMLWriter writer;

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
     * @return Последнее время инициализации.
     */
    public LocalDateTime getLastInitTime() {
        return lastInitTime;
    }

    /**
     * @return Последнее время сохранения.
     */
    public LocalDateTime getLastSaveTime() {
        return lastSaveTime;
    }

    /**
     * @return коллекция.
     */
    public LinkedList<Route> getroutes() {
        return routes;
    }

    /**
     * Получить Route по ID
     */
    public Route byId(long id) { return routesMap.get(id); }

    /**
     * Содержит ли колекции Route
     */
    public boolean isСontain(Route e) { return e == null || byId(e.getId()) != null; }

    /**
     * Получить свободный ID
     */
    public long getFreeId() {
        while (byId(++currentId) != null);
        return currentId;
    }

    /**
     * Добавляет Route
     */
    public boolean add(Route a) {
        if (isСontain(a)) return false;
        routesMap.put(a.getId(), a);
        routes.add(a);
        update();
        return true;
    }


    /**
     * Обновляет Route
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
     * Удаляет Route по ID
     */
    public boolean remove(long id) {
        var a = byId(id);
        if (a == null) return false;
        routesMap.remove(a.getId());
        routes.remove(a);
        update();
        return true;
    }

    public void clear(){
        routes.clear();
        routesMap.clear();
    }

    /**
     * Фиксирует изменения коллекции
     */
    public void update() {
        Collections.sort(routes);
        lastUpdateTime = LocalDateTime.now();
        if (lastInitTime == null){
            lastInitTime = lastUpdateTime;
        }
    }

    /**
     * Сохраняет коллекцию в файл
     */
    public void saveRoutes() throws IOException {
        writer.writeRoutes(routes);
        lastSaveTime = LocalDateTime.now();
    }

    public boolean isContainId(Long id){
        if (routesMap.containsKey(id)){
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        if (routes.isEmpty()) return "Коллекция пуста!";

        String info = "Время инициализации: " + lastInitTime.toString() + '\n' +
                "Врема последнего изменения: " + lastUpdateTime.toString() + '\n' +
                "Тип объектов: Routes" + '\n' +
                "Количество элементов: " + routes.size();

        return info;
    }
}

