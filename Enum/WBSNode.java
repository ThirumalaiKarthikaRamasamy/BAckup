package com.ge.seawolf.planning.template.domain;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

/*
 Container of WBSElement for tree structure
 */

@Entity
@Table(name = "node")
public class WBSNode {

    public Long getNodeId() {
        return id;
    }

    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private WBSNode parent;

    @OneToMany(mappedBy = "parent", orphanRemoval = true, cascade = CascadeType.REMOVE)
    private List<WBSNode> children;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "element_id")
    private WBSElement element;

    @Column(name = "breakdown")
    private String breakdownId;
    
    @Transient
    private Long treeId;

    public WBSNode(WBSElement element, WBSNode parent) {
        this.element = element;
        this.parent = parent;
        children = new LinkedList<WBSNode>();
    }

    public WBSNode(WBSElement element) {
        this(element, null);
    }

    public WBSNode() {
    }

    // Walk the subtree and add the nodes to a list
    private void walk(WBSNode node, List<WBSNode> list) {
        list.add(node);
        for (WBSNode eachNode : node.getChildren()) {
            walk(eachNode, list);
        }
    }

    public List<WBSNode> toList() {
        List<WBSNode> list = new LinkedList<WBSNode>();
        walk(this, list);
        return list;
    }

    public boolean hasParent() {
        if (parent == null) {
            return false;
        }
        return true;
    }

    public boolean isLeaf() {
        if (children == null || children.isEmpty()) {
            return true;
        }
        return false;
    }

    public int getLevel() {
        if (!hasParent()) {
            return 1;
        }
        return parent.getLevel() + 1;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public WBSElement getElement() {
        return element;
    }

    public WBSNode getParent() {
        return parent;
    }

    public void setParent(WBSNode parent) {
        this.parent = parent;
    }

    public List<WBSNode> getChildren() {
        return new LinkedList<WBSNode>(children);
    }

    public String getBreakdownId() {
        return breakdownId;
    }

    public void setBreakdownId(String breakdownId) {
        this.breakdownId = breakdownId;
    }

    public boolean acceptsChildren() {
        return element.acceptsChildren();
    }
    
    public Long getTreeId() {
        return treeId;
    }

    public void setTreeId(Long treeId) {
        this.treeId = treeId;
    }


    @Override
    public String toString() {
        return "WBSNode [id=" + id + ", element=" + element + "]";
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = PRIME * result + ((element == null) ? 0 : element.hashCode());
        result = PRIME * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        WBSNode other = (WBSNode) obj;
        if (element == null) {
            if (other.element != null)
                return false;
        } else if (!element.equals(other.element))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
}
