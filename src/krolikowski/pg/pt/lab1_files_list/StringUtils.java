package krolikowski.pg.pt.lab1_files_list;

public class StringUtils {
    public static String repeat(String str, int times){
        return new String(new char[times]).replace("\0", str);
    }
}
