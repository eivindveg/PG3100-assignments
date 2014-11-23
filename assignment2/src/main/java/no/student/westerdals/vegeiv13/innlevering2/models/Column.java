package no.student.westerdals.vegeiv13.innlevering2.models;

public class Column implements Comparable<Column> {

    private final String name;
    private final DataType dataType;
    private final Integer width;
    private Integer index;
    private boolean primaryKey;
    public Column(final DataType dataType, final int index, final int width, final String name) {
        this.index = index;
        this.dataType = dataType;
        this.width = width;
        this.name = name;
    }

    public DataType getDataType() {
        return dataType;
    }

    @Override
    public String toString() {
        return "Column{" +
                "name='" + name + '\'' +
                ", index=" + index +
                ", dataType=" + dataType +
                ", width=" + width +
                '}';
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final Column column = (Column) o;

        return primaryKey == column.primaryKey && dataType == column.dataType && !(index != null ? !index.equals(column.index) : column.index != null) && !(name != null ? !name.equals(column.name) : column.name != null);

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (index != null ? index.hashCode() : 0);
        result = 31 * result + (primaryKey ? 1 : 0);
        result = 31 * result + (dataType != null ? dataType.hashCode() : 0);
        result = 31 * result + (width != null ? width.hashCode() : 0);
        return result;
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
    public int compareTo(Column o) {
        if (o == this || o.equals(this)) {
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
