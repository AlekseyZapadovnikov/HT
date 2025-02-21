package comands;

import managers.ColectionManager;

public class RemoveById extends Command {
    ColectionManager colMan;

    public RemoveById(String name, String description, ColectionManager colMan) {
        super(name, description);
        this.colMan = colMan;
    }

    @Override
    public void execute(String[] args) {
        try {
            long id = Long.parseLong(args[0]);
            if (colMan.isContainId(id)) {
                colMan.remove(id);
                System.out.println("Объект успешно удалён");
            } else {
                System.out.println("в коллекции нет объекта с таким ID");
            }
        } catch (NumberFormatException e) {
            System.out.println("вы ввели ID некорректо, ID - тип long");
        } catch (IndexOutOfBoundsException e){
            System.out.println("вы не ввели ID");
        } catch (Exception e){
            System.out.println("Что-то пошло не так =(");
        }
    }
}
