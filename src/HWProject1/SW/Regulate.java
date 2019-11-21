package HWProject1.SW;


public class Regulate {

    HRSW hrsw = new HRSW();

    public int checkId(String inputId) {
        String[] idArr = formEachArr("id");
        int checkInt = 0;
        for (int j = 0; j < idArr.length; j++) {
            if (idArr[j].equals(inputId)) {
                checkInt += 1;
            }
        }
        return checkInt;
        // id 중복되는거 찾아주는 method
    }

    public String[] checkDepart(String inputDepart) {
        String[] output = new String[2];
        if (inputDepart.equals("비서실") || inputDepart.equals("총무팀") || inputDepart.equals("인사팀") || inputDepart.equals("개발1팀") || inputDepart.equals("개발2팀") || inputDepart.equals("개발3팀")) {
            output[0] = "true";
            output[1] = inputDepart;
            return output;
        } else {
            output[0] = "false";
            output[1] = "SW 부서에는 " + inputDepart + "이/가 없습니다.";;
            return output;
        }
        // 해당 부서 이외의 다른 depart 이름이 입력되는 경우 방지
    }

    public String[] formEachArr(String key) {
        String str = hrsw.loadFile();
        String[] strArr = str.split("\n");
        String[] memberArr;
        String[] newArr = new String[strArr.length];

        if (key == "id") {
            String id;
            for (int i = 0; i < strArr.length; i++) {
                memberArr = strArr[i].split("/");
                id = memberArr[0];
                newArr[i] = id;
            }
        } else if ( key == "name") {
            String name;
            for (int i = 0; i < strArr.length; i++) {
                memberArr = strArr[i].split("/");
                name = memberArr[1];
                newArr[i] = name;
            }
        } else if ( key == "depart") {
            String depart;
            for (int i = 0; i < strArr.length; i++) {
                memberArr = strArr[i].split("/");
                depart = memberArr[2];
                newArr[i] = depart;
            }
        } else if ( key == "position") {
            String position;
            for (int i = 0; i < strArr.length; i++) {
                memberArr = strArr[i].split("/");
                position = memberArr[3];
                newArr[i] = position;
            }
        } else if ( key == "manager") {
            String manager;
            for (int i = 0; i < strArr.length; i++) {
                memberArr = strArr[i].split("/");
                manager = memberArr[4];
                newArr[i] = manager;
            }
        }
        return newArr;
    }

    public boolean checkManager (String id) {
        int idIndex = 0;
        String[] idArr = formEachArr("id");
        for (int i = 0; i < idArr.length; i++) {
            if (idArr[i].equals(id)) {
                idIndex = i;
            }
        }
        String[] managerArr = formEachArr("manager");
        boolean returnBoolean = false;

        if (managerArr[idIndex].equals("O")) {
            returnBoolean = true;
        }

        return returnBoolean;
    }

}
