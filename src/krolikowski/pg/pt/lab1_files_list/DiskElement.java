package krolikowski.pg.pt.lab1_files_list;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Comparator;

public abstract class DiskElement implements Comparable<DiskElement> {

    protected final File file;


    public DiskElement(File file) {
        this.file = file;
    }

    public final void print(Comparator<DiskElement> comparator) {
        printHeader();
        print(0, comparator);
    }

    public void print() {
        printHeader();
        print(0);
    }


    protected abstract void print(int level, Comparator<DiskElement> comparator);

    protected void print(int level) {
        char symbol = this instanceof DiskFile ? '|' : '\\';
        int distance = 60 - level - file.getName().length();
        distance = (distance <= 0) ? 5 : distance;
        System.out.format("%s%c %s%s%s\t%s\n",
                StringUtils.repeat(" ", level),
                symbol,
                file.getName(),
                StringUtils.repeat(".", distance),
                new SimpleDateFormat("yyyy-MM-dd").format(file.lastModified()),
                getSizeString());
    }

    private void printHeader() {
        System.out.println("Name" + StringUtils.repeat(" ", 57) + "Last mod date " + " Size [Elements/Bytes]");
        System.out.println(StringUtils.repeat("-", 100));
    }


    protected abstract String getSizeString();

    protected abstract long getSize();


    @Override
    public int compareTo(DiskElement other) { // compare by name
        return file.getName().compareToIgnoreCase(other.file.getName());
    }
}
