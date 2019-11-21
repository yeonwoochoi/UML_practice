package HWProject1.Person;

public class Staff extends Employee {
    public Staff (String id, String name, String depart, boolean manager) {
        super(id, name, depart, manager);
        super.setPosition("Staff");
    }
}
