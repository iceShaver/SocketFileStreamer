package krolikowski.pg.pt.lab1_files_list;


import java.io.File;
import java.util.Comparator;

public class Main {

    public static void main(String[] args) {
        Comparator<DiskElement> sizeComparator = (x,y)->(int) (x.getSize() - y.getSize());
        try {
            DiskDirectory diskDirectory = new DiskDirectory(new File("/home/kamil/Code/"));
            diskDirectory.print(sizeComparator);
        } catch (NullPointerException e) {
            System.out.println("File doesn't exists or is not a directory");
        }
    }
}
