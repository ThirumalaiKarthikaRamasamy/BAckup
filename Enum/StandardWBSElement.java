package com.ge.seawolf.planning.template.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("StandardWBSElement")
public class StandardWBSElement extends WBSElement {

    public StandardWBSElement() {
    }

    @Override
    public boolean acceptsChildren() {
        return true;
    }
}
