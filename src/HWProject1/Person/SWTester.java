package HWProject1.Person;

public class SWTester extends Employee{
    public SWTester (String id, String name, String depart, boolean manager) {
        super(id, name, depart, manager);
        super.setPosition("SW Tester");
    }
}
