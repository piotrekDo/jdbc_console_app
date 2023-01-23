package app.model;

public class Sort {
    private String SortBy;
    private boolean isDescending;

    public Sort(String sortBy, boolean isDescending) {
        SortBy = sortBy;
        this.isDescending = isDescending;
    }

    public String getSortBy() {
        return SortBy;
    }

    public void setSortBy(String sortBy) {
        SortBy = sortBy;
    }

    public boolean isDescending() {
        return isDescending;
    }

    public void setDescending(boolean descending) {
        isDescending = descending;
    }
}
