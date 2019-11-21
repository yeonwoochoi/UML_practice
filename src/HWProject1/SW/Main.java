package HWProject1.SW;

import HWProject1.Person.SWTester;
import HWProject1.Person.Staff;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        //file load


        Scanner scanner = new Scanner(System.in);
        Regulate regulate = new Regulate();
        HRSW hrsw = new HRSW();
        boolean run = true;


        while (run) {
            Main main = new Main();
            main.printMenu();
            int inputInt = scanner.nextInt();

            if (inputInt == 1) {
                addEmployee(scanner, hrsw, regulate);
            } else if (inputInt == 2) {
                deleteOrUpdateEmployee(scanner, hrsw, regulate);
            } else if (inputInt == 3) {
                findEmployee(scanner, hrsw, regulate);
            } else if (inputInt == 4) {
                moveDepartment(scanner, hrsw, regulate);
            } else if (inputInt == 5) {
                History(scanner, hrsw, regulate);
            } else if (inputInt == 6) {
                System.out.println("프로그램 종료");
                run = false;
            } else {
                System.out.println("1,2,3,4,5,6 중 하나만 입력하시요.");
            }
        }
    }


    // 직원 관리 프로그램 메뉴 호출하는 method
    public static void printMenu() {
        System.out.println("-------SW 직원 관리 프로그램-------\n" +
                "1. 신규 직원 추가\n" +
                "2. 기존 직원 삭제 및 수정\n" +
                "3. 직원 검색\n" +
                "4. 부서 이동\n" +
                "5. 히스토리 기능\n" +
                "6. 프로그램 종료\n" +
                "---------------------------------" +
                "입력 >> ");
    }



    // 1. 신규 직원 추가 method
    public static void addEmployee(Scanner scanner, HRSW hrsw, Regulate regulate) {

        // id 입력
        System.out.println("※ 아이디는 중복이 허용되지 않습니다 ※\n");
        System.out.println("직원 아이디 >> ");
        String inputId = scanner.next();

        // id 중복확인
        int checkInt = regulate.checkId(inputId);
        if (checkInt > 0) {
            // id 중복되는 경우
            System.out.println("이미 존재하는 ID 입니다.");

        } else if (checkInt == 0){
            // id 중복되지 않는 경우


            // name 입력
            System.out.println("직원 이름 >> ");
            String inputName = scanner.next();


            // depart 입력
            System.out.println("※ 부서 종류 : 비서실, 총무팀, 인사팀, 개발1팀, 개발2팀, 개발3팀 ※");
            System.out.println("직원 부서 >> ");
            String inputDepart = scanner.next();

            // depart 입력값 오류 확인
            String[] checkArr1 = regulate.checkDepart(inputDepart);
            if (checkArr1[0] == "false") {
                // 입력값이 SW 부서 이름이 아닐때

                System.out.println(checkArr1[1]);

            } else if (checkArr1[0] == "true") {
                //입력값이 부서 이름이 맞을때

                if (checkArr1[1].equals("비서실") || checkArr1[1].equals("총무팀") || checkArr1[1].equals("인사팀")) {

                    // Staff 클래스 호출
                    Staff staff = new Staff(inputId, inputName, inputDepart, false);
                    String inputStaff;
                    String[] checkFirst = regulate.formEachArr("id");
                    if (checkFirst.length > 1) {
                        inputStaff = "\n" + staff.toString();
                        System.out.println("0 : " + checkFirst[0]);
                    } else if (checkFirst[0] != ""){
                        inputStaff = "\n" + staff.toString();
                    } else {
                        inputStaff = staff.toString();
                    }
                    // Staff 클래스의 toString 메소드 호출


                    // SW_employee.txt 에 Staff 정보 저장
                    hrsw.saveToFile(inputStaff);
                    hrsw.saveToLog("신규 직원 추가 : 직원 아이디 = " + inputId);

                } else {

                    // SWTester 클래스 호출
                    SWTester staff = new SWTester(inputId, inputName, inputDepart, false);
                    String inputSWTester;

                    // SWTester 클래스의 toString 메소드 호출
                    String[] checkFirst = regulate.formEachArr("id");
                    if (checkFirst.length > 1) {
                        inputSWTester = "\n" + staff.toString();
                    } else if (checkFirst[0] != ""){
                        inputSWTester = "\n" + staff.toString();
                    } else {
                        inputSWTester = staff.toString();
                    }

                    // SW_employee.txt 에 SWTester 정보 저장
                    hrsw.saveToFile(inputSWTester);
                    hrsw.saveToLog("신규 직원 추가 : 직원 아이디 = " + inputId);
                }
            }
        }
    }



    // 2. 기존 직원 삭제 및 수정 method
    public static void deleteOrUpdateEmployee(Scanner scanner, HRSW hrsw, Regulate regulate) {
        boolean run2 = true;

        while (run2) {
            System.out.println("-------2. 기존 직원 삭제 및 수정-------\n" +
                    "본인 ID >> ");
            String id = scanner.next();
            int checkInt = regulate.checkId(id);
            if (checkInt == 0) {
                // 입력받은 아이디가 존재하지 않을 때

                System.out.println("존재하지 않는 아이디입니다.");
            } else {
                // 입력받은 아이디가 존재할 때

                String[] managerArr = regulate.formEachArr("manager");
                String[] idArr = regulate.formEachArr("id");
                String[] departArr = regulate.formEachArr("depart");


                int index = -1;

                for (int i = 0; i < idArr.length; i++) {
                    if (idArr[i].equals(id)) {
                        index = i;
                    }
                }

                int length = 0;
                for (int j = 0; j < departArr.length; j++) {
                    if (departArr[j].equals(departArr[index])) {
                        length++;
                    }
                }
                int[] indexArr = new int[length];
                int indexA = 0;
                for (int j = 0; j < departArr.length; j++) {
                    if (departArr[j].equals(departArr[index])) {
                        indexArr[indexA] = j;
                        indexA++;
                    }
                }

                int m = 0;
                for (int j = 0; j < indexArr.length; j++) {
                    if (managerArr[indexArr[j]].equals("O")) {
                        m++;
                    }
                }



                boolean continue1;
                if (m > 0) {
                    if (!regulate.checkManager(id)) {

                        // 입력 받은 id가 manager 권한이 없을때
                        run2 = false;
                        continue1 = false;
                    } else {
                        continue1 = true;
                    }
                } else {
                    continue1 = true;
                }


                // id로 직원 정보 찾아서 manager 여부 파악 혹은 부서에 manager 가 없는 경우 확인
                if (!continue1) {

                    // 입력 받은 id가 manager 권한이 없을때
                    System.out.println("manager 만 권한이 있습니다.");
                    run2 = false;
                } else {
                    // 입력 받은 id가 manager 권한이 있을때 혹은 부서에 manager가 없을 때

                    boolean run3 = true;

                    while (run3) {
                        // 삭제할건지 수정할건지 선택
                        System.out.println("-------2. 기존 직원 삭제 및 수정-------\n" +
                                "1. 기존 직원 삭제\n" +
                                "2. 기존 직원 수정\n" +
                                "-------------------------------------\n" +
                                "입력 >> ");
                        int choice = scanner.nextInt();


                        if (choice == 1) {
                            // 삭제를 선택한 경우

                            System.out.println("----------2-1.기존 직원 삭제----------\n" +
                                    "직원 아이디 >> ");
                            String inputId = scanner.next();


                            // 삭제할 직원아이디 검색
                            int checkNumber = regulate.checkId(inputId);


                            // 삭제할 직원아이디가 해당 매니저와 같은 부서인지 확인

                            int indexManager = -1;
                            int indexEmployee = -1;
                            for (int i = 0; i < idArr.length; i++) {
                                if (idArr[i].equals(id)) {
                                    indexManager = i;
                                }
                                if (idArr[i].equals(inputId)) {
                                    indexEmployee = i;
                                }
                            }



                            if (checkNumber == 0) {
                                System.out.println("해당하는 직원이 없습니다.");
                                run3 = false;
                            } else {
                                // 검색 결과가 있는 경우 삭제

                                // 같은 부서의 직원 정보를 변경하는 건지 확인
                                if (departArr[indexManager].equals(departArr[indexEmployee])) {
                                    boolean removeCheck = hrsw.removeLine(inputId);
                                    if (removeCheck) {
                                        System.out.println("삭제 성공");
                                        hrsw.saveToLog("기존 직원 삭제 : 직원 아이디 = " + inputId);
                                    } else {
                                        System.out.println("삭제 실패");
                                    }
                                    run3 = false;
                                } else {
                                    System.out.println("다른 부서의 직원 정보를 변경할 수 없습니다.");
                                    run3 = false;
                                }
                            }

                        } else if (choice == 2) {
                            // 수정을 선택한 경우

                            System.out.println("----------2-2.기존 직원 수정----------\n" +
                                    "직원 아이디 >> ");
                            String inputId = scanner.next();
                            // 수정할 직원아이디 검색
                            int checkNumber = regulate.checkId(inputId);


                            // 수정할 직원아이디가 해당 매니저와 같은 부서인지 확인

                            int indexManager = -1;
                            int indexEmployee = -1;
                            for (int i = 0; i < idArr.length; i++) {
                                if (idArr[i].equals(id)) {
                                    indexManager = i;
                                }
                                if (idArr[i].equals(inputId)) {
                                    indexEmployee = i;
                                }
                            }



                            if (checkNumber == 0) {
                                System.out.println("해당하는 직원이 없습니다.");
                                run3 = false;
                            } else {
                                // 같은 부서의 직원 정보를 변경하는 건지 확인
                                if (departArr[indexManager].equals(departArr[indexEmployee])) {
                                    // 같은 부서인 경우

                                    System.out.println("----------2-2.기존 직원 수정----------\n" +
                                            "1.이름 변경   2.직책 변경   3.매니저 권한 설정 변경 \n" +
                                            "※ 1~3 숫자를 입력하시오." +
                                            "입력 >> ");
                                    int newData = scanner.nextInt();

                                    if (newData == 1) {
                                        System.out.println("----------2-2.기존 직원 수정----------\n" +
                                                "이름 >> ");
                                        String newName = scanner.next();

                                        hrsw.updateFile(inputId, "name", newName);
                                        hrsw.saveToLog("기존 직원 변경 : 직원아이디 =  " + inputId + " 가 이름을 " + newName + "으로 변경");


                                        run3 = false;
                                    } else if (newData == 2) {
                                        hrsw.updateFile(inputId, "position", "");
                                        hrsw.saveToLog("기존 직원 변경 : 직원아이디 =  " + inputId + " 가 position 변경함");

                                        run3 = false;
                                    } else if (newData == 3) {

                                        // 새로운 manager 임명
                                        hrsw.updateFile(inputId, "manager", "O");
                                        hrsw.saveToLog("기존 직원 변경 : 직원아이디 =  " + inputId + " 의 매니저 권한 변경");

                                        if (!inputId.equals(id)) {
                                            // 기존 manager 사임

                                            hrsw.updateFile(id, "manager", "X");
                                            hrsw.saveToLog("기존 직원 변경 : 기존 매니저였던 직원아이디 =  " + inputId + " 의 매니저 권한 변경");

                                        }

                                        run3 = false;

                                    } else {
                                        System.out.println("1, 2, 3 중 하나만 입력해 주십시오.");
                                    }
                                } else {
                                    // 다른 부서인 경우
                                    System.out.println("다른 부서의 직원 정보를 변경할 수 없습니다.");
                                    run3 = false;
                                }
                            }
                        } else {
                            //잘못된 값을 입력한 경우
                            System.out.println(" 1, 2 중 하나만 입력해 주십시오.");
                        }
                    }
                    run2 = false;
                }

            }
        }

    }




    // 3. 직원 검색 method
    public static void findEmployee(Scanner scanner, HRSW hrsw, Regulate regulate) {

        System.out.println("-----------3. 직원 검색-----------\n" +
                "본인 아이디 >> ");
        String id = scanner.next();
        String output;
        int checkInt = regulate.checkId(id);
        if (checkInt == 0) {
            System.out.println("존재하지 않는 아이디입니다.");
        } else {
            System.out.println("--------------------------------\n" +
                    "검색할 아이디 >> ");
            String owner_id = scanner.next();
            int checkint2 = regulate.checkId(owner_id);
            if (checkint2 == 0) {
                System.out.println("존재하지 않는 아이디입니다.");
            } else {
                output = hrsw.findData(owner_id);
                System.out.println("\n" + output);
                hrsw.saveToLog("직원 검색 : 직원아이디 =  " + id + " 가 직원아이디 " + owner_id +  "의 data 검색함.");
            }

        }
    }


    // 4. 부서 이동 method
    public static void moveDepartment(Scanner scanner, HRSW hrsw, Regulate regulate) {
        boolean run2 = true;
        while (run2) {
            System.out.println("-------2. 기존 직원 삭제 및 수정-------\n" +
                    "본인 ID >> ");
            String id = scanner.next();
            int checkInt = regulate.checkId(id);
            if (checkInt == 0) {
                // 입력받은 아이디가 존재하지 않을 때

                System.out.println("존재하지 않는 아이디입니다.");
            } else {
                // 입력받은 아이디가 존재할 때

                String[] managerArr = regulate.formEachArr("manager");
                String[] idArr = regulate.formEachArr("id");
                String[] departArr = regulate.formEachArr("depart");


                int index = -1;

                for (int i = 0; i < idArr.length; i++) {
                    if (idArr[i].equals(id)) {
                        index = i;
                    }
                }

                int length = 0;
                for (int j = 0; j < departArr.length; j++) {
                    if (departArr[j].equals(departArr[index])) {
                        length++;
                    }
                }
                int[] indexArr = new int[length];
                int indexA = 0;
                for (int j = 0; j < departArr.length; j++) {
                    if (departArr[j].equals(departArr[index])) {
                        indexArr[indexA] = j;
                        indexA++;
                    }
                }

                int m = 0;
                for (int j = 0; j < indexArr.length; j++) {
                    if (managerArr[indexArr[j]].equals("O")) {
                        m++;
                    }
                }



                boolean continue1;
                if (m > 0) {
                    if (!regulate.checkManager(id)) {

                        // 입력 받은 id가 manager 권한이 없을때
                        System.out.println("manager 만 권한이 있습니다.");
                        run2 = false;
                        continue1 = false;
                    } else {
                        continue1 = true;
                    }
                } else {
                    continue1 = true;
                }


                // id로 직원 정보 찾아서 manager 여부 파악 혹은 부서에 manager 가 없는 경우 확인
                if (!continue1) {

                    // 입력 받은 id가 manager 권한이 없을때
                    System.out.println("manager 만 권한이 있습니다.");
                    run2 = false;
                } else {
                    boolean run3 = true;
                    while (run3) {
                        // 이동할 직원 아이디 입력
                        System.out.println("--------------4. 부서이동-------------\n" +
                                "이동할 직원 아이디 >> ");
                        String inputId = scanner.next();

                        // 이동할 직원아이디 검색
                        int checkNumber = regulate.checkId(inputId);


                        // 삭제할 직원아이디가 해당 매니저와 같은 부서인지 확인

                        int indexManager = -1;
                        int indexEmployee = -1;
                        for (int i = 0; i < idArr.length; i++) {
                            if (idArr[i].equals(id)) {
                                indexManager = i;
                            }
                            if (idArr[i].equals(inputId)) {
                                indexEmployee = i;
                            }
                        }



                        if (checkNumber == 0) {
                            System.out.println("해당하는 직원이 없습니다.");
                        } else {
                            // 검색 결과가 있는 경우

                            // 같은 부서의 직원 정보를 변경하는 건지 확인
                            if (departArr[indexManager].equals(departArr[indexEmployee])) {
                                System.out.println("--------------4. 부서이동-------------\n" +
                                        "※ 부서 이름 : 비서실 총무팀 인사팀 개발1팀 개발2팀 개발3팀\n" +
                                        " 이동할 부서 이름 >> ");
                                String new_str = scanner.next();
                                String[] checkArr = regulate.checkDepart(new_str);
                                if (checkArr[0] == "false") {
                                    System.out.println(checkArr[1]);
                                } else if (checkArr[0] == "true") {
                                    if (checkArr[1].equals("비서실") || checkArr[1].equals("총무팀") || checkArr[1].equals("인사팀")){
                                        hrsw.updateFile(inputId, "depart", checkArr[1]);
                                        hrsw.updateFile(inputId, "position", "Staff");
                                        hrsw.saveToLog("부서 이동 : 직원아이디 =  " + inputId + "이 부서를 " + checkArr[1] + "로 변경");
                                    } else {
                                        hrsw.updateFile(inputId, "depart", checkArr[1]);
                                        hrsw.updateFile(inputId, "position", "SW Tester");
                                        hrsw.saveToLog("부서 이동 : 직원아이디 =  " + inputId + "이 부서를 " + checkArr[1] + "로 변경");

                                    }
                                }
                                run3 = false;
                            } else {
                                System.out.println("다른 부서의 직원 정보를 변경할 수 없습니다.");
                            }
                        }


                    }
                    run2 = false;
                }
            }
        }
    }


    // 5. 히스토리 기능 method
    public static void History(Scanner scanner, HRSW hrsw, Regulate regulate) {
        String output = hrsw.loadLog();
        System.out.println("--------------5. 히스토리-------------\n" +
                output);

    }

}
