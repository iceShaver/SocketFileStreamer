package krolikowski.pg.pt.lab1_files_list;

import java.io.File;
import java.util.Comparator;

public final class DiskFile extends DiskElement {

    public DiskFile(File file) {
        super(file);
    }


    @Override
    protected final void print(int level, Comparator<DiskElement> comparator) {
        print(level);
    }

    @Override
    protected final String getSizeString() {
        return String.format("%-6d B", getSize());
    }

    @Override
    protected long getSize() {
        return file.length();
    }
}