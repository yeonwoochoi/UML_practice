package HWProject1.Person;

public abstract class Employee {
    private String id;
    private String name;
    private String depart;
    private boolean manager;
    private String position;

    public Employee (String  id, String name, String depart, boolean manager) {
        this.id = id;
        this.name = name;
        this.depart = depart;
        this.manager = manager;
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getDepart() {
        return this.depart;
    }

    public boolean getManager() {
        return this.manager;
    }

    public String getPosition() {
        return this.position;
    }

    public void setId (String id) {
        this.id = id;
    }

    public void setName (String name) {
        this.name = name;
    }

    public void setDepart(String depart) {
        this.depart = depart;
    }

    public void setManager(boolean manager) {
        this.manager = manager;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Override
    public String toString() {
        String manager;
        if (getManager() == true) {
            manager = "O";
        } else {
            manager = "X";
        }
        return getId() + "/" + getName() + "/" + getDepart() + "/" + getPosition() + "/" + manager ;
    }
}
