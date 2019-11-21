package HWProject1.SW;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HRSW {
    private String readFile (File SW_file) {
        String output = "";
        try {
            FileReader SW_reader = new FileReader(SW_file);
            BufferedReader bufferedReader = new BufferedReader(SW_reader);

            String line = "";

            while ((line = bufferedReader.readLine()) != null) {
                output += line + "\n";
            }
            bufferedReader.close();
            return output;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            output += String.valueOf(e);
        }
        return output;
    }

    public String loadFile () {
        File SW_file = new File("C:\\Users\\최연우\\IdeaProjects\\UML_practice\\src\\HWProject1\\SW_employee.txt");
        String output = readFile(SW_file);
        return output;
        // 프로그램 시작시 호출되는 method : FileReader BufferedReader 이용
    }

    public String loadLog() {
        File SW_file = new File("C:\\Users\\최연우\\IdeaProjects\\UML_practice\\src\\HWProject1\\SW_Log.txt");
        String output = readFile(SW_file);
        return output;
        // Log.txt 파일 내용 한 라인씩 불러와 String 으로 만들어 리턴
    }


    public boolean saveToFile(String input_str) {
        File SW_file = new File("C:\\Users\\최연우\\IdeaProjects\\UML_practice\\src\\HWProject1\\SW_employee.txt");
        boolean success;

        try {
            FileWriter f_writer = new FileWriter(SW_file, true);
            BufferedWriter SW_writer = new BufferedWriter(f_writer);

            if (SW_file.isFile() && SW_file.canWrite()) {
                SW_writer.write(""+input_str);
                SW_writer.close();
                success = true;
            } else {
                success = false;
            }


        } catch (FileNotFoundException e) {
            success = false;
        } catch (IOException e) {
            success = false;
        }
        return success;
        // BufferWriter FileWriter 로 SW_employee.txt 에 저장하는 method
    }

    public boolean saveToLog(String input_str) {
        File SW_file = new File("C:\\Users\\최연우\\IdeaProjects\\UML_practice\\src\\HWProject1\\SW_Log.txt");
        boolean success;
        try {
            FileWriter f_writer = new FileWriter(SW_file, true);
            BufferedWriter SW_writer = new BufferedWriter(f_writer);

            if (SW_file.isFile() && SW_file.canWrite()) {

                Date today = new Date();
                SimpleDateFormat format;
                format  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String nowTime = format.format(today);

                String save_str = nowTime + " " + input_str;
                SW_writer.write("\n" + save_str);
                success = true;
                SW_writer.close();
            } else {
                success = false;
            }
        } catch (FileNotFoundException e) {
            success = false;
        } catch (IOException e){
            success = false;
        }
        return success;
        // 명령어들 입력될 때 로그를 기록하기 위해 실행되는 BufferedWrite 클래스와 시간을 출력하기 위한 SimpleDateFormat 클래스를 사용하여 인자로 전달된 String 값 => SW_Log.txt 에 String 추가
    }



    public String findData (String owner_id) {
        String output = "";
        try {
            FileReader SW_reader = new FileReader("C:\\Users\\최연우\\IdeaProjects\\UML_practice\\src\\HWProject1\\SW_employee.txt");
            BufferedReader bufferedReader = new BufferedReader(SW_reader);

            String line = "";

            while ((line = bufferedReader.readLine()) != null) {
                if (line.contains(owner_id)) {
                    output += line + "\n";
                }
            }
            bufferedReader.close();
            return output;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            output += String.valueOf(e);
        }
        return output;
    }



    public boolean updateFile (String owner_id, String change_type, String new_str) {
        boolean success = false;
        String fileName = "C:\\Users\\최연우\\IdeaProjects\\UML_practice\\src\\HWProject1\\SW_employee.txt";
        String newFileName = "C:\\Users\\최연우\\IdeaProjects\\UML_practice\\src\\HWProject1\\newFile.txt";
        File pseudoFile = new File(newFileName);

        try {
            if (pseudoFile.createNewFile()) {
                success = true;
            }

        } catch (IOException e) {
            success = false;
        } catch (Exception e) {
            success = false;
        }


        if (success) {
            BufferedReader br = null;
            BufferedWriter bw = null;

            try {
                Regulate regulate = new Regulate();
                int index = -1;
                String[] idArr = regulate.formEachArr("id");
                for (int i = 0; i < idArr.length; i++) {
                    if (idArr[i].equals(owner_id)) {
                        index = i;
                    }
                }

                String old_str = null;
                if (index >= 0) {
                    if (change_type == "name") {
                        String[] nameArr = regulate.formEachArr("name");
                        old_str = nameArr[index];
                    } else if (change_type == "depart") {
                        String[] departArr = regulate.formEachArr("depart");
                        old_str = departArr[index];
                    } else if (change_type == "manager") {
                        String[] managerArr = regulate.formEachArr("manager");
                        old_str = managerArr[index];
                        if (old_str.equals("X")) {
                            new_str = "O";
                        }
                        if (old_str.equals("O")){
                            new_str = "X";
                        }
                    } else if (change_type == "position") {
                        String[] positionArr = regulate.formEachArr("position");
                        old_str = positionArr[index];

                        String[] departArr = regulate.formEachArr("depart");
                        String oldDepart = departArr[index];
                        if (oldDepart.equals("비서실") || oldDepart.equals("총무팀") || oldDepart.equals("인사팀")) {
                            new_str = old_str;
                        } else {
                            if (old_str.equals("SW Tester")) {
                                new_str = "SW Developer";
                            } else if (old_str.equals("SW Developer")) {
                                new_str = "SW Tester";
                            } else {
                                new_str = "SW Tester";
                            }
                        }
                    }
                }


                br = new BufferedReader(new FileReader(fileName));
                bw = new BufferedWriter(new FileWriter(newFileName));
                String line;
                while ((line = br.readLine()) != null) {
                    if (line.contains(owner_id)) {
                        line = line.replace(old_str, new_str);
                    }
                    bw.write(line + "\n");
                }
                success = true;
            } catch (Exception e) {
                success = false;
            } finally {
                try {
                    if (br != null) {
                        br.close();
                    }
                } catch (IOException e) {
                    success = false;
                }
                try {
                    if (bw != null) {
                        bw.close();
                    }
                } catch (IOException e) {
                    success = false;
                }
            }
        }

        File oldFile = new File(fileName);
        File newFile = new File(newFileName);

        oldFile.delete();
        newFile.renameTo(oldFile);
        return success;
    }



    public boolean removeLine(String old_str) {
        boolean success = false;
        String fileName = "C:\\Users\\최연우\\IdeaProjects\\UML_practice\\src\\HWProject1\\SW_employee.txt";
        String newFileName = "C:\\Users\\최연우\\IdeaProjects\\UML_practice\\src\\HWProject1\\newFile.txt";
        File pseudoFile = new File(newFileName);

        try {
            if (pseudoFile.createNewFile()) {
                success = true;
            }

        } catch (IOException e) {
            success = false;
        } catch (Exception e) {
            success = false;
        }


        if (success) {
            BufferedReader br = null;
            BufferedWriter bw = null;

            try {
                br = new BufferedReader(new FileReader(fileName));
                bw = new BufferedWriter(new FileWriter(newFileName));
                String line;
                while ((line = br.readLine()) != null) {
                    if (line.contains(old_str)) {
                        line = line.replace(line, "");
                        bw.write(line);
                    } else {
                        bw.write(line + "\n");
                    }
                }
                success = true;
            } catch (Exception e) {
                success = false;
            } finally {
                try {
                    if (br != null) {
                        br.close();
                    }
                } catch (IOException e) {
                    success = false;
                }
                try {
                    if (bw != null) {
                        bw.close();
                    }
                } catch (IOException e) {
                    success = false;
                }
            }
        }

        File oldFile = new File(fileName);
        File newFile = new File(newFileName);

        oldFile.delete();
        newFile.renameTo(oldFile);
        return success;
    }

}
