package krolikowski.pg.pt.lab1_files_list;


import java.io.File;
import java.util.Comparator;

public class Main {

    public static void main(String[] args) {
        if (args.length == 0){
            System.err.println("You have to specify the path");
            printUsage();
            System.exit(1);
        }

        String path = args[0];
        DiskDirectory diskDirectory = null;
        try {
            diskDirectory = new DiskDirectory(new File(path));
        } catch (NullPointerException e) {
            System.out.println("File doesn't exists or is not a directory");
            System.exit(2);
        }

        if(args.length == 1 || args[1].equals("-n"))
            diskDirectory.print();
        else if(args[1].equals("-s")) {
            Comparator<DiskElement> sizeComparator = (x, y) -> (int) (x.getSize() - y.getSize());
            diskDirectory.print(sizeComparator);
        }else{
            System.err.println("Invalid sorting specifier: " + args[1]);
            printUsage();
            System.exit(3);
        }
    }

    private static void printUsage(){
        System.out.println("Usage:");
        System.out.println("FilesList [path/to/directory] [sortFlag]");
        System.out.println("Available sortFlags:");
        System.out.println("* -s -> Sort by size");
        System.out.println("* -n -> sort by name");
    }
}
