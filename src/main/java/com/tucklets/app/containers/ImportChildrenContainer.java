package com.tucklets.app.containers;

import com.tucklets.app.containers.enums.ImportStatus;
import com.tucklets.app.entities.Child;

import java.util.List;

public class ImportChildrenContainer {

    /**
     * List of children that were added.
     */
    private List<Child> children;

    /**
     * Number of children that were updated.
     */
    private int numChildrenUpdated;

    /**
     * Number of new children that were imported.
     */
    private int numChildrenAdded;

    /**
     * ImportStatus indicating the status of the import.
     */
    ImportStatus importStatus;

    public ImportChildrenContainer(List<Child> children, int numChildrenUpdated, int numChildrenAdded, ImportStatus importStatus) {
        this.children = children;
        this.numChildrenUpdated = numChildrenUpdated;
        this.numChildrenAdded = numChildrenAdded;
        this.importStatus = importStatus;
    }

    public List<Child> getChildren() {
        return children;
    }

    public void setChildren(List<Child> children) {
        this.children = children;
    }

    public int getNumChildrenUpdated() {
        return numChildrenUpdated;
    }

    public void setNumChildrenUpdated(int numChildrenUpdated) {
        this.numChildrenUpdated = numChildrenUpdated;
    }

    public int getNumChildrenAdded() {
        return numChildrenAdded;
    }

    public void setNumChildrenAdded(int numChildrenAdded) {
        this.numChildrenAdded = numChildrenAdded;
    }

    public ImportStatus getImportStatus() {
        return importStatus;
    }

    public void setImportStatus(ImportStatus importStatus) {
        this.importStatus = importStatus;
    }
}
