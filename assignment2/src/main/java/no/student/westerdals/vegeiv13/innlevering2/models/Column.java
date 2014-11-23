package no.student.westerdals.vegeiv13.innlevering2.models;

import com.sun.istack.internal.NotNull;

public class Column implements Comparable<Column> {

    private final String name;
    private Integer index;
    private boolean primaryKey;

    public DataType getDataType() {
        return dataType;
    }

    private final DataType dataType;
    private final Integer width;

    @Override
    public String toString() {
        return "Column{" +
                "name='" + name + '\'' +
                ", index=" + index +
                ", dataType=" + dataType +
                ", width=" + width +
                '}';
    }

    public Column(final DataType dataType, final int index, final int width, final String name) {
        this.index = index;
        this.dataType = dataType;
        this.width = width;
        this.name = name;
    }

    public int getWidth() {
        return width;
    }

    public String getName() {
        return name;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(final int index) {
        this.index = index;
    }

    @Override
    public int compareTo(@NotNull Column o) {
        if(o == this || o.equals(this)) {
            return 0;
        }
        return this.index - o.index;
    }

    public boolean isPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(final boolean primaryKey) {
        this.primaryKey = primaryKey;
    }
}
