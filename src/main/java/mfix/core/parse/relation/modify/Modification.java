package mfix.core.parse.relation.modify;

import mfix.core.parse.relation.Relation;

import java.util.HashSet;
import java.util.Set;

public abstract class Modification {

    private Set<Relation> _relatedRelations;

    protected Modification() {
        _relatedRelations = new HashSet<>();
    }

    public void addRelatedRelations(Relation r) {
        _relatedRelations.add(r);
    }

    public Set<Relation> getRelatedRelations() {
        return _relatedRelations;
    }

}
