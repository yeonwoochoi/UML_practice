package HWProject1.Person;

public class SWDeveloper extends Employee {
    public SWDeveloper (String id, String name, String depart, boolean manager) {
        super(id, name, depart, manager);
        super.setPosition("SW Developer");
    }
}
